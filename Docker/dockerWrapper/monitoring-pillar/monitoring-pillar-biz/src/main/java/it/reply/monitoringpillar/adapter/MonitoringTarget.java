package it.reply.monitoringpillar.adapter;

import java.util.List;

import javax.inject.Named;
import javax.naming.NameNotFoundException;

import org.apache.velocity.exception.ResourceNotFoundException;

import it.reply.monitoringpillar.adapter.zabbix.exception.ZabbixException;
import it.reply.monitoringpillar.domain.dsl.monitoring.businesslayer.paas.request.Port;
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
import it.reply.monitoringpillar.domain.exception.MonitoringException;
import it.reply.utils.web.ws.rest.apiclients.prisma.APIErrorException;
import it.reply.utils.web.ws.rest.apiencoding.MappingException;
import it.reply.utils.web.ws.rest.apiencoding.NoMappingModelFoundException;
import it.reply.utils.web.ws.rest.apiencoding.ServerErrorResponseException;
import it.reply.utils.web.ws.rest.restclient.exceptions.RestClientException;

//@Stateless
@Named
public interface MonitoringTarget {

	/**
	 * 
	 * @param testbed
	 * @param hostuuid
	 * @param group
	 * @param service_category
	 * @param tag_service
	 * @param atomic_service_id
	 * @param metrics_id
	 * @param triggers_id
	 * @param history
	 * @param server_type
	 * @param requestTime
	 * @return
	 * @throws MappingException
	 * @throws NoMappingModelFoundException
	 * @throws ServerErrorResponseException
	 * @throws APIErrorException
	 * @throws RestClientException
	 * @throws ResourceNotFoundException
	 * @throws Exception
	 */
	// public MonitoringWrappedResponsePaas getOverallPaaSMetricsV2_4(String
	// testbed, String serverType, String hostuuid,
	// String group, String service_category, String tag_service, List<String>
	// atomic_service_id, String metric,
	// List<String> triggers_id, String history, FilterTimeRequest requestTime)
	// throws MonitoringException;

	// UPDATE GROUPNAME
	public HostGroupResponse updateMonitoredHostGroup(String zone, String serverType, String groupName,
			UpdateGroupName newGroupName) throws MonitoringException;

	/***********
	 * IAAS
	 ***********/
	/**
	 * Method for obtaining the Iaas machines status
	 * 
	 * @param adapter
	 * @param testbedType
	 * @param iaasType
	 * @return MonitoringWrappedResponseIaasV2 Info about IaaS machines,
	 *         metrics, triggers depending what parameters are passed as
	 *         arguments
	 * @throws MappingException
	 * @throws NoMappingModelFoundException
	 * @throws ServerErrorResponseException
	 * @throws APIErrorException
	 * @throws RestClientException
	 * @throws Exception
	 */
	// public MonitoringWrappedResponseIaas getIaaSOverallMachines(String
	// testbedType, String serverType, String iaasType,
	// String iaashost) throws MonitoringException;

	public Object getInfoWrapperGeneric(String zone, String serverType, String group, String host,
			String service_category, String tag_service, List<String> atomic_service_id, String metric,
			List<String> triggers_id, String history, FilterTimeRequest requestTime) throws MonitoringException;

	/***
	 * GET Triggers from IAAS Platform
	 * 
	 * @return
	 * @throws Exception
	 */
	public WrappedIaasHealthByTrigger getTriggerByGroup(String zone, String serverType, String hostgroup)
			throws MonitoringException;

	/****************
	 * PAAS
	 ***************/
	/**
	 * Create a host with certain characteristics
	 * 
	 * @param adapter
	 * @param testbedType
	 * @param uuid
	 * @param vmip
	 * @param serviceCategory
	 * @param serviceTag
	 * @param service
	 * @return
	 * @throws NameNotFoundException
	 * @throws MappingException
	 * @throws NoMappingModelFoundException
	 * @throws ServerErrorResponseException
	 * @throws APIErrorException
	 * @throws RestClientException
	 * @throws Exception
	 */

	public void creationMonitoredHost(String zone, String serverType, String hostGroup, String hostName, String vmuuid,
			String vmip, String serviceCategory, String serviceTag, List<String> service, Boolean activeMode,
			List<Port> ports, String proxyapi) throws MonitoringException;

	/**
	 * 
	 * @param testbedType
	 * @param hostGroupName
	 * @return
	 * @throws NameNotFoundException
	 * @throws MappingException
	 * @throws NoMappingModelFoundException
	 * @throws ServerErrorResponseException
	 * @throws APIErrorException
	 * @throws RestClientException
	 * @throws Exception
	 */
	public void creationMonitoredHostGroup(String zone, String serverType, String hostGroupName)
			throws MonitoringException;

