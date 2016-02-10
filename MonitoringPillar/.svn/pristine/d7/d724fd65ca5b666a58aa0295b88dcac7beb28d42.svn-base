package it.monitoringpillar;

import it.monitoringpillar.exception.IllegalArgumentMonitoringException;
import it.monitoringpillar.exception.MonitoringException;
import it.monitoringpillar.exception.NotFoundMonitoringException;
import it.prisma.domain.dsl.monitoring.businesslayer.paas.request.HostMonitoringRequest;
import it.prisma.domain.dsl.monitoring.pillar.wrapper.paas.FilterTimeRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.UpdateGroupName;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/adapters")
public interface MonitoringPillarWS {

	/**********************
	 * IAAS / PAAS
	 *********************/

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response listAllAdapter();

	@GET
	@Path("/{adapterType}/zones")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response listAllZones(@DefaultValue("zabbix") @PathParam("adapterType") String adapterType)
			throws IllegalArgumentMonitoringException, NotFoundMonitoringException;

	@GET
	@Path("/{adapterType}/zones/{zone}/monitoring-types")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response listAllServerType(@DefaultValue("zabbix") @PathParam("adapterType") String adapterType,
			@PathParam("zone") String zone) throws IllegalArgumentMonitoringException, NotFoundMonitoringException;

	// TODO: if an endpoint didn't manage some groups how are we supposed to be
	// dealing with that?

	/******************************
	 * GET IaaS/PaaS Info by Groups
	 ******************************/
	@GET
	@Path("/{adapterType}/zones/{zone}/monitoring-types/{type}/groups/")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getGroupList(@DefaultValue("zabbix") @PathParam("adapterType") String adapterType,
			@PathParam("zone") String zone, @PathParam("type") String type) throws MonitoringException;

	/******************************
	 * GET IaaS/PaaS Info by Groups
	 ******************************/
	@GET
	@Path("/{adapterType}/zones/{zone}/monitoring-types/{type}/groups/{group}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getGroupInfo(@DefaultValue("zabbix") @PathParam("adapterType") String adapterType,
			@PathParam("zone") String zone, @PathParam("type") String type, @PathParam("group") String group)
			throws MonitoringException;

	/*****************************************************************
	 * CREATE GROUP INTO PAAS and IAAS PLATFORM by passing all as type
	 **************************************************************/
	@PUT
	@Path("/{adapterType}/{zone}/groups/monitoring-types/{type}/zones/{group}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createGroup(@DefaultValue("zabbix") @PathParam("adapterType") String adapterType,
			@PathParam("zone") String zone, @PathParam("type") String type, @PathParam("group") String group)
			throws MonitoringException;

	/*******************************************
	 * DELETE GROUP FROM PAAS and IAAS PLATFORM
	 ******************************************/
	@DELETE
	@Path("/{adapterType}/zones/{zone}/monitoring-types/{type}/groups/{group}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteGroup(@DefaultValue("zabbix") @PathParam("adapterType") String adapterType,
			@PathParam("zone") String zone, @PathParam("type") String type, @PathParam("group") String group)
			throws MonitoringException;

	/***********************
	 * UPDATE GROUP NAME
	 **********************/
	@POST
	@Path("/{adapterType}/zones/{zone}/monitoring-types/{type}/groups/{group}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateGroup(@DefaultValue("zabbix") @PathParam("adapterType") String adapterType,
			@PathParam("zone") String zone, @PathParam("type") String type, @PathParam("group") String group,
			UpdateGroupName newGroupName) throws MonitoringException;

	/******************************************************
	 * GET HOSTS STATUS (EVENT API Equivalent) BY GROUP
	 ******************************************************/
	@GET
	@Path("/{adapterType}/zones/{zone}/monitoring-types/{type}/groups/{group}/hosts")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getHost(@DefaultValue("zabbix") @PathParam("adapterType") String adapterType,
			@PathParam("zone") String zone, @PathParam("type") String type, @PathParam("group") String group,
			@QueryParam("thresholds") Boolean thresholds, @QueryParam("service-id") String serviceId)
			throws MonitoringException;

