package it.monitoringpillar.adapter.zabbix.handler;

import it.monitoringpillar.adapter.zabbix.clientbuilder.ZabbixAdapterClientSetter;
import it.monitoringpillar.adapter.zabbix.exception.NotFoundZabbixException;
import it.monitoringpillar.adapter.zabbix.exception.ZabbixException;
import it.monitoringpillar.adapter.zabbix.handler.ZabbixFeatures.ZabbixMethods;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.response.ZabbixHostGroupResponse;
import it.prisma.utils.web.ws.rest.apiclients.prisma.APIErrorException;
import it.prisma.utils.web.ws.rest.apiencoding.MappingException;
import it.prisma.utils.web.ws.rest.apiencoding.NoMappingModelFoundException;
import it.prisma.utils.web.ws.rest.apiencoding.ServerErrorResponseException;
import it.prisma.utils.web.ws.rest.restclient.exceptions.RestClientException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * 
 * @author m.grandolfo This class retrieves Host group IDs
 */
@Stateless
public class GroupIDByName implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private ZabbixAdapterClientSetter<?> zabAdapSetter;

    /**
     * 
     * 
     * @param testbedType
     * @param serverType
     * @param hostGroupName
     * @param groups
     * @return
     * @throws APIErrorException
     * @throws MappingException
     * @throws NoMappingModelFoundException
     * @throws ServerErrorResponseException
     * @throws ZabbixAPIErrorException
     * @throws RestClientException
     * @throws Exception
     */
    public String getGroupIDsintoZabbix(String serverType, String hostGroupName, List<ZabbixHostGroupResponse> groups)
	    throws ZabbixException {

	if (groups == null || groups.isEmpty()) {
	    ArrayList<ZabbixHostGroupResponse> hostGroups = zabAdapSetter.getHostGroupsService(serverType,
		    ZabbixMethods.HOSTGROUP.getzabbixMethod(), null, null, null);

	    for (ZabbixHostGroupResponse zabbixHostGroupResponse : hostGroups) {

		if (zabbixHostGroupResponse.getName().equalsIgnoreCase(hostGroupName)) {
		    return zabbixHostGroupResponse.getGroupid();
		}
	    }
	    throw new NotFoundZabbixException("Group [" + hostGroupName + "] not exists");
	}
	// In case the List Has been retrieved from calling object (in order to
	// save number of calls)
	else {
	    for (ZabbixHostGroupResponse zabbixHostGroupResponse : groups) {
		if (zabbixHostGroupResponse.getName().equalsIgnoreCase(hostGroupName)) {
		    return zabbixHostGroupResponse.getGroupid();
		}
	    }
	    throw new NotFoundZabbixException("Group [" + hostGroupName + "] not exists");
	}
    }
}