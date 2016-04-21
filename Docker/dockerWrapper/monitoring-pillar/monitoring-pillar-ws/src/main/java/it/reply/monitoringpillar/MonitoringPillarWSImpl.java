package it.reply.monitoringpillar;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;
import javax.interceptor.Interceptors;
import javax.naming.NameNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import it.reply.monitoringpillar.adapter.DelegatorAdapter;
import it.reply.monitoringpillar.adapter.zabbix.handler.ZabbixFeatures.ServerSignature;
import it.reply.monitoringpillar.config.Configuration;
import it.reply.monitoringpillar.domain.dsl.monitoring.Adapter;
import it.reply.monitoringpillar.domain.dsl.monitoring.InfoType;
import it.reply.monitoringpillar.domain.dsl.monitoring.businesslayer.paas.request.HostGroupMonitoringCreateRequest;
import it.reply.monitoringpillar.domain.dsl.monitoring.businesslayer.paas.request.HostMonitoringRequest;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.protocol.MonitoringResponse;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.protocol.PillarAdapter;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.protocol.PillarServerType;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.MonitPillarEventResponse;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.ProxyCreationRequest;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.iaas.WrappedIaasHealthByTrigger;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas.EventCallbackRequest;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas.FilterTimeRequest;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas.MonitoringPillarEventCallbackResponse;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas.MonitoringWrappedResponsePaas;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas.MonitoringWrappedResponsePaasGroups;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas.MonitoringWrappedResponsePaasGroups4HostList;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.request.SendMailRequest;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.request.UpdateGroupName;
import it.reply.monitoringpillar.domain.exception.IllegalArgumentMonitoringException;
import it.reply.monitoringpillar.domain.exception.MonitoringException;
import it.reply.monitoringpillar.domain.exception.NotFoundMonitoringException;
import it.reply.monitoringpillar.wrapper.WrapperProvider;
import it.reply.utils.web.ws.rest.apiclients.prisma.APIErrorException;
import it.reply.utils.web.ws.rest.apiencoding.MappingException;
import it.reply.utils.web.ws.rest.apiencoding.NoMappingModelFoundException;
import it.reply.utils.web.ws.rest.apiencoding.ServerErrorResponseException;
import it.reply.utils.web.ws.rest.restclient.exceptions.RestClientException;

