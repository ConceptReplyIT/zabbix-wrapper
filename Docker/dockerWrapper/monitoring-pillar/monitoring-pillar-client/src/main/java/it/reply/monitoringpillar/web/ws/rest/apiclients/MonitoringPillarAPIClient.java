package it.reply.monitoringpillar.web.ws.rest.apiclients;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.resteasy.specimpl.MultivaluedMapImpl;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import it.reply.domain.dsl.exceptions.accounting.UnauthorizedException;
import it.reply.domain.dsl.prisma.restprotocol.Error;
import it.reply.domain.dsl.prisma.restprotocol.PrismaResponseWrapper;
import it.reply.monitoringpillar.domain.dsl.monitoring.InfoType;
import it.reply.monitoringpillar.domain.dsl.monitoring.MonitoringConstant;
import it.reply.monitoringpillar.domain.dsl.monitoring.businesslayer.paas.request.HostGroupMonitoringCreateRequest;
import it.reply.monitoringpillar.domain.dsl.monitoring.businesslayer.paas.request.HostMonitoringDeleteRequest;
import it.reply.monitoringpillar.domain.dsl.monitoring.businesslayer.paas.request.HostMonitoringRequest;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.ProxyCreationRequest;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.iaas.MonitoringWrappedResponseIaas;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.iaas.WrappedIaasHealthByTrigger;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas.CreatedHostInPaaS;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas.FilterTimeRequest;
import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas.MonitoringWrappedResponsePaas;
import it.reply.monitoringpillar.domain.exception.NotFoundMonitoringException;
import it.reply.utils.web.ws.rest.apiclients.AbstractAPIClient;
import it.reply.utils.web.ws.rest.apiclients.prisma.APIErrorException;
import it.reply.utils.web.ws.rest.apiclients.prisma.PrismaMetaData;
import it.reply.utils.web.ws.rest.apiclients.prisma.PrismaRestResponseDecoder;
import it.reply.utils.web.ws.rest.apiencoding.MappingException;
import it.reply.utils.web.ws.rest.apiencoding.NoMappingModelFoundException;
import it.reply.utils.web.ws.rest.apiencoding.ServerErrorResponseException;
import it.reply.utils.web.ws.rest.apiencoding.decode.BaseRestResponseResult;
import it.reply.utils.web.ws.rest.restclient.RestClient;
import it.reply.utils.web.ws.rest.restclient.RestClient.RestMethod;
import it.reply.utils.web.ws.rest.restclient.RestClientFactory;
import it.reply.utils.web.ws.rest.restclient.RestClientFactoryImpl;
import it.reply.utils.web.ws.rest.restclient.RestClientHelper;
import it.reply.utils.web.ws.rest.restclient.exceptions.RestClientException;

/**
 * Monitoring Pillar Client. Use PrismaMonitoringAPIClient methods to invoke
 * pillar WS
 * 
 */
public class MonitoringPillarAPIClient extends AbstractAPIClient {

	private Logger LOG = LogManager.getLogger(MonitoringPillarAPIClient.class);

	/**
	 * Creates a {@link MonitoringPillarAPIClient} using the default
	 * {@link RestClientFactoryImpl}.
	 * 
	 * @param baseWSUrl
	 *            The URL of Prisma BizWS.
	 */
	public MonitoringPillarAPIClient(String baseURL) {
		this(baseURL, new RestClientFactoryImpl());
	}

	/**
	 * Creates a {@link MonitoringPillarAPIClient} with the given
	 * {@link RestClientFactory}.
	 * 
	 * @param baseWSUrl
	 *            The URL of Prisma BizWS.
	 * @param restClientFactory
	 *            The custom factory for the {@link RestClient}.
	 */
	public MonitoringPillarAPIClient(String baseWSUrl, RestClientFactory restClientFactory) {
		super(baseWSUrl, restClientFactory);
	}

	protected String addAdapterType(String url, String adapterType) {
		return url + adapterType;
	}

	protected String addZone(String url, String zone) {
		return url + "/zones/" + zone;
	}

