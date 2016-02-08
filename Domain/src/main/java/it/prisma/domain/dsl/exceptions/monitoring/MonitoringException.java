package it.prisma.domain.dsl.exceptions.monitoring;

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