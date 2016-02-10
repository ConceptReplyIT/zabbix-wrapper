package it.monitoringpillar.adapter.zabbix;

import it.monitoringpillar.adapter.MonitoringAdapteeZabbix;
import it.monitoringpillar.adapter.MonitoringTarget;
import it.monitoringpillar.adapter.zabbix.ZabbixConstant.MetricAction;
import it.monitoringpillar.adapter.zabbix.clientbuilder.ZabbixAdapterClientSetter;
import it.monitoringpillar.adapter.zabbix.exception.DuplicateResourceZabbixException;
import it.monitoringpillar.adapter.zabbix.exception.ZabbixException;
import it.monitoringpillar.adapter.zabbix.handler.GroupCheck;
import it.monitoringpillar.adapter.zabbix.handler.GroupIDByName;
import it.monitoringpillar.adapter.zabbix.handler.HostIDByName;
import it.monitoringpillar.adapter.zabbix.handler.ZabbixFeatures.ZabbixMethods;
import it.monitoringpillar.config.Configuration;
import it.monitoringpillar.exception.DuplicateResourceMonitoringException;
import it.monitoringpillar.exception.MonitoringException;
import it.monitoringpillar.exception.NotFoundMonitoringException;
import it.monitoringpillar.wrapper.HostsWrapper;
import it.monitoringpillar.wrapper.zabbix.ZabbixEventBean;
import it.monitoringpillar.wrapper.zabbix.ZabbixGroupBean;
import it.monitoringpillar.wrapper.zabbix.iaas.WrapperIaaSBean;
import it.monitoringpillar.wrapper.zabbix.iaas.WrapperIaasHealthBean;
import it.monitoringpillar.wrapper.zabbix.paas.WrapperPaasBean;
import it.monitoringpillar.wrapper.zabbix.paas.WrapperTriggerPaaSBean;
import it.prisma.domain.dsl.monitoring.InfoType;
import it.prisma.domain.dsl.monitoring.businesslayer.paas.response.MonitoringVMCredentialsResponse;
import it.prisma.domain.dsl.monitoring.pillar.wrapper.MonitPillarEventResponse;
import it.prisma.domain.dsl.monitoring.pillar.wrapper.iaas.WrappedIaasHealthByTrigger;
import it.prisma.domain.dsl.monitoring.pillar.wrapper.paas.FilterTimeRequest;
import it.prisma.domain.dsl.monitoring.pillar.wrapper.paas.MonitoringWrappedResponsePaasGroups;
import it.prisma.domain.dsl.monitoring.pillar.wrapper.paas.MonitoringWrappedResponsePaasGroups4HostList;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.UpdateGroupName;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.response.HostGroupResponse;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.response.ZabbixHostGroupResponse;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.response.ZabbixItemResponse;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.response.ZabbixMonitoredHostResponseV2_4;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.response.ZabbixMonitoredHostsResponse;
import it.prisma.utils.web.ws.rest.apiclients.prisma.APIErrorException;
import it.prisma.utils.web.ws.rest.apiencoding.MappingException;
import it.prisma.utils.web.ws.rest.apiencoding.NoMappingModelFoundException;
import it.prisma.utils.web.ws.rest.apiencoding.ServerErrorResponseException;
import it.prisma.utils.web.ws.rest.restclient.exceptions.RestClientException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.naming.NameNotFoundException;

/**
 * 
 * @author m.grandolfo This class implements the interface for giving back all
 *         the methods (for properly managing Zabbix API) to be used by
 *         MoniotringPillarService class which will expose them as Restful API
 */
@IMonitAdaptZabbix
@Stateless
// @Local(MonitoringTarget.class)
public class MonitoringAdapterZabbix extends MonitoringAdapteeZabbix implements MonitoringTarget {

	// private static final Logger LOG =
	// LogManager.getLogger(MonitoringAdapterZabbix.class);
	private static String errorServerTypeUnallowed = "Error: Not Allowed for Infrastructure type, just Paas Platform type";

	@Inject
	private Configuration config;

	@Inject
	private ZabbixHelperBean zabbixHelper;

