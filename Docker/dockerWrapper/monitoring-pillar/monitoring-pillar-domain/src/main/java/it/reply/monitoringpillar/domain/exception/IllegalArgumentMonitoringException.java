package it.reply.monitoringpillar.domain.exception;

public class IllegalArgumentMonitoringException extends MonitoringException {

	private static final long serialVersionUID = 4377449055260705550L;

	public IllegalArgumentMonitoringException(String message) {
		super(message);
	}

	public IllegalArgumentMonitoringException(Throwable e) {
		super(e);
	}

	public IllegalArgumentMonitoringException(String message, Throwable e) {
		super(message, e);
	}
}