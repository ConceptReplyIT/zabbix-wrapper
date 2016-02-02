package it.monitoringpillar;

import it.monitoringpillar.adapter.DelegatorAdapter;
import it.monitoringpillar.adapter.zabbix.handler.ZabbixFeatures.ServerType;
import it.monitoringpillar.config.ConfigPillar;
import it.monitoringpillar.config.Configuration;
import it.monitoringpillar.exception.IllegalArgumentMonitoringException;
import it.monitoringpillar.exception.MonitoringException;
import it.monitoringpillar.exception.NotFoundMonitoringException;
import it.monitoringpillar.wrapper.WrapperProvider;
import it.prisma.domain.dsl.monitoring.Adapter;
import it.prisma.domain.dsl.monitoring.InfoType;
import it.prisma.domain.dsl.monitoring.Zone;
import it.prisma.domain.dsl.monitoring.businesslayer.paas.request.HostMonitoringRequest;
import it.prisma.domain.dsl.monitoring.pillar.protocol.MonitoringResponse;
import it.prisma.domain.dsl.monitoring.pillar.protocol.PillarAdapter;
import it.prisma.domain.dsl.monitoring.pillar.protocol.PillarServerType;
import it.prisma.domain.dsl.monitoring.pillar.protocol.PillarZone;
import it.prisma.domain.dsl.monitoring.pillar.wrapper.MonitPillarEventResponse;
import it.prisma.domain.dsl.monitoring.pillar.wrapper.iaas.WrappedIaasHealthByTrigger;
import it.prisma.domain.dsl.monitoring.pillar.wrapper.paas.FilterTimeRequest;
import it.prisma.domain.dsl.monitoring.pillar.wrapper.paas.MonitoringWrappedResponsePaas;
import it.prisma.domain.dsl.monitoring.pillar.wrapper.paas.MonitoringWrappedResponsePaasGroups;
import it.prisma.domain.dsl.monitoring.pillar.wrapper.paas.MonitoringWrappedResponsePaasGroups4HostList;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.UpdateGroupName;
import it.prisma.utils.datetime.TimestampMonitoring;
import it.prisma.utils.web.ws.rest.apiclients.prisma.APIErrorException;
import it.prisma.utils.web.ws.rest.apiencoding.MappingException;
import it.prisma.utils.web.ws.rest.apiencoding.NoMappingModelFoundException;
import it.prisma.utils.web.ws.rest.apiencoding.ServerErrorResponseException;
import it.prisma.utils.web.ws.rest.restclient.exceptions.RestClientException;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author m.grandolfo <br>
 * <br>
 *         Class containing RESTful API that wrap Monitoring products' API such
 *         as Zabbix's ones
 * 
 */
@Interceptors(LoggingInterceptor.class)
public class MonitoringPillarWSImpl implements MonitoringPillarWS {

	private static Logger LOG = LogManager.getLogger(MonitoringPillarWSImpl.class);
	private static String errorServerTypeUnallowed = "Error: Not Allowed for Infrastructure type, just Paas Platform type";
	private Calendar calendar = Calendar.getInstance();
	private Date now = calendar.getTime();

	@Inject
	DelegatorAdapter delegateAdapt;

	@Inject
	private WrapperProvider<?> wrapper;

	@Inject
	private Configuration config;

