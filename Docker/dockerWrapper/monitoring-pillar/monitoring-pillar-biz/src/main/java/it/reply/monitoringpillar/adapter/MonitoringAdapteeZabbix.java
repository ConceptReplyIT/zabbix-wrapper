package it.reply.monitoringpillar.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

import it.reply.monitoringpillar.adapter.zabbix.ZabbixConstant;
import it.reply.monitoringpillar.adapter.zabbix.clientbuilder.ZabbixAdapterClientSetter;
import it.reply.monitoringpillar.adapter.zabbix.exception.AuthenticationZabbixException;
import it.reply.monitoringpillar.adapter.zabbix.exception.DuplicateResourceZabbixException;
import it.reply.monitoringpillar.adapter.zabbix.exception.IllegalArgumentZabbixException;
import it.reply.monitoringpillar.adapter.zabbix.exception.NotFoundZabbixException;
import it.reply.monitoringpillar.adapter.zabbix.exception.ZabbixException;
import it.reply.monitoringpillar.adapter.zabbix.handler.GroupCheck;
import it.reply.monitoringpillar.adapter.zabbix.handler.GroupIDByName;
import it.reply.monitoringpillar.adapter.zabbix.handler.ZabbixFeatures.ZabbixMethods;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.iaas.MonitoringWrappedResponseIaas;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas.FilterTimeRequest;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas.MonitoringWrappedResponsePaas;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.response.ZabbixHostGroupResponse;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.response.ZabbixItemResponse;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.response.ZabbixMonitoredHostResponseV2_4;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.response.ZabbixMonitoredHostsResponse;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.response.ZabbixTemplateResponseV2_4;
import it.reply.monitoringpillar.domain.exception.DuplicateResourceMonitoringException;
import it.reply.monitoringpillar.domain.exception.IllegalArgumentMonitoringException;
import it.reply.monitoringpillar.domain.exception.MonitoringException;
import it.reply.monitoringpillar.domain.exception.NotFoundMonitoringException;
import it.reply.monitoringpillar.wrapper.zabbix.iaas.WrapperIaaSBean;
import it.reply.monitoringpillar.wrapper.zabbix.paas.WrapperPaasBean;
import it.reply.utils.web.ws.rest.apiencoding.ServerErrorResponseException;
import it.reply.utils.web.ws.rest.restclient.exceptions.RestClientException;

@Stateless
public abstract class MonitoringAdapteeZabbix {

	@Inject
	private WrapperIaaSBean wrapperIaaS;

	@Inject
	private WrapperPaasBean wrapperPaas;

	@Inject
	private GroupIDByName zabGroupId;

	@EJB
	private ZabbixAdapterClientSetter<?> zabAdapClientSetter;

	@EJB
	private GroupCheck checkGroup;

	/**
	 * Convert a Zabbix Exception into a Monitoring Exception
	 * 
	 * @param e
	 * @return
	 * @throws MonitoringException
	 */
	protected MonitoringException handleException(ZabbixException e) {

		if (e instanceof IllegalArgumentZabbixException)
			return new IllegalArgumentMonitoringException(e.getMessage(), e);

		if (e instanceof AuthenticationZabbixException)
			return new MonitoringException(e.getMessage(), e);

		if (e instanceof DuplicateResourceZabbixException) {
			return new DuplicateResourceMonitoringException(e.getMessage(), e);
		}

		if (e instanceof NotFoundZabbixException) {
			return new NotFoundMonitoringException(e.getMessage(), e);
		}

		return new MonitoringException(e.getMessage(), e);
	}