/**
 * 
 * @author m.grandolfo <br>
 *         <br>
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
	 *            <br>
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
	 *            <br>
	 *            <br>
	 * @return the ID belonging to the cancelled host
	 *         </ul>
	 *         <br>
	 *         <br>
	 *         <ul>
	 *         <li>deleting a monitored host </br>
	 * @param bodyRequest
	 *            <br>
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
	 *            <br>
	 * @return the ID belonging to the cancelled host
	 *         </ul>
	 * 
	 *         <br>
	 *         <br>
	 * 
	 */

	/*****************
	 * GET ADAPTERS
	 * 
	 * @throws IOException
	 * @throws
	 * 
	 * 			@throws
	 *             NotFoundMonitoringException
	 ****************/
	@Override
	public Response listAllAdapter(@Context HttpServletRequest request, @Context HttpServletResponse response) {
		return MonitoringResponse.status(Status.OK, new PillarAdapter().withAdapters(Adapter.getAllAdapter())).build()
				.build();
	}

	/*****************
	 * GET ALL ZONES
	 * 
	 * @throws IOException
	 * @throws
	 * 
	 * 			@throws
	 *             NotFoundMonitoringException
	 ****************/
	@Override
	public Response listAllZones(@Context HttpServletRequest request, @Context HttpServletResponse response,
			String adapterType) {
		return MonitoringResponse.status(Status.OK, config.getZoneNames()).build().build();
	}

	/*******************************
	 * GET LIST OF SERVERS
	 * 
	 * @throws IllegalArgumentMonitoringException
	 * @throws NotFoundMonitoringException
	 ************/
	@Override
	public Response listAllServerType(@Context HttpServletRequest request, @Context HttpServletResponse response,
			String adapterType, String zone) throws IllegalArgumentMonitoringException, NotFoundMonitoringException {
		delegateAdapt.getAdapter(adapterType);
		return MonitoringResponse.status(Status.OK, new PillarServerType().withServers(ServerSignature.getAllServer()))
				.build().build();
	}

	/*******************************
	 * GET GROUPLIST WRAPPED MINIMAL
	 * 
	 * @throws MonitoringException
	 * 			@throws
	 *****************************/
	@Override
	public Response getGroupList(@Context HttpServletRequest request, @Context HttpServletResponse response,
			String adapterType, String zone, String serverType) throws MonitoringException {
		MonitoringWrappedResponsePaasGroups wrappedGroup = new MonitoringWrappedResponsePaasGroups();
		wrappedGroup = delegateAdapt.getAdapter(adapterType).getGroupsInfoWrapped(zone, serverType);

		return MonitoringResponse.status(Status.OK, wrappedGroup).build().build();
	}

	public Response getHostList(@Context HttpServletRequest request, @Context HttpServletResponse response,
			String adapterType, String zone, String serverType, String groupName) throws MonitoringException {
		MonitoringWrappedResponsePaasGroups4HostList wrappedHostList = delegateAdapt.getAdapter(adapterType)
				.getHostsInfoWrapped(zone, serverType, groupName);

		return MonitoringResponse.status(Status.OK, wrappedHostList).build().build();

	}

	/*******************
	 * UPDATE HOSTGROUP
	 ******************/
	@Override
	public Response updateGroup(@Context HttpServletRequest request, @Context HttpServletResponse response,
			String adapterType, String zone, String serverType, String groupName, UpdateGroupName newGroupName)
					throws MonitoringException {
		// checkZone(zone);
		delegateAdapt.getAdapter(adapterType).updateMonitoredHostGroup(zone, serverType, groupName, newGroupName);
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
	 * @throws @throws
	 *             IllegalArgumentException
	 * @throws Exception
	 */
	@Override
	public Response createGroup(@Context HttpServletRequest request, @Context HttpServletResponse response,
			String adapterType, String zone, String serverType, HostGroupMonitoringCreateRequest group)
					throws MonitoringException {
		delegateAdapt.getAdapter(adapterType).creationMonitoredHostGroup(zone, serverType, group.getHostGroupName());
		return MonitoringResponse.status(Status.CREATED, group.getHostGroupName()).build().build();

	}

	/**********************
	 * CREATE PROXY SERVER
	 **********************/
	@Override
	public Response createProxy(@Context HttpServletRequest request, @Context HttpServletResponse response,
			String adapterType, String zone, String serverType, String workGroup, ProxyCreationRequest proxy)
					throws MonitoringException {
		delegateAdapt.getAdapter(adapterType).creationMonitoredProxy(zone, serverType, workGroup, proxy.getProxyName());
		return MonitoringResponse.status(Status.CREATED, proxy.getProxyName()).build().build();
	}

	/**********************
	 * CREATE PROXY SERVER
	 **********************/
	@Override
	public Response deleteProxy(@Context HttpServletRequest request, @Context HttpServletResponse response,
			String adapterType, String zone, String serverType, String workGroup, String proxyName)
					throws MonitoringException {
		delegateAdapt.getAdapter(adapterType).deleteMonitoredProxy(zone, serverType, proxyName);
		return MonitoringResponse.status(Status.NO_CONTENT).build().build();
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
	public Response deleteGroup(@Context HttpServletRequest request, @Context HttpServletResponse response,
			String adapterType, String zone, String serverType, String groupName) throws MonitoringException {
		delegateAdapt.getAdapter(adapterType).deleteMonitoredHostGroup(zone, serverType, groupName);
		return MonitoringResponse.status(Status.NO_CONTENT).build().build();

	}

	/***************************************
	 * CREATE HOST INTO IAAS /PAAS PLATFORM
	 * 
	 * @param adapterType
	 * @param hostMonitoringRequest
	 * @return
	 * @throws NameNotFoundException
	 * @throws Exception
	 */
	@Override
	public Response createHost(@Context HttpServletRequest request, @Context HttpServletResponse response,
			String adapterType, String zone, String serverType, String groupName, String hostName,
			HostMonitoringRequest hostMonitoringRequest) throws MonitoringException, NameNotFoundException {

		delegateAdapt.getAdapter(adapterType).creationMonitoredHost(zone, serverType, groupName, hostName,
				hostMonitoringRequest.getUuid(), hostMonitoringRequest.getIp(),
				hostMonitoringRequest.getServiceCategory(), hostMonitoringRequest.getServiceId(),
				hostMonitoringRequest.getAtomicServices(), hostMonitoringRequest.getActiveMode(),
				hostMonitoringRequest.getPort(), hostMonitoringRequest.getProxyName());
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
	public Response deleteHost(@Context HttpServletRequest request, @Context HttpServletResponse response,
			String adapterType, String zone, String serverType, String groupName, String hostName)
					throws MonitoringException {
		delegateAdapt.getAdapter(adapterType).deleteMonitoredHost(zone, serverType, hostName, null // serviceId
		);
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
	public Response getGroupInfo(@Context HttpServletRequest request, @Context HttpServletResponse response,
			String adapterType, String zone, String serverType, String group) throws MonitoringException {
		return MonitoringResponse
				.status(Status.OK, wrapper.getWrapperIaaSPaaS(adapterType, zone, serverType, group, null)).build()
				.build();

	}

	/***********************************************
	 * GET platform Info by GROUP and HOSTNAMES
	 *********************************************/
	@Override
	public Response getHostInfo(@Context HttpServletRequest request, @Context HttpServletResponse response,
			String adapterType, String zone, String serverType, String group, String host) throws MonitoringException {
		return MonitoringResponse
				.status(Status.OK, wrapper.getWrapperIaaSPaaS(adapterType, zone, serverType, group, host)).build()
				.build();

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
	public Response getHost(@Context HttpServletRequest request, @Context HttpServletResponse response,
			String adapterType, String zone, String serverType, String group, Boolean thresholds, String serviceId)
					throws MonitoringException {
		return MonitoringResponse.status(Status.OK,
				wrapper.getWrapperHostComb(adapterType, zone, serverType, group, null, thresholds, null, serviceId))
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
	public Response getThresholdsByHost(@Context HttpServletRequest request, @Context HttpServletResponse response,
			String adapterType, String zone, String serverType, String group, String host) throws MonitoringException {
		WrappedIaasHealthByTrigger wrapperTrigger = delegateAdapt.getAdapter(adapterType).getTriggerByHost(zone,
				serverType, group, host);
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
	public Response getEvents(@Context HttpServletRequest request, @Context HttpServletResponse response,
			String adapterType, String zone, String serverType, String group, String host) throws MonitoringException {
		MonitPillarEventResponse wrappedEvent = delegateAdapt.getAdapter(adapterType).getOverallServerEvents(zone,
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
	@Deprecated
	@Override
	public Response getFilteredEvents(@Context HttpServletRequest request, @Context HttpServletResponse response,
			String adapterType, String zone, String serverType, String group, String host,
			FilterTimeRequest requestTime) throws MonitoringException {
		MonitPillarEventResponse wrappedEvent = delegateAdapt.getAdapter(adapterType).getOverallServerEvents(zone,
				serverType, null, null, null, requestTime);
		return MonitoringResponse.status(Status.OK, wrappedEvent).build().build();
	}

	@Override
	public Response getFilteredEventsByTimestamp(@Context HttpServletRequest request,
			@Context HttpServletResponse response, String adapterType, String zone, String serverType, String group,
			String host, Long from, Long to) throws MonitoringException {
		FilterTimeRequest filterTime = new FilterTimeRequest();
		filterTime.setFrom(from);
		filterTime.setTo(to);
		MonitPillarEventResponse wrappedEvent = delegateAdapt.getAdapter(adapterType).getOverallServerEvents(zone,
				serverType, host, group, null, filterTime);
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
	public Response getMetricList(@Context HttpServletRequest request, @Context HttpServletResponse response,
			String adapterType, String zone, String serverType, String group, String host) throws MonitoringException {
		return MonitoringResponse
				.status(Status.OK,
						wrapper.getWrapperHostComb(adapterType, zone, serverType, group, host, null, true, null))
				.build().build();
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
	public Response getMetric(@Context HttpServletRequest request, @Context HttpServletResponse response,
			String adapterType, String zone, String serverType, String group, String host, String metric)
					throws MonitoringException {
		if (!isTypeAllowed(serverType)) {
			return MonitoringResponse.status(Status.BAD_REQUEST, errorServerTypeUnallowed).build().build();
		} else {
			MonitoringWrappedResponsePaas wrappedPaas = (MonitoringWrappedResponsePaas) delegateAdapt
					.getAdapter(adapterType).getInfoWrapperGeneric(zone, serverType, group, host, null, null, null,
							metric, null // triggers_id
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
	public Response getHistory(@Context HttpServletRequest request, @Context HttpServletResponse response,
			String adapterType, String zone, String serverType, String group, String host, String metric)
					throws MonitoringException {
		String history = "true";
		if (!isTypeAllowed(serverType)) {
			return MonitoringResponse.status(Status.BAD_REQUEST, errorServerTypeUnallowed).build().build();
		}

		MonitoringWrappedResponsePaas wrappedPaas = (MonitoringWrappedResponsePaas) delegateAdapt
				.getAdapter(adapterType).getInfoWrapperGeneric(zone, serverType, group, host, null, null, // service_id,
						null, // atomic_services,
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
	public Response getHistoryByTime(@Context HttpServletRequest request, @Context HttpServletResponse response,
			String adapterType, String zone, String serverType, String group, String host, String metric,
			FilterTimeRequest requestTime) throws MonitoringException {
		String history = "true";
		if (!isTypeAllowed(serverType)) {
			return MonitoringResponse.status(Status.BAD_REQUEST, errorServerTypeUnallowed).build().build();
		} else {
			MonitoringWrappedResponsePaas wrappedPaas = (MonitoringWrappedResponsePaas) delegateAdapt
					.getAdapter(adapterType).getInfoWrapperGeneric(zone, serverType, group, host, null, null, null,
							metric, null, history, requestTime);
			return MonitoringResponse.status(Status.OK, wrappedPaas).build().build();
		}

	}

	private String ckeckDateTo(FilterTimeRequest requestTime) {
		if (requestTime.getDateTo().getDay() != null) {

			return requestTime.getDateTo().getYear() + ", " + "{month: " + requestTime.getDateTo().getMonth() + ", "
					+ "{day: " + requestTime.getDateTo().getDay() + ",  " + "{time: {"
					+ requestTime.getDateTo().getTime().getHh() + ", " + "{" + requestTime.getDateTo().getTime().getMm()
					+ ", " + "{" + requestTime.getDateTo().getTime().getSs() + "}," + "upToNow: "
					+ requestTime.getDateTo().getUpToNow() + " } }";

		} else
			return requestTime.getDateTo().getUpToNow() + " } }";

	}

	/*********************************
	 * History By Timestamp FilterTime
	 ********************************/
	@Override
	public Response getHistoryByTime(@Context HttpServletRequest request, @Context HttpServletResponse response,
			String adapterType, String zone, String serverType, String group, String host, String metric, Long from,
			Long to) throws MonitoringException {
		FilterTimeRequest filterTime = new FilterTimeRequest();
		filterTime.setFrom(from);
		filterTime.setTo(to);
		MonitoringWrappedResponsePaas wrappedPaas = (MonitoringWrappedResponsePaas) delegateAdapt
				.getAdapter(adapterType).getInfoWrapperGeneric(zone, serverType, group, host, null, null, null, metric,
						null, "true", filterTime);
		return MonitoringResponse.status(Status.OK, wrappedPaas).build().build();
	}

	/***********************
	 * HISTORY BY SERVICEID
	 ***********************/
	@Override
	public Response getHistoryByServiceandTime(@Context HttpServletRequest request,
			@Context HttpServletResponse response, String adapterType, String zone, String serverType, String group,
			Long serviceId, String metric, FilterTimeRequest requestTime) throws MonitoringException {
		String history = "true";
		if (!isTypeAllowed(serverType)) {
			return MonitoringResponse.status(Status.BAD_REQUEST, errorServerTypeUnallowed).build().build();
		} else {
			MonitoringWrappedResponsePaas wrappedPaas = (MonitoringWrappedResponsePaas) delegateAdapt
					.getAdapter(adapterType).getInfoWrapperGeneric(zone, serverType, group, null, null,
							String.valueOf(serviceId), null, metric, null, history, requestTime);
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
	public Response getDisableHost(@Context HttpServletRequest request, @Context HttpServletResponse response,
			String adapterType, String zone, String serverType, String group, String hostName, String update)
					throws MonitoringException {
		delegateAdapt.getAdapter(adapterType).getDisablingHost(zone, serverType, group, hostName, null, null, update);
		return MonitoringResponse.status(Status.OK, hostName).build().build();

	}

	/*****************
	 * DISABLE METRIC
	 *****************/
	@Override
	public Response getDisableMetric(@Context HttpServletRequest request, @Context HttpServletResponse response,
			String adapterType, String zone, String serverType, String group, String hostName, String metric,
			String update) throws MonitoringException {
		delegateAdapt.getAdapter(adapterType).getDisablingItem(zone, serverType, group, hostName, metric, null, update);

		return MonitoringResponse.status(Status.OK, metric).build().build();
	}

	/***********************
	 * EVENT CALLBACK UPDATE
	 ***********************/
	@Override
	public Response getEventCallback(@Context HttpServletRequest request, @Context HttpServletResponse response,
			String adapterType, String zone, String serverType, EventCallbackRequest eventCallback)
					throws MonitoringException {
		MonitoringPillarEventCallbackResponse wrappedEventCallback = delegateAdapt.getAdapter(adapterType)
				.manageCallbackEvent(zone, eventCallback.getGroup(), eventCallback.getHostName(),
						eventCallback.getHostId(), eventCallback.getPaaSServiceId(), eventCallback.getIp(),
						eventCallback.getMetric(), eventCallback.getThreshold(), eventCallback.getTriggerStatus(),
						eventCallback.getDescription());

		return MonitoringResponse.status(Status.OK, wrappedEventCallback).build().build();
	}

	/**************
	 * SEND MAILS
	 **************/
	@Override
	public Response getSendMail(@Context HttpServletRequest request, @Context HttpServletResponse response,
			String adapterType, String zone, String serverType, SendMailRequest sendMailRequest)
					throws MonitoringException {
		delegateAdapt.getAdapter(adapterType).getSendMails(zone, serverType, sendMailRequest);

		return MonitoringResponse.status(Status.CREATED, sendMailRequest.getSendmailTo()).build().build();

	}

	// Method for giving warning about using just the paas platform
	public boolean isTypeAllowed(String serverType) throws MonitoringException {
		if (serverType.equalsIgnoreCase(InfoType.INFRASTRUCTURE.getInfoType())) {
			return false;
		} else {
			return true;
		}
	}

}