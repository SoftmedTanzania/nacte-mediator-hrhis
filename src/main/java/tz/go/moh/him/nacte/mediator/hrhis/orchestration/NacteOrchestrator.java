package tz.go.moh.him.nacte.mediator.hrhis.orchestration;

import akka.actor.ActorSelection;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.openhim.mediator.engine.MediatorConfig;
import org.openhim.mediator.engine.messages.FinishRequest;
import org.openhim.mediator.engine.messages.MediatorHTTPRequest;
import org.openhim.mediator.engine.messages.MediatorHTTPResponse;
import tz.go.moh.him.mediator.core.domain.ErrorMessage;
import tz.go.moh.him.mediator.core.domain.ResultDetail;
import tz.go.moh.him.nacte.mediator.hrhis.domain.HrhisRequest;
import tz.go.moh.him.nacte.mediator.hrhis.gsonTypeAdapter.AttributeSummaryTypeDeserializer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NacteOrchestrator extends UntypedActor {
    /**
     * The logger instance.
     */
    protected final LoggingAdapter log = Logging.getLogger(getContext().system(), this);

    /**
     * The mediator configuration.
     */
    protected final MediatorConfig config;

    /**
     * Represents a mediator request.
     */
    protected MediatorHTTPRequest workingRequest;

    /**
     * Initializes a new instance of the {@link NacteOrchestrator} class.
     *
     * @param config The mediator configuration.
     */
    public NacteOrchestrator(MediatorConfig config) {
        this.config = config;
    }

    /**
     * Handles the received message.
     *
     * @param msg The received message.
     */
    @Override
    public void onReceive(Object msg) {
        if (msg instanceof MediatorHTTPRequest) {

            workingRequest = (MediatorHTTPRequest) msg;

            log.info("Received request: " + workingRequest.getHost() + " " + workingRequest.getMethod() + " " + workingRequest.getPath());

            Map<String, String> headers = new HashMap<>();

            headers.put(HttpHeaders.CONTENT_TYPE, "application/json");

            List<Pair<String, String>> parameters = new ArrayList<>();

            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(HrhisRequest.SummaryType.class, new AttributeSummaryTypeDeserializer());
            gsonBuilder.registerTypeAdapter(HrhisRequest.SummaryType.class, new AttributeSummaryTypeDeserializer());
            Gson gson = gsonBuilder.create();

            HrhisRequest hfrRequest = gson.fromJson(workingRequest.getBody(), HrhisRequest.class);

            String host;
            int port;
            String path;
            String scheme;
            String authenticationToken;

            if (config.getDynamicConfig().isEmpty()) {
                log.debug("Dynamic config is empty, using config from mediator.properties");

                host = config.getProperty("destination.host");
                port = Integer.parseInt(config.getProperty("destination.port"));
                path = config.getProperty("destination.path");
                scheme = config.getProperty("destination.scheme");
                authenticationToken = config.getProperty("destination.authenticationToken");
            } else {
                log.debug("Using dynamic config");

                JSONObject destinationProperties = new JSONObject(config.getDynamicConfig()).getJSONObject("destinationConnectionProperties");

                host = destinationProperties.getString("destinationHost");
                port = destinationProperties.getInt("destinationPort");
                path = destinationProperties.getString("destinationPath");
                scheme = destinationProperties.getString("destinationScheme");
                authenticationToken = destinationProperties.getString("destinationAuthenticationToken");
            }

            if (hfrRequest.getEndpoint().equals("graduateshealth") || hfrRequest.getEndpoint().equals("enrollmenthealth"))
                host = scheme + "://" + host + ":" + port + path + "/" + hfrRequest.getEndpoint() + "/" + hfrRequest.getAcademicYear() + "-" + hfrRequest.getPageNumber() + "-" + hfrRequest.getPageSize() + "-" + hfrRequest.getSummary() + "/" + authenticationToken;
            else if (hfrRequest.getEndpoint().equals("institutionsdetailshas"))
                host = scheme + "://" + host + ":" + port + path + "/" + hfrRequest.getEndpoint() + "/" + authenticationToken;
            else if (hfrRequest.getEndpoint().equals("hasteachingstaff"))
                host = scheme + "://" + host + ":" + port + path + "/" + hfrRequest.getEndpoint() + "/" + hfrRequest.getInstitutionCode() + "/" + authenticationToken;
            else {
                ErrorMessage errorMessage = new ErrorMessage(workingRequest.getBody(), Collections.singletonList(new ResultDetail(ResultDetail.ResultsDetailsType.ERROR, "Unknown endpoint", null)));

                FinishRequest finishRequest = new FinishRequest(new Gson().toJson(errorMessage), "application/json", HttpStatus.SC_BAD_REQUEST);
                ((MediatorHTTPRequest) msg).getRequestHandler().tell(finishRequest, getSelf());
                return;
            }

            MediatorHTTPRequest request = new MediatorHTTPRequest(workingRequest.getRequestHandler(), getSelf(), "Sending Request", "GET",
                    host, null, headers, parameters);

            ActorSelection httpConnector = getContext().actorSelection(config.userPathFor("http-connector"));
            httpConnector.tell(request, getSelf());

        } else if (msg instanceof MediatorHTTPResponse) {
            workingRequest.getRequestHandler().tell(((MediatorHTTPResponse) msg).toFinishRequest(), getSelf());
        } else {
            unhandled(msg);
        }
    }
}