	/*************************************
	 * WRAPPER GENERIC FOR IAAS ZABBIX SERVER
	 * 
	 * @param testbed
	 * @param serverType
	 * @param iaasgroupName
	 * @param iaashostName
	 * @return
	 * @throws MonitoringException
	 */
	public MonitoringWrappedResponseIaas getOverallIaas(String zone, String serverType, String iaasgroupName,
			String iaashostName) throws MonitoringException {

		try {

			String groupID = null;
			// Get IaaS Server Credentials
			ArrayList<ZabbixHostGroupResponse> groupsInZabbixIaas = new ArrayList<>();
			ArrayList<ZabbixMonitoredHostsResponse> hostsinZabbixIaas = new ArrayList<>();
			List<ZabbixHostGroupResponse> groupsUseful = new ArrayList<>();
			ArrayList<ZabbixMonitoredHostsResponse> onlyUsefulHost = new ArrayList<>();
			String hostId = null;

			if (iaasgroupName != null && checkGroup.isGroupPresent(zone, serverType, iaasgroupName, iaashostName, null))

				groupsInZabbixIaas = zabAdapClientSetter.getHostGroupsService(zone, serverType,
						ZabbixMethods.HOSTGROUP.getzabbixMethod(), null, null, null);
			for (ZabbixHostGroupResponse zabbixHostGroupResponse : groupsInZabbixIaas) {

				if (zabbixHostGroupResponse.getName().equalsIgnoreCase(iaasgroupName)) {
					groupID = zabbixHostGroupResponse.getGroupid();
					groupsUseful.add(zabbixHostGroupResponse);
					break;
				}
			}
			if (groupID == null)
				throw new IllegalArgumentException("Error this hostgroup is listed in zabbix server");

			hostsinZabbixIaas = zabAdapClientSetter.getHostsService(zone, serverType,
					ZabbixMethods.HOST.getzabbixMethod(), null, groupID, null, null);

			if (iaashostName != null) {

				for (ZabbixMonitoredHostsResponse host : hostsinZabbixIaas) {
					if (iaashostName.equalsIgnoreCase(host.getName())) {

						onlyUsefulHost.add(host);
						hostsinZabbixIaas = onlyUsefulHost;
					}
				}
			}

			MonitoringWrappedResponseIaas result = wrapperIaaS.getWrappedIaas(zone, serverType, iaasgroupName,
					iaashostName, groupsUseful, groupID, hostsinZabbixIaas, hostId);
			return result;
		} catch (ZabbixException e) {
			throw handleException(e);
		}
	}

	/*********************************************
	 * WRAPPER GENERIC PAAS FOR ZABBIX METRICS AND WATCHER
	 * 
	 * @throws ZabbixAPIErrorException
	 * @throws ServerErrorResponseException
	 * @throws RestClientException
	 * @throws IllegalArgumentException
	 * @throws Exception
	 ********************************/