	/**
	 * 
	 * Method useful for: <br>
	 * <ul>
	 * <li>creating a host to be monitored </br>
	 * 
	 * @param bodyRequest
	 * <br>
	 *            JSON String with the body request <br>
	 *            { <br>
	 *            POST: "http://<url-pillar>/monitoring/prisma-services/", <br>
	 *            { <br>
	 *            "testbed-type":"infn", <br>
	 *            "action-type":"create_monitored_host", <br>
	 *            "parameters":{ <br>
	 *            "vmuuid": "6ad90238-9a82-4235-bb90-e0bd2aed627b", <br>
	 *            "vmip":"<public or private machine's ip>", <br>
	 *            "service-category":"<service-category>", <br>
	 *            "tag-service":"<service-id>", <br>
	 *            "service-type":["<atomic services>"] <br>
	 *            , <br>
	 *            "adapter-type":"zabbix"
	 * 
	 * <br>
	 * <br>
	 * @return the ID belonging to the cancelled host
	 *         </ul>
	 * <br>
	 * <br>
	 *         <ul>
	 *         <li>deleting a monitored host </br>
	 * @param bodyRequest
	 * <br>
	 *            JSON String with the body request <br>
	 *            { <br>
	 *            POST: "http://<url-pillar>/monitoring/prisma-services/", <br>
	 *            { <br>
	 *            "testbed-type":"infn", <br>
	 *            "action-type":"delete_monitored_host", <br>
	 *            "parameters":{ <br>
	 *            "vmuuid": "6ad90238-9a82-4235-bb90-e0bd2aed627b", <br>
	 *            , <br>
	 *            "adapter-type":"zabbix" <br>
	 * 
	 * <br>
	 * @return the ID belonging to the cancelled host
	 *         </ul>
	 * 
	 * <br>
	 * <br>
	 * 
	 */

	/*****************
	 * GET ADAPTERS
	 * 
	 * @throws IOException
	 * @throws
	 * 
	 * @throws NotFoundMonitoringException
	 ****************/
	@Override
	public Response listAllAdapter() {
		String message = "GET: <url>/monitoring/adapters/";
		getLog(message);
		return MonitoringResponse.status(Status.OK, new PillarAdapter().withAdapters(Adapter.getAllAdapter())).build()
				.build();
	}

	/************************
	 * List of ZONES
	 * 
	 * @param adapterType
	 * @return
	 * @throws IllegalArgumentMonitoringException
	 * @throws NotFoundMonitoringException
	 */
	@Override
	public Response listAllZones(String adapterType) throws IllegalArgumentMonitoringException,
			NotFoundMonitoringException {
		String message = "GET: <url>/monitoring/adapters/" + adapterType + "/zones";
		getLog(message);
		delegateAdapt.getAdapter(adapterType);
		return MonitoringResponse.status(Status.OK, new PillarZone().withZones(Zone.getAllZone())).build().build();
	}

	/*******************************
	 * GET LIST OF SERVERS PER ZONE
	 * 
	 * @throws IllegalArgumentMonitoringException
	 * @throws NotFoundMonitoringException
	 ************/
	@Override
	public Response listAllServerType(String adapterType, String zone) throws IllegalArgumentMonitoringException,
			NotFoundMonitoringException {
		String message = "GET: <url>/monitoring/adapters/" + adapterType + "/zones/" + zone;
		getLog(message);
		delegateAdapt.getAdapter(adapterType);
		checkZone(zone);
		return MonitoringResponse.status(Status.OK, new PillarServerType().withServers(ServerType.getAllServer()))
				.build().build();
	}

	/*******************************
	 * GET GROUPLIST WRAPPED MINIMAL
	 * 
	 * @throws MonitoringException
	 * @throws
	 *****************************/
	@Override
	public Response getGroupList(String adapterType, String zoneType, String serverType) throws MonitoringException {
		LOG.info("GET: <url>/monitoring/adapters/" + adapterType + "/types/" + serverType + "/groups");
		MonitoringWrappedResponsePaasGroups wrappedGroup = delegateAdapt.getAdapter(adapterType).getGroupsInfoWrapped(
				serverType);

		return MonitoringResponse.status(Status.OK, wrappedGroup).build().build();
	}

	public Response getHostList(String adapterType, String zoneType, String serverType, String groupName)
			throws MonitoringException {
		String message = "GET: <url>/monitoring/adapters/" + adapterType + "/types/" + serverType + "/groups/"
				+ groupName;
		getLog(message);
		MonitoringWrappedResponsePaasGroups4HostList wrappedHostList = delegateAdapt.getAdapter(adapterType)
				.getHostsInfoWrapped(serverType, groupName);

		return MonitoringResponse.status(Status.OK, wrappedHostList).build().build();

	}

