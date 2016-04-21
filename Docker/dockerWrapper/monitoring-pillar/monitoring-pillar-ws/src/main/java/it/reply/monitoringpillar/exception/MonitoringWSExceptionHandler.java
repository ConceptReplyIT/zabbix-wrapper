package it.reply.monitoringpillar.exception;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.reply.monitoringpillar.config.Configuration;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.protocol.MonitoringResponse;
import it.reply.monitoringpillar.domain.exception.DuplicateResourceMonitoringException;
import it.reply.monitoringpillar.domain.exception.IllegalArgumentMonitoringException;
import it.reply.monitoringpillar.domain.exception.MonitoringException;
import it.reply.monitoringpillar.domain.exception.NotFoundMonitoringException;
import it.reply.monitoringpillar.domain.exception.NotImplementedMonitoringException;
import it.reply.utils.misc.StackTrace;

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
		if (config.getMonitoringConfigurations().getOptions().isWsDebug()) {
			verbose = StackTrace.getStackTraceToString(e);
		}

		if (e instanceof NotImplementedMonitoringException) {
			return MonitoringResponse.status(Status.NOT_FOUND, e.getMessage(), verbose).build().build();
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