	@Inject
	private WrapperIaaSBean wrapperIaaS;

	@Inject
	private WrapperPaasBean wrapperPaas;

	// @Inject
	// private WrapperProvider<?> wrapper;

	@Inject
	private ZabbixGroupBean groupswrapp;

	@EJB
	private GroupCheck checkGroup;

	@Inject
	private HostsWrapper hostswrapp;

	@Inject
	private ZabbixEventBean wrapperEvents;

	@Inject
	private WrapperIaasHealthBean wrapperTrigger4Iaas;

	@Inject
	private WrapperTriggerPaaSBean wrapperTriggerPaas;

	@EJB
	private ZabbixAdapterClientSetter<?> zabAdapClientSetter;

	@EJB
	private HostIDByName zabHostId;

	@Inject
	private GroupIDByName zabGroupId;

	// GET GROUPS MINIMAL INFO
	@Override
	public MonitoringWrappedResponsePaasGroups getGroupsInfoWrapped(String serverType) throws MonitoringException {

		try {
			List<ZabbixHostGroupResponse> groupsInZabbixPaaSOverall = zabAdapClientSetter.getHostGroupsService(
					serverType, ZabbixMethods.HOSTGROUP.getzabbixMethod(), null, null, null);

			return groupswrapp.getWrappedGroup(groupsInZabbixPaaSOverall);
		} catch (ZabbixException e) {
			throw handleException(e);
		}
	}

	/**
	 * GET IAAS/PAAS SHOT TRIGGERS
	 * 
	 * @throws ZabbixAPIErrorException
	 * @throws ServerErrorResponseException
	 * @throws RestClientException
	 * @throws APIErrorException
	 */
	@Override
	public WrappedIaasHealthByTrigger getTriggerByGroup(String serverType, String hostgroup) throws MonitoringException {

		try {
			checkGroup.isGroupPresent(serverType, hostgroup, null, null);

			ArrayList<ZabbixHostGroupResponse> groupsIntoPlatf = zabAdapClientSetter.getHostGroupsService(serverType,
					ZabbixMethods.HOSTGROUP.getzabbixMethod(), null, null, null);

			for (ZabbixHostGroupResponse group : groupsIntoPlatf) {
				if (group.getName().equalsIgnoreCase(hostgroup)) {
					WrappedIaasHealthByTrigger test = wrapperTrigger4Iaas.getTriggerforIaasHealth(
							config.getMonitoringEnvironment(), serverType, hostgroup);
					return test;
				}
			}
			throw new IllegalArgumentException(ZabbixConstant.WRONGGROUPNAME);
		} catch (ZabbixException e) {
			throw handleException(e);
		}
	}

	@Override
	public HostGroupResponse updateMonitoredHostGroup(String serverType, String groupName, UpdateGroupName newGroupName)
			throws MonitoringException {

		try {

			String groupid2Zabbix = zabGroupId.getGroupIDsintoZabbix(serverType, groupName, null);
			return zabAdapClientSetter.updateHostGroupService(serverType, newGroupName.getNewHostGroupName(),
					groupid2Zabbix, ZabbixMethods.HOSTGROUPUPDATE.getzabbixMethod());
		} catch (ZabbixException e) {
			throw handleException(e);
		}
	}