	/************************************
	 * CREATE HOST INTO IAAS/PAAS PLATFORM
	 ************************************/
	@PUT
	@Path("/{adapterType}/zones/{zone}/monitoring-types/{type}/groups/{group}/hosts/{host}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createHost(@DefaultValue("zabbix") @PathParam("adapterType") String adapterType,
			@PathParam("zone") String zone, @PathParam("type") String type, @PathParam("group") String group,
			@PathParam("host") String host, HostMonitoringRequest hostMonitoringRequest) throws MonitoringException;

	/***************************************
	 * DELETE HOST FROM IAAS /PAAS PLATFORM
	 **************************************/
	@DELETE
	@Path("/{adapterType}/zones/{zone}/monitoring-types/{type}/groups/{group}/hosts/{host}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteHost(@DefaultValue("zabbix") @PathParam("adapterType") String adapterType,
			@PathParam("zone") String zone, @PathParam("type") String type, @PathParam("group") String group,
			@PathParam("host") String host) throws MonitoringException;

	/*******************************************************************************
	 * GET A SPECIFIC HOST INFO (and its carachteristics ) BY ITS NAME/UUID
	 ******************************************************************************/
	/**
	 * Method useful for having a SPECIFIC HOST info (and its characteristics )
	 * belonging to a SPECIFIC Category service regardless of the fact that it
	 * could be belonging to a group of host composing a Category service
	 * identified by the ID.
	 */
	@GET
	@Path("/{adapterType}/zones/{zone}/monitoring-types/{type}/groups/{group}/hosts/{host}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getHostInfo(@DefaultValue("zabbix") @PathParam("adapterType") String adapterType,
			@PathParam("zone") String zone, @PathParam("type") String type, @PathParam("group") String group,
			@PathParam("host") String host) throws MonitoringException;

	/*****************
	 * DISABLE HOST
	 *****************/
	/**
	 * Method useful for having Events happening into the monitoring platform.
	 * They can be filtered by period of time as much as the history
	 */
	@POST
	@Path("/{adapterType}/zones/{zone}/monitoring-types/{type}/groups/{group}/hosts/{host}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getDisableHost(@DefaultValue("zabbix") @PathParam("adapterType") String adapterType,
			@PathParam("zone") String zone, @PathParam("type") String type, @PathParam("group") String group,
			@PathParam("host") String host, @QueryParam("update") String update) throws MonitoringException;

	/*********************************************************
	 * GET TRIGGERS STATUS (EVENT API Equivalent) BY HOSTNAME
	 **********************************************************/
	@Path("/{adapterType}/zones/{zone}/monitoring-types/{type}/groups/{group}/hosts/{host}/thresholds")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getThresholdsByHost(@DefaultValue("zabbix") @PathParam("adapterType") String adapterType,
			@PathParam("zone") String zone, @PathParam("type") String type, @PathParam("group") String group,
			@PathParam("host") String host) throws MonitoringException;

	/*********************************
	 * HOST's EVENTS - NO FILTER TIME
	 ********************************/
	/**
	 * Method useful for having Events happening into the monitoring platform.
	 * They can be filtered by period of time as much as the history
	 */
	@Path("/{adapterType}/zones/{zone}/monitoring-types/{type}/groups/{group}/hosts/{host}/events")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getEvents(@DefaultValue("zabbix") @PathParam("adapterType") String adapterType,
			@PathParam("zone") String zone, @PathParam("type") String type, @PathParam("group") String group,
			@PathParam("host") String host) throws MonitoringException;

	/*********************************
	 * HOST's EVENTS WITH FILTER TIME
	 ********************************/
	/**
	 * Method useful for having Events happening into the monitoring platform.
	 * They can be filtered by period of time as much as the history
	 */
	@Path("/{adapterType}/zones/{zone}/monitoring-types/{type}/groups/{group}/hosts/{host}/events")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getFilteredEvents(@DefaultValue("zabbix") @PathParam("adapterType") String adapterType,
			@PathParam("zone") String zone, @PathParam("type") String type, @PathParam("group") String group,
			@PathParam("host") String host, FilterTimeRequest requestTime) throws MonitoringException;