	/*******************
	 * UPDATE HOSTGROUP
	 ******************/
	@Override
	public Response updateGroup(String adapterType, String zoneType, String serverType, String groupName,
			UpdateGroupName newGroupName) throws MonitoringException {
		String message = "POST: <url>/monitoring/adapters/" + adapterType + "/types/" + serverType + "/groups/"
				+ groupName + " BODY: {newHostGroupName: newGroupName}";
		getLog(message);

		delegateAdapt.getAdapter(adapterType).updateMonitoredHostGroup(serverType, groupName, newGroupName);
		return MonitoringResponse.status(Status.ACCEPTED, newGroupName.getNewHostGroupName()).build().build();

	}

	/********************************************************************************************
	 * IAAS / PAAS
	 ********************************************************************************************/

	/*****************************************
	 * CREATE GROUP INTO PAAS/IAAS PLATFORM
	 * 
	 * @param adapterType
	 * @param hostGroupMonitoringCreateRequest
	 * @return
	 * @throws MonitoringException
	 * @throws
	 * @throws IllegalArgumentException
	 * @throws Exception
	 */
	@Override
	public Response createGroup(String adapterType, String zoneType, String serverType, String groupName)
			throws MonitoringException {
		String message = "PUT: <url>/monitoring/adapters/" + adapterType + "/types/" + serverType + "/groups/"
				+ groupName;
		getLog(message);
		delegateAdapt.getAdapter(adapterType).creationMonitoredHostGroup(groupName);
		return MonitoringResponse.status(Status.CREATED, groupName).build().build();

	}

	/*******************************************
	 * DELETE GROUP FROM PAAS / IAAS PLATFORM
	 * 
	 * @param adapterType
	 * @param hostGroupMonitoringDeleteRequest
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response deleteGroup(String adapterType, String zoneType, String serverType, String groupName)
			throws MonitoringException {
		String message = "DELETE: <url>/monitoring/adapters/" + adapterType + "/types/" + serverType + "/groups/"
				+ groupName;
		getLog(message);
		delegateAdapt.getAdapter(adapterType).deleteMonitoredHostGroup(groupName);
		return MonitoringResponse.status(Status.NO_CONTENT).build().build();

	}

	/***************************************
	 * CREATE HOST INTO IAAS /PAAS PLATFORM
	 * 
	 * @param adapterType
	 * @param hostMonitoringRequest
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response createHost(String adapterType, String zoneType, String serverType, String groupName,
			String hostName, HostMonitoringRequest hostMonitoringRequest) throws MonitoringException {
		String message = "PUT: <url>/monitoring/adapters/" + adapterType + "/types/" + serverType + "/groups/"
				+ groupName + "/hosts/" + hostName + " BODY: { {ip: " + hostMonitoringRequest.ip + "}, "
				+ "{serviceCategory: " + hostMonitoringRequest.serviceCategory + "}, " + "{serviceId: "
				+ hostMonitoringRequest.serviceId + "}, " + "{atomicServices: " + hostMonitoringRequest.atomicServices
				+ "}, " + "{activeMode: " + hostMonitoringRequest.activeMode + "}, " + "{vmuui: "
				+ hostMonitoringRequest.uuid + hostMonitoringRequest.port + "} }";
		getLog(message);

		delegateAdapt.getAdapter(adapterType).creationMonitoredHost(serverType, groupName, hostName,
				hostMonitoringRequest.getUuid(), hostMonitoringRequest.getIp(),
				hostMonitoringRequest.getServiceCategory(), hostMonitoringRequest.getServiceId(),
				hostMonitoringRequest.getAtomicServices(), hostMonitoringRequest.getActiveMode(),
				hostMonitoringRequest.getPort());
		return MonitoringResponse.status(Status.CREATED, hostName).build().build();

	}

	/***************************************
	 * DELETE HOST FROM IAAS /PAAS PLATFORM
	 * 
	 * @param adapterType
	 * @param hostMonitoringDeleteRequest
	 * @return
	 * @throws Exception
	 */
	@Override
	public Response deleteHost(String adapterType, String zoneType, String serverType, String groupName, String hostName)
			throws MonitoringException {
		String message = "DELETE: <url>/monitoring/adapters/" + adapterType + "/types/" + serverType + "/groups/"
				+ groupName + "/hosts/" + hostName;
		getLog(message);

		delegateAdapt.getAdapter(adapterType).deleteMonitoredHost(hostName, null, // serviceId
				serverType);
		return MonitoringResponse.status(Status.NO_CONTENT).build().build();

	}