	/*******************
	 * PAAS
	 *******************/
	/******************************
	 * CREATE GROUP in PAAS AND IAAS
	 * 
	 * @throws ServerErrorResponseException
	 * @throws RestClientException
	 * @throws ZabbixAPIErrorException
	 */
	@Override
	public void creationMonitoredHostGroup(String hostGroupName) throws MonitoringException {

		try {

			// Create Group into Metrics server
			String url = zabbixHelper.getZabbixURL(InfoType.SERVICE.getInfoType());
			String token = zabbixHelper.getZabbixToken(InfoType.SERVICE.getInfoType());
			boolean isDuplicate = false;
			try {
				zabAdapClientSetter.createHostGroupService(url, token, hostGroupName,
						ZabbixMethods.HOSTGROUPCREATE.getzabbixMethod());
			} catch (DuplicateResourceZabbixException e) {
				// If the group already exists it keeps verifying on other
				// server
				// but remembering it!
				isDuplicate = true;
			}
			// Create a Host into Watcher Server
			url = zabbixHelper.getZabbixURL(InfoType.WATCHER.getInfoType());
			token = zabbixHelper.getZabbixToken(InfoType.WATCHER.getInfoType());
			try {
				zabAdapClientSetter.createHostGroupService(url, token, hostGroupName,
						ZabbixMethods.HOSTGROUPCREATE.getzabbixMethod());
			} catch (DuplicateResourceZabbixException e) {
				// If the group already exists, keep verifying on other groups
				// but still recording it exists!
				isDuplicate = true;
			}

			// Create a Host into IaaS Server
			url = zabbixHelper.getZabbixURL(InfoType.INFRASTRUCTURE.getInfoType());
			token = zabbixHelper.getZabbixToken(InfoType.INFRASTRUCTURE.getInfoType());
			try {
				zabAdapClientSetter.createHostGroupService(url, token, hostGroupName,
						ZabbixMethods.HOSTGROUPCREATE.getzabbixMethod());
			} catch (DuplicateResourceZabbixException e) {
				// If the group already exists I do verify it anyway on other
				// servers but I remember it
				isDuplicate = true;
			}

			if (isDuplicate) {
				throw new DuplicateResourceMonitoringException("Group [" + hostGroupName + "] already exists.");
			}
		} catch (ZabbixException e) {
			throw handleException(e);
		}
	}

	/*********************************
	 * HOST CREATION IN PAAS AND IAAS /** Implements the Interface for creating
	 * a host from adapter
	 * 
	 * @return the host Id that Zabbix API returns when creating an host
	 * 
	 *         in order to choose which URL to set in the client
	 * @param uuid
	 *            of the host
	 * @param vmip
	 * @param serviceCategory
	 *            group to which the host belongs
	 * @param serviceTag
	 *            ID identifying the group of hosts in the service
	 * @param one
	 *            or more (depending on the type of serviceCategory) atomic
	 *            service
	 * @throws NameNotFoundException
	 * @throws APIErrorException
	 * @throws NotFoundMonitoringException
	 * @throws ZabbixAPIErrorException
	 * @throws ServerErrorResponseException
	 * @throws RestClientException
	 * @throws IllegalArgumentException
	 * @throws DuplicateResourceMonitoringException
	 * @throws Exception
	 * 
	 */
	@Override
	public void creationMonitoredHost(String serverType, String hostGroup, String hostName, String vmuuid, String vmip,
			String serviceCategory, String serviceTag, List<String> services, Boolean activeMode,
			List<it.prisma.domain.dsl.monitoring.businesslayer.paas.request.Port> ports) throws MonitoringException,
			NameNotFoundException {

		try {

			ArrayList<MonitoringVMCredentialsResponse> createdHosts = new ArrayList<MonitoringVMCredentialsResponse>();

			/*************
			 * IAAS CASE
			 ************/
			boolean isDuplicate = false;
			if (serverType.toLowerCase().equals(InfoType.INFRASTRUCTURE.getInfoType())) {
				// ZABBIX-IAAS
				if (checkGroup.isGroupPresent(InfoType.INFRASTRUCTURE.getInfoType(), hostGroup, null, null)) {
					try {
						MonitoringVMCredentialsResponse createdHostintoIaaS = zabAdapClientSetter.createHostService(
								InfoType.INFRASTRUCTURE, hostGroup, hostName, vmuuid, vmip, serviceCategory,
								serviceTag, services, activeMode, ZabbixMethods.HOSTCREATE.getzabbixMethod(), null);

						createdHosts.add(createdHostintoIaaS);
					} catch (DuplicateResourceZabbixException e) {
						isDuplicate = true;
					}
				}
			}

			/********************************************
			 * PAAS CASE: it Creates two hosts in two servers
			 *******************************************/
			// Create a Host into Metrics and Watcher Server
			else {

				// ZABBIX-METRICS
				// Check if Group Exists, if not, it throws the exception
				if (checkGroup.isGroupPresent(InfoType.SERVICE.getInfoType(), hostGroup, null, null)
						&& checkGroup.isGroupPresent(InfoType.WATCHER.getInfoType(), hostGroup, null, null)) {
					try {
						MonitoringVMCredentialsResponse createdHostintoMetrics = zabAdapClientSetter.createHostService(
								InfoType.SERVICE, hostGroup, hostName, vmuuid, vmip, serviceCategory, serviceTag,
								services, activeMode, ZabbixMethods.HOSTCREATE.getzabbixMethod(), ports);

						createdHosts.add(createdHostintoMetrics);
					} catch (DuplicateResourceZabbixException e) {
						isDuplicate = true;
					}
					// Create a Host into Watcher Server
					try {
						MonitoringVMCredentialsResponse createdHostintoWatcher = zabAdapClientSetter.createHostService(
								InfoType.WATCHER, hostGroup, hostName, vmuuid, vmip, serviceCategory, serviceTag,
								services, activeMode, ZabbixMethods.HOSTCREATE.getzabbixMethod(), ports);

						createdHosts.add(createdHostintoWatcher);
					} catch (DuplicateResourceZabbixException e) {
						isDuplicate = true;
					}
				}
			}
			if (isDuplicate) {
				throw new DuplicateResourceMonitoringException("Host already exists.");
			}
		} catch (ZabbixException e) {
			throw handleException(e);
		}
	}