	/***********
	 * PAAS
	 ***********/

	/***********************************************
	 * LIST OF METRICS ASSOCIATED TO a SPECIFIC HOST
	 ***********************************************/
	/**
	 * Method useful for having a specific metric info (identified by its name)
	 * belonging to a specific host, category group, atomic service which all
	 * are to be specified in the request.
	 */
	@Path("/{adapterType}/zones/{zone}/monitoring-types/{type}/groups/{group}/hosts/{host}/metrics")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getMetricList(@DefaultValue("zabbix") @PathParam("adapterType") String adapterType,
			@PathParam("zone") String zone, @PathParam("type") String type, @PathParam("group") String group,
			@PathParam("host") String host) throws MonitoringException;

	/*********************************
	 * SPECIFIC METRIC REQUESTED
	 ********************************/
	/**
	 * Method useful for having a specific metric info (identified by its name)
	 * belonging to a specific host, category group, atomic service which all
	 * are to be specified in the request.
	 */
	@Path("/{adapterType}/zones/{zone}/monitoring-types/{type}/groups/{group}/hosts/{host}/metrics/{metric}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getMetric(@DefaultValue("zabbix") @PathParam("adapterType") String adapterType,
			@PathParam("zone") String zone, @PathParam("type") String type, @PathParam("group") String group,
			@PathParam("host") String host, @PathParam("metric") String metric) throws MonitoringException;

	/*****************************
	 * History without filter Time
	 ****************************/
	@Path("/{adapterType}/zones/{zone}/monitoring-types/{type}/groups/{group}/hosts/{host}/metrics/{metric}/history")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getHistory(@DefaultValue("zabbix") @PathParam("adapterType") String adapterType,
			@PathParam("zone") String zone, @PathParam("type") String type, @PathParam("group") String group,
			@PathParam("host") String host, @PathParam("metric") String metric) throws MonitoringException;

	/*********************************
	 * HISTORY BY HOSTNAME AND FILTER TIME
	 ********************************/
	/**
	 * Method useful for having a specific metric info (identified by its name)
	 * belonging to a specific host, category group, atomic service which all
	 * are to be specified in the request.
	 */
	@Path("/{adapterType}/zones/{zone}/monitoring-types/{type}/groups/{group}/hosts/{host}/metrics/{metric}/history")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getHistoryByTime(@DefaultValue("zabbix") @PathParam("adapterType") String adapterType,
			@PathParam("zone") String zone, @PathParam("type") String type, @PathParam("group") String group,
			@PathParam("host") String host, @PathParam("metric") String metric, FilterTimeRequest requestTime)
			throws MonitoringException;

	/**************************************
	 * HISTORY BY SERVICEID AND FILTER TIME
	 **************************************/
	/**
	 * Method useful for having a specific metric info (identified by its name)
	 * belonging to a specific host, category group, atomic service which all
	 * are to be specified in the request.
	 */
	@Path("/{adapterType}/zones/{zone}/monitoring-types/{type}/groups/{group}/services/{service}/metrics/{metric}/history")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getHistoryByServiceandTime(@DefaultValue("zabbix") @PathParam("adapterType") String adapterType,
			@PathParam("zone") String zone, @PathParam("type") String type, @PathParam("group") String group,
			@PathParam("service") Long service, @PathParam("metric") String metric, FilterTimeRequest requestTime)
			throws MonitoringException;

	/*****************
	 * DISABLE METRIC
	 *****************/
	@Path("/{adapterType}/zones/{zone}/monitoring-types/{type}/groups/{group}/hosts/{host}/metrics/{metric}")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getDisableMetric(@DefaultValue("zabbix") @PathParam("adapterType") String adapterType,
			@PathParam("zone") String zone, @PathParam("type") String type, @PathParam("group") String group,
			@PathParam("host") String host, @PathParam("metric") String metric, @QueryParam("update") String update)
			throws MonitoringException;
}