	/**
	 * Gets the trigger(only triggered) for the given hostgroup
	 * 
	 * @param adapterType
	 * @param hostGroup
	 * @return
	 * @throws RestClientException
	 * @throws NoMappingModelFoundException
	 * @throws MappingException
	 * @throws ServerErrorResponseException
	 */
	public WrappedIaasHealthByTrigger getShotTriggerByHostGroup(String adapterType, String zone, String hostGroup)
			throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {

		// TODO: putrt into DB or Enum the type of serverType
		String URL = addZone(addAdapterType(baseWSUrl, adapterType), zone) + "/types/"
				+ InfoType.INFRASTRUCTURE.getInfoType() + "/groups/" + hostGroup + "/hosts?thresholds=true";

		LOG.trace("Called: getShotTriggerByHostGroup({}, {})", adapterType, hostGroup);

		LOG.trace(URL);
		MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers, null, null, null, null,
				new PrismaRestResponseDecoder<WrappedIaasHealthByTrigger>(WrappedIaasHealthByTrigger.class), null);
		return handleResult(result);
	}

	/**
	 * Create the host to Zabbix metrics and watcher.
	 * 
	 * HostMonitoringRequest -> atomicServices should be null in case of VMaaS
	 * 
	 * @param adapterType
	 * @param request
	 * @returns
	 * @throws MappingException
	 * @throws APIErrorException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws RestClientException
	 * @throws NoMappingModelFoundException
	 * @throws ServerErrorResponseException
	 */
	// NOW just formatted for zabbix Paas (service e watcher)
	// TODO: parametrizzare il tipo di server
	public void addHostToMonitoring(String adapterType, String zone, String group, String host, String uuid,
			HostMonitoringRequest request)
					throws MappingException, APIErrorException, JsonParseException, JsonMappingException, IOException,
					RestClientException, NoMappingModelFoundException, ServerErrorResponseException {
		LOG.trace("Called: addHostToMonitoring({}, {})", adapterType, request);
		String URL = addZone(addAdapterType(baseWSUrl, adapterType), zone) + "/types/" + InfoType.PAAS.getInfoType()
				+ "/groups/" + request.getHostGroup() + "/hosts/" + host;
		LOG.trace(URL);
		MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();

		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);

		BaseRestResponseResult result = restClient.postRequest(URL, headers, ge,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE, new PrismaRestResponseDecoder<String>(String.class),
				null);

		handleResult(result);
	}

	/**
	 * Delete service from zabbix metrics and watcher
	 * 
	 * @param adapterType
	 * @param request
	 * @return
	 * @throws MappingException
	 * @throws APIErrorException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws RestClientException
	 * @throws NoMappingModelFoundException
	 * @throws ServerErrorResponseException
	 */
	// NOW just formatted for zabbix Paas (service e watcher)
	// TODO: parametrizzare il tipo di server
	public void deleteHostFromMonitoring(String adapterType, String zone, String group, String host,
			HostMonitoringDeleteRequest request) throws MappingException, APIErrorException, JsonParseException,
					JsonMappingException, IOException, RestClientException, NoMappingModelFoundException,
					ServerErrorResponseException, NotFoundMonitoringException {

		String URL = addZone(addAdapterType(baseWSUrl, adapterType), zone) + "/types/" + InfoType.PAAS.getInfoType()
				+ "/groups/" + request.getGroup() + "/hosts/" + request.getUuid();

		LOG.trace("Called: deleteHostFromMonitoring({}, {})", adapterType, request);

		LOG.trace(URL);

		MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();

		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);

		BaseRestResponseResult result = restClient.deleteRequest(URL, headers, ge,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE,
				new PrismaRestResponseDecoder<CreatedHostInPaaS>(CreatedHostInPaaS.class), null);
		handleResult(result);
	}

	/**
	 * Group Overall INFO
	 * 
	 * @return
	 * @throws MappingException
	 * @throws NoMappingModelFoundException
	 * @throws ServerErrorResponseException
	 * @throws APIErrorException
	 * @throws RestClientException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws TimeoutException
	 */
	public MonitoringWrappedResponseIaas getMachineByHostGroup(String adapterType, String zone, String hostGroup)
			throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {

		// TODO: infrastructure is the name of the server type we are making
		// requests, to be implemented other yet
		String URL = addZone(addAdapterType(baseWSUrl, adapterType), zone) + "/types/"
				+ InfoType.INFRASTRUCTURE.getInfoType() + "/groups/" + hostGroup;

		LOG.trace("Called: getMachineByHostGroup({}, {})", adapterType, hostGroup);

		// TODO: infrastructure is the name of the server type we are making
		// requests
		LOG.trace(URL);

		MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers, null, null, null, null,
				new PrismaRestResponseDecoder<MonitoringWrappedResponseIaas>(MonitoringWrappedResponseIaas.class),
				null);

		return handleResult(result);
	}

	// NOW just formatted for zabbix Iaas (infrastructure)
	public MonitoringWrappedResponseIaas getItemsByHostGroupAndHost(String adapterType, String zone, String hostGroup,
			String hostName) throws RestClientException, NoMappingModelFoundException, MappingException,
					ServerErrorResponseException {

		LOG.trace("Called: getItemsByHostGroupAndHost({}, {}, {})", adapterType, hostGroup, hostName);

		String URL = addZone(addAdapterType(baseWSUrl, adapterType), zone) + "/types/"
				+ InfoType.INFRASTRUCTURE.getInfoType() + "/groups/" + hostGroup + "/hosts/" + hostName;

		MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers, null, null, null, null,
				new PrismaRestResponseDecoder<MonitoringWrappedResponseIaas>(MonitoringWrappedResponseIaas.class),
				null);

		return handleResult(result);
	}

	/**
	 * Returns all the metrics and thresholds associated to all the monitored
	 * hosts with the given ID.
	 * 
	 * @param adapterType
	 *            for example "zabbix". This value must be know to
	 *            MonitoringPillar
	 * @param serviceGroup
	 *            the group in which the service has been added (usually is the
	 *            workgroupID)
	 * @param serviceId
	 *            the paasServiceID
	 * @return
	 * @throws RestClientException
	 * @throws NoMappingModelFoundException
	 * @throws MappingException
	 * @throws ServerErrorResponseException
	 */
	public MonitoringWrappedResponsePaas getMonitoringServiceByTag(String adapterType, String zone, String group,
			String serviceId) throws RestClientException, NoMappingModelFoundException, MappingException,
					ServerErrorResponseException {

		LOG.trace("Called: getMonitoringServiceByTag({}, {})", adapterType, serviceId);
		// TODO: parametrizzare serverType
		String URL = addZone(addAdapterType(baseWSUrl, adapterType), zone).toLowerCase() + "/types/"
				+ InfoType.SERVICE.getInfoType() + "/groups/" + group + "/hosts?service-id=" + serviceId;
		LOG.trace(URL);

		MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<MonitoringWrappedResponsePaas>(MonitoringWrappedResponsePaas.class),
				null);

		return handleResult(result);
	}

	/**
	 * Returns all the metrics and thresholds associated to the hosts with the
	 * given ID.
	 * 
	 * @param adapterType
	 *            for example "zabbix". This value must be know to
	 *            MonitoringPillar
	 * @param serviceGroup
	 *            the group in which the service has been added (usually is the
	 *            workgroupID)
	 * @param hostId
	 *            the ID of the host
	 * @return
	 * @throws RestClientException
	 * @throws NoMappingModelFoundException
	 * @throws MappingException
	 * @throws ServerErrorResponseException
	 */
	public MonitoringWrappedResponsePaas getMonitoringHostById(String adapterType, String zone, String group,
			String hostId) throws RestClientException, NoMappingModelFoundException, MappingException,
					ServerErrorResponseException {

		String URL = addZone(addAdapterType(baseWSUrl, adapterType), zone).toLowerCase() + "/types/"
				+ InfoType.SERVICE.getInfoType() + "/groups/" + group + "/hosts/" + hostId;

		MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<MonitoringWrappedResponsePaas>(MonitoringWrappedResponsePaas.class),
				null);

		return handleResult(result);
	}

	/**
	 * Get Iaas status from external script
	 * 
	 * @param adapterType
	 * @param zone
	 * @param iaasType
	 * @param iaashost
	 * @return MonitoringWrappedResponseIaas
	 * @throws MappingException
	 * @throws NoMappingModelFoundException
	 * @throws ServerErrorResponseException
	 * @throws APIErrorException
	 * @throws RestClientException
	 * @throws Exception
	 */
	public MonitoringWrappedResponseIaas getPrismaIaasHealth(String adapterType, String zone, String iaasType,
			String iaashost) throws MappingException, NoMappingModelFoundException, ServerErrorResponseException,
					APIErrorException, RestClientException, Exception {
		String URL = addZone(addAdapterType(baseWSUrl, adapterType), zone).toLowerCase() + "/types/"
				+ InfoType.INFRASTRUCTURE.getInfoType() + "/groups/" + iaasType + "/hosts/" + iaashost;

		MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<MonitoringWrappedResponseIaas>(MonitoringWrappedResponseIaas.class),
				null);

		return handleResult(result);
	}

	/**
	 * Create Workgroup in monitoring platform
	 * 
	 * @param adapterType
	 * @param zone
	 * @param groupName
	 * @throws MappingException
	 * @throws APIErrorException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws RestClientException
	 * @throws NoMappingModelFoundException
	 * @throws ServerErrorResponseException
	 */
	public void createGroup(String adapterType, String zone, String groupName)
			throws MappingException, APIErrorException, JsonParseException, JsonMappingException, IOException,
			RestClientException, NoMappingModelFoundException, ServerErrorResponseException {

		LOG.trace("Called: createGroup({}, {})", adapterType, groupName);
		String URL = addZone(addAdapterType(baseWSUrl, adapterType), zone) + "/types/" + InfoType.ALL.getInfoType()
				+ "/groups/" + groupName;
		LOG.trace(URL);

		MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();
		HostGroupMonitoringCreateRequest request = new HostGroupMonitoringCreateRequest();
		request.setHostGroupName(groupName);
		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);

		BaseRestResponseResult result = restClient.postRequest(URL, headers, ge,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE, new PrismaRestResponseDecoder<String>(String.class),
				null);
		handleResult(result);
	}

	/**
	 * Create Proxy into Monitoring platform
	 * 
	 * @param adapterType
	 * @param zone
	 * @param proxyName
	 * @throws MappingException
	 * @throws APIErrorException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws RestClientException
	 * @throws NoMappingModelFoundException
	 * @throws ServerErrorResponseException
	 */
	public void createProxy(String adapterType, String zone, Long wgId)
			throws MappingException, APIErrorException, JsonParseException, JsonMappingException, IOException,
			RestClientException, NoMappingModelFoundException, ServerErrorResponseException {

		LOG.trace("Called: createProxy({}, {})", adapterType, MonitoringConstant.WG_PREFIX + wgId);
		String URL = addZone(addAdapterType(baseWSUrl, adapterType), zone) + "/types/" + InfoType.ALL.getInfoType()
				+ "/groups/" + MonitoringConstant.WG_PREFIX + wgId + "/proxies";
		LOG.trace(URL);

		MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();

		ProxyCreationRequest request = new ProxyCreationRequest();
		request.setProxyName(MonitoringConstant.PROXY_PREFIX + wgId);
		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);

		BaseRestResponseResult result = restClient.postRequest(URL, headers, ge,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE, new PrismaRestResponseDecoder<String>(String.class),
				null);
		handleResult(result);
	}

	/**
	 * Delete Workgroup from Monitoring Platform
	 * 
	 * @param adapterType
	 * @param zone
	 * @param groupName
	 * @throws MappingException
	 * @throws APIErrorException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 * @throws RestClientException
	 * @throws NoMappingModelFoundException
	 * @throws ServerErrorResponseException
	 */
	public void deleteGroup(String adapterType, String zone, String groupName)
			throws MappingException, APIErrorException, JsonParseException, JsonMappingException, IOException,
			RestClientException, NoMappingModelFoundException, ServerErrorResponseException {

		LOG.trace("Called: deleteGroup({}, {})", adapterType, groupName);
		String URL = addZone(addAdapterType(baseWSUrl, adapterType), zone) + "/types/" + InfoType.ALL.getInfoType()
				+ "/groups/" + groupName;
		LOG.trace(URL);

		MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();

		HostGroupMonitoringCreateRequest request = new HostGroupMonitoringCreateRequest();
		request.setHostGroupName(groupName);

		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);

		BaseRestResponseResult result = restClient.deleteRequest(URL, headers, ge,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE, new PrismaRestResponseDecoder<String>(String.class),
				null);
		handleResult(result);
	}

	/*********************************
	 * GET HISTORY BY SERVICE ID
	 * 
	 * @param adapterType
	 * @param groupName
	 * @param hostName
	 * @param filterTime
	 * @return the history filtered via request time
	 * @throws RestClientException
	 * @throws NoMappingModelFoundException
	 * @throws MappingException
	 * @throws ServerErrorResponseException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public MonitoringWrappedResponsePaas getHistoryByServiceIdFilteredClient(String adapterType, String zone,
			String groupName, Long serviceId, String metricName, FilterTimeRequest filterTimeRequest)
					throws RestClientException, NoMappingModelFoundException, MappingException,
					ServerErrorResponseException, JsonParseException, JsonMappingException, IOException {

		LOG.trace("Called: getHistory({}, {})", adapterType, groupName, serviceId);
		String URL = addZone(addAdapterType(baseWSUrl, adapterType), zone) + "/types/" + InfoType.SERVICE.getInfoType()
				+ "/groups/" + "Workgroup-" + groupName + "/services/" + serviceId + "/metrics/" + metricName
				+ "/history/";
		LOG.trace(URL);

		MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();

		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(filterTimeRequest);

		BaseRestResponseResult result = restClient.postRequest(URL, headers, ge,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE,
				new PrismaRestResponseDecoder<MonitoringWrappedResponsePaas>(MonitoringWrappedResponsePaas.class),
				null);

		return handleResult(result);
	}

	/****************************
	 * GET HISTORY BY HOSTNAME
	 * 
	 * @param adapterType
	 * @param groupName
	 * @param hostName
	 * @param metricName
	 * @param filterTimeRequest
	 * @return
	 * @throws RestClientException
	 * @throws NoMappingModelFoundException
	 * @throws MappingException
	 * @throws ServerErrorResponseException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public MonitoringWrappedResponsePaas getHistoryByHostNameFilteredClient(String adapterType, String zone,
			String groupName, String hostName, String metricName, FilterTimeRequest filterTimeRequest)
					throws RestClientException, NoMappingModelFoundException, MappingException,
					ServerErrorResponseException, JsonParseException, JsonMappingException, IOException {

		LOG.trace("Called: getHistory({}, {})", adapterType, groupName, hostName);
		String URL = addZone(addAdapterType(baseWSUrl, adapterType), zone) + "/types/" + InfoType.SERVICE.getInfoType()
				+ "/groups/" + checkGroupName(groupName) + "/hosts/" + hostName + "/metrics/" + metricName
				+ "/history/";
		LOG.trace(URL);

		MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();

		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(filterTimeRequest);

		BaseRestResponseResult result = restClient.postRequest(URL, headers, ge,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE,
				new PrismaRestResponseDecoder<MonitoringWrappedResponsePaas>(MonitoringWrappedResponsePaas.class),
				null);

		return handleResult(result);
	}

	private String checkGroupName(String groupName) {

		if (groupName.toLowerCase().contains(MonitoringConstant.WG_PREFIX.toLowerCase())) {
			return groupName;
		} else
			return MonitoringConstant.WG_PREFIX + groupName;
	}

	/**
	 * Generic comment
	 * 
	 * @param result
	 * @param meta
	 * @return
	 * @throws APIErrorException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected <RETURN_TYPE> RETURN_TYPE handleResult(BaseRestResponseResult result, PrismaMetaData meta)
			throws APIErrorException {
		if (result.getStatus().getFamily() == Status.Family.SUCCESSFUL) {
			try {
				if (meta != null) {
					meta.setMeta(((PrismaResponseWrapper<?>) result.getResult()).getMeta());
					meta.setBaseRestResponseResult(result);
					meta.setPrismaResponseWrapper(((PrismaResponseWrapper<?>) result.getResult()));
				}
				if (result.getResult() != null)
					return ((PrismaResponseWrapper<RETURN_TYPE>) result.getResult()).getResult();
				else
					return null;
			} catch (Exception e) {
				throw new APIErrorException("Unexpected response type.", e);
			}
		} else {
			Error error = ((PrismaResponseWrapper) result.getResult()).getError();
			// TODO: Is this right ?! The Monitoring Pillar currently doesn't
			// have auth !!
			if (result.getStatus().getStatusCode() == Status.UNAUTHORIZED.getStatusCode()) {
				throw new UnauthorizedException(error.getErrorMsg());
			} else
				throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
						((PrismaResponseWrapper) result.getResult()).getError());
		}

	}

	protected <REPRESENTATION_CLASS> REPRESENTATION_CLASS handleResult(BaseRestResponseResult result)
			throws APIErrorException {
		return handleResult(result, null);
	}
}