	public MonitoringWrappedResponsePaas getOverallPaaS(String zone, String serverType, String group, String hostuuid,
			String service_category, String tag_service, List<String> atomic_service_id, String metric,
			List<String> triggers_id, String history, FilterTimeRequest requestTime) throws MonitoringException {

		try {

			MonitoringWrappedResponsePaas wrappedResult = new MonitoringWrappedResponsePaas();

			String hostId = null;
			String groupId = null;
			String groupName = null;
			List<ZabbixMonitoredHostResponseV2_4> hostsinZabbixPaaS = new ArrayList<>();
			List<ZabbixHostGroupResponse> groupsInZabbixPaaS = new ArrayList<>();
			List<ZabbixItemResponse> items = new ArrayList<>();
			List<ZabbixTemplateResponseV2_4> templatesExtended = new ArrayList<>();
			Map<ZabbixMonitoredHostResponseV2_4, List<ZabbixTemplateResponseV2_4>> hostsByTagMap = new HashMap<>();
			Map<ZabbixTemplateResponseV2_4, List<ZabbixItemResponse>> itemsByTemplate = new HashMap<>();

			if (group != null && (checkGroup.isGroupPresent(zone, serverType, group, hostuuid, tag_service)))
				;

			/*****************************
			 * ONLY TAG ID PASSED FROM API
			 ****************************/
			if (tag_service != null && hostuuid == null && group != null) {
				// Get the list of EXTENDED hosts (templates and metrics
				// associated to em)
				hostsinZabbixPaaS = zabAdapClientSetter.getMonitoredHostsZABBIXV2_4(zone, serverType,
						ZabbixMethods.HOST.getzabbixMethod(), null, null, null, tag_service);

				if (hostsinZabbixPaaS.isEmpty())
					throw new IllegalArgumentException("Wrong Service ID Inserted: Not Existing Into Platform");

				for (ZabbixMonitoredHostResponseV2_4 host : hostsinZabbixPaaS) {
					hostId = host.getHostid();
					// GET the list of items
					items = host.getItems();
					// GET the list of templates
					templatesExtended = zabAdapClientSetter.getTemplatesExtendedService(zone, serverType, hostId,
							ZabbixMethods.TEMPLATE.getzabbixMethod());
					hostsByTagMap.put(host, templatesExtended);
					for (ZabbixTemplateResponseV2_4 template : templatesExtended) {

						// GET ONLY USEFUL ITEMS REALLY ASSOCIATED TO TEMPLATES
						List<ZabbixItemResponse> usefulItems = new ArrayList<>();
						for (ZabbixItemResponse itemFromExtHost : items) {
							for (ZabbixItemResponse item : template.getItems()) {
								if (item.getName().equals(itemFromExtHost.getName())) {
									usefulItems.add(itemFromExtHost);
								}
							}
						}
						itemsByTemplate.put(template, usefulItems);
					}
				}
				groupsInZabbixPaaS = zabAdapClientSetter.getHostGroupsService(zone, serverType,
						ZabbixMethods.HOSTGROUP.getzabbixMethod(), groupId, null, hostId);

				if (groupsInZabbixPaaS.isEmpty())
					throw new IllegalArgumentException(ZabbixConstant.WRONGGROUPNAME);
				groupId = groupsInZabbixPaaS.get(0).getGroupid();
				groupName = groupsInZabbixPaaS.get(0).getName();
			}

			/**********************************
			 * ONLY HOST UUID OR NAME SPECIFIED
			 **********************************/
			else if (hostuuid != null && group != null && tag_service == null) {
				hostsinZabbixPaaS = zabAdapClientSetter.getMonitoredHostsZABBIXV2_4(zone, serverType,
						ZabbixMethods.HOST.getzabbixMethod(), null, null, hostuuid, null);
				if (hostsinZabbixPaaS.isEmpty())
					throw new IllegalArgumentException(ZabbixConstant.WRONGHOSTNAME);
				for (ZabbixMonitoredHostResponseV2_4 host : hostsinZabbixPaaS) {
					hostId = host.getHostid();
					// GET the list of items
					items = host.getItems();
					// GET the list of templates EXTENDED (useful for cycling on
					// metrics associated to em.. From extended host answer
					// couldn't
					// know it)
					templatesExtended = zabAdapClientSetter.getTemplatesExtendedService(zone, serverType, hostId,
							ZabbixMethods.TEMPLATE.getzabbixMethod());

					hostsByTagMap.put(host, templatesExtended);
					for (ZabbixTemplateResponseV2_4 template : templatesExtended) {

						// GET ONLY USEFUL ITEMS REALLY ASSOCIATED TO TEMPLATES
						List<ZabbixItemResponse> usefulItems = new ArrayList<>();
						for (ZabbixItemResponse itemFromExtHost : items) {
							for (ZabbixItemResponse item : template.getItems()) {
								if (item.getName().equals(itemFromExtHost.getName())) {
									usefulItems.add(itemFromExtHost);
								}
							}
						}
						itemsByTemplate.put(template, usefulItems);
					}
				}
				groupsInZabbixPaaS = zabAdapClientSetter.getHostGroupsService(zone, serverType,
						ZabbixMethods.HOSTGROUP.getzabbixMethod(), groupId, null, hostId);
				if (groupsInZabbixPaaS.isEmpty())
					throw new IllegalArgumentException(ZabbixConstant.WRONGGROUPNAME);
				groupId = groupsInZabbixPaaS.get(0).getGroupid();
				groupName = groupsInZabbixPaaS.get(0).getName();
			}

			/**************************
			 * ONLY GROUP SPECIFIED
			 **************************/
			else if (group != null && hostuuid == null && tag_service == null) {
				List<ZabbixHostGroupResponse> groupsuseful = new ArrayList<>();
				groupName = group;
				List<ZabbixHostGroupResponse> groupsInZabbixPaaSOverall = zabAdapClientSetter.getHostGroupsService(zone,
						serverType, ZabbixMethods.HOSTGROUP.getzabbixMethod(), null, null, null);
				if (groupsInZabbixPaaSOverall.isEmpty())
					throw new IllegalArgumentException(ZabbixConstant.WRONGGROUPNAME);
				groupId = zabGroupId.getGroupIDsintoZabbix(zone, serverType, groupName, groupsInZabbixPaaS);
				for (ZabbixHostGroupResponse groupInfo : groupsInZabbixPaaSOverall) {
					if (groupInfo.getName().equalsIgnoreCase(groupName)) {
						groupsuseful.add(groupInfo);
						groupsInZabbixPaaS = groupsuseful;
						break;
					}
				}
				if (groupsuseful.isEmpty())
					throw new IllegalArgumentException(ZabbixConstant.WRONGGROUPNAME);

				hostsinZabbixPaaS = zabAdapClientSetter.getMonitoredHostsZABBIXV2_4(zone, serverType,
						ZabbixMethods.HOST.getzabbixMethod(), null, groupId, null, null);
			}

			wrappedResult = wrapperPaas.getWrappedPaas(zone, serverType, groupName, hostuuid, service_category,
					tag_service, atomic_service_id, metric, triggers_id, history, requestTime, groupsInZabbixPaaS,
					groupId, hostsinZabbixPaaS, hostId, items, templatesExtended, hostsByTagMap, itemsByTemplate);

			return wrappedResult;
		} catch (ZabbixException e) {
			throw handleException(e);
		}
	}

}