	/**************************
	 * GET Info by Groups
	 **************************/
	/**
	 * Method that wraps Zabbix API in order to provide info about the gruop
	 * containing all the monitored hosts, the metrics associated to themselves,
	 * as well as trigger. The API consumer will be able to take actions based
	 * on the metrics and triggers' actual values. All this coming from a
	 * specific testbed, zabbix server (in this case zabbic iaas) and adapter.
	 */
	@Override
	public Response getGroupInfo(String adapterType, String zoneType, String serverType, String group)
			throws MonitoringException {
		String message = "GET: <url>/monitoring/adapters/" + adapterType + "/types/" + serverType + "/groups/" + group;
		getLog(message);
		return MonitoringResponse.status(Status.OK, wrapper.getWrapperIaaSPaaS(adapterType, serverType, group, null))
				.build().build();

	}

	/***********************************************
	 * GET platform Info by GROUP and HOSTNAMES
	 * *********************************************/
	@Override
	public Response getHostInfo(String adapterType, String zoneType, String serverType, String group, String host)
			throws MonitoringException {
		String message = "GET: <url>/monitoring/adapters/" + adapterType + "/types/" + serverType + "/groups/" + group
				+ "/hosts/" + host;
		getLog(message);
		return MonitoringResponse.status(Status.OK, wrapper.getWrapperIaaSPaaS(adapterType, serverType, group, host))
				.build().build();

	}

	/******************************************************
	 * GET HOSTs INFO, OR hosts affected by shot THRESHOLDS, OR HOSTs INFO By
	 * serviceId A deployed service might be composed of two or more machines,
	 * so that it's needed to call those hosts belonging to the category by
	 * searching them by the serviceID attached to themselves at the creation
	 * stage. It retrieves all hosts belonging to a specific ATOMIC Service
	 * (properly tagged since the creation of it) created by the user.
	 * 
	 * @param adapter
	 * @param host_id
	 * @param server
	 * @param requestTime
	 * @return
	 ************************************************/
	@Override
	public Response getHost(String adapterType, String zoneType, String serverType, String group, Boolean thresholds,
			String serviceId) throws MonitoringException {
		String message = "GET: <url>/monitoring/adapters/" + adapterType + "/types/" + serverType + "/groups/" + group
				+ "/hosts" + "?service-id=" + serviceId + " OR " + "?thresholds=" + thresholds;
		getLog(message);
		return MonitoringResponse
				.status(Status.OK, wrapper.getWrapperHostComb(adapterType, serverType, group, thresholds, serviceId))
				.build().build();

	}

	/********************************************************
	 * GET THRESHOLDS STATUS (EVENT API Equivalent) BY HOST
	 * 
	 * @param adapter
	 * @param host_id
	 * @param server
	 * @return
	 *****************************************************/

	@Override
	public Response getThresholdsByHost(String adapterType, String zoneType, String serverType, String group,
			String host) throws MonitoringException {
		String message = "GET: <url>/monitoring/adapters/" + adapterType + "/types/" + serverType + "/groups/" + group
				+ "/hosts/" + host + "/thresholds";
		getLog(message);

		WrappedIaasHealthByTrigger wrapperTrigger = delegateAdapt.getAdapter(adapterType).getTriggerByHost(serverType,
				group, host);
		return MonitoringResponse.status(Status.OK, wrapperTrigger).build().build();

	}

	/*********************************
	 * HOST's EVENTS NO FILTER TIME
	 ********************************/
	/**
	 * Method useful for having Events happening into the monitoring platform.
	 * They can be filtered by period of time as much as the history
	 */
	@Override
	public Response getEvents(String adapterType, String zoneType, String serverType, String group, String host)
			throws MonitoringException {
		String message = "GET: <url>/monitoring/adapters/" + adapterType + "/types/" + serverType + "/groups/" + group
				+ "/hosts/" + host + "/events";
		getLog(message);

		MonitPillarEventResponse wrappedEvent = delegateAdapt.getAdapter(adapterType).getOverallServerEvents(
				serverType, host, group, null, null);
		return MonitoringResponse.status(Status.OK, wrappedEvent).build().build();

	}