	/*****************************************************************************
	 * This method removes the selected host by passing the vmuuid as
	 * information HOST DELETE from IaaS and PaaS depending upon some conditions
	 *****************************************************************************/
	@Override
	public void deleteMonitoredHost(String hostvmuuids, String serviceId, String serverType) throws MonitoringException {

		try {

			ArrayList<MonitoringVMCredentialsResponse> removedPaasHosts = new ArrayList<MonitoringVMCredentialsResponse>();
			MonitoringVMCredentialsResponse hostRemovedFromMetrics = new MonitoringVMCredentialsResponse();
			MonitoringVMCredentialsResponse hostRemovedFromWatcher = new MonitoringVMCredentialsResponse();
			MonitoringVMCredentialsResponse hostInfo = new MonitoringVMCredentialsResponse();
			ArrayList<String> hostIdsnames = new ArrayList<>();
			hostIdsnames.add(hostvmuuids);
			hostInfo.setHostids(hostIdsnames);
			Boolean isHostExisting = true;

			/*************
			 * IAAS CASE
			 ************/
			if (serverType.toLowerCase().equalsIgnoreCase(InfoType.INFRASTRUCTURE.getInfoType())) {
				// Create a Host into Metrics Server
				try {
					// if(hostExists())
					String url = zabbixHelper.getZabbixURL(InfoType.INFRASTRUCTURE.getInfoType());
					String token = zabbixHelper.getZabbixToken(InfoType.INFRASTRUCTURE.getInfoType());
					String hostid2Zabbix = zabHostId.getHostID(InfoType.INFRASTRUCTURE.getInfoType(), null,
							hostvmuuids, null);

					MonitoringVMCredentialsResponse hostRemovedFromIaaS = zabAdapClientSetter.deleteHostService(url,
							token, hostid2Zabbix, ZabbixMethods.HOSTDELETE.getzabbixMethod());
					removedPaasHosts.add(hostRemovedFromIaaS);
				} catch (DuplicateResourceZabbixException e) {
					isHostExisting = true;
				}

			}

			/********
			 * PAAS
			 ********/
			else {
				/********************
				 * For ZABBIX METRICS
				 ********************/
				try {
					String url = zabbixHelper.getZabbixURL(InfoType.SERVICE.getInfoType());
					String token = zabbixHelper.getZabbixToken(InfoType.SERVICE.getInfoType());
					String hostid2Zabbix = zabHostId.getHostID(InfoType.SERVICE.getInfoType(), null, hostvmuuids, null);

					hostRemovedFromMetrics = zabAdapClientSetter.deleteHostService(url, token, hostid2Zabbix,
							ZabbixMethods.HOSTDELETE.getzabbixMethod());
					removedPaasHosts.add(hostRemovedFromMetrics);

				} catch (DuplicateResourceZabbixException e) {
					isHostExisting = true;
				}

				/*********************
				 * For ZABBIX WATCHER
				 ********************/
				try {
					String url = zabbixHelper.getZabbixURL(InfoType.WATCHER.getInfoType());
					String token = zabbixHelper.getZabbixToken(InfoType.WATCHER.getInfoType());
					String hostid2Zabbix = zabHostId.getHostID(InfoType.WATCHER.getInfoType(), null, hostvmuuids, null);

					hostRemovedFromWatcher = zabAdapClientSetter.deleteHostService(url, token, hostid2Zabbix,
							ZabbixMethods.HOSTDELETE.getzabbixMethod());
					removedPaasHosts.add(hostRemovedFromWatcher);

				} catch (DuplicateResourceZabbixException e) {
					isHostExisting = true;
				}
				if (!isHostExisting) {
					throw new DuplicateResourceMonitoringException("Hostgroup already exists.");
				}
			}
		} catch (ZabbixException e) {
			throw handleException(e);
		}
	}

