package it.reply.monitoringpillar.domain.exception;

/**
 * 
 * Basic Monitoring exception
 * 
 */
public class MonitoringException extends RuntimeException {

	private static final long serialVersionUID = 2834951083585038374L;

	public MonitoringException(String message) {
		super(message);
	}

	public MonitoringException(Throwable e) {
		super(e);
	}

	public MonitoringException(String message, Throwable e) {
		super(message, e);
	}
}