package it.reply.monitoringpillar.domain.exception;

public class NotFoundMonitoringException extends MonitoringException {

    private static final long serialVersionUID = 4377449055260705550L;

    public NotFoundMonitoringException(String message) {
	super(message);
    }

    public NotFoundMonitoringException(Throwable e) {
	super(e);
    }

    public NotFoundMonitoringException(String message, Throwable e) {
	super(message, e);
    }
}