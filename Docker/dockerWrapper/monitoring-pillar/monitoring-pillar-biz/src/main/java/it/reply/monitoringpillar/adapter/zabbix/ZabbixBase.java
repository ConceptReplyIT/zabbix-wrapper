package it.reply.monitoringpillar.adapter.zabbix;

import it.reply.monitoringpillar.adapter.zabbix.exception.AuthenticationZabbixException;
import it.reply.monitoringpillar.adapter.zabbix.exception.DuplicateResourceZabbixException;
import it.reply.monitoringpillar.adapter.zabbix.exception.NotFoundZabbixException;
import it.reply.monitoringpillar.adapter.zabbix.exception.ZabbixException;
import it.reply.utils.web.ws.rest.apiclients.zabbix.exception.AuthenticationException;
import it.reply.utils.web.ws.rest.apiclients.zabbix.exception.ClientResponseException;
import it.reply.utils.web.ws.rest.apiclients.zabbix.exception.ZabbixClientException;

public abstract class ZabbixBase {

    /**
     * Convert a ZabbixClientException into a ZabbixException
     * 
     * @param e
     * @return
     */
    protected ZabbixException handleException(ZabbixClientException e) {

	if (e instanceof AuthenticationException)
	    return new AuthenticationZabbixException(e.getMessage(), e);

	if (e instanceof ClientResponseException) {
	    ClientResponseException responseException = (ClientResponseException) e;
	    switch (responseException.getStatus()) {
	    case NOT_FOUND:
		return new NotFoundZabbixException(e.getMessage(), e);
	    case CONFLICT:
		return new DuplicateResourceZabbixException(e.getMessage(), e);
	    default:
		return new ZabbixException(e.getMessage(), e);
	    }
	}

	return new ZabbixException(e.getMessage(), e);
    }

}