	/**
	 * 
	 * @param hostGroupName
	 * @throws MonitoringException
	 * @throws ZabbixException
	 */
	public void creationMonitoredProxy(String zone, String serverType, String tenantId, String proxyName)
			throws MonitoringException;

	/**
	 * 
	 * @param serverType
	 * @param workGroup
	 * @param proxyName
	 */
	public void deleteMonitoredProxy(String zone, String serverType, String proxyName);

	/**
	 * Interface for deleting the host
	 * 
	 * @param testbedType
	 * @param hostvmuuids
	 * @param serviceId
	 * @return
	 * @throws MappingException
	 * @throws NoMappingModelFoundException
	 * @throws ServerErrorResponseException
	 * @throws APIErrorException
	 * @throws RestClientException
	 * @throws Exception
	 */
	public void deleteMonitoredHost(String zone, String serverType, String hostvmuuids, String serviceId)
			throws MonitoringException;

	/********************
	 * DELETE HOSTGROUP
	 * 
	 * @param testbedType
	 * @param hostGroupName
	 * @return
	 * @throws MappingException
	 * @throws NoMappingModelFoundException
	 * @throws ServerErrorResponseException
	 * @throws APIErrorException
	 * @throws RestClientException
	 * @throws Exception
	 */
	public void deleteMonitoredHostGroup(String zone, String serverType, String hostGroupName)
			throws MonitoringException;

	/**
	 * Interface useful for getting all events for Monitoring platform
	 */
	/**
	 * 
	 * @param serverType
	 * @param host
	 * @param group
	 * @param tag_service
	 * @param requestTime
	 * @return MonitPillarEventResponse
	 * @throws MonitoringException
	 */
	public MonitPillarEventResponse getOverallServerEvents(String zone, String serverType, String host, String group,
			String tag_service, FilterTimeRequest requestTime) throws MonitoringException;

	/**
	 * Useful for disabling a specific host and a specific metric for specified
	 * host
	 * 
	 * @param testbed
	 * @param host_id
	 * @param metric_id
	 * @param tag_service
	 * @param server_type
	 * @return the ID of the disabled host, metric (array of 'em)
	 * @throws MappingException
	 * @throws NoMappingModelFoundException
	 * @throws ServerErrorResponseException
	 * @throws APIErrorException
	 * @throws RestClientException
	 * @throws Exception
	 */
	public void getDisablingHost(String zone, String serverType, String group, String host_id, String metric_id,
			String tag_service, String update) throws MonitoringException;

	/*******************
	 * DISABLE METRIC
	 * 
	 * @param testbed
	 * @param server_type
	 * @param host_id
	 * @param metric_id
	 * @param tag_service
	 * @param update
	 * @return
	 * @throws MappingException
	 * @throws NoMappingModelFoundException
	 * @throws ServerErrorResponseException
	 * @throws APIErrorException
	 * @throws RestClientException
	 * @throws Exception
	 */
	public void getDisablingItem(String zone, String serverType, String group, String host_id, String metric_id,
			String tag_service, String update) throws MonitoringException;

	/**
	 * GET TRIGGER EVENTs BY HOST
	 * 
	 * @param testbed
	 * @param host
	 * @param serverType
	 * @return
	 * @throws Exception
	 */
	WrappedIaasHealthByTrigger getTriggerByHost(String zone, String serverType, String group, String host)
			throws MonitoringException;

	// GET GROUPs List
	public MonitoringWrappedResponsePaasGroups getGroupsInfoWrapped(String zone, String serverType)
			throws MonitoringException;

	// GET HOSTs List
	public MonitoringWrappedResponsePaasGroups4HostList getHostsInfoWrapped(String zone, String serverType,
			String groupName) throws MonitoringException;

	/***
	 * Interface useful for getting callbacks from Monitoring platform
	 * 
	 * @param group
	 * @param hostName
	 * @param vmuuid
	 * @param hostServiceId
	 * @param hostIP
	 * @param metric
	 * @param threshold
	 * @param status
	 * @return
	 * @throws MonitoringException
	 */
	public MonitoringPillarEventCallbackResponse manageCallbackEvent(String zone, String group, String hostName,
			String vmuuid, String hostServiceId, String hostIP, String metric, String threshold, String status,
			String description) throws MonitoringException;

	/**
	 * Get metrics list wrapped response
	 * 
	 * @param serverType
	 * @param group
	 * @param host
	 * @return
	 * @throws MonitoringException
	 */
	public MonitoringWrappedResponsePaasGroups4MetricList getMetricsByHost(String zone, String serverType, String group,
			String host) throws MonitoringException;

	/**
	 * SendMails
	 * 
	 * @param serverType
	 * @param sendMailRequest
	 */
	public void getSendMails(String zone, String serverType, SendMailRequest sendMailRequest)
			throws MonitoringException;

}