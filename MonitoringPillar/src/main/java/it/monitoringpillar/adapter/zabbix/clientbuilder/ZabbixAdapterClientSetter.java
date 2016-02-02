package it.monitoringpillar.adapter.zabbix.clientbuilder;

import it.monitoringpillar.adapter.zabbix.ZabbixBase;
import it.monitoringpillar.adapter.zabbix.ZabbixConstant;
import it.monitoringpillar.adapter.zabbix.ZabbixConstant.MetricAction;
import it.monitoringpillar.adapter.zabbix.ZabbixHelperBean;
import it.monitoringpillar.adapter.zabbix.exception.ZabbixException;
import it.monitoringpillar.adapter.zabbix.handler.GroupIDByName;
import it.monitoringpillar.adapter.zabbix.handler.TemplateIDByName;
import it.monitoringpillar.adapter.zabbix.handler.ZabbixFeatures.ZabbixMethods;
import it.monitoringpillar.config.Configuration;
import it.monitoringpillar.utility.TimestampMonitoring;
import it.prisma.domain.dsl.monitoring.InfoType;
import it.prisma.domain.dsl.monitoring.MonitoringConstant;
import it.prisma.domain.dsl.monitoring.businesslayer.paas.request.Port;
import it.prisma.domain.dsl.monitoring.businesslayer.paas.response.MonitoringVMCredentialsResponse;
import it.prisma.domain.dsl.monitoring.pillar.wrapper.paas.FilterTimeRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.HostGroupParamRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.HostIDMassUpdate;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.JSONRPCRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.JsonRpcDeleteHostRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.Macros;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.SearchInventory;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.TriggerParamRequestByGroup;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.Zabbix2_4ParamHost;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.ZabbixFilterRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.ZabbixParamCreateHostRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.ZabbixParamCreateProxyRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.ZabbixParamGroupIntoHostCreateRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.ZabbixParamHistoryRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.ZabbixParamHostGroupRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.ZabbixParamInterfaceIntoHostCreateRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.ZabbixParamItemRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.ZabbixParamRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.ZabbixParamTemplateRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.ZabbixParamTrendRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.ZabbixParamTriggerRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.ZabbixParamUpdate;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.ZabbixSearchKeyRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.ZabbixUpdateProxyRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.response.HostGroupResponse;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.response.Inventory;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.response.UpdatedItemsResponse;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.response.ZabbixHostGroupResponse;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.response.ZabbixItemResponse;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.response.ZabbixMonitoredHostResponseV2_4;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.response.ZabbixMonitoredHostsResponse;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.response.ZabbixTemplateResponse;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.response.ZabbixTemplateResponseV2_4;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.response.ZabbixUpdateProxyResponse;
import it.prisma.utils.datetime.FilterTimeRequestHandlerMonitoring;
import it.prisma.utils.web.ws.rest.apiclients.prisma.APIErrorException;
import it.prisma.utils.web.ws.rest.apiclients.zabbix.ZabbixAPIClient;
import it.prisma.utils.web.ws.rest.apiclients.zabbix.exception.ZabbixClientException;
import it.prisma.utils.web.ws.rest.apiencoding.MappingException;
import it.prisma.utils.web.ws.rest.apiencoding.NoMappingModelFoundException;
import it.prisma.utils.web.ws.rest.apiencoding.ServerErrorResponseException;
import it.prisma.utils.web.ws.rest.restclient.exceptions.RestClientException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ZabbixAdapterClientSetter<T> extends ZabbixBase implements Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private GroupIDByName groupid;

	@EJB
	private TemplateIDByName idv2;

	@Inject
	private ZabbixHelperBean zabbixHelper;

	@Inject
	private Configuration config;

	/************************
	 * CREATE HOST IN ZABBIX
	 ************************/

	/**
	 * Gets host's created ID in PrismaMetrics
	 * 
	 * 
	 */
	public MonitoringVMCredentialsResponse createHostService(InfoType serverType, String hostGroup, String hostName,
			String vmuuid, String vmIP, String serviceCategory, String serviceTag, List<String> services,
			Boolean activeMode, String zabbixMethod, List<Port> ports) throws ZabbixException {
		try {
			ZabbixAPIClient zabClient = new ZabbixAPIClient(zabbixHelper.getZabbixURL(serverType.toString()));

			JSONRPCRequest<ZabbixParamCreateHostRequest> requestCreate = new JSONRPCRequest<ZabbixParamCreateHostRequest>();
			requestCreate.setJsonrpc(zabbixHelper.getZabbixRPCVersion());
			requestCreate.setMethod(zabbixMethod);

			requestCreate.setAuth(zabbixHelper.getZabbixToken(serverType.toString()));

			Macros macro = new Macros();
			List<Macros> macros = new ArrayList<>();
			if (hostName.toLowerCase().contains(MonitoringConstant.ATOMIC_SERVICE_AGGREGATOR.toLowerCase())) {
				macro.setMacro("{$GROUPID}");
				macro.setValue(hostGroup);
				macros.add(macro);
			} else {
				macro = new Macros();
				macro.setMacro("{$UUID}");
				macro.setValue(vmuuid);
				macros.add(macro);
				if (ports != null && !ports.isEmpty()) {
					for (Port port : ports) {
						macro = new Macros();
						macro.setValue(getPortName(port));
						macro.setMacro("{$" + port.getPortName() + "}");
						macros.add(macro);
					}
					if (ports.isEmpty()) {
						throw new ZabbixException("MACRO Name not Listed into Property");
					}
				}
			}
			ZabbixParamCreateHostRequest hostCreationParameters = new ZabbixParamCreateHostRequest();
			hostCreationParameters.setMacros(macros);

			hostCreationParameters.setHost(hostName);

			if (config.isProxyEnabled())
				hostCreationParameters.setProxy_hostid(zabbixHelper.getStoredProxyId(serverType));

			ZabbixParamInterfaceIntoHostCreateRequest interfaceParameter = new ZabbixParamInterfaceIntoHostCreateRequest();
			interfaceParameter.setMain(1);
			interfaceParameter.setIp(vmIP);
			interfaceParameter.setDns("");
			interfaceParameter.setType(1);
			interfaceParameter.setPort(ZabbixConstant.ZABBIX_PORT_AGENT);
			interfaceParameter.setUseip(1);

			ArrayList<ZabbixParamInterfaceIntoHostCreateRequest> interfaceArray = new ArrayList<ZabbixParamInterfaceIntoHostCreateRequest>();
			interfaceArray.add(interfaceParameter);
			String groupID = null;

			if (services == null) {
				String ceilometer = "";
				services = new ArrayList<>();
				services.add(ceilometer);
			}

			for (int i = 0; i < services.size(); i++) {
				groupID = groupid.getGroupIDsintoZabbix(serverType.toString(), hostGroup, null);
			}
			ZabbixParamGroupIntoHostCreateRequest grouprequest = new ZabbixParamGroupIntoHostCreateRequest();
			grouprequest.setGroupid(groupID);
			ArrayList<ZabbixParamGroupIntoHostCreateRequest> groupParamArray = new ArrayList<ZabbixParamGroupIntoHostCreateRequest>();
			groupParamArray.add(grouprequest);

			hostCreationParameters.setGroups(groupParamArray);
			hostCreationParameters.setInterfaces(interfaceArray);

			hostCreationParameters.setTemplates(idv2.getTemplateID(serverType.toString(), services, activeMode));

			Inventory inventory = new Inventory();
			inventory.setTag(serviceTag);
			inventory.setType(serviceCategory);
			hostCreationParameters.setInventory(inventory);

			requestCreate.setParams(hostCreationParameters);
			requestCreate.setId(ZabbixConstant.ID);

			return zabClient.createHostClient(requestCreate);
		} catch (ZabbixClientException e) {
			throw handleException(e);
		}
	}

	private boolean isPortNameInProperties(Port port) {
		return config.isMACROexisting(port.getPortName());
	}

	private String getPortName(Port port) throws ZabbixClientException {
		if (isPortNameInProperties(port)) {
			return port.getValue();
		} else
			throw new ZabbixClientException("PORT LISTED into Properties File does not match with the passed one");
	}

	/***********************
	 * DELETE HOST IN ZABBIX
	 ***********************/

	/**
	 * It removes the monitored host
	 * 
	 * 
	 * @param hostid
	 * @param zabbixMethod
	 * @return the host's ID
	 * @throws MappingException
	 * @throws NoMappingModelFoundException
	 * @throws ServerErrorResponseException
	 * @throws it.prisma.utils.web.ws.rest.apiclients.zabbix.ZabbixAPIErrorException
	 * @throws RestClientException
	 * @throws Exception
	 */
	public MonitoringVMCredentialsResponse deleteHostService(String url, String token, String hostid,
			String zabbixMethod) throws ZabbixException {
		try {
			// ZabAPIClient zabClient = new ZabAPIClient(url);
			ZabbixAPIClient zabClient = new ZabbixAPIClient(url);
			JsonRpcDeleteHostRequest requestDelete = new JsonRpcDeleteHostRequest();
			requestDelete.setJsonrpc(zabbixHelper.getZabbixRPCVersion());
			requestDelete.setMethod(zabbixMethod);
			ArrayList<String> host2delete = new ArrayList<>();
			host2delete.add(hostid);
			requestDelete.setParams(host2delete);

			requestDelete.setAuth(token);

			requestDelete.setId(ZabbixConstant.ID);

			return zabClient.deleteHostClient(requestDelete);
		} catch (ZabbixClientException e) {
			throw handleException(e);
		}
	}

	/*******************
	 * DELETE HOSTGROUP
	 * 
	 * @throws ServerErrorResponseException
	 * @throws RestClientException
	 * @throws ZabbixAPIErrorException
	 ******************/
	public HostGroupResponse deleteHostGroupService(String serverType, String hostGroupName, String zabbixMethod)
			throws ZabbixException {
		try {
			ZabbixAPIClient zabClient = new ZabbixAPIClient(zabbixHelper.getZabbixURL(serverType));

			JsonRpcDeleteHostRequest requestDelete = new JsonRpcDeleteHostRequest();

			requestDelete.setJsonrpc(zabbixHelper.getZabbixRPCVersion());
			requestDelete.setMethod(zabbixMethod);
			ArrayList<String> host2delete = new ArrayList<>();
			host2delete.add(hostGroupName);
			requestDelete.setParams(host2delete);

			requestDelete.setAuth(zabbixHelper.getZabbixToken(serverType));

			requestDelete.setId(ZabbixConstant.ID);

			return zabClient.deleteHostGroupClient(requestDelete);
		} catch (ZabbixClientException e) {
			throw handleException(e);
		}
	}

	/********************
	 * CREATE HOSTGROUP
	 * 
	 * @throws ServerErrorResponseException
	 * @throws RestClientException
	 * @throws ZabbixAPIErrorException
	 *             - also in case of duplicate resources
	 ********************/
	public HostGroupResponse createHostGroupService(String url, String token, String hostGroupName, String zabbixMethod)
			throws ZabbixException {

		try {
			// ZabAPIClient zabClient = new ZabAPIClient(url);
			ZabbixAPIClient zabClient = new ZabbixAPIClient(url);
			JSONRPCRequest<HostGroupParamRequest> request = new JSONRPCRequest<>();
			HostGroupParamRequest hostGroupParamRequest = new HostGroupParamRequest();
			request.setJsonrpc(zabbixHelper.getZabbixRPCVersion());
			request.setMethod(zabbixMethod);

			hostGroupParamRequest.setName(hostGroupName);
			request.setParams(hostGroupParamRequest);
			request.setAuth(token);
			request.setId(ZabbixConstant.ID);
			return zabClient.createHostGroupClient(request);
		} catch (ZabbixClientException e) {
			throw handleException(e);
		}
	}

	/********************
	 * UPDATE HOSTGROUP
	 ********************/
	public HostGroupResponse updateHostGroupService(String serverType, String hostGroupName, String groupId,
			String zabbixMethod) throws ZabbixException {
		try {
			ZabbixAPIClient zabClient = new ZabbixAPIClient(zabbixHelper.getZabbixURL(serverType));
			JSONRPCRequest<HostGroupParamRequest> request = new JSONRPCRequest<>();
			HostGroupParamRequest hostGroupParamRequest = new HostGroupParamRequest();
			request.setJsonrpc(zabbixHelper.getZabbixRPCVersion());
			request.setMethod(zabbixMethod);

			hostGroupParamRequest.setName(hostGroupName);
			hostGroupParamRequest.setGroupid(groupId);
			request.setParams(hostGroupParamRequest);
			request.setAuth(zabbixHelper.getZabbixToken(serverType));
			request.setId(ZabbixConstant.ID);
			return zabClient.updateGroupClient(request);
		} catch (ZabbixClientException e) {
			throw handleException(e);
		}
	}

	/**************
	 * UPDATE PROXY
	 **************/
	@Deprecated
	public ZabbixUpdateProxyResponse updateProxyService(String serverType, String proxyId, String hostId,
			String zabbixMethod) throws ZabbixException {
		try {
			ZabbixAPIClient zabClient = new ZabbixAPIClient(zabbixHelper.getZabbixURL(serverType));
			JSONRPCRequest<ZabbixUpdateProxyRequest> request = new JSONRPCRequest<>();
			ZabbixUpdateProxyRequest paramRequest = new ZabbixUpdateProxyRequest();
			request.setJsonrpc(zabbixHelper.getZabbixRPCVersion());
			request.setMethod(zabbixMethod);

			List<String> hostids = new ArrayList<>();
			hostids.add(hostId);
			paramRequest.setHosts(hostids);
			paramRequest.setProxyid(proxyId);
			request.setParams(paramRequest);
			request.setAuth(zabbixHelper.getZabbixToken(serverType));
			request.setId(ZabbixConstant.ID);
			return zabClient.updateProxyClient(request);
		} catch (ZabbixClientException e) {
			throw handleException(e);
		}
	}

	/********************
	 * CREATE PROXY
	 * 
	 * @param url
	 * @param token
	 * @param hostGroupNameId
	 * @param getzabbixMethod
	 * @throws ZabbixException
	 */
	public ZabbixUpdateProxyResponse createProxyService(String url, String token, String hostGroupNameId,
			String zabbixMethod) throws ZabbixException {
		try {
			ZabbixAPIClient zabClient = new ZabbixAPIClient(url);
			JSONRPCRequest<ZabbixParamCreateProxyRequest> request = new JSONRPCRequest<>();
			ZabbixParamCreateProxyRequest paramRequest = new ZabbixParamCreateProxyRequest();
			request.setJsonrpc(zabbixHelper.getZabbixRPCVersion());
			request.setMethod(zabbixMethod);
			paramRequest
					.setHost(hostGroupNameId.replace(MonitoringConstant.WG_PREFIX, MonitoringConstant.PROXY_PREFIX));
			paramRequest.setStatus(ZabbixConstant.PROXY_ACTIVE_PROP);
			request.setParams(paramRequest);
			request.setAuth(token);
			request.setId(ZabbixConstant.ID);
			return zabClient.createProxyClient(request);
		} catch (ZabbixClientException e) {
			throw handleException(e);
		}
	}

	/*******************************
	 * UPDATE HOST/METRIC IN ZABBIX
	 *******************************/

	/**
	 * Method useful for enabling/disabling the host
	 * 
	 * 
	 * @param hostid
	 * @param itemid
	 * @param zabbixMethod
	 * @param urlRuntime
	 * @return The array of disabled metrics or hosts
	 * @throws APIErrorException
	 * @throws MappingException
	 * @throws NoMappingModelFoundException
	 * @throws ServerErrorResponseException
	 * @throws it.prisma.utils.web.ws.rest.apiclients.zabbix.ZabbixAPIErrorException
	 * @throws RestClientException
	 * @throws Exception
	 */
	public MonitoringVMCredentialsResponse updateHostService(String serverType, String hostid, String itemid,
			String zabbixMethod, MetricAction update) throws ZabbixException {
		try {
			ZabbixAPIClient zabClient = new ZabbixAPIClient(zabbixHelper.getZabbixURL(serverType));

			HostIDMassUpdate host2update = new HostIDMassUpdate();
			JSONRPCRequest<ZabbixParamUpdate> requestUpdate = new JSONRPCRequest<ZabbixParamUpdate>();
			requestUpdate.setJsonrpc(zabbixHelper.getZabbixRPCVersion());
			requestUpdate.setMethod(zabbixMethod);
			ZabbixParamUpdate paramUpdate = new ZabbixParamUpdate();

			ArrayList<HostIDMassUpdate> hosts2update = new ArrayList<>();

			host2update.setHostid(hostid);
			hosts2update.add(host2update);
			paramUpdate.setHosts(hosts2update);
			paramUpdate.setStatus(update.getStatus());

			requestUpdate.setAuth(zabbixHelper.getZabbixToken(serverType));
			requestUpdate.setId(ZabbixConstant.ID);
			requestUpdate.setParams(paramUpdate);

			return zabClient.updateHostClient(requestUpdate);
		} catch (ZabbixClientException e) {
			throw handleException(e);
		}
	}

	public UpdatedItemsResponse updateItemService(String serverType, String hostid, String itemid, String zabbixMethod,
			MetricAction update) throws ZabbixException {
		try {
			ZabbixAPIClient zabClient = new ZabbixAPIClient(zabbixHelper.getZabbixURL(serverType));

			JSONRPCRequest<ZabbixParamUpdate> requestUpdate = new JSONRPCRequest<ZabbixParamUpdate>();
			requestUpdate.setJsonrpc(zabbixHelper.getZabbixRPCVersion());
			requestUpdate.setMethod(zabbixMethod);
			ZabbixParamUpdate paramUpdate = new ZabbixParamUpdate();

			paramUpdate.setItemid(itemid);
			paramUpdate.setStatus(update.getStatus());

			requestUpdate.setAuth(zabbixHelper.getZabbixToken(serverType));
			requestUpdate.setId(ZabbixConstant.ID);
			requestUpdate.setParams(paramUpdate);

			return zabClient.updateItemClient(requestUpdate);
		} catch (ZabbixClientException e) {
			throw handleException(e);
		}
	}

	/**
	 * Get Monitored HOSTS LIST in Zabbix Platform useful for returning the host
	 * 
	 */
	public ArrayList<ZabbixMonitoredHostsResponse> getHostsService(String serverType, String zabbixMethod,
			String hostid, String groupids, String filterByHostName, String triggerId) throws ZabbixException {
		try {
			ZabbixAPIClient zabClient = new ZabbixAPIClient(zabbixHelper.getZabbixURL(serverType));

			JSONRPCRequest<ZabbixParamRequest> request = new JSONRPCRequest<ZabbixParamRequest>();
			request.setJsonrpc(zabbixHelper.getZabbixRPCVersion());
			request.setMethod(zabbixMethod);

			request.setAuth(zabbixHelper.getZabbixToken(serverType));

			ZabbixParamRequest parametersHosts = new ZabbixParamRequest();
			parametersHosts.setOutput(ZabbixConstant.EXTEND);

			if (!(groupids == null)) {
				parametersHosts.setGroupids(groupids);
			}

			if (!(hostid == null)) {
				parametersHosts.setHostids(hostid);
			}
			ZabbixFilterRequest filter = new ZabbixFilterRequest();
			if (filterByHostName != null) {
				filter.setHost(filterByHostName);
			}
			if (triggerId != null) {
				hostid = null;
				parametersHosts.setTriggerids(triggerId);
			}
			parametersHosts.setFilter(filter);
			parametersHosts.setSelectInventory("ZabbixConstant.EXTEND");
			request.setParams(parametersHosts);
			request.setId(ZabbixConstant.ID);

			return zabClient.getHostsClient(request);
		} catch (ZabbixClientException e) {

			throw handleException(e);
		}
	}

	/**
	 * 
	 * 
	 * @param url
	 * @param token
	 * @param zabbixMethod
	 * @param hostids
	 * @param groupids
	 * @param filterByHostName
	 * @param tagId
	 * @return
	 * @throws MappingException
	 * @throws NoMappingModelFoundException
	 * @throws ServerErrorResponseException
	 * @throws it.prisma.utils.web.ws.rest.apiclients.zabbix.ZabbixAPIErrorException
	 * @throws RestClientException
	 * @throws Exception
	 */
	public ArrayList<ZabbixMonitoredHostsResponse> getMonitoredHostsExtendedService(String serverType,
			String zabbixMethod, String hostids, String groupids, String filterByHostName, String tagId)
			throws ZabbixException {
		try {
			ZabbixAPIClient zabClient = new ZabbixAPIClient(zabbixHelper.getZabbixURL(serverType));

			JSONRPCRequest<Zabbix2_4ParamHost> request = new JSONRPCRequest<Zabbix2_4ParamHost>();
			SearchInventory searchInventory = new SearchInventory();

			request.setJsonrpc(zabbixHelper.getZabbixRPCVersion());
			request.setMethod(zabbixMethod);

			request.setAuth(zabbixHelper.getZabbixToken(serverType));

			Zabbix2_4ParamHost parametersHosts = new Zabbix2_4ParamHost();
			parametersHosts.setOutput(ZabbixConstant.EXTEND);

			if (!(groupids == null)) {
				parametersHosts.setGroupids(groupids);
			}

			if (!(hostids == null)) {
				parametersHosts.setHostids(hostids);
			}
			ZabbixFilterRequest filter = new ZabbixFilterRequest();
			if (filterByHostName != null) {
				filter.setHost(filterByHostName);
			}
			searchInventory.setTag(tagId);
			parametersHosts.setSearchInventory(searchInventory);
			parametersHosts.setSearchWildcardsEnabled(true);
			parametersHosts.setFilter(filter);
			parametersHosts.setSelectInventory(ZabbixConstant.EXTEND);
			parametersHosts.setSelectItems(null);
			parametersHosts.setSelectParentTemplates(null);
			request.setParams(parametersHosts);
			request.setId(ZabbixConstant.ID);

			return zabClient.getHostsExtended(request);
		} catch (ZabbixClientException e) {
			throw handleException(e);
		}
	}

	/**
	 * Get Monitored HOSTS LIST in Zabbix 2.4 Metrics/Watcher
	 * 
	 */
	public ArrayList<ZabbixMonitoredHostResponseV2_4> getMonitoredHostsZABBIXV2_4(String serverType,
			String zabbixMethod, String hostids, String groupids, String filterByHostName, String tagId)
			throws ZabbixException {
		try {
			ZabbixAPIClient zabClient = new ZabbixAPIClient(zabbixHelper.getZabbixURL(serverType));

			JSONRPCRequest<Zabbix2_4ParamHost> request = new JSONRPCRequest<Zabbix2_4ParamHost>();
			SearchInventory searchInventory = new SearchInventory();

			request.setJsonrpc(zabbixHelper.getZabbixRPCVersion());
			request.setMethod(zabbixMethod);

			request.setAuth(zabbixHelper.getZabbixToken(serverType));

			Zabbix2_4ParamHost parametersHosts = new Zabbix2_4ParamHost();
			parametersHosts.setOutput(ZabbixConstant.EXTEND);
			parametersHosts.setSelectGroups(ZabbixConstant.EXTEND);

			if (!(groupids == null)) {
				parametersHosts.setGroupids(groupids);
			}

			if (!(hostids == null)) {
				parametersHosts.setHostids(hostids);
			}
			ZabbixFilterRequest filter = new ZabbixFilterRequest();
			if (filterByHostName != null) {
				filter.setHost(filterByHostName);
			}
			searchInventory.setTag(tagId);
			parametersHosts.setSearchInventory(searchInventory);
			parametersHosts.setSearchWildcardsEnabled(true);
			parametersHosts.setFilter(filter);
			parametersHosts.setSelectInventory(ZabbixConstant.EXTEND);
			parametersHosts.setSelectItems(ZabbixConstant.EXTEND);
			parametersHosts.setSelectParentTemplates(ZabbixConstant.EXTEND);
			request.setParams(parametersHosts);
			request.setId(ZabbixConstant.ID);

			return zabClient.getHostsExtended2_4(request);
		} catch (ZabbixClientException e) {
			throw handleException(e);
		}
	}

	/**
	 * Get TEMPLATE LIST into PrismaMetrics Server
	 * 
	 */
	public ArrayList<ZabbixTemplateResponse> getTemplateService(String serverType, String zabbixMethod)
			throws ZabbixException {
		try {
			ZabbixAPIClient zabClient = new ZabbixAPIClient(zabbixHelper.getZabbixURL(serverType));

			JSONRPCRequest<ZabbixParamHostGroupRequest> request = new JSONRPCRequest<ZabbixParamHostGroupRequest>();
			request.setJsonrpc(zabbixHelper.getZabbixRPCVersion());
			request.setMethod(zabbixMethod);

			request.setAuth(zabbixHelper.getZabbixToken(serverType));

			ZabbixParamHostGroupRequest parametersHosts = new ZabbixParamHostGroupRequest();
			parametersHosts.setOutput(ZabbixConstant.EXTEND);

			ZabbixFilterRequest filter = new ZabbixFilterRequest();
			parametersHosts.setFilter(filter);
			request.setParams(parametersHosts);
			request.setId(ZabbixConstant.ID);

			return (zabClient).getTemplate(request);
		} catch (ZabbixClientException e) {
			throw handleException(e);
		}
	}

	/**
	 * Get Zabbix Features USEFUL FOR MULTIPLE PURPOSES depending on method
	 * passed
	 * 
	 */
	public ArrayList<ZabbixItemResponse> getZabbixFeatureMultiService(String serverType, String hostId,
			String templateIdtoAdapter, String zabbixMethod) throws ZabbixException {
		try {
			ZabbixAPIClient zabClient = new ZabbixAPIClient(zabbixHelper.getZabbixURL(serverType));
			JSONRPCRequest<ZabbixParamItemRequest> requestItem = new JSONRPCRequest<ZabbixParamItemRequest>();
			requestItem.setJsonrpc(zabbixHelper.getZabbixRPCVersion());
			requestItem.setMethod(zabbixMethod);

			requestItem.setAuth(zabbixHelper.getZabbixToken(serverType));

			ZabbixParamItemRequest params = new ZabbixParamItemRequest();
			params.setOutput(ZabbixConstant.EXTEND);
			params.setHostids(hostId);

			if (!(templateIdtoAdapter == null)) {
				params.setTemplateids(templateIdtoAdapter);
				params.setHostids(null);
			}
			ZabbixSearchKeyRequest key = new ZabbixSearchKeyRequest();
			key.setKey("");
			params.setSearch(key);
			requestItem.setParams(params);
			requestItem.setId(ZabbixConstant.ID);

			requestItem.setParams(params);

			return zabClient.getItemsClient(requestItem);
		} catch (ZabbixClientException e) {
			throw handleException(e);
		}
	}

	/**
	 * GET TEMPLATES AND ASSOCIATED ITEMS
	 * 
	 */
	public ArrayList<ZabbixTemplateResponseV2_4> getTemplatesExtendedService(String serverType, String hostId,
			String zabbixMethod) throws ZabbixException {
		try {
			ZabbixAPIClient zabClient = new ZabbixAPIClient(zabbixHelper.getZabbixURL(serverType));

			JSONRPCRequest<ZabbixParamTemplateRequest> requestTemplate = new JSONRPCRequest<>();
			ZabbixParamTemplateRequest paramTemplate = new ZabbixParamTemplateRequest();

			requestTemplate.setJsonrpc(zabbixHelper.getZabbixRPCVersion());
			requestTemplate.setMethod(zabbixMethod);

			requestTemplate.setAuth(zabbixHelper.getZabbixToken(serverType));

			paramTemplate.setOutput(ZabbixConstant.EXTEND);
			paramTemplate.setHostids(hostId);

			paramTemplate.setSelectItems(ZabbixConstant.EXTEND);
			requestTemplate.setParams(paramTemplate);
			requestTemplate.setId(ZabbixConstant.ID);

			return zabClient.getTemplatesClient(requestTemplate);
		} catch (ZabbixClientException e) {
			throw handleException(e);
		}

	}

	/**
	 * Get LIST of METRICS for Specific host
	 * 
	 */
	public ArrayList<ZabbixItemResponse> getItemsService(String serverType, String hostId, String templateIds,
			String metricIds, String triggerIds, String zabbixMethod, FilterTimeRequest requestTime)
			throws ZabbixException {

		ArrayList<ZabbixItemResponse> result = new ArrayList<>();

		result = getItemHistoryService(serverType, hostId, templateIds, metricIds, triggerIds, zabbixMethod,
				requestTime);

		return result;
	}

	/**
	 * GET HISTORY / EVENTS
	 * 
	 */
	public ArrayList<ZabbixItemResponse> getItemHistoryService(String serverType, String hostId, String templateIds,
			String metricIds, String triggerIds, String zabbixMethod, FilterTimeRequest requestTime)
			throws ZabbixException {
		try {

			ZabbixAPIClient zabClient = new ZabbixAPIClient(zabbixHelper.getZabbixURL(serverType));
			JSONRPCRequest<ZabbixParamItemRequest> requestItem = new JSONRPCRequest<ZabbixParamItemRequest>();
			ZabbixParamItemRequest itemParameters = new ZabbixParamItemRequest();

			requestItem.setJsonrpc(zabbixHelper.getZabbixRPCVersion());
			requestItem.setMethod(zabbixMethod);
			String token = zabbixHelper.getZabbixToken(serverType);
			requestItem.setAuth(token);

			itemParameters = new ZabbixParamItemRequest();
			itemParameters.setOutput(ZabbixConstant.EXTEND);

			/***************************************
			 * SETTING HISTORY or EVENTS in ZABBIX
			 **************************************/
			if (zabbixMethod.equals(ZabbixMethods.HISTORY.getzabbixMethod())
					|| zabbixMethod.equals(ZabbixMethods.EVENT.getzabbixMethod())) {
				int limit = 10;
				String sortfield = "clock";

				/*****************
				 * EVENT CASE
				 *****************/
				if (zabbixMethod.equals(ZabbixMethods.EVENT.getzabbixMethod())) {
					limit = 30;
				}
				// TODO: understand which limit to set for history considering
				// that
				// the sample are taken every 30s
				// (quite a lot for returning history) if every 10mins, then 144
				// samples cover the entire day; 3 days-->432; 7days-->1008;
				// 30days-->4320; 90day-->12960
				else if (requestTime != null) {
					limit = FilterTimeRequestHandlerMonitoring.determineHowManySamples(requestTime);
				}

				int history = ZabbixConstant.TEXT_HISTORY_TYPE;

				ZabbixParamHistoryRequest parameters4history = new ZabbixParamHistoryRequest();
				JSONRPCRequest<ZabbixParamHistoryRequest> request = new JSONRPCRequest<ZabbixParamHistoryRequest>();

				String sortorder = "ASC"; // "DESC"

				request.setJsonrpc(zabbixHelper.getZabbixRPCVersion());
				request.setMethod(zabbixMethod);
				request.setAuth(token);

				parameters4history.setOutput(ZabbixConstant.EXTEND);
				parameters4history.setHistory(history);
				parameters4history.setLimit(limit);
				/*************
				 * TIME FILTER
				 *************/
				if (requestTime != null) {
					parameters4history.setTimeFrom(TimestampMonitoring.getDateFromFormatter(requestTime.getDateFrom())
							.toString());
					parameters4history.setTimeTill(TimestampMonitoring.getDateToFormatter(requestTime.getDateTo())
							.toString());
				}
				parameters4history.setSortfield(sortfield);
				parameters4history.setSortorder(sortorder);

				if (!(metricIds == null)) {
					parameters4history.setItemids(metricIds);
					itemParameters.setTemplateids(null);
					itemParameters.setHostids(null);
				} else {
					parameters4history.setHostids(hostId);
				}

				request.setParams(parameters4history);
				request.setId(ZabbixConstant.ID);
				ArrayList<ZabbixItemResponse> result = new ArrayList<>();

				result = zabClient.getHistoryClient(request);

				/*************
				 * HISTORY CASE
				 **************/
				if (result.size() == 0 && (zabbixMethod.equals(ZabbixMethods.HISTORY.getzabbixMethod()))) {
					history = ZabbixConstant.INT_HISTORY_TYPE;
					parameters4history.setHistory(history);

					/**************
					 * FILTER TIME
					 *************/
					if (requestTime != null) {
						parameters4history.setTimeFrom(TimestampMonitoring.getDateFromFormatter(
								requestTime.getDateFrom()).toString());
						parameters4history.setTimeTill(TimestampMonitoring.getDateToFormatter(requestTime.getDateTo())
								.toString());
					}
					result = zabClient.getHistoryClient(request);
					if (result.size() == 0) {
						history = ZabbixConstant.FLOAT_HISTORY_TYPE;
						parameters4history.setHistory(history);

						result = zabClient.getHistoryClient(request);
					}
				}
				return result;
			}

			/***************************
			 * SETTING ITEMS in ZABBIX
			 ***************************/
			else {

				itemParameters.setHostids(hostId);

				if (!(templateIds == null) && !(hostId == null)) {
					itemParameters.setTemplateids(null);
				} else if (!(templateIds == null)) {
					itemParameters.setTemplateids(templateIds);
					itemParameters.setHostids(null);
				}
				// For getting specific metrics and history
				else if (!(metricIds == null)) {
					itemParameters.setItemids(metricIds);
					itemParameters.setTemplateids(null);
					itemParameters.setHostids(null);
				}
				// This parameter has been exploited when requesting metrics
				// into
				// Event wrapper
				if (triggerIds != null) {
					itemParameters.setTriggerids(triggerIds);
				}
				// itemParameters.setTemplateids(templateIds);
				ZabbixSearchKeyRequest key = new ZabbixSearchKeyRequest();
				key = new ZabbixSearchKeyRequest();
				key.setKey("");

				itemParameters.setSearch(key);

				requestItem.setParams(itemParameters);
				requestItem.setId(ZabbixConstant.ID);

				return zabClient.getItemsClient(requestItem);
			}
		} catch (ZabbixClientException e) {
			throw handleException(e);
		}
	}

	/*************************
	 * GET TRENDS
	 *********************/
	public ArrayList<ZabbixItemResponse> getTrendService(String serverType, String hostId, String templateIds,
			String metricIds, String triggerIds, String zabbixMethod, FilterTimeRequest requestTime)
			throws ZabbixException {
		try {
			ZabbixAPIClient zabClient = new ZabbixAPIClient(zabbixHelper.getZabbixURL(serverType));
			JSONRPCRequest<ZabbixParamItemRequest> requestItem = new JSONRPCRequest<ZabbixParamItemRequest>();
			ZabbixParamItemRequest itemParameters = new ZabbixParamItemRequest();
			Long dateFrom = null;
			Long dateTill = null;

			if (requestTime != null) {
				dateFrom = TimestampMonitoring.getDateFromFormatter(requestTime.getDateFrom());
				dateTill = TimestampMonitoring.getDateToFormatter(requestTime.getDateTo());
			}

			requestItem.setJsonrpc(zabbixHelper.getZabbixRPCVersion());
			requestItem.setMethod(zabbixMethod);
			String token = zabbixHelper.getZabbixToken(serverType);
			requestItem.setAuth(token);

			itemParameters = new ZabbixParamItemRequest();
			itemParameters.setOutput(ZabbixConstant.EXTEND);

			int history = ZabbixConstant.TEXT_HISTORY_TYPE;

			ZabbixParamTrendRequest parameters4trend = new ZabbixParamTrendRequest();
			JSONRPCRequest<ZabbixParamTrendRequest> request = new JSONRPCRequest<ZabbixParamTrendRequest>();

			String sortorder = "ASC"; // "DESC"

			request.setJsonrpc(zabbixHelper.getZabbixRPCVersion());
			request.setMethod(zabbixMethod);
			request.setAuth(token);

			parameters4trend.setOutput(ZabbixConstant.EXTEND);
			parameters4trend.setHistory(history);
			parameters4trend.setLimit(400);
			parameters4trend.setTimeFrom(dateFrom.toString());
			parameters4trend.setTimeTill(dateTill.toString());
			parameters4trend.setSortorder(sortorder);

			if (!(metricIds == null)) {
				parameters4trend.setItemids(metricIds);
				itemParameters.setTemplateids(null);
				itemParameters.setHostids(null);
			} else {
				parameters4trend.setHostids(hostId);
			}

			request.setParams(parameters4trend);
			request.setId(ZabbixConstant.ID);

			ArrayList<ZabbixItemResponse> resultTrend = zabClient.getTrendClient(request);
			if (resultTrend.isEmpty()) {
				parameters4trend.setHistory(ZabbixConstant.FLOAT_HISTORY_TYPE);
				return zabClient.getTrendClient(request);
			} else {
				return resultTrend;
			}

		} catch (ZabbixClientException e) {
			throw handleException(e);
		}
	}

	/**
	 * TriggerList for wrapper
	 * 
	 */
	public ArrayList<ZabbixItemResponse> getTriggerService(String serverType, String createdHostId, String itemId,
			String zabbixMethod) throws ZabbixException {
		try {
			ZabbixAPIClient zabClient = new ZabbixAPIClient(zabbixHelper.getZabbixURL(serverType));

			JSONRPCRequest<ZabbixParamTriggerRequest> request = new JSONRPCRequest<ZabbixParamTriggerRequest>();
			request.setJsonrpc(zabbixHelper.getZabbixRPCVersion());
			request.setMethod(zabbixMethod);

			request.setAuth(zabbixHelper.getZabbixToken(serverType));

			ZabbixParamTriggerRequest triggerParameters = new ZabbixParamTriggerRequest();
			triggerParameters.setOutput(ZabbixConstant.EXTEND);
			if (!(itemId == null)) {
				ArrayList<String> itemIdsarray = new ArrayList<>();
				itemIdsarray.add(itemId);
				triggerParameters.setItemids(itemIdsarray);
				createdHostId = null;
			}
			triggerParameters.setOnly_true(null);
			triggerParameters.setExpandDescription(true);
			triggerParameters.setHostids(createdHostId);
			request.setParams(triggerParameters);
			request.setId(ZabbixConstant.ID);

			return zabClient.getTriggerClient(request);
		} catch (ZabbixClientException e) {
			throw handleException(e);
		}
	}

	public ArrayList<ZabbixItemResponse> getTriggerWithProblemsService(String serverType, String hostId, String itemId,
			String methodFromManager, String group) throws ZabbixException {

		boolean problems = true;
		return setTriggersService(serverType, hostId, itemId, methodFromManager, problems, group);
	}

	private ArrayList<ZabbixItemResponse> setTriggersService(String serverType, String hostId, String itemId,
			String methodFromManager, boolean problems, String group) throws ZabbixException {
		try {
			ZabbixAPIClient zabClient = new ZabbixAPIClient(zabbixHelper.getZabbixURL(serverType));

			JSONRPCRequest<ZabbixParamTriggerRequest> request = new JSONRPCRequest<ZabbixParamTriggerRequest>();
			JSONRPCRequest<TriggerParamRequestByGroup> requestByGroup = new JSONRPCRequest<>();

			request.setJsonrpc(zabbixHelper.getZabbixRPCVersion());
			request.setMethod(methodFromManager);

			request.setAuth(zabbixHelper.getZabbixToken(serverType));

			ZabbixParamTriggerRequest itemParameters = new ZabbixParamTriggerRequest();
			itemParameters.setOutput(ZabbixConstant.EXTEND);
			itemParameters.setExpandDescription(true);

			itemParameters.setHostids(hostId);

			ArrayList<String> itemIdsarray = new ArrayList<String>();
			itemIdsarray.add(itemId);
			itemParameters.setItemids(itemIdsarray);
			itemParameters.setExpandExpression(true);

			if (!problems == false) {
				TriggerParamRequestByGroup paramsByGroup = new TriggerParamRequestByGroup();
				paramsByGroup.setOnlyTrue(problems);
				paramsByGroup.setGroup(group);
				paramsByGroup.setOutput(ZabbixConstant.EXTEND);
				paramsByGroup.setExpandExpression(true);
				// paramsByGroup.setLastChangeTill("");
				if (hostId != null) {
					paramsByGroup.setHostids(hostId);
				}
				requestByGroup.setId(ZabbixConstant.ID);
				requestByGroup.setMethod(methodFromManager);
				requestByGroup.setAuth(zabbixHelper.getZabbixToken(serverType));
				requestByGroup.setJsonrpc(zabbixHelper.getZabbixRPCVersion());
				requestByGroup.setParams(paramsByGroup);
				return zabClient.getTriggerByGroupClient(requestByGroup);
			}
			request.setParams(itemParameters);
			request.setId(ZabbixConstant.ID);

			return zabClient.getTriggerClient(request);
		} catch (ZabbixClientException e) {
			throw handleException(e);
		}
	}

	/**
	 * Get GROUPS LIST
	 * 
	 * @param serverType
	 * @param zabbixMethod
	 * @param hostGroupIdtoAdapter
	 * @param tagService
	 * @param hostid
	 * @return
	 * @throws APIErrorException
	 * @throws RestClientException
	 * @throws ServerErrorResponseException
	 * @throws ZabbixAPIErrorException
	 */
	public ArrayList<ZabbixHostGroupResponse> getHostGroupsService(String serverType, String zabbixMethod,
			String hostGroupIdtoAdapter, String tagService, String hostid) throws ZabbixException {
		try {
			ZabbixAPIClient zabClient = new ZabbixAPIClient(zabbixHelper.getZabbixURL(serverType));

			JSONRPCRequest<ZabbixParamHostGroupRequest> request = new JSONRPCRequest<ZabbixParamHostGroupRequest>();
			request.setJsonrpc(zabbixHelper.getZabbixRPCVersion());
			request.setMethod(zabbixMethod);

			request.setAuth(zabbixHelper.getZabbixToken(serverType));

			ZabbixParamHostGroupRequest parametersHosts = new ZabbixParamHostGroupRequest();
			parametersHosts.setOutput(ZabbixConstant.EXTEND);

			if (hostid != null) {
				parametersHosts.setHostids(hostid);
			}
			ZabbixFilterRequest filter = new ZabbixFilterRequest();

			parametersHosts.setFilter(filter);
			request.setParams(parametersHosts);
			request.setId(ZabbixConstant.ID);

			return zabClient.getHostGroupClient(request);
		} catch (ZabbixClientException e) {
			throw handleException(e);
		}
	}

}