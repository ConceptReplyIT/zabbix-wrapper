package it.prisma.utils.web.ws.rest.apiclients.zabbix;

import it.prisma.domain.dsl.monitoring.businesslayer.paas.response.MonitoringVMCredentialsResponse;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.HostGroupParamRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.JSONRPCRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.JsonRpcDeleteHostRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.TriggerParamRequestByGroup;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.Zabbix2_4ParamHost;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.ZabbixAuthenticationRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.ZabbixParamCreateHostRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.ZabbixParamHistoryRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.ZabbixParamHostGroupRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.ZabbixParamItemRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.ZabbixParamRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.ZabbixParamTemplateRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.ZabbixParamTrendRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.ZabbixParamTriggerRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.ZabbixParamUpdate;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.ZabbixParamUpdateInventoryTag;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.ZabbixProxyInfoRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.ZabbixUpdateProxyRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.response.HostGroupResponse;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.response.JSONRPCError;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.response.JSONRPCResponse;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.response.UpdatedItemsResponse;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.response.ZabbixHostGroupResponse;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.response.ZabbixItemResponse;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.response.ZabbixMonitoredHostResponseV2_4;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.response.ZabbixMonitoredHostsResponse;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.response.ZabbixProxyInfoResponse;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.response.ZabbixTemplateResponse;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.response.ZabbixTemplateResponseV2_4;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.response.ZabbixUpdateProxyResponse;
import it.prisma.utils.web.ws.rest.apiclients.AbstractAPIClient;
import it.prisma.utils.web.ws.rest.apiclients.prisma.APIErrorException;
import it.prisma.utils.web.ws.rest.apiclients.zabbix.exception.ClientResponseException;
import it.prisma.utils.web.ws.rest.apiclients.zabbix.exception.ZabbixClientException;
import it.prisma.utils.web.ws.rest.apiencoding.MappingException;
import it.prisma.utils.web.ws.rest.apiencoding.NoMappingModelFoundException;
import it.prisma.utils.web.ws.rest.apiencoding.ServerErrorResponseException;
import it.prisma.utils.web.ws.rest.apiencoding.decode.BaseRestResponseResult;
import it.prisma.utils.web.ws.rest.restclient.RestClientFactory;
import it.prisma.utils.web.ws.rest.restclient.RestClientFactoryImpl;
import it.prisma.utils.web.ws.rest.restclient.RestClientHelper;
import it.prisma.utils.web.ws.rest.restclient.exceptions.RestClientException;

import java.io.IOException;
import java.util.ArrayList;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status;

import org.jboss.resteasy.specimpl.MultivaluedMapImpl;
//import it.prisma.domain.dsl.zabbix.response.JSONRPCResponse4ItemTrigger;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * This class contains Zabbix API Client requests implementation.
 * 
 */
public class ZabbixAPIClient extends AbstractAPIClient {

	// private Logger LOG = LogManager.getLogger(ZabbixAPIClient.class);

	private BaseRestResponseResult result;

	public ZabbixAPIClient(String baseWSUrl) {
		this(baseWSUrl, new RestClientFactoryImpl());
	}

	public ZabbixAPIClient(String baseWSUrl, RestClientFactory restClientFactory) {
		super(baseWSUrl, restClientFactory);
	}