	/*********************************
	 * HOST's EVENTS WITH FILTER TIME
	 ********************************/
	/**
	 * Method useful for having Events happening into the monitoring platform.
	 * They can be filtered by period of time as much as the history
	 * 
	 * @param adapter
	 * @param host_id
	 * @param history_filtered
	 * @param server
	 * @param requestTime
	 * @return the wrapped Zabbix APIs describing
	 */
	@Override
	public Response getFilteredEvents(String adapterType, String zoneType, String serverType, String group,
			String host, FilterTimeRequest requestTime) throws MonitoringException {

		String message = "POST: <url>/monitoring/adapters/" + adapterType + "/types/" + serverType + "/groups/" + group
				+ "/hosts/" + host + "/events" + "BODY: {" + "dateFrom: {year: " + requestTime.getDateFrom().getYear()
				+ ", " + "{month: " + requestTime.getDateFrom().getMonth() + ", " + "{day: "
				+ requestTime.getDateFrom().getDay() + ",  " + "{time: {" + requestTime.getDateFrom().getTime().getHh()
				+ ", " + "{" + requestTime.getDateFrom().getTime().getMm() + ", " + "{"
				+ requestTime.getDateFrom().getTime().getSs() + " } }, {" + "dateTo: {year: "
				+ ckeckDateTo(requestTime);
		getLog(message);

		MonitPillarEventResponse wrappedEvent = delegateAdapt.getAdapter(adapterType).getOverallServerEvents(
				serverType, null, null, null, requestTime);
		return MonitoringResponse.status(Status.OK, wrappedEvent).build().build();

	}

	/***********************************************************************
	 * PaaS
	 ***********************************************************************/

	/*************************************************
	 * GET LIST OF METRICS ASSOCIATED A sPECIFIC HOST
	 * **********************************************
	 */
	@Override
	public Response getMetricList(String adapterType, String zone, String type, String group, String host)
			throws MonitoringException {
		// TODO Auto-generated method stub

		return null;
	}

	/*********************************
	 * SPECIFIC METRIC REQUESTED
	 ********************************/
	/**
	 * Method useful for having a specific metric info (identified by its name)
	 * belonging to a specific host, category group, atomic service which all
	 * are to be specified in the request.
	 * 
	 * @param testbeds_id
	 * @param adapter
	 * @param host_id
	 * @param service_category_id
	 * @param tag_service
	 * @param atomic_service_id
	 * @param metrics_id
	 * @return MonitoringWrappedResponsePaaS Object that wraps Zabbix APIs in
	 *         order to give needed information about a host, service category,
	 *         atomic services, metrics, triggers
	 * @throws MappingException
	 * @throws APIErrorException
	 * @throws NoMappingModelFoundException
	 * @throws ServerErrorResponseException
	 * @throws RestClientException
	 * @throws Exception
	 */

	@Override
	public Response getMetric(String adapterType, String zoneType, String serverType, String group, String host,
			String metric) throws MonitoringException {
		String message = "GET: <url>/monitoring/adapters/" + adapterType + "/types/" + serverType + "/groups/" + group
				+ "/hosts/" + host + "/metrics/" + metric;
		getLog(message);

		if (!isTypeAllowed(serverType)) {
			return MonitoringResponse.status(Status.NOT_IMPLEMENTED, errorServerTypeUnallowed).build().build();
		} else {
			MonitoringWrappedResponsePaas wrappedPaas = (MonitoringWrappedResponsePaas) delegateAdapt.getAdapter(
					adapterType).getInfoWrapperGeneric(serverType, group, host, null, null, null, metric, null // triggers_id
					, null, null // TimeRequestBody
					);
			return MonitoringResponse.status(Status.OK, wrappedPaas).build().build();
		}
	}

