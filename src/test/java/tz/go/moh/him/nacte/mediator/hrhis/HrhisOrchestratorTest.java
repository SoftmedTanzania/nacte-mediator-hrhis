package tz.go.moh.him.nacte.mediator.hrhis;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.openhim.mediator.engine.messages.FinishRequest;
import org.openhim.mediator.engine.messages.MediatorHTTPRequest;
import org.openhim.mediator.engine.testing.MockLauncher;
import org.openhim.mediator.engine.testing.TestingUtils;
import tz.go.moh.him.nacte.mediator.hrhis.mock.MockDestination;
import tz.go.moh.him.nacte.mediator.hrhis.orchestration.NacteOrchestrator;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Contains tests for the {@link tz.go.moh.him.nacte.mediator.hrhis.orchestration.NacteOrchestrator} class.
 */
public class HrhisOrchestratorTest extends BaseOrchestratorTest {


    @Override
    public void before() {
        List<MockLauncher.ActorToLaunch> actorsToLaunch = new LinkedList<>();

        actorsToLaunch.add(new MockLauncher.ActorToLaunch("http-connector", MockDestination.class));

        TestingUtils.launchActors(system, configuration.getName(), actorsToLaunch);
    }

    /**
     * Tests the mediator.
     *
     * @throws Exception if an exception occurs.
     */
    @Test
    public void testHfrRequest() throws Exception {
        new JavaTestKit(system) {{
            final ActorRef orchestrator = system.actorOf(Props.create(NacteOrchestrator.class, configuration));

            InputStream stream = HrhisOrchestratorTest.class.getClassLoader().getResourceAsStream("request.json");

            Assert.assertNotNull(stream);

            MediatorHTTPRequest request = new MediatorHTTPRequest(
                    getRef(),
                    getRef(),
                    "unit-test",
                    "POST",
                    "http",
                    null,
                    null,
                    "/hfr",
                    IOUtils.toString(stream),
                    Collections.singletonMap("Content-Type", "application/json"),
                    Collections.emptyList()
            );

            orchestrator.tell(request, getRef());

            final Object[] out = new ReceiveWhile<Object>(Object.class, duration("3 seconds")) {
                @Override
                protected Object match(Object msg) {
                    if (msg instanceof FinishRequest) {
                        return msg;
                    }
                    throw noMatch();
                }
            }.get();

            Assert.assertTrue(Arrays.stream(out).anyMatch(c -> c instanceof FinishRequest));
        }};
    }
}