	public String authentication(JSONRPCRequest<ZabbixAuthenticationRequest> request) throws ZabbixClientException {

		try {

			MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();

			GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);

			result = restClient.postRequest(baseWSUrl, headers, ge, RestClientHelper.JsonEntityBuilder.MEDIA_TYPE,
					new ZabbixResponseDecoder<String>(String.class), null);
			return handleResult(result);

		} catch (IOException | RestClientException | NoMappingModelFoundException | MappingException
				| ServerErrorResponseException e) {
			throw new ZabbixClientException(e);
		}
	}

	/*
	 * Client for creating the host from Adapter
	 */

	public MonitoringVMCredentialsResponse createHostClient(JSONRPCRequest<ZabbixParamCreateHostRequest> request)
			throws ZabbixClientException {

		try {
			MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();

			GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);

			result = restClient.postRequest(baseWSUrl, headers, ge, RestClientHelper.JsonEntityBuilder.MEDIA_TYPE,
					new ZabbixResponseDecoder<MonitoringVMCredentialsResponse>(MonitoringVMCredentialsResponse.class),
					null);
			return handleResult(result);
		} catch (IOException | RestClientException | NoMappingModelFoundException | MappingException
				| ServerErrorResponseException e) {
			throw new ZabbixClientException(e);
		}

	}

	/*****************************
	 * GET PROXY INFO
	 * 
	 * @param request
	 * @return
	 * @throws ZabbixClientException
	 ********************************/
	public ArrayList<ZabbixProxyInfoResponse> getProxyInfoClient(JSONRPCRequest<ZabbixProxyInfoRequest> request)
			throws ZabbixClientException {

		try {
			MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();
			GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);
			result = restClient.postRequest(baseWSUrl, headers, ge, RestClientHelper.JsonEntityBuilder.MEDIA_TYPE,
					new ZabbixResponseDecoder<ArrayList<ZabbixProxyInfoResponse>>(ArrayList.class,
							ZabbixProxyInfoResponse.class), null);
			return handleResult(result);
		} catch (IOException | RestClientException | NoMappingModelFoundException | MappingException
				| ServerErrorResponseException e) {
			throw new ZabbixClientException(e);
		}
	}

	/****************
	 * UPDATE PROXY
	 ***************/
	public ZabbixUpdateProxyResponse updateProxyClient(JSONRPCRequest<ZabbixUpdateProxyRequest> request)
			throws ZabbixClientException {

		try {
			MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();
			GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);
			result = restClient.postRequest(baseWSUrl, headers, ge, RestClientHelper.JsonEntityBuilder.MEDIA_TYPE,
					new ZabbixResponseDecoder<ZabbixUpdateProxyResponse>(ZabbixUpdateProxyResponse.class), null);
			return handleResult(result);
		} catch (IOException | RestClientException | NoMappingModelFoundException | MappingException
				| ServerErrorResponseException e) {
			throw new ZabbixClientException(e);
		}
	}

	/*****************
	 * DELETE HOSTS
	 *****************/
	public MonitoringVMCredentialsResponse deleteHostClient(JsonRpcDeleteHostRequest request)
			throws ZabbixClientException {

		try {

			MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();

			GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);

			result = restClient.postRequest(baseWSUrl, headers, ge, RestClientHelper.JsonEntityBuilder.MEDIA_TYPE,
					new ZabbixResponseDecoder<MonitoringVMCredentialsResponse>(MonitoringVMCredentialsResponse.class),
					null);
			return handleResult(result);

		} catch (IOException | RestClientException | NoMappingModelFoundException | MappingException
				| ServerErrorResponseException e) {
			throw new ZabbixClientException(e);
		}
	}

	/*****************
	 * GET GROUPS
	 *****************/
	/**
	 * Get Groups
	 * 
	 * @param request
	 * @return the list of groups in zabbix
	 * @throws MappingException
	 * @throws NoMappingModelFoundException
	 * @throws ServerErrorResponseException
	 * @throws APIErrorException
	 * @throws RestClientException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public ArrayList<ZabbixHostGroupResponse> getHostGroupClient(JSONRPCRequest<ZabbixParamHostGroupRequest> request)
			throws ZabbixClientException {

		try {

			MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();

			GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);

			result = restClient.postRequest(baseWSUrl, headers, ge, RestClientHelper.JsonEntityBuilder.MEDIA_TYPE,
					new ZabbixResponseDecoder<ArrayList<ZabbixHostGroupResponse>>(ArrayList.class,
							ZabbixHostGroupResponse.class), null);
			return handleResult(result);

		} catch (IOException | RestClientException | NoMappingModelFoundException | MappingException
				| ServerErrorResponseException e) {
			throw new ZabbixClientException(e);
		}
	}

	/*****************
	 * GET TEMPLATES
	 *****************/
	/**
	 * Get Templates
	 * 
	 * @param request
	 * @return the list of templates
	 * @throws MappingException
	 * @throws NoMappingModelFoundException
	 * @throws ServerErrorResponseException
	 * @throws APIErrorException
	 * @throws RestClientException
	 * @throws Exception
	 */
	public ArrayList<ZabbixTemplateResponse> getTemplate(JSONRPCRequest<ZabbixParamHostGroupRequest> request)
			throws ZabbixClientException {

		try {

			MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();

			GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);

			result = restClient.postRequest(baseWSUrl, headers, ge, RestClientHelper.JsonEntityBuilder.MEDIA_TYPE,
					new ZabbixResponseDecoder<ArrayList<ZabbixTemplateResponse>>(ArrayList.class,
							ZabbixTemplateResponse.class), null);
			return handleResult(result);

		} catch (IOException | RestClientException | NoMappingModelFoundException | MappingException
				| ServerErrorResponseException e) {
			throw new ZabbixClientException(e);
		}
	}

	/***************
	 * GET HOSTS
	 ***************/
	public ArrayList<ZabbixMonitoredHostsResponse> getHostsClient(JSONRPCRequest<ZabbixParamRequest> request)
			throws ZabbixClientException {

		try {

			MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();

			GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);

			result = restClient.postRequest(baseWSUrl, headers, ge, RestClientHelper.JsonEntityBuilder.MEDIA_TYPE,
					new ZabbixResponseDecoder<ArrayList<ZabbixMonitoredHostsResponse>>(ArrayList.class,
							ZabbixMonitoredHostsResponse.class), null);

			return handleResult(result);

		} catch (IOException | RestClientException | NoMappingModelFoundException | MappingException
				| ServerErrorResponseException e) {
			throw new ZabbixClientException(e);
		}
	}

	/********************
	 * GET HOSTS 2_4
	 *******************/
	public ArrayList<ZabbixMonitoredHostsResponse> getHostsExtended(JSONRPCRequest<Zabbix2_4ParamHost> request)
			throws ZabbixClientException {

		try {

			MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();

			GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);

			result = restClient.postRequest(baseWSUrl, headers, ge, RestClientHelper.JsonEntityBuilder.MEDIA_TYPE,
					new ZabbixResponseDecoder<ArrayList<ZabbixMonitoredHostsResponse>>(ArrayList.class,
							ZabbixMonitoredHostsResponse.class), null);
			return handleResult(result);

		} catch (IOException | RestClientException | NoMappingModelFoundException | MappingException
				| ServerErrorResponseException e) {
			throw new ZabbixClientException(e);
		}
	}

	/**************************************************************************
	 * GET HOSTS FILTERED BY NEW ZABBIX 2.4 (extended with items and templates)
	 **************************************************************************/
	public ArrayList<ZabbixMonitoredHostResponseV2_4> getHostsExtended2_4(JSONRPCRequest<Zabbix2_4ParamHost> request)
			throws ZabbixClientException {

		try {

			MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();

			GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);

			result = restClient.postRequest(baseWSUrl, headers, ge, RestClientHelper.JsonEntityBuilder.MEDIA_TYPE,
					new ZabbixResponseDecoder<ArrayList<ZabbixMonitoredHostResponseV2_4>>(ArrayList.class,
							ZabbixMonitoredHostResponseV2_4.class), null);
			return handleResult(result);

		} catch (IOException | RestClientException | NoMappingModelFoundException | MappingException
				| ServerErrorResponseException e) {
			throw new ZabbixClientException(e);
		}
	}

	/************
	 * GET ITEMs
	 ***********/
	public ArrayList<ZabbixItemResponse> getItemsClient(JSONRPCRequest<ZabbixParamItemRequest> request)
			throws ZabbixClientException {

		try {

			MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();

			GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);

			result = restClient
					.postRequest(baseWSUrl, headers, ge, RestClientHelper.JsonEntityBuilder.MEDIA_TYPE,
							new ZabbixResponseDecoder<ArrayList<ZabbixItemResponse>>(ArrayList.class,
									ZabbixItemResponse.class), null);
			return handleResult(result);

		} catch (IOException | RestClientException | NoMappingModelFoundException | MappingException
				| ServerErrorResponseException e) {
			throw new ZabbixClientException(e);
		}
	}

	/************
	 * GET ITEMs
	 ************/
	public ArrayList<ZabbixItemResponse> getFilterdItemNOZabbixWrappingResponse(
			JSONRPCRequest<ZabbixParamTemplateRequest> request) throws ZabbixClientException, RestClientException,
			ServerErrorResponseException {

		try {

			MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();

			GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);

			result = restClient
					.postRequest(baseWSUrl, headers, ge, RestClientHelper.JsonEntityBuilder.MEDIA_TYPE,
							new ZabbixResponseDecoder<ArrayList<ZabbixItemResponse>>(ArrayList.class,
									ZabbixItemResponse.class), null);
			return handleResult(result);

		} catch (IOException | RestClientException | NoMappingModelFoundException | MappingException
				| ServerErrorResponseException e) {
			throw new ZabbixClientException(e);
		}
	}

	/******************************************
	 * GET ITEMS USEFUL FOR HISTORY AND EVENTs
	 *****************************************/
	public ArrayList<ZabbixItemResponse> getHistoryClient(JSONRPCRequest<ZabbixParamHistoryRequest> request)
			throws ZabbixClientException {

		try {
			MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();

			GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);

			result = restClient
					.postRequest(baseWSUrl, headers, ge, RestClientHelper.JsonEntityBuilder.MEDIA_TYPE,
							new ZabbixResponseDecoder<ArrayList<ZabbixItemResponse>>(ArrayList.class,
									ZabbixItemResponse.class), null);
			return handleResult(result);

		} catch (IOException | RestClientException | NoMappingModelFoundException | MappingException
				| ServerErrorResponseException e) {
			throw new ZabbixClientException(e);
		}
	}

	/***********
	 * GET TREND
	 ***********/
	public ArrayList<ZabbixItemResponse> getTrendClient(JSONRPCRequest<ZabbixParamTrendRequest> request)
			throws ZabbixClientException {

		try {
			MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();

			GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);

			result = restClient
					.postRequest(baseWSUrl, headers, ge, RestClientHelper.JsonEntityBuilder.MEDIA_TYPE,
							new ZabbixResponseDecoder<ArrayList<ZabbixItemResponse>>(ArrayList.class,
									ZabbixItemResponse.class), null);
			return handleResult(result);

		} catch (IOException | RestClientException | NoMappingModelFoundException | MappingException
				| ServerErrorResponseException e) {
			throw new ZabbixClientException(e);
		}
	}

	/*************
	 * GET TRIGGER
	 *************/
	public ArrayList<ZabbixItemResponse> getTriggerClient(JSONRPCRequest<ZabbixParamTriggerRequest> request)
			throws ZabbixClientException {

		try {

			MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();

			GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);

			result = restClient
					.postRequest(baseWSUrl, headers, ge, RestClientHelper.JsonEntityBuilder.MEDIA_TYPE,
							new ZabbixResponseDecoder<ArrayList<ZabbixItemResponse>>(ArrayList.class,
									ZabbixItemResponse.class), null);
			return handleResult(result);

		} catch (IOException | RestClientException | NoMappingModelFoundException | MappingException
				| ServerErrorResponseException e) {
			throw new ZabbixClientException(e);
		}
	}

	/**************
	 * GET TRIGGER
	 **************/
	public ArrayList<ZabbixItemResponse> getTriggerByGroupClient(JSONRPCRequest<TriggerParamRequestByGroup> request)
			throws ZabbixClientException {

		try {

			MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();

			GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);

			result = restClient
					.postRequest(baseWSUrl, headers, ge, RestClientHelper.JsonEntityBuilder.MEDIA_TYPE,
							new ZabbixResponseDecoder<ArrayList<ZabbixItemResponse>>(ArrayList.class,
									ZabbixItemResponse.class), null);
			return handleResult(result);

		} catch (IOException | RestClientException | NoMappingModelFoundException | MappingException
				| ServerErrorResponseException e) {
			throw new ZabbixClientException(e);
		}
	}

	/*****************************
	 * GET HOSTGROUP CREATION
	 ****************************/
	public HostGroupResponse createHostGroupClient(JSONRPCRequest<HostGroupParamRequest> request)
			throws ZabbixClientException {

		try {

			MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();
			GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);
			result = restClient.postRequest(baseWSUrl, headers, ge, RestClientHelper.JsonEntityBuilder.MEDIA_TYPE,
					new ZabbixResponseDecoder<HostGroupResponse>(HostGroupResponse.class), null);
			return handleResult(result);

		} catch (IOException | RestClientException | NoMappingModelFoundException | MappingException
				| ServerErrorResponseException e) {
			throw new ZabbixClientException(e);
		}

	}

	/*******************
	 * UPDATE GROUP
	 * 
	 * @param request
	 * @return
	 * @throws ZabbixAPIErrorException
	 * @throws RestClientException
	 * @throws ServerErrorResponseException
	 */
	public HostGroupResponse updateGroupClient(JSONRPCRequest<HostGroupParamRequest> request)
			throws ZabbixClientException {

		try {

			MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();

			GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);

			result = restClient.postRequest(baseWSUrl, headers, ge, RestClientHelper.JsonEntityBuilder.MEDIA_TYPE,
					new ZabbixResponseDecoder<HostGroupResponse>(HostGroupResponse.class), null);
			return handleResult(result);

		} catch (IOException | RestClientException | NoMappingModelFoundException | MappingException
				| ServerErrorResponseException e) {
			throw new ZabbixClientException(e);
		}

	}

	/*******************
	 * DELETE GROUP
	 *******************/
	public HostGroupResponse deleteHostGroupClient(JsonRpcDeleteHostRequest request) throws ZabbixClientException {

		try {

			MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();
			GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);

			result = restClient.postRequest(baseWSUrl, headers, ge, RestClientHelper.JsonEntityBuilder.MEDIA_TYPE,
					new ZabbixResponseDecoder<HostGroupResponse>(HostGroupResponse.class), null);
			return handleResult(result);

		} catch (IOException | RestClientException | NoMappingModelFoundException | MappingException
				| ServerErrorResponseException e) {
			throw new ZabbixClientException(e);
		}
	}

	/***********************************
	 * GET UPDATE HOST (DISABLE/ENABLE)
	 *********************************/
	public MonitoringVMCredentialsResponse updateHostClient(JSONRPCRequest<ZabbixParamUpdate> request)
			throws ZabbixClientException {

		try {

			MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();

			GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);

			result = restClient.postRequest(baseWSUrl, headers, ge, RestClientHelper.JsonEntityBuilder.MEDIA_TYPE,
					new ZabbixResponseDecoder<MonitoringVMCredentialsResponse>(MonitoringVMCredentialsResponse.class),
					null);
			return handleResult(result);

		} catch (IOException | RestClientException | NoMappingModelFoundException | MappingException
				| ServerErrorResponseException e) {
			throw new ZabbixClientException(e);
		}

	}

	/***********************************
	 * GET UPDATE HOST FORCE TO HAVE TAG
	 *********************************/
	public MonitoringVMCredentialsResponse updateHostTagClient(JSONRPCRequest<ZabbixParamUpdateInventoryTag> request)
			throws ZabbixClientException {

		try {

			MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();

			GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);

			result = restClient.postRequest(baseWSUrl, headers, ge, RestClientHelper.JsonEntityBuilder.MEDIA_TYPE,
					new ZabbixResponseDecoder<MonitoringVMCredentialsResponse>(MonitoringVMCredentialsResponse.class),
					null);
			return handleResult(result);

		} catch (IOException | RestClientException | NoMappingModelFoundException | MappingException
				| ServerErrorResponseException e) {
			throw new ZabbixClientException(e);
		}

	}

	/********************************
	 * GET UPDATE ITEM (DISABLE/ENABLE)
	 *********************************/
	public UpdatedItemsResponse updateItemClient(JSONRPCRequest<ZabbixParamUpdate> request)
			throws ZabbixClientException {

		try {

			MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();

			GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);

			result = restClient.postRequest(baseWSUrl, headers, ge, RestClientHelper.JsonEntityBuilder.MEDIA_TYPE,
					new ZabbixResponseDecoder<UpdatedItemsResponse>(UpdatedItemsResponse.class), null);
			return handleResult(result);

		} catch (IOException | RestClientException | NoMappingModelFoundException | MappingException
				| ServerErrorResponseException e) {
			throw new ZabbixClientException(e);
		}
	}

	/****************************************************
	 * CLIENT FOR HANDLING TEMPLATES WITH ITEMS EXTENDED
	 * 
	 * @throws ServerErrorResponseException
	 * @throws MappingException
	 * @throws NoMappingModelFoundException
	 * @throws RestClientException
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 **************************************************/
	public ArrayList<ZabbixTemplateResponseV2_4> getTemplatesClient(
			JSONRPCRequest<ZabbixParamTemplateRequest> requestTemplate) throws ZabbixClientException {

		MultivaluedMap<String, Object> headers = new MultivaluedMapImpl<String, Object>();

		BaseRestResponseResult result = null;
		try {
			GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(requestTemplate);
			result = restClient.postRequest(baseWSUrl, headers, ge, RestClientHelper.JsonEntityBuilder.MEDIA_TYPE,
					new ZabbixResponseDecoder<ArrayList<ZabbixTemplateResponseV2_4>>(ArrayList.class,
							ZabbixTemplateResponseV2_4.class), null);
			return handleResult(result);

		} catch (IOException | RestClientException | NoMappingModelFoundException | MappingException
				| ServerErrorResponseException e) {
			throw new ZabbixClientException(e);
		}

	}

	@SuppressWarnings("unchecked")
	protected <REPRESENTATION_CLASS> REPRESENTATION_CLASS handleResult(BaseRestResponseResult result)
			throws ZabbixClientException {

		if (result.getStatus().getFamily() == Status.Family.SUCCESSFUL) {

			JSONRPCResponse<REPRESENTATION_CLASS> r = (JSONRPCResponse<REPRESENTATION_CLASS>) result.getResult();
			if (r.getResult() != null) {
				return ((JSONRPCResponse<REPRESENTATION_CLASS>) result.getResult()).getResult();
			} else {
				throw parseZabbixError(r.getError());
			}
		} else {
			throw new ZabbixClientException(result.getStatus().toString());
		}
	}

	private ZabbixClientException parseZabbixError(JSONRPCError error) {
		switch (ZabbixErrorCode.lookupFromName(error.getCode())) {
		case DUPLICATE_RESOURCE:
			return new ClientResponseException(error.getData(), Status.CONFLICT);
		case NOT_EXISTING_RESOURCE:
			return new ClientResponseException(error.getData(), Status.NOT_FOUND);
		default:
			return new ZabbixClientException("Unexpected status code");
		}

	}

	private enum ZabbixErrorCode {

		DUPLICATE_RESOURCE(-32602), NOT_EXISTING_RESOURCE(-32500);

		int code;

		private ZabbixErrorCode(int code) {
			this.code = code;
		}

		public int getCode() {
			return code;
		}

		public static ZabbixErrorCode lookupFromName(int code) throws IllegalArgumentException {
			for (ZabbixErrorCode c : values()) {
				if (code == c.getCode()) {
					return c;
				}
			}
			throw new IllegalArgumentException("Cannot find [" + code + "] into ZabbixErrorCode enum");
		}

	}
}