	/*********************
	 * DELETE HOSTGROUP
	 *********************/
	@Override
	public void deleteMonitoredHostGroup(String hostGroupName) throws MonitoringException {

		try {
			ArrayList<HostGroupResponse> removedPaasHosts = new ArrayList<HostGroupResponse>();
			HostGroupResponse hostRemovedFromMetrics = new HostGroupResponse();
			HostGroupResponse hostRemovedFromIaaS = new HostGroupResponse();
			HostGroupResponse hostRemovedFromWatcher = new HostGroupResponse();
			HostGroupResponse group = new HostGroupResponse();
			List<String> groupIds = new ArrayList<>();
			groupIds.add(hostGroupName);
			group.setGroupids(groupIds);
			boolean isHostExisting = true;
			/*************
			 * IAAS CASE
			 ************/
			try {
				String groupid2Zabbix = zabGroupId.getGroupIDsintoZabbix(InfoType.INFRASTRUCTURE.getInfoType(),
						hostGroupName, null);

				hostRemovedFromIaaS = zabAdapClientSetter.deleteHostGroupService(InfoType.INFRASTRUCTURE.getInfoType(),
						groupid2Zabbix, ZabbixMethods.GROUPDELETE.getzabbixMethod());
				removedPaasHosts.add(hostRemovedFromIaaS);
			} catch (DuplicateResourceZabbixException e) {
				isHostExisting = true;
			}
			/********************
			 * For ZABBIX METRICS
			 ********************/
			try {
				String groupid2Zabbix = zabGroupId.getGroupIDsintoZabbix(InfoType.SERVICE.getInfoType(), hostGroupName,
						null);

				hostRemovedFromMetrics = zabAdapClientSetter.deleteHostGroupService(InfoType.SERVICE.getInfoType(),
						groupid2Zabbix, ZabbixMethods.GROUPDELETE.getzabbixMethod());
				removedPaasHosts.add(hostRemovedFromMetrics);
			} catch (DuplicateResourceZabbixException e) {
				isHostExisting = true;
			}
			/************************
			 * AND For ZABBIX WATCHER
			 ***********************/
			try {
				String groupid2Zabbix = zabGroupId.getGroupIDsintoZabbix(InfoType.WATCHER.getInfoType(), hostGroupName,
						null);

				hostRemovedFromWatcher = zabAdapClientSetter.deleteHostGroupService(InfoType.WATCHER.getInfoType(),
						groupid2Zabbix, ZabbixMethods.GROUPDELETE.getzabbixMethod());
				removedPaasHosts.add(hostRemovedFromWatcher);
			} catch (DuplicateResourceZabbixException e) {
				isHostExisting = true;
			}
			if (!isHostExisting) {
				throw new DuplicateResourceMonitoringException("Hostgroup already exists.");
			}
		} catch (ZabbixException e) {
			throw handleException(e);
		}
	}