	/*****************************
	 * HISTORY without filter Time
	 * 
	 * @param adapter
	 * @param host_id
	 * @param service_category_id
	 * @param tag_service
	 * @param history
	 * @param server
	 * @param atomic_services
	 * @param metrics_id
	 * @return REST response listing the values and history
	 */

	@Override
	public Response getHistory(String adapterType, String zoneType, String serverType, String group, String host,
			String metric) throws MonitoringException {
		String message = "GET: <url>/monitoring/adapters/" + adapterType + "/types/" + serverType + "/groups/" + group
				+ "/hosts/" + host + "/metrics/" + metric + "/history/";
		getLog(message);

		String history = "true";
		if (!isTypeAllowed(serverType)) {
			return MonitoringResponse.status(Status.NOT_IMPLEMENTED, errorServerTypeUnallowed).build().build();
		}

		MonitoringWrappedResponsePaas wrappedPaas = (MonitoringWrappedResponsePaas) delegateAdapt.getAdapter(
				adapterType).getInfoWrapperGeneric(serverType, group, host, null, null,// service_id,
				null,// atomic_services,
				metric, null // triggers_id
				, history, null // TimeRequestBody
				);
		return MonitoringResponse.status(Status.OK, wrappedPaas).build().build();

	}

	/*********************************
	 * HISTORY BY FILTER TIME
	 ********************************/
	/**
	 * Method useful for having a specific metric info (identified by its name)
	 * belonging to a specific host, category group, atomic service which all
	 * are to be specified in the request.
	 * 
	 * @param testbeds_id
	 * @param adapter
	 * @param host
	 * @param service_category_id
	 * @param tag_service
	 * @param atomic_service_id
	 * @param metrics_id
	 * @return MonitoringWrappedResponsePaaS Object that wraps Zabbix APIs in
	 *         order to give needed information about a host, service category,
	 *         atomic services, metrics, triggers
	 * @throws MappingException
	 * @throws APIErrorException
	 * @throws NoMappingModelFoundException
	 * @throws ServerErrorResponseException
	 * @throws RestClientException
	 * @throws Exception
	 */

	@Override
	public Response getHistoryByTime(String adapterType, String zoneType,
	// String host,
			String serverType, String group, String host, String metric, FilterTimeRequest requestTime)
			throws MonitoringException {
		String message = "POST: <url>/monitoring/adapters/" + adapterType + "/types/" + serverType + "/groups/" + group
				+ "/hosts/" + host + "/metrics/" + metric + "/history/" + "BODY: {" + "dateFrom: {year: "
				+ requestTime.getDateFrom().getYear() + ", " + "{month: " + requestTime.getDateFrom().getMonth() + ", "
				+ "{day: " + requestTime.getDateFrom().getDay() + ",  " + "{time: {"
				+ requestTime.getDateFrom().getTime().getHh() + ", " + "{"
				+ requestTime.getDateFrom().getTime().getMm() + ", " + "{"
				+ requestTime.getDateFrom().getTime().getSs() + " } }, {" + "dateTo: {year: "
				+ ckeckDateTo(requestTime);

		getLog(message);

		String history = "true";
		if (!isTypeAllowed(serverType)) {
			return MonitoringResponse.status(Status.NOT_IMPLEMENTED, errorServerTypeUnallowed).build().build();
		} else {
			MonitoringWrappedResponsePaas wrappedPaas = (MonitoringWrappedResponsePaas) delegateAdapt.getAdapter(
					adapterType).getInfoWrapperGeneric(serverType, group, host, null, null, null, metric, null,
					history, requestTime);
			return MonitoringResponse.status(Status.OK, wrappedPaas).build().build();
		}

	}

	private String ckeckDateTo(FilterTimeRequest requestTime) {
		if (requestTime.getDateTo().getDay() != null) {

			return requestTime.getDateTo().getYear() + ", " + "{month: " + requestTime.getDateTo().getMonth() + ", "
					+ "{day: " + requestTime.getDateTo().getDay() + ",  " + "{time: {"
					+ requestTime.getDateTo().getTime().getHh() + ", " + "{"
					+ requestTime.getDateTo().getTime().getMm() + ", " + "{"
					+ requestTime.getDateTo().getTime().getSs() + "}," + "upToNow: "
					+ requestTime.getDateTo().getUpToNow() + " } }";

		} else
			return requestTime.getDateTo().getUpToNow() + " } }";

	}

