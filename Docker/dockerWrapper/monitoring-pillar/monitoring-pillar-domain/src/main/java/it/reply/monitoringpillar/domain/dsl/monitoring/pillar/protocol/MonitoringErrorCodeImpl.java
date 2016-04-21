package it.reply.monitoringpillar.domain.dsl.monitoring.pillar.protocol;

import it.reply.domain.dsl.prisma.ErrorCode;

public class MonitoringErrorCodeImpl  implements ErrorCode {

    private int code;
    private String name;
    private String description;
    private int httpStatusCode;
    
    public MonitoringErrorCodeImpl(int code, String name, String description, int httpStatusCode) {
	super();
	this.code = code;
	this.name = name;
	this.description = description;
	this.httpStatusCode = httpStatusCode;
    }

    @Override
    public String getDescription() {
	return this.description;
    }

    @Override
    public int getCode() {
	return this.code;
    }

    @Override
    public String getName() {
	return this.name;
    }

    @Override
    public int getHttpStatusCode() {
	return this.httpStatusCode;
    }

    //TODO: esperimento
    @Override
    public ErrorCode lookupFromCode(int errorCode) {
	return this;
    }
    
    //TODO: esperimento
    @Override
    public ErrorCode lookupFromName(String errorName) {
	return this;
    }

}
