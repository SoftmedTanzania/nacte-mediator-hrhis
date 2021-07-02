package tz.go.moh.him.nacte.mediator.hrhis;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.testkit.JavaTestKit;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;
import org.openhim.mediator.engine.messages.FinishRequest;
import org.openhim.mediator.engine.messages.MediatorHTTPRequest;
import tz.go.moh.him.nacte.mediator.hrhis.orchestration.NacteOrchestrator;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;

/**
 * Contains tests for the {@link tz.go.moh.him.nacte.mediator.hrhis.orchestration.NacteOrchestrator} class.
 */
public class HrhisOrchestratorTest extends BaseOrchestratorTest {

    /**
     * Tests sending enrollments and graduates request.
     *
     * @throws Exception if an exception occurs.
     */
    @Test
    public void testEnrollmentsRequest() throws Exception {
        new JavaTestKit(system) {{
            final ActorRef orchestrator = system.actorOf(Props.create(NacteOrchestrator.class, configuration));

            InputStream stream = HrhisOrchestratorTest.class.getClassLoader().getResourceAsStream("enrollments_request.json");

            Assert.assertNotNull(stream);

            MediatorHTTPRequest request = new MediatorHTTPRequest(
                    getRef(),
                    getRef(),
                    "unit-test",
                    "POST",
                    "http",
                    null,
                    null,
                    "/nacte",
                    IOUtils.toString(stream),
                    Collections.singletonMap("Content-Type", "application/json"),
                    Collections.emptyList()
            );

            orchestrator.tell(request, getRef());

            final Object[] out = new ReceiveWhile<Object>(Object.class, duration("5 seconds")) {
                @Override
                protected Object match(Object msg) {
                    if (msg instanceof FinishRequest) {
                        return msg;
                    }
                    throw noMatch();
                }
            }.get();

            Assert.assertTrue(Arrays.stream(out).anyMatch(c -> c instanceof FinishRequest));
            Assert.assertTrue(Arrays.stream(out).anyMatch(c -> ((FinishRequest) c).getResponseStatus() == HttpStatus.SC_OK));
        }};
    }

    /**
     * Tests sending institutions request.
     *
     * @throws Exception if an exception occurs.
     */
    @Test
    public void tesInstitutionsRequest() throws Exception {
        new JavaTestKit(system) {{
            final ActorRef orchestrator = system.actorOf(Props.create(NacteOrchestrator.class, configuration));

            InputStream stream = HrhisOrchestratorTest.class.getClassLoader().getResourceAsStream("institutions_request.json");

            Assert.assertNotNull(stream);

            MediatorHTTPRequest request = new MediatorHTTPRequest(
                    getRef(),
                    getRef(),
                    "unit-test",
                    "POST",
                    "http",
                    null,
                    null,
                    "/nacte",
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
            Assert.assertTrue(Arrays.stream(out).anyMatch(c -> ((FinishRequest) c).getResponseStatus() == HttpStatus.SC_OK));
        }};
    }


    /**
     * Tests sending teaching staffs request.
     *
     * @throws Exception if an exception occurs.
     */
    @Test
    public void tesTeachingStaffsRequest() throws Exception {
        new JavaTestKit(system) {{
            final ActorRef orchestrator = system.actorOf(Props.create(NacteOrchestrator.class, configuration));

            InputStream stream = HrhisOrchestratorTest.class.getClassLoader().getResourceAsStream("teaching_staffs_request.json");

            Assert.assertNotNull(stream);

            MediatorHTTPRequest request = new MediatorHTTPRequest(
                    getRef(),
                    getRef(),
                    "unit-test",
                    "POST",
                    "http",
                    null,
                    null,
                    "/nacte",
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
            Assert.assertTrue(Arrays.stream(out).anyMatch(c -> ((FinishRequest) c).getResponseStatus() == HttpStatus.SC_OK));
        }};
    }

    /**
     * Tests the mediator.
     *
     * @throws Exception if an exception occurs.
     */
    @Test
    public void testBadRequest() throws Exception {
        new JavaTestKit(system) {{
            final ActorRef orchestrator = system.actorOf(Props.create(NacteOrchestrator.class, configuration));

            InputStream stream = HrhisOrchestratorTest.class.getClassLoader().getResourceAsStream("bad_request.json");

            Assert.assertNotNull(stream);

            MediatorHTTPRequest request = new MediatorHTTPRequest(
                    getRef(),
                    getRef(),
                    "unit-test",
                    "POST",
                    "http",
                    null,
                    null,
                    "/nacte",
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
            Assert.assertTrue(Arrays.stream(out).anyMatch(c -> ((FinishRequest) c).getResponseStatus() == HttpStatus.SC_BAD_REQUEST));
        }};
    }
}