	/****************
	 * DISABLE METRIC
	 * 
	 * @param <T>
	 * @throws ZabbixAPIErrorException
	 * @throws ServerErrorResponseException
	 * @throws RestClientException
	 * @throws IllegalArgumentException
	 * @throws Exception
	 * @throws NoMappingModelFoundException
	 * @throws MappingException
	 ****************/
	@Override
	public void getDisablingHost(String serverType, String group, String host_uuid, String metric_id,
			String tag_service, String update) throws MonitoringException {

		try {
			String hostid2Zabbix = null;

			checkGroup.isGroupPresent(serverType, group, host_uuid, tag_service);

			// In case uuid has been passed as parameter
			if (tag_service == null && host_uuid != null && metric_id == null) {
				ArrayList<ZabbixMonitoredHostsResponse> hostsinZabbixPaaS = zabAdapClientSetter.getHostsService(
						serverType, ZabbixMethods.HOST.getzabbixMethod(), null, null, host_uuid, null);
				for (ZabbixMonitoredHostsResponse host : hostsinZabbixPaaS) {
					hostid2Zabbix = host.getHostid();
				}
			}

			zabAdapClientSetter.updateHostService(serverType, hostid2Zabbix, null,
					ZabbixMethods.MASSUPDATE.getzabbixMethod(), MetricAction.lookupFromName(update));
		} catch (ZabbixException e) {
			throw handleException(e);
		}
	}

	@Override
	public void getDisablingItem(String serverType, String group, String host, String metric_id, String tag_service,
			String update) throws MonitoringException {

		try {
			ArrayList<ZabbixItemResponse> item_id = new ArrayList<>();
			String hostid2Zabbix = null;
			String metricName = null;
			String metricIdtoAdapter = null;

			checkGroup.isGroupPresent(serverType, group, host, null);

			if (!(metric_id == null)) {
				ArrayList<ZabbixMonitoredHostsResponse> hostsinZabbixPaaS = zabAdapClientSetter.getHostsService(
						serverType, ZabbixMethods.HOST.getzabbixMethod(), null, null, host, null);

				hostid2Zabbix = hostsinZabbixPaaS.get(0).getHostid();

				item_id = zabAdapClientSetter.getItemsService(serverType, hostid2Zabbix, null, null, null,
						ZabbixMethods.METRIC.getzabbixMethod(), null);

				Iterator<ZabbixItemResponse> metricIter = item_id.iterator();
				boolean metricFound = false;
				while (!metricFound && metricIter.hasNext()) {
					ZabbixItemResponse adapterResult = metricIter.next();
					metricName = adapterResult.getName();
					if (metricName.equalsIgnoreCase(metric_id)) {
						metricIdtoAdapter = adapterResult.getItemid();
						metricFound = true;
					}
				}
				if (metricFound == false && !(metricIter.hasNext())) {
					throw new IllegalArgumentException("Wrong resourse item name inserted");
				}

				zabAdapClientSetter.updateItemService(serverType, hostid2Zabbix, metricIdtoAdapter,
						ZabbixMethods.ITEMUPDATE.getzabbixMethod(), MetricAction.lookupFromName(update));
			}
		} catch (ZabbixException e) {
			throw handleException(e);
		}
	}

	// GET HOST MINIMAL INFO
	@Override
	public MonitoringWrappedResponsePaasGroups4HostList getHostsInfoWrapped(String serverType, String groupName)
			throws MonitoringException {

		try {
			checkGroup.isGroupPresent(serverType, groupName, null, null);
			String groupId = null;
			List<ZabbixHostGroupResponse> groupUseful = new ArrayList<>();
			List<ZabbixHostGroupResponse> groups = zabAdapClientSetter.getHostGroupsService(serverType,
					ZabbixMethods.HOSTGROUP.getzabbixMethod(), null, null, null);

			for (ZabbixHostGroupResponse group : groups) {
				if (group.getName().equalsIgnoreCase(groupName)) {
					groupId = group.getGroupid();
					groupUseful.add(group);
					break;
				}
			}
			if (groupId == null) {
				throw new NotFoundMonitoringException(groupName + "does not exists");
			}

			List<ZabbixMonitoredHostResponseV2_4> hosts = zabAdapClientSetter.getMonitoredHostsZABBIXV2_4(serverType,
					ZabbixMethods.HOST.getzabbixMethod(), null, groupId, null, null);

			return hostswrapp.getHostsWrapped(serverType, groupName, groupUseful, hosts);
		} catch (ZabbixException e) {
			throw handleException(e);
		}
	}

