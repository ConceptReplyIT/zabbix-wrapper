package it.reply.utils.web.ws.rest.apiclients.zabbix.exception;

import javax.ws.rs.core.Response.Status;

/**
* An exception that is thrown when autentication to server fail
*/
public class AuthenticationException extends ResponseException {
    
    private static final long serialVersionUID = 1L;

    public AuthenticationException(String message, Status status, Throwable cause) {
	super(message, status, cause);
    }

    public AuthenticationException(String message, Status status) {
	super(message, status);
    }
}