	/***********************
	 * HISTORY BY SERVICEID
	 ***********************/
	@Override
	public Response getHistoryByServiceandTime(String adapterType, String zoneType, String serverType, String group,
			Long serviceId, String metric, FilterTimeRequest requestTime) throws MonitoringException {

		String message = "POST: <url>/monitoring/adapters/" + adapterType + "/types/" + serverType + "/groups/" + group
				+ "/services/" + serviceId + "/history/" + "BODY: {" + "dateFrom: {year: "
				+ requestTime.getDateFrom().getYear() + ", " + "{month: " + requestTime.getDateFrom().getMonth() + ", "
				+ "{day: " + requestTime.getDateFrom().getDay() + ",  " + "{time: {"
				+ requestTime.getDateFrom().getTime().getHh() + ", " + "{"
				+ requestTime.getDateFrom().getTime().getMm() + ", " + "{"
				+ requestTime.getDateFrom().getTime().getSs() + " } }, {" + "dateTo: {year: "
				+ ckeckDateTo(requestTime);
		getLog(message);

		String history = "true";
		if (!isTypeAllowed(serverType)) {
			return MonitoringResponse.status(Status.NOT_IMPLEMENTED, errorServerTypeUnallowed).build().build();
		} else {
			MonitoringWrappedResponsePaas wrappedPaas = (MonitoringWrappedResponsePaas) delegateAdapt.getAdapter(
					adapterType).getInfoWrapperGeneric(serverType, group, null, null, String.valueOf(serviceId), null,
					metric, null, history, requestTime);
			return MonitoringResponse.status(Status.OK, wrappedPaas).build().build();
		}
	}

	/*****************
	 * DISABLE HOST
	 *****************/
	/**
	 * Method useful for having Events happening into the monitoring platform.
	 * They can be filtered by period of time as much as the history
	 * 
	 * @param adapter
	 * @param host_id
	 * @param history_filtered
	 * @param server
	 * @param requestTime
	 * @return the wrapped Zabbix APIs describing
	 */

	@Override
	public Response getDisableHost(String adapterType, String zoneType, String serverType, String group,
			String hostName, String update) throws MonitoringException {
		String message = "GET: <url>/monitoring/adapters/" + adapterType + "/types/" + serverType + "/groups/" + group
				+ "/hosts/" + hostName + "?update=" + update;
		getLog(message);

		delegateAdapt.getAdapter(adapterType).getDisablingHost(serverType, group, hostName, null, null, update);
		return MonitoringResponse.status(Status.OK, hostName).build().build();

	}

	/*****************
	 * DISABLE METRIC
	 *****************/
	@Override
	public Response getDisableMetric(String adapterType, String zoneType, String serverType, String group,
			String hostName, String metric, String update) throws MonitoringException {
		String message = "GET: <url>/monitoring/adapters/" + adapterType + "/types/" + serverType + "/groups/" + group
				+ "/hosts/" + hostName + "/metrics/" + metric + "?update=" + update;
		getLog(message);

		delegateAdapt.getAdapter(adapterType).getDisablingItem(serverType, group, hostName, metric, null, update);

		return MonitoringResponse.status(Status.OK, metric).build().build();
	}

	// Logging method
	private void getLog(String message) {
		try {
			ConfigPillar.writeToLogPillarFile(message, TimestampMonitoring.decodUnixTime2Date(now.getTime()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// Method for giving warning about using just the paas platform
	public boolean isTypeAllowed(String serverType) throws MonitoringException {
		if (serverType.equalsIgnoreCase(InfoType.INFRASTRUCTURE.getInfoType())) {
			return false;
		} else {
			return true;
		}
	}

	// Method for getting the right zone and making sure the right one has been
	// passed via API
	private Boolean checkZone(String zone) {
		return config.loadMonitoringPropertiesFileAndZoneProperties(zone);
	}

}