	/**********************************
	 * TRIGGER EVENTS IN PAAS PLATFORM
	 **********************************/
	@Override
	public WrappedIaasHealthByTrigger getTriggerByHost(String serverType, String group, String host)
			throws MonitoringException {

		try {

			String hostId = null;
			String groupName = null;

			checkGroup.isGroupPresent(serverType, group, host, null);

			List<ZabbixMonitoredHostResponseV2_4> hostsinZabbixPaaS = zabAdapClientSetter.getMonitoredHostsZABBIXV2_4(
					serverType, ZabbixMethods.HOST.getzabbixMethod(), null, null, host, null);

			hostId = hostsinZabbixPaaS.get(0).getHostid();

			List<ZabbixHostGroupResponse> groups = zabAdapClientSetter.getHostGroupsService(serverType,
					ZabbixMethods.HOSTGROUP.getzabbixMethod(), null, null, hostId);

			if (groups.isEmpty()) {
				throw new IllegalArgumentException(ZabbixConstant.WRONGGROUPNAME);
			}
			groupName = groups.get(0).getName();

			if (hostsinZabbixPaaS.get(0).getName().equalsIgnoreCase(host)) {
				return wrapperTriggerPaas.getTriggerWrapper(serverType, hostsinZabbixPaaS, groupName);
			}
			throw new IllegalArgumentException(ZabbixConstant.WRONGGROUPNAME);
		} catch (ZabbixException e) {
			throw handleException(e);
		}
	}

	/*****************************************
	 * IT WRAPS EVENTS RETURNED FROM PLATFORM
	 *****************************************/
	@Override
	public MonitPillarEventResponse getOverallServerEvents(String serverType, String host_uuid, String group,
			String tag_service, FilterTimeRequest requestTime) throws MonitoringException {

		try {

			MonitPillarEventResponse wrappedEvents = new MonitPillarEventResponse();
			String hostId = null;
			String groupId = null;

			checkGroup.isGroupPresent(serverType, group, host_uuid, tag_service);

			// IF VMUUID is specified
			List<ZabbixMonitoredHostResponseV2_4> hosts = zabAdapClientSetter.getMonitoredHostsZABBIXV2_4(serverType,
					ZabbixMethods.HOST.getzabbixMethod(), null, null, host_uuid, null);

			if (hosts.isEmpty()) {
				throw new IllegalArgumentException(ZabbixConstant.WRONGHOSTNAME);
			}
			hostId = hosts.get(0).getHostid();

			List<ZabbixHostGroupResponse> groups = zabAdapClientSetter.getHostGroupsService(serverType,
					ZabbixMethods.HOSTGROUP.getzabbixMethod(), null, null, hostId);

			if (groups.isEmpty()) {
				throw new IllegalArgumentException();
			}
			groupId = groups.get(0).getGroupid();

			wrappedEvents = wrapperEvents
					.getEvent(serverType, hosts, hostId, groups, groupId, tag_service, requestTime);

			return wrappedEvents;
		} catch (ZabbixException e) {
			throw handleException(e);
		}
	}

	@Override
	public Object getInfoWrapperGeneric(String serverType, String group, String host, String service_category,
			String tag_service, List<String> atomic_service_id, String metric, List<String> triggers_id,
			String history, FilterTimeRequest requestTime) throws MonitoringException {

		if (serverType.equalsIgnoreCase(InfoType.INFRASTRUCTURE.getInfoType())) {
			return super.getOverallIaas(serverType, group, host);
		} else {
			return super.getOverallPaaS(serverType, group, host, service_category, tag_service, atomic_service_id,
					metric, triggers_id, history, requestTime);
		}

	}
}