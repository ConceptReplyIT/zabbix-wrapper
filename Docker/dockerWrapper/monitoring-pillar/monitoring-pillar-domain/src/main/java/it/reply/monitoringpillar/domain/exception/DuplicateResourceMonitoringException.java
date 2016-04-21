package it.reply.monitoringpillar.domain.exception;

public class DuplicateResourceMonitoringException extends MonitoringException {

    private static final long serialVersionUID = 4377449055260705550L;

    public DuplicateResourceMonitoringException(String message) {
	super(message);
    }

    public DuplicateResourceMonitoringException(Throwable e) {
	super(e);
    }

    public DuplicateResourceMonitoringException(String message, Throwable e) {
	super(message, e);
    }
}