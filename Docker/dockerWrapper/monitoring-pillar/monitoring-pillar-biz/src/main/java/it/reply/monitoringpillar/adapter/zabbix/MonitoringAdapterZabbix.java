package it.reply.monitoringpillar.adapter.zabbix;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.naming.NameNotFoundException;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.reply.monitoringpillar.adapter.MonitoringAdapteeZabbix;
import it.reply.monitoringpillar.adapter.MonitoringTarget;
import it.reply.monitoringpillar.adapter.zabbix.ZabbixConstant.MetricAction;
import it.reply.monitoringpillar.adapter.zabbix.clientbuilder.ZabbixAdapterClientSetter;
import it.reply.monitoringpillar.adapter.zabbix.exception.DuplicateResourceZabbixException;
import it.reply.monitoringpillar.adapter.zabbix.exception.ZabbixException;
import it.reply.monitoringpillar.adapter.zabbix.handler.GroupCheck;
import it.reply.monitoringpillar.adapter.zabbix.handler.GroupIDByName;
import it.reply.monitoringpillar.adapter.zabbix.handler.HostIDByName;
import it.reply.monitoringpillar.adapter.zabbix.handler.ZabbixFeatures.ZabbixMethods;
import it.reply.monitoringpillar.config.Configuration;
import it.reply.monitoringpillar.domain.dsl.monitoring.InfoType;
import it.reply.monitoringpillar.domain.dsl.monitoring.MonitoringConstant;
import it.reply.monitoringpillar.domain.dsl.monitoring.businesslayer.paas.response.MonitoringVMCredentialsResponse;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.MonitPillarEventResponse;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.iaas.WrappedIaasHealthByTrigger;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas.FilterTimeRequest;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas.MonitoringPillarEventCallbackResponse;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas.MonitoringWrappedResponsePaasGroups;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas.MonitoringWrappedResponsePaasGroups4HostList;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas.MonitoringWrappedResponsePaasGroups4MetricList;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.request.SendMailRequest;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.request.UpdateGroupName;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.response.HostGroupResponse;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.response.MediaTypeResponse;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.response.Usrgrp;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.response.ZabbixGetUserResponse;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.response.ZabbixHostGroupResponse;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.response.ZabbixItemResponse;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.response.ZabbixMonitoredHostResponseV2_4;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.response.ZabbixMonitoredHostsResponse;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.response.ZabbixUpdateProxyResponse;
import it.reply.monitoringpillar.domain.exception.DuplicateResourceMonitoringException;
import it.reply.monitoringpillar.domain.exception.MonitoringException;
import it.reply.monitoringpillar.domain.exception.NotFoundMonitoringException;
import it.reply.monitoringpillar.wrapper.HostsWrapper;
import it.reply.monitoringpillar.wrapper.MetricsWrapperBean;
import it.reply.monitoringpillar.wrapper.zabbix.ZabbixEventBean;
import it.reply.monitoringpillar.wrapper.zabbix.ZabbixGroupBean;
import it.reply.monitoringpillar.wrapper.zabbix.iaas.WrapperIaasHealthBean;
import it.reply.monitoringpillar.wrapper.zabbix.paas.WrapperTriggerPaaSBean;
import it.reply.utils.web.ws.rest.apiclients.prisma.APIErrorException;
import it.reply.utils.web.ws.rest.apiencoding.MappingException;
import it.reply.utils.web.ws.rest.apiencoding.NoMappingModelFoundException;
import it.reply.utils.web.ws.rest.apiencoding.ServerErrorResponseException;
import it.reply.utils.web.ws.rest.restclient.exceptions.RestClientException;

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

	@Inject
	private Configuration config;

	@Inject
	private ZabbixHelperBean zabbixHelper;

	@Inject
	private MetricsWrapperBean wrapperMetrics;

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

	private static Logger LOG = LogManager.getLogger(MonitoringAdapterZabbix.class);

	// GET GROUPS MINIMAL INFO
	@Override
	public MonitoringWrappedResponsePaasGroups getGroupsInfoWrapped(String zone, String serverType)
			throws MonitoringException {

		try {
			List<ZabbixHostGroupResponse> groupsInZabbixPaaSOverall = zabAdapClientSetter.getHostGroupsService(zone,
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
	public WrappedIaasHealthByTrigger getTriggerByGroup(String zone, String serverType, String hostgroup)
			throws MonitoringException {

		WrappedIaasHealthByTrigger triggerGroup = new WrappedIaasHealthByTrigger();
		try {
			boolean groupFound = false;
			checkGroup.isGroupPresent(zone, serverType, hostgroup, null, null);

			ArrayList<ZabbixHostGroupResponse> groupsIntoPlatf = zabAdapClientSetter.getHostGroupsService(zone,
					serverType, ZabbixMethods.HOSTGROUP.getzabbixMethod(), null, null, null);

			for (ZabbixHostGroupResponse group : groupsIntoPlatf) {
				if (group.getName().equalsIgnoreCase(hostgroup)) {
					triggerGroup = wrapperTrigger4Iaas.getTriggerforIaasHealth(zone, serverType, hostgroup);
					groupFound = true;
					break;
				}
			}
			if (!groupFound)
				throw new IllegalArgumentException(ZabbixConstant.WRONGGROUPNAME);
		} catch (ZabbixException e) {
			throw handleException(e);
		}
		return triggerGroup;
	}

	@Override
	public HostGroupResponse updateMonitoredHostGroup(String zone, String serverType, String groupName,
			UpdateGroupName newGroupName) throws MonitoringException {

		HostGroupResponse groupResponse = new HostGroupResponse();
		try {
			String groupid2Zabbix = zabGroupId.getGroupIDsintoZabbix(zone, serverType, groupName, null);
			groupResponse = zabAdapClientSetter.updateHostGroupService(zone, serverType,
					newGroupName.getNewHostGroupName(), groupid2Zabbix,
					ZabbixMethods.HOSTGROUPUPDATE.getzabbixMethod());
		} catch (ZabbixException e) {
			throw handleException(e);
		}
		return groupResponse;
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
	public void creationMonitoredHostGroup(String zone, String serverType, String hostGroupNameId)
			throws MonitoringException {

		String url = null;
		String token = null;
		boolean isDuplicate = false;
		try {
			if (!config.getMonitoringConfigurations().getOptions().isMultipleServers()) {
				url = config.getZabbixServerURL(zone, serverType);
				token = zabbixHelper.getZabbixToken(zone, serverType);
				try {
					zabAdapClientSetter.createHostGroupService(url, token, hostGroupNameId,
							ZabbixMethods.HOSTGROUPCREATE.getzabbixMethod());
				} catch (DuplicateResourceZabbixException e) {
					isDuplicate = true;
				}
			} else {
				if (config.getMonitoringConfigurations().getOptions().isMultipleServers()) {
					// Create Group into Metrics server
					// zabbixHelper.reInitialize(zone);
					url = config.getZabbixServerURL(zone, InfoType.SERVICE.getInfoType());
					token = zabbixHelper.getZabbixToken(zone, InfoType.SERVICE.getInfoType());
					try {
						zabAdapClientSetter.createHostGroupService(url, token, hostGroupNameId,
								ZabbixMethods.HOSTGROUPCREATE.getzabbixMethod());
						if (config.getMonitoringConfigurations().getOptions().isProxyArchitecture()) {
							if (config.getMonitoringZones().getZone(zone).getDistributedArchitecture()) {
								zabAdapClientSetter.createProxyService(zone, url, token, InfoType.SERVICE.getInfoType(),
										hostGroupNameId,
										MonitoringConstant.PROXY_PREFIX + "-"
												+ config.getProxyNames(zone, serverType, hostGroupNameId),
										ZabbixMethods.CREATEPROXY.getzabbixMethod());
							}
						}
					} catch (DuplicateResourceZabbixException e) {
						// If the group already exists it keeps verifying on
						// other
						// server
						// but remembering it!
						isDuplicate = true;
					}
					// Create a Host into Watcher Server
					url = config.getZabbixServerURL(zone, InfoType.WATCHER.getInfoType());
					token = zabbixHelper.getZabbixToken(zone, InfoType.WATCHER.getInfoType());
					try {
						zabAdapClientSetter.createHostGroupService(url, token, hostGroupNameId,
								ZabbixMethods.HOSTGROUPCREATE.getzabbixMethod());
						if (config.getMonitoringConfigurations().getOptions().isProxyArchitecture()) {
							if (config.getMonitoringZones().getZone(zone).getDistributedArchitecture()) {
								zabAdapClientSetter.createProxyService(zone, url, token, InfoType.WATCHER.getInfoType(),
										hostGroupNameId,
										MonitoringConstant.PROXY_PREFIX + "-"
												+ config.getProxyNames(zone, serverType, hostGroupNameId),
										ZabbixMethods.CREATEPROXY.getzabbixMethod());
							}
						}
					} catch (DuplicateResourceZabbixException e) {
						// If the group already exists, keep verifying on other
						// groups
						// but still recording it exists!
						isDuplicate = true;
					}

					// Create a Host into IaaS Server
					url = config.getZabbixServerURL(zone, InfoType.INFRASTRUCTURE.getInfoType());
					token = zabbixHelper.getZabbixToken(zone, InfoType.INFRASTRUCTURE.getInfoType());
					try {
						zabAdapClientSetter.createHostGroupService(url, token, hostGroupNameId,
								ZabbixMethods.HOSTGROUPCREATE.getzabbixMethod());
						if (config.getMonitoringZones().getZone(zone).getDistributedArchitecture()) {
							zabAdapClientSetter.createProxyService(zone, url, token,
									InfoType.INFRASTRUCTURE.getInfoType(), hostGroupNameId,
									MonitoringConstant.PROXY_PREFIX + "-"
											+ config.getProxyNames(zone, serverType, hostGroupNameId),
									ZabbixMethods.CREATEPROXY.getzabbixMethod());
						}
					} catch (DuplicateResourceZabbixException e) {
						// If the group already exists I do verify it anyway on
						// other
						// servers but I remember it
						isDuplicate = true;
					}

					if (isDuplicate) {
						throw new DuplicateResourceMonitoringException(
								"Group [" + hostGroupNameId + "] already exists.");
					}
				} else {
					url = config.getZabbixServerURL(zone, serverType);
					token = zabbixHelper.getZabbixToken(zone, serverType);
					try {
						zabAdapClientSetter.createHostGroupService(url, token, hostGroupNameId,
								ZabbixMethods.HOSTGROUPCREATE.getzabbixMethod());
					} catch (DuplicateResourceZabbixException e) {
						isDuplicate = true;
					}
				}
			}
		} catch (

		ZabbixException e)

		{
			throw handleException(e);
		}

	}

	/*****************
	 * Method for checking whether distributed Architecture is implemented. In
	 * this case, when creating a group, becomes important creating a proxy
	 * whose Id is the same as the Workgroup's (tenant in Infrastructure), in
	 * order to have an automatic mapping into monitoring platforms product:
	 * wg's ID <--> proxy's ID
	 * 
	 * @param url
	 * @param token
	 * @param hostGroupNameId
	 * @throws ZabbixException
	 * @throws MonitoringException
	 */
	@Override
	public void creationMonitoredProxy(String zone, String serverType, String hostGroup, String proxyName)
			throws MonitoringException {

		String url = null;
		String token = null;
		boolean isDuplicate = false;
		try {
			checkGroup.isGroupPresent(zone, serverType, hostGroup, null, null);
			if (!config.getMonitoringConfigurations().getOptions().isMultipleServers()
					&& config.isProxyArchitectureExisting(zone)) {
				url = config.getZabbixServerURL(zone, serverType);
				token = zabbixHelper.getZabbixToken(zone, serverType);
				zabAdapClientSetter.createProxyService(zone, url, token, serverType, hostGroup, proxyName,
						ZabbixMethods.CREATEPROXY.getzabbixMethod());
			}

			else {

				try {
					// Create Group into Metrics server
					url = config.getZabbixServerURL(zone, InfoType.SERVICE.getInfoType());
					token = zabbixHelper.getZabbixToken(zone, InfoType.SERVICE.getInfoType());
					zabAdapClientSetter.createProxyService(zone, url, token, serverType, hostGroup, proxyName,
							ZabbixMethods.CREATEPROXY.getzabbixMethod());

				} catch (DuplicateResourceZabbixException e) {
					// If the group already exists it keeps verifying on other
					// server
					// but remembering it!
					isDuplicate = true;
				}

				try {
					// Create a Host into Watcher Server
					url = config.getZabbixServerURL(zone, InfoType.WATCHER.getInfoType());
					token = zabbixHelper.getZabbixToken(zone, InfoType.WATCHER.getInfoType());
					zabAdapClientSetter.createProxyService(zone, url, token, serverType, hostGroup, proxyName,
							ZabbixMethods.CREATEPROXY.getzabbixMethod());
				} catch (DuplicateResourceZabbixException e) {
					// Se il gruppo esiste già faccio comunque una verifica
					// sugli
					// altri server ma mi ricordo che esiste!
					isDuplicate = true;
				}

				// Create a Host into IaaS Server
				url = config.getZabbixServerURL(zone, InfoType.INFRASTRUCTURE.getInfoType());
				token = zabbixHelper.getZabbixToken(zone, InfoType.INFRASTRUCTURE.getInfoType());
				try {
					zabAdapClientSetter.createProxyService(zone, url, token, serverType, hostGroup, proxyName,
							ZabbixMethods.CREATEPROXY.getzabbixMethod());
				} catch (DuplicateResourceZabbixException e) {
					// If the group already exists I do verify it anyway on
					// other
					// servers but I remember it
					isDuplicate = true;
				}

				if (isDuplicate) {
					throw new DuplicateResourceMonitoringException("Group [" + hostGroup + "] already exists.");
				}

			}
		} catch (ZabbixException ze) {
			handleException(ze);
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
	public void creationMonitoredHost(String zone, String serverType, String hostGroup, String hostName, String vmuuid,
			String vmip, String serviceCategory, String serviceTag, List<String> services, Boolean activeMode,
			List<it.reply.monitoringpillar.domain.dsl.monitoring.businesslayer.paas.request.Port> ports,
			String proxyapi) throws MonitoringException {

		boolean isDuplicate = false;
		ArrayList<MonitoringVMCredentialsResponse> createdHosts = new ArrayList<MonitoringVMCredentialsResponse>();

		try {
			if (!config.getMonitoringConfigurations().getOptions().isMultipleServers()) {
				if (checkGroup.isGroupPresent(zone, serverType, hostGroup, null, null)) {
					try {
						MonitoringVMCredentialsResponse createdHostintoIaaS = zabAdapClientSetter.createHostService(
								zone, serverType, hostGroup, hostName, vmuuid, vmip, serviceCategory, serviceTag,
								services, activeMode, ZabbixMethods.HOSTCREATE.getzabbixMethod(), ports, proxyapi);

						createdHosts.add(createdHostintoIaaS);
					} catch (DuplicateResourceZabbixException e) {
						isDuplicate = true;
					}
				}
			} else {

				/*************
				 * IAAS CASE
				 ************/
				if (serverType.toLowerCase().equals(InfoType.INFRASTRUCTURE)) {
					// ZABBIX-IAAS
					if (checkGroup.isGroupPresent(zone, InfoType.INFRASTRUCTURE.getInfoType(), hostGroup, null, null)) {
						try {
							MonitoringVMCredentialsResponse createdHostintoIaaS = zabAdapClientSetter.createHostService(
									zone, InfoType.INFRASTRUCTURE.getInfoType(), hostGroup, hostName, vmuuid, vmip,
									serviceCategory, serviceTag, services, activeMode,
									ZabbixMethods.HOSTCREATE.getzabbixMethod(), ports, proxyapi);

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
					if (checkGroup.isGroupPresent(zone, InfoType.SERVICE.getInfoType(), hostGroup, null, null)
							&& checkGroup.isGroupPresent(zone, InfoType.WATCHER.getInfoType(), hostGroup, null, null)) {
						try {
							MonitoringVMCredentialsResponse createdHostintoMetrics = zabAdapClientSetter
									.createHostService(zone, InfoType.SERVICE.getInfoType(), hostGroup, hostName,
											vmuuid, vmip, serviceCategory, serviceTag, services, activeMode,
											ZabbixMethods.HOSTCREATE.getzabbixMethod(), ports, proxyapi);

							createdHosts.add(createdHostintoMetrics);
						} catch (DuplicateResourceZabbixException e) {
							isDuplicate = true;
						}
						// Create a Host into Watcher Server
						try {
							MonitoringVMCredentialsResponse createdHostintoWatcher = zabAdapClientSetter
									.createHostService(zone, InfoType.WATCHER.getInfoType(), hostGroup, hostName,
											vmuuid, vmip, serviceCategory, serviceTag, services, activeMode,
											ZabbixMethods.HOSTCREATE.getzabbixMethod(), ports, proxyapi);

							createdHosts.add(createdHostintoWatcher);
						} catch (DuplicateResourceZabbixException e) {
							isDuplicate = true;
						}
					}
				}
			}
			if (isDuplicate) {
				throw new DuplicateResourceMonitoringException("Host already exists.");
			}

		} catch (NameNotFoundException e1) {
			e1.printStackTrace();
		} catch (ZabbixException e) {
			handleException(e);
		}
	}

	/*****************************************************************************
	 * This method removes the selected host by passing the vmuuid as
	 * information HOST DELETE from IaaS and PaaS depending upon some conditions
	 *****************************************************************************/
	@Override
	public void deleteMonitoredHost(String zone, String serverType, String hostvmuuids, String serviceId)
			throws MonitoringException {

		try {
			ArrayList<MonitoringVMCredentialsResponse> removedPaasHosts = new ArrayList<MonitoringVMCredentialsResponse>();
			MonitoringVMCredentialsResponse hostRemovedFromMetrics = new MonitoringVMCredentialsResponse();
			MonitoringVMCredentialsResponse hostRemovedFromWatcher = new MonitoringVMCredentialsResponse();
			MonitoringVMCredentialsResponse hostInfo = new MonitoringVMCredentialsResponse();
			ArrayList<String> hostIdsnames = new ArrayList<>();
			hostIdsnames.add(hostvmuuids);
			hostInfo.setHostids(hostIdsnames);
			Boolean isHostExisting = true;
			String url = null;
			String token = null;

			/*************
			 * GENERAL CASE
			 ************/
			if (!config.getMonitoringConfigurations().getOptions().isMultipleServers()) {

				try {
					url = config.getZabbixServerURL(zone, serverType);
					token = zabbixHelper.getZabbixToken(zone, serverType);
					String hostid2Zabbix = zabHostId.getHostID(zone, serverType, null, hostvmuuids, null);

					MonitoringVMCredentialsResponse hostRemovedFromIaaS = zabAdapClientSetter.deleteHostService(url,
							token, hostid2Zabbix, ZabbixMethods.HOSTDELETE.getzabbixMethod());
					removedPaasHosts.add(hostRemovedFromIaaS);
				} catch (DuplicateResourceZabbixException e) {
					isHostExisting = true;
				}
			}

			else {
				/*************
				 * IAAS CASE
				 ************/
				if (serverType.toLowerCase().equalsIgnoreCase(InfoType.INFRASTRUCTURE.getInfoType())) {
					// Create a Host into Metrics Server
					try {
						// if(hostExists())
						url = config.getZabbixServerURL(zone, InfoType.INFRASTRUCTURE.getInfoType());
						token = zabbixHelper.getZabbixToken(zone, InfoType.INFRASTRUCTURE.getInfoType());
						String hostid2Zabbix = zabHostId.getHostID(zone, InfoType.INFRASTRUCTURE.getInfoType(), null,
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
						url = config.getZabbixServerURL(zone, InfoType.SERVICE.getInfoType());
						token = zabbixHelper.getZabbixToken(zone, InfoType.SERVICE.getInfoType());
						String hostid2Zabbix = zabHostId.getHostID(zone, InfoType.SERVICE.getInfoType(), null,
								hostvmuuids, null);

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
						url = config.getZabbixServerURL(zone, InfoType.WATCHER.getInfoType());
						token = zabbixHelper.getZabbixToken(zone, InfoType.WATCHER.getInfoType());
						String hostid2Zabbix = zabHostId.getHostID(zone, InfoType.WATCHER.getInfoType(), null,
								hostvmuuids, null);

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
			}
		} catch (ZabbixException e) {
			throw handleException(e);
		}
	}

	/*********************
	 * DELETE HOSTGROUP
	 *********************/
	@Override
	public void deleteMonitoredHostGroup(String zone, String serverType, String hostGroupName)
			throws MonitoringException {

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
			 * GENERAL CASE
			 ************/
			if (!config.getMonitoringConfigurations().getOptions().isMultipleServers()) {
				try {
					String groupid2Zabbix = zabGroupId.getGroupIDsintoZabbix(zone, serverType, hostGroupName, null);

					hostRemovedFromIaaS = zabAdapClientSetter.deleteHostGroupService(zone, serverType, groupid2Zabbix,
							ZabbixMethods.GROUPDELETE.getzabbixMethod());
					removedPaasHosts.add(hostRemovedFromIaaS);
				} catch (DuplicateResourceZabbixException e) {
					isHostExisting = true;
				}
			} else {
				/*************
				 * IAAS CASE
				 ************/
				try {
					String groupid2Zabbix = zabGroupId.getGroupIDsintoZabbix(zone,
							InfoType.INFRASTRUCTURE.getInfoType(), hostGroupName, null);

					hostRemovedFromIaaS = zabAdapClientSetter.deleteHostGroupService(zone,
							InfoType.INFRASTRUCTURE.getInfoType(), groupid2Zabbix,
							ZabbixMethods.GROUPDELETE.getzabbixMethod());
					removedPaasHosts.add(hostRemovedFromIaaS);
				} catch (DuplicateResourceZabbixException e) {
					isHostExisting = true;
				}
				/********************
				 * For ZABBIX METRICS
				 ********************/
				try {
					String groupid2Zabbix = zabGroupId.getGroupIDsintoZabbix(zone, InfoType.SERVICE.getInfoType(),
							hostGroupName, null);

					hostRemovedFromMetrics = zabAdapClientSetter.deleteHostGroupService(zone,
							InfoType.SERVICE.getInfoType(), groupid2Zabbix,
							ZabbixMethods.GROUPDELETE.getzabbixMethod());
					removedPaasHosts.add(hostRemovedFromMetrics);
				} catch (DuplicateResourceZabbixException e) {
					isHostExisting = true;
				}
				/************************
				 * For ZABBIX WATCHER
				 ***********************/
				try {
					String groupid2Zabbix = zabGroupId.getGroupIDsintoZabbix(zone, InfoType.WATCHER.getInfoType(),
							hostGroupName, null);

					hostRemovedFromWatcher = zabAdapClientSetter.deleteHostGroupService(zone,
							InfoType.WATCHER.getInfoType(), groupid2Zabbix,
							ZabbixMethods.GROUPDELETE.getzabbixMethod());
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
	 * DELETE PROXY
	 *********************/
	@Override
	public void deleteMonitoredProxy(String zone, String serverType, String proxyName) throws MonitoringException {

		try {
			ArrayList<ZabbixUpdateProxyResponse> removedProxy = new ArrayList<ZabbixUpdateProxyResponse>();
			ZabbixUpdateProxyResponse proxyRemovedFromMetrics = new ZabbixUpdateProxyResponse();
			ZabbixUpdateProxyResponse proxyRemovedFromIaaS = new ZabbixUpdateProxyResponse();
			ZabbixUpdateProxyResponse proxyRemovedFromWatcher = new ZabbixUpdateProxyResponse();
			List<String> proxyIds = new ArrayList<>();
			proxyIds.add(proxyName);
			// proxy.setProxyid(proxyIds);
			boolean isHostExisting = true;

			if (!config.getMonitoringConfigurations().getOptions().isMultipleServers()) {
				try {
					proxyRemovedFromIaaS = zabAdapClientSetter.deleteProxyService(zone, serverType,
							zabbixHelper.getProxyId(zone, serverType, proxyName),
							ZabbixMethods.DELETEPROXY.getzabbixMethod());
					removedProxy.add(proxyRemovedFromIaaS);
				} catch (DuplicateResourceZabbixException e) {
					isHostExisting = true;
				}
			} else {
				if (serverType.equalsIgnoreCase(InfoType.ALL.getInfoType())) {
					/*************
					 * IAAS CASE
					 ************/
					try {
						proxyRemovedFromIaaS = zabAdapClientSetter.deleteProxyService(zone,
								InfoType.INFRASTRUCTURE.getInfoType(),
								zabbixHelper.getProxyId(zone, InfoType.INFRASTRUCTURE.getInfoType(), proxyName),
								ZabbixMethods.DELETEPROXY.getzabbixMethod());
						removedProxy.add(proxyRemovedFromIaaS);
					} catch (DuplicateResourceZabbixException e) {
						isHostExisting = true;
					}
					/********************
					 * For ZABBIX METRICS
					 ********************/
					try {

						proxyRemovedFromMetrics = zabAdapClientSetter.deleteProxyService(zone,
								InfoType.SERVICE.getInfoType(),
								zabbixHelper.getProxyId(zone, InfoType.SERVICE.getInfoType(), proxyName),
								ZabbixMethods.DELETEPROXY.getzabbixMethod());
						removedProxy.add(proxyRemovedFromMetrics);
					} catch (DuplicateResourceZabbixException e) {
						isHostExisting = true;
					}
					/************************
					 * AND For ZABBIX WATCHER
					 ***********************/
					try {

						proxyRemovedFromWatcher = zabAdapClientSetter.deleteProxyService(zone,
								InfoType.WATCHER.getInfoType(),
								zabbixHelper.getProxyId(zone, InfoType.SERVICE.getInfoType(), proxyName),
								ZabbixMethods.DELETEPROXY.getzabbixMethod());
						removedProxy.add(proxyRemovedFromWatcher);
					} catch (DuplicateResourceZabbixException e) {
						isHostExisting = true;
					}

					if (!isHostExisting) {
						throw new DuplicateResourceMonitoringException("Proxy already exists.");
					}
				} else {
					zabAdapClientSetter.deleteProxyService(zone, serverType,
							zabbixHelper.getProxyId(zone, serverType, proxyName),
							ZabbixMethods.DELETEPROXY.getzabbixMethod());
				}
			}
		} catch (ZabbixException e) {
			throw handleException(e);
		} catch (NameNotFoundException e1) {
			throw new MonitoringException("Unable to find the proxy parameter passed");
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
	public void getDisablingHost(String zone, String serverType, String group, String host_uuid, String metric_id,
			String tag_service, String update) throws MonitoringException {

		try {
			String hostid2Zabbix = null;

			checkGroup.isGroupPresent(zone, serverType, group, host_uuid, tag_service);

			// In case uuid has been passed as parameter
			if (tag_service == null && host_uuid != null && metric_id == null) {
				ArrayList<ZabbixMonitoredHostsResponse> hostsinZabbixPaaS = zabAdapClientSetter.getHostsService(zone,
						serverType, ZabbixMethods.HOST.getzabbixMethod(), null, null, host_uuid, null);
				for (ZabbixMonitoredHostsResponse host : hostsinZabbixPaaS) {
					hostid2Zabbix = host.getHostid();
				}
			}

			zabAdapClientSetter.updateHostService(zone, serverType, hostid2Zabbix, null,
					ZabbixMethods.MASSUPDATE.getzabbixMethod(), MetricAction.lookupFromName(update));
		} catch (ZabbixException e) {
			throw handleException(e);
		}
	}

	@Override
	public void getDisablingItem(String zone, String serverType, String group, String host, String metric_id,
			String tag_service, String update) throws MonitoringException {

		try {
			List<ZabbixItemResponse> item_id = new ArrayList<>();
			String hostid2Zabbix = null;
			String metricName = null;
			String metricIdtoAdapter = null;

			checkGroup.isGroupPresent(zone, serverType, group, host, null);

			if (!(metric_id == null)) {
				ArrayList<ZabbixMonitoredHostsResponse> hostsinZabbixPaaS = zabAdapClientSetter.getHostsService(zone,
						serverType, ZabbixMethods.HOST.getzabbixMethod(), null, null, host, null);

				hostid2Zabbix = hostsinZabbixPaaS.get(0).getHostid();

				item_id = zabAdapClientSetter.getItemsService(zone, serverType, hostid2Zabbix, null, null, null,
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

				zabAdapClientSetter.updateItemService(zone, serverType, hostid2Zabbix, metricIdtoAdapter,
						ZabbixMethods.ITEMUPDATE.getzabbixMethod(), MetricAction.lookupFromName(update));
			}
		} catch (ZabbixException e) {
			throw handleException(e);
		}
	}

	// GET HOST MINIMAL INFO
	@Override
	public MonitoringWrappedResponsePaasGroups4HostList getHostsInfoWrapped(String zone, String serverType,
			String groupName) throws MonitoringException {

		MonitoringWrappedResponsePaasGroups4HostList groupResponse = new MonitoringWrappedResponsePaasGroups4HostList();
		try {
			try {
			} catch (MonitoringException e) {
				e.printStackTrace();
			}
			checkGroup.isGroupPresent(zone, serverType, groupName, null, null);
			String groupId = null;
			List<ZabbixHostGroupResponse> groupUseful = new ArrayList<>();
			List<ZabbixHostGroupResponse> groups = zabAdapClientSetter.getHostGroupsService(zone, serverType,
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

			ArrayList<ZabbixMonitoredHostResponseV2_4> hosts = zabAdapClientSetter.getMonitoredHostsZABBIXV2_4(zone,
					serverType, ZabbixMethods.HOST.getzabbixMethod(), null, groupId, null, null);

			groupResponse = hostswrapp.getHostsWrapped(zone, serverType, groupName, groupUseful, hosts);
		} catch (ZabbixException e) {
			throw handleException(e);
		}
		return groupResponse;
	}

	/*******************************************
	 * METRICS WRAPPPER IT GETS METRICS MINIMAL INFO
	 ************************************/
	@Override
	public MonitoringWrappedResponsePaasGroups4MetricList getMetricsByHost(String zone, String serverType,
			String groupName, String hostName) throws MonitoringException {

		MonitoringWrappedResponsePaasGroups4MetricList metricsWrapped = new MonitoringWrappedResponsePaasGroups4MetricList();
		List<ZabbixMonitoredHostResponseV2_4> hosts = new ArrayList<>();
		ZabbixHostGroupResponse hostGroup = new ZabbixHostGroupResponse();
		List<ZabbixHostGroupResponse> groupList = new ArrayList<>();
		try {
			checkGroup.isGroupPresent(zone, serverType, groupName, null, null);
			List<ZabbixHostGroupResponse> groups = zabAdapClientSetter.getHostGroupsService(zone, serverType,
					ZabbixMethods.HOSTGROUP.getzabbixMethod(), null, null, null);
			for (ZabbixHostGroupResponse group : groups) {
				if (groupName.equalsIgnoreCase(group.getName())) {
					hostGroup = group;
					groupList.add(hostGroup);
					break;
				}
			}
			hosts = zabAdapClientSetter.getMonitoredHostsZABBIXV2_4(zone, serverType,
					ZabbixMethods.HOST.getzabbixMethod(), null, null, hostName, null);
			if (hosts.isEmpty())
				throw new IllegalArgumentException(ZabbixConstant.WRONGHOSTNAME);

			metricsWrapped = wrapperMetrics.getMetricsWrapped(zone, serverType, groupName, groupList, hostGroup, hosts);

		} catch (ZabbixException e) {
			throw handleException(e);
		}
		return metricsWrapped;
	}

	/**********************************
	 * TRIGGER EVENTS IN PAAS PLATFORM
	 **********************************/
	@Override
	public WrappedIaasHealthByTrigger getTriggerByHost(String zone, String serverType, String group, String host)
			throws MonitoringException {

		WrappedIaasHealthByTrigger triggerHealth = new WrappedIaasHealthByTrigger();
		try {
			String hostId = null;
			String groupName = null;

			checkGroup.isGroupPresent(zone, serverType, group, host, null);

			List<ZabbixMonitoredHostResponseV2_4> hostsinZabbixPaaS = zabAdapClientSetter.getMonitoredHostsZABBIXV2_4(
					zone, serverType, ZabbixMethods.HOST.getzabbixMethod(), null, null, host, null);

			hostId = hostsinZabbixPaaS.get(0).getHostid();

			List<ZabbixHostGroupResponse> groups = zabAdapClientSetter.getHostGroupsService(zone, serverType,
					ZabbixMethods.HOSTGROUP.getzabbixMethod(), null, null, hostId);

			if (groups.isEmpty()) {
				throw new IllegalArgumentException(ZabbixConstant.WRONGGROUPNAME);
			}
			groupName = groups.get(0).getName();

			if (hostsinZabbixPaaS.get(0).getName().equalsIgnoreCase(host))
				triggerHealth = wrapperTriggerPaas.getTriggerWrapper(zone, serverType, hostsinZabbixPaaS, groupName);
			else
				throw new IllegalArgumentException(ZabbixConstant.WRONGGROUPNAME);
		} catch (ZabbixException e) {
			throw handleException(e);
		}
		return triggerHealth;
	}

	/*****************************************
	 * IT WRAPS EVENTS RETURNED FROM PLATFORM
	 *****************************************/

	@Override
	public MonitPillarEventResponse getOverallServerEvents(String zone, String serverType, String host, String group,
			String tag_service, FilterTimeRequest requestTime) throws MonitoringException {

		MonitPillarEventResponse wrappedEvents = new MonitPillarEventResponse();
		try {
			String hostId = null;
			String groupId = null;

			checkGroup.isGroupPresent(zone, serverType, group, host, tag_service);

			// IF VMUUID is specified
			List<ZabbixMonitoredHostResponseV2_4> hosts = zabAdapClientSetter.getMonitoredHostsZABBIXV2_4(zone,
					serverType, ZabbixMethods.HOST.getzabbixMethod(), null, null, host, null);

			if (hosts.isEmpty()) {
				throw new IllegalArgumentException(ZabbixConstant.WRONGHOSTNAME);
			}
			hostId = hosts.get(0).getHostid();

			List<ZabbixHostGroupResponse> groups = zabAdapClientSetter.getHostGroupsService(zone, serverType,
					ZabbixMethods.HOSTGROUP.getzabbixMethod(), null, null, hostId);

			if (groups.isEmpty()) {
				throw new IllegalArgumentException();
			}
			groupId = groups.get(0).getGroupid();

			return wrapperEvents.getEvent(zone, serverType, hosts, hostId, groups, groupId, tag_service, requestTime);
		} catch (ZabbixException e) {
			throw handleException(e);
		}
	}

	@Override
	public Object getInfoWrapperGeneric(String zone, String serverType, String group, String host,
			String service_category, String tag_service, List<String> atomic_service_id, String metric,
			List<String> triggers_id, String history, FilterTimeRequest requestTime) throws MonitoringException {

		if (config.getMonitoringConfigurations().getOptions().isMultipleServers()) {
			if (serverType.equalsIgnoreCase(InfoType.INFRASTRUCTURE.getInfoType())) {
				return super.getOverallIaas(zone, serverType, group, host);
			} else {
				return super.getOverallPaaS(zone, serverType, group, host, service_category, tag_service,
						atomic_service_id, metric, triggers_id, history, requestTime);
			}
		} else {
			return super.getOverallPaaS(zone, serverType, group, host, service_category, tag_service, atomic_service_id,
					metric, triggers_id, history, requestTime);
		}

	}

	@Override
	public MonitoringPillarEventCallbackResponse manageCallbackEvent(String zone, String group, String hostName,
			String vmuuid, String hostServiceId, String hostIP, String metric, String threshold, String status,
			String description) throws MonitoringException {

		MonitoringPillarEventCallbackResponse eventWrappedCallback = new MonitoringPillarEventCallbackResponse();
		eventWrappedCallback.setVmuuid(vmuuid);
		eventWrappedCallback.setServiceId(hostServiceId);
		eventWrappedCallback.setTenant(group);
		eventWrappedCallback.setVmName(hostName);
		eventWrappedCallback.setIp(hostIP);
		eventWrappedCallback.setStatus(status);
		eventWrappedCallback.setMetricName(metric);
		eventWrappedCallback.setTriggerName(threshold);
		eventWrappedCallback.setDescription(description);
		eventWrappedCallback.setTime(System.currentTimeMillis());

		LOG.log(Level.forName("CUSTOM_LOG", 350),
				"{" + "\"vm_uuid\":" + "\"" + eventWrappedCallback.getVmuuid() + "\", " + "\"vm_name\":" + "\""
						+ eventWrappedCallback.getVmName() + "\", " + "\"tenant\":" + "\""
						+ eventWrappedCallback.getTenant() + "\", " + "\"vm_ip\":" + "\"" + eventWrappedCallback.getIp()
						+ "\", " + "\"service_id\": " + "\"" + eventWrappedCallback.getServiceId() + "\", "
						+ "\"metric_name\":" + "\"" + eventWrappedCallback.getMetricName() + "\", "
						+ "\"threshold_name\":" + "\"" + eventWrappedCallback.geTriggerName() + "\", "
						+ "\"event_type\":" + "\"" + eventWrappedCallback.getStatus() + "\", " + "\"description\":"
						+ "\"" + eventWrappedCallback.getDescription() + "\", " + "\"time\":" + "\""
						+ eventWrappedCallback.getTime() + "\"" + "}");

		return eventWrappedCallback;
	}

	/**
	 * 
	 */
	@Override
	public void getSendMails(String zone, String serverType, SendMailRequest sendMailRequest)
			throws MonitoringException {

		String userGroupId = null;
		boolean mediaTypeFound = false;
		String mediaID = null;
		try {
			List<ZabbixGetUserResponse> users = zabAdapClientSetter.getUsersService(zone, serverType);
			for (ZabbixGetUserResponse user : users) {

				for (Usrgrp userGroup : user.getUsrgrps()) {

					if (userGroup.getName().equalsIgnoreCase(ZabbixConstant.ZAB_ADMINS)) {
						userGroupId = userGroup.getUsrgrpid();
						break;
					}
				}
				for (MediaTypeResponse media : user.getMediatypes()) {

					if (sendMailRequest.getScriptName().equalsIgnoreCase(media.getExecPath())) {
						mediaID = media.getMediatypeid();
						mediaTypeFound = true;
						break;
					}
				}
				if (!mediaTypeFound)
					throw new MonitoringException(
							"Script name inserted does not match with the one set into monitoring platform");
			}

			zabAdapClientSetter.createUserService(zone, serverType, sendMailRequest.getUsername(),
					sendMailRequest.getPassword(), sendMailRequest.getSendmailTo(), userGroupId, mediaID);

		} catch (ZabbixException e) {
			handleException(e);
		}

	}

}