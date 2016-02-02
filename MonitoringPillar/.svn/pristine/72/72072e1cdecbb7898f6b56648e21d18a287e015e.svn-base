package it.monitoringpillar.exception;

import it.monitoringpillar.config.Configuration;
import it.prisma.domain.dsl.monitoring.pillar.protocol.MonitoringResponse;
import it.prisma.utils.misc.StackTrace;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * This class handles all the {@link MonitoringException} and it maps the
 * exceptions to a proper HTTP response.< /br>
 * 
 * Exception stack trace is logged here.
 * 
 * @author m.bassi
 * 
 */
@Provider
public class MonitoringWSExceptionHandler implements ExceptionMapper<MonitoringException> {

    private static Logger LOG = LogManager.getLogger(MonitoringWSExceptionHandler.class);

    @Inject
    private Configuration config;

    @Override
    public Response toResponse(MonitoringException e) {

	// Logs all the stack trace
	LOG.catching(e);

	String verbose = null;
	if (config.isWebServiceDebugEnabled()) {
	    verbose = StackTrace.getStackTraceToString(e);
	}

	if (e instanceof NotImplementedMonitoringException) {
	    return MonitoringResponse.status(Status.NOT_IMPLEMENTED, e.getMessage(), verbose).build().build();
	}
	if (e instanceof IllegalArgumentMonitoringException) {
	    return MonitoringResponse.status(Status.BAD_REQUEST, e.getMessage(), verbose).build().build();
	}
	if (e instanceof IllegalArgumentMonitoringException) {
	    return MonitoringResponse.status(Status.BAD_REQUEST, e.getMessage(), verbose).build().build();
	}
	if (e instanceof DuplicateResourceMonitoringException) {
	    return MonitoringResponse.status(Status.CONFLICT, e.getMessage(), verbose).build().build();
	} else if (e instanceof NotFoundMonitoringException) {
	    return MonitoringResponse.status(Status.NOT_FOUND, e.getMessage(), verbose).build().build();
	}
	return MonitoringResponse.status(Status.INTERNAL_SERVER_ERROR, e.getMessage(), verbose).build().build();
    }

}
