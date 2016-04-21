package it.reply.monitoringpillar.adapter.zabbix.handler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import it.reply.monitoringpillar.adapter.zabbix.clientbuilder.ZabbixAdapterClientSetter;
import it.reply.monitoringpillar.adapter.zabbix.exception.NotFoundZabbixException;
import it.reply.monitoringpillar.adapter.zabbix.exception.ZabbixException;
import it.reply.monitoringpillar.adapter.zabbix.handler.ZabbixFeatures.ZabbixMethods;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.response.ZabbixHostGroupResponse;
import it.reply.utils.web.ws.rest.apiclients.prisma.APIErrorException;
import it.reply.utils.web.ws.rest.apiencoding.MappingException;
import it.reply.utils.web.ws.rest.apiencoding.NoMappingModelFoundException;
import it.reply.utils.web.ws.rest.apiencoding.ServerErrorResponseException;
import it.reply.utils.web.ws.rest.restclient.exceptions.RestClientException;

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
	public String getGroupIDsintoZabbix(String zone, String serverType, String hostGroupName,
			List<ZabbixHostGroupResponse> groups) throws ZabbixException {

		if (groups == null || groups.isEmpty()) {
			ArrayList<ZabbixHostGroupResponse> hostGroups = zabAdapSetter.getHostGroupsService(zone, serverType,
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