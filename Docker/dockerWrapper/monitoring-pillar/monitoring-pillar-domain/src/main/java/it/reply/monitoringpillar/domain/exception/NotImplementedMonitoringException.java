package it.reply.monitoringpillar.domain.exception;

public class NotImplementedMonitoringException extends MonitoringException {

    private static final long serialVersionUID = 4377449055260705550L;

    public NotImplementedMonitoringException(String message) {
	super(message);
    }

    public NotImplementedMonitoringException(Throwable e) {
	super(e);
    }

    public NotImplementedMonitoringException(String message, Throwable e) {
	super(message, e);
    }
}