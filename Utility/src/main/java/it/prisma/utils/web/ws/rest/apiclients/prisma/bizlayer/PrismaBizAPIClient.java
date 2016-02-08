package it.prisma.utils.web.ws.rest.apiclients.prisma.bizlayer;

import it.prisma.domain.dsl.accounting.AuthTokenChangeExpirationTimeRequest;
import it.prisma.domain.dsl.accounting.AuthTokenRepresentation;
import it.prisma.domain.dsl.accounting.organizations.OrganizationRepresentation;
import it.prisma.domain.dsl.accounting.users.UserAccessRepresentation;
import it.prisma.domain.dsl.accounting.users.UserRepresentation;
import it.prisma.domain.dsl.accounting.users.requests.SignUpUserRequest;
import it.prisma.domain.dsl.accounting.users.responses.RetrieveUserProfileImageResponse;
import it.prisma.domain.dsl.accounting.workgroups.ActionRepresentation;
import it.prisma.domain.dsl.accounting.workgroups.PrivilegeAllowsActionRepresentation;
import it.prisma.domain.dsl.accounting.workgroups.PrivilegeRepresentation;
import it.prisma.domain.dsl.accounting.workgroups.WorkgroupMembershipRepresentation;
import it.prisma.domain.dsl.accounting.workgroups.WorkgroupRepresentation;
import it.prisma.domain.dsl.accounting.workgroups.requests.PrivilegeAddActionRequest;
import it.prisma.domain.dsl.accounting.workgroups.requests.PrivilegeCreationRequest;
import it.prisma.domain.dsl.accounting.workgroups.requests.WorkgroupAddUserAccountRequest;
import it.prisma.domain.dsl.accounting.workgroups.requests.WorkgroupCreationRequest;
import it.prisma.domain.dsl.accounting.workgroups.requests.WorkgroupEditRequest;
import it.prisma.domain.dsl.accounting.workgroups.requests.WorkgroupMembershipRequest;
import it.prisma.domain.dsl.accounting.workgroups.requests.WorkgroupPrivilegeRequest;
import it.prisma.domain.dsl.auditing.AuditingActionRepresentation;
import it.prisma.domain.dsl.auditing.DeployablePaaSServiceActionRepresentation;
import it.prisma.domain.dsl.configuration.PlatformConfiguration;
import it.prisma.domain.dsl.configuration.PlatformConfigurations;
import it.prisma.domain.dsl.deployments.VirtualMachineRepresentation;
import it.prisma.domain.dsl.exceptions.accounting.NotEnoughIaasTenantsConfiguredException;
import it.prisma.domain.dsl.exceptions.accounting.UnauthorizedException;
import it.prisma.domain.dsl.iaas.IaaSImageRepresentation;
import it.prisma.domain.dsl.iaas.configurations.CloudifyInstanceRepresentation;
import it.prisma.domain.dsl.iaas.configurations.DNSRecordRepresentation;
import it.prisma.domain.dsl.iaas.configurations.DNSZoneRepresentation;
import it.prisma.domain.dsl.iaas.configurations.IaaSAvailabilityZoneRepresentation;
import it.prisma.domain.dsl.iaas.configurations.IaaSFlavorRepresentation;
import it.prisma.domain.dsl.iaas.configurations.IaaSQoSProfileRepresentation;
import it.prisma.domain.dsl.iaas.configurations.IaaSZoneRepresentation;
import it.prisma.domain.dsl.iaas.configurations.PrismaZoneRepresentation;
import it.prisma.domain.dsl.iaas.network.NetworkRepresentation;
import it.prisma.domain.dsl.iaas.openstack.keypairs.KeypairRepresentation;
import it.prisma.domain.dsl.iaas.openstack.keypairs.KeypairRequest;
import it.prisma.domain.dsl.iaas.tenant.IaaSTenantRepresentation;
import it.prisma.domain.dsl.iaas.tenant.IaasEnvironmentRepresentation;
import it.prisma.domain.dsl.iaas.tenant.request.CloudifyInstanceCreationRequest;
import it.prisma.domain.dsl.iaas.tenant.request.IaaSTenantCreationRequest;
import it.prisma.domain.dsl.iaas.tenant.request.IaaSTenantSendEmailCreationRequest;
import it.prisma.domain.dsl.iaas.vmaas.VMRepresentation;
import it.prisma.domain.dsl.iaas.vmaas.request.VMDeployRequest;
import it.prisma.domain.dsl.infrastructure.WorkgroupQuota;
import it.prisma.domain.dsl.monitoring.businesslayer.iaas.hypervisor.Hypervisor;
import it.prisma.domain.dsl.monitoring.businesslayer.paas.response.IaaSHealth;
import it.prisma.domain.dsl.monitoring.pillar.wrapper.iaas.MonitoringWrappedResponseIaas;
import it.prisma.domain.dsl.monitoring.pillar.wrapper.paas.MonitoringWrappedResponsePaas;
import it.prisma.domain.dsl.orchestrator.LRTRepresentation;
import it.prisma.domain.dsl.paas.services.GenericPaaSServiceRepresentation;
import it.prisma.domain.dsl.paas.services.PaaSServiceEventRepresentation;
import it.prisma.domain.dsl.paas.services.appaas.request.APPaaSDeployRequest;
import it.prisma.domain.dsl.paas.services.appaas.request.APPaaSEnvironmentDeployRequest;
import it.prisma.domain.dsl.paas.services.appaas.response.APPaaSEnvironmentRepresentation;
import it.prisma.domain.dsl.paas.services.appaas.response.APPaaSRepresentation;
import it.prisma.domain.dsl.paas.services.appaas.response.ApplicationRepositoryEntryRepresentation;
import it.prisma.domain.dsl.paas.services.biaas.BIaaSRepresentation;
import it.prisma.domain.dsl.paas.services.biaas.request.BIaaSDeployRequest;
import it.prisma.domain.dsl.paas.services.bpmaas.BPMaaSDeployRequest;
import it.prisma.domain.dsl.paas.services.bpmaas.BPMaaSRepresentation;
import it.prisma.domain.dsl.paas.services.caaas.CertificateInfoRepresentation;
import it.prisma.domain.dsl.paas.services.dbaas.DBaaSRepresentation;
import it.prisma.domain.dsl.paas.services.dbaas.request.DBaaSDeployRequest;
import it.prisma.domain.dsl.paas.services.dnsaas.PaaSServiceDNRequest;
import it.prisma.domain.dsl.paas.services.emailaas.Credential;
import it.prisma.domain.dsl.paas.services.emailaas.EmailDomains;
import it.prisma.domain.dsl.paas.services.emailaas.EmailInfoRepresentation;
import it.prisma.domain.dsl.paas.services.emailaas.history.RowEmail;
import it.prisma.domain.dsl.paas.services.generic.request.GenericPaaSServiceDeployRequest;
import it.prisma.domain.dsl.paas.services.mqaas.MQaaSDeployRequest;
import it.prisma.domain.dsl.paas.services.mqaas.MQaaSRepresentation;
import it.prisma.domain.dsl.paas.services.odaas.ODaaSDeployRequest;
import it.prisma.domain.dsl.paas.services.odaas.ODaaSRepresentation;
import it.prisma.domain.dsl.paas.services.smsaas.IsActiveStatus;
import it.prisma.domain.dsl.paas.services.smsaas.SMSNotificationsRepresentation;
import it.prisma.domain.dsl.paas.services.smsaas.history.History;
import it.prisma.domain.dsl.prisma.AccountingErrorCode;
import it.prisma.domain.dsl.prisma.DebugInformations;
import it.prisma.domain.dsl.prisma.EmailRequest;
import it.prisma.domain.dsl.prisma.OrchestratorErrorCode;
import it.prisma.domain.dsl.prisma.WSPathParam;
import it.prisma.domain.dsl.prisma.paas.services.appaas.apprepo.request.AddAppSrcFileRequest;
import it.prisma.domain.dsl.prisma.paas.services.appaas.apprepo.response.AddAppSrcFileResponse;
import it.prisma.domain.dsl.prisma.prismaprotocol.Error;
import it.prisma.domain.dsl.prisma.prismaprotocol.PrismaResponseWrapper;
import it.prisma.domain.dsl.prisma.prismaprotocol.restwsrequests.RestWSParamsContainer;
import it.prisma.utils.json.JsonUtility;
import it.prisma.utils.web.ws.rest.apiclients.prisma.APIErrorException;
import it.prisma.utils.web.ws.rest.apiclients.prisma.AbstractPrismaAPIClient;
import it.prisma.utils.web.ws.rest.apiclients.prisma.PrismaMetaData;
import it.prisma.utils.web.ws.rest.apiclients.prisma.PrismaRestResponseDecoder;
import it.prisma.utils.web.ws.rest.apiencoding.MappingException;
import it.prisma.utils.web.ws.rest.apiencoding.NoMappingModelFoundException;
import it.prisma.utils.web.ws.rest.apiencoding.RestMessage;
import it.prisma.utils.web.ws.rest.apiencoding.ServerErrorResponseException;
import it.prisma.utils.web.ws.rest.apiencoding.decode.BaseRestResponseResult;
import it.prisma.utils.web.ws.rest.restclient.RestClient;
import it.prisma.utils.web.ws.rest.restclient.RestClient.RestMethod;
import it.prisma.utils.web.ws.rest.restclient.RestClientFactory;
import it.prisma.utils.web.ws.rest.restclient.RestClientFactoryImpl;
import it.prisma.utils.web.ws.rest.restclient.RestClientHelper;
import it.prisma.utils.web.ws.rest.restclient.RestClientHelper.FormDataEntityBuilder;
import it.prisma.utils.web.ws.rest.restclient.exceptions.RestClientException;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.Status.Family;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

/**
 * This class contains Prisma BizLib Rest API requests implementation.
 * 
 * @author l.biava
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class PrismaBizAPIClient extends AbstractPrismaAPIClient {

	private Logger LOG = LogManager.getLogger(PrismaBizAPIClient.class);

	/**
	 * Creates a {@link PrismaBizAPIClient} using the default
	 * {@link RestClientFactoryImpl}.
	 * 
	 * @param baseWSUrl
	 *            The URL of Prisma BizWS.
	 */
	public PrismaBizAPIClient(String baseWSUrl) {
		this(baseWSUrl, new RestClientFactoryImpl());
	}

	/**
	 * Creates a {@link PrismaBizAPIClient} with the given
	 * {@link RestClientFactory}.
	 * 
	 * @param baseWSUrl
	 *            The URL of Prisma BizWS.
	 * @param restClientFactory
	 *            The custom factory for the {@link RestClient}.
	 */
	public PrismaBizAPIClient(String baseWSUrl, RestClientFactory restClientFactory) {
		super(baseWSUrl + "/biz/rest", restClientFactory);
	}

	// public AuthTokenRepresentation getAvailableAuthTokenForUser(String
	// userID,
	// String auth_data) throws RestClientException,
	// NoMappingModelFoundException, MappingException,
	// ServerErrorResponseException {
	// String URL = baseWSUrl + "/auth/tokens/" + userID;
	//
	// PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(
	// auth_data);
	//
	// BaseRestResponseResult result = restClient.getRequest(URL, headers,
	// new PrismaRestResponseDecoder<AuthTokenRepresentation>(
	// ArrayList.class, AuthTokenRepresentation.class), null);
	// if (result.getStatus() == Status.OK){
	// ArrayList<AuthTokenRepresentation> authTokenList = new ArrayList(
	// ((PrismaResponseWrapper<List<AuthTokenRepresentation>>) result
	// .getResult()).getResult());
	// return authTokenList.size()>0?authTokenList.get(0):null;
	// }else {
	// throw new APIErrorException("API_ERROR", null,
	// result.getOriginalRestMessage(),
	// ((PrismaResponseWrapper) result.getResult()).getError());
	// }
	// }

	/**
	 * Return session token, Not viewable to user
	 * 
	 * @param userID
	 * @param auth_data
	 * @return
	 * @throws RestClientException
	 * @throws NoMappingModelFoundException
	 * @throws MappingException
	 * @throws ServerErrorResponseException
	 */
	public AuthTokenRepresentation getAvailableAuthTokenForUser(Long userID, String auth_data)
			throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {
		String URL = baseWSUrl + "/auth/tokens/session/" + userID;

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<AuthTokenRepresentation>(AuthTokenRepresentation.class), null);
		return handleResult(result);
	}

	// Restituzione di tutti i token
	public ArrayList<AuthTokenRepresentation> getAuthTokenForUser(Long userID, RestWSParamsContainer params,
			PrismaMetaData meta, String auth_data) throws Exception {

		String URL = baseWSUrl + "/auth/tokens/" + userID;
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers,
				addRestWSParamsToQueryParams(params), null, null, null,
				new PrismaRestResponseDecoder<ArrayList<AuthTokenRepresentation>>(ArrayList.class,
						AuthTokenRepresentation.class), null);
		return handleResult(result, meta);
	}

	public AuthTokenRepresentation generateNewSessionAuthTokenForUser(Long userID, String auth_data)
			throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {
		String URL = baseWSUrl + "/auth/tokens/session/" + userID;
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.putRequest(URL, headers, null,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE, new PrismaRestResponseDecoder<AuthTokenRepresentation>(
						AuthTokenRepresentation.class), null);

		return handleResult(result);
	}

	public AuthTokenRepresentation generateNewAuthTokenForUser(String userID, String auth_data)
			throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {

		String URL = baseWSUrl + "/auth/tokens/" + userID;

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.putRequest(URL, headers, null,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE, new PrismaRestResponseDecoder<AuthTokenRepresentation>(
						AuthTokenRepresentation.class), null);

		return handleResult(result);
		//
		// if (result.getStatus() == Status.OK) {
		// ArrayList<AuthTokenRepresentation> authTokenList = new ArrayList(
		// ((PrismaResponseWrapper<List<AuthTokenRepresentation>>) result
		// .getResult()).getResult());
		// boolean tokenFound = false;
		// int i = 0;
		// AuthTokenRepresentation authToken = null;
		// while ((i < authTokenList.size()) && !tokenFound) {
		// authToken = authTokenList.get(i);
		// tokenFound = AuthTokenManagementUtil.isTokenValid(authToken
		// .getExpiresAt());
		// i++;
		// }
		//
		// if (tokenFound)
		// return authToken;
		// else
		// return null;
		// } else {
		// throw new APIErrorException("API_ERROR", null,
		// result.getOriginalRestMessage(),
		// ((PrismaResponseWrapper) result.getResult()).getError());
		// }
	}

	public AuthTokenRepresentation changeExpirationTime(String token, AuthTokenChangeExpirationTimeRequest request,
			String auth_data) throws RestClientException, NoMappingModelFoundException, MappingException,
			ServerErrorResponseException, JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/auth/tokens/" + token + "/expiration";

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);

		BaseRestResponseResult result = restClient.putRequest(URL, headers, ge,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE, new PrismaRestResponseDecoder<AuthTokenRepresentation>(
						AuthTokenRepresentation.class), null);

		return handleResult(result);

	}

	public boolean deleteAuthTokenForUser(String tokenID, String auth_data) throws MappingException,
			NoMappingModelFoundException, ServerErrorResponseException, APIErrorException, RestClientException,
			JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/auth/tokens/" + tokenID;

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		RestMessage result = restClient.doRequest(RestMethod.DELETE, URL, headers, null, null, null, null);

		if (Status.Family.familyOf(result.getHttpStatusCode()) == Status.Family.SUCCESSFUL) {
			return true;
		} else {
			return false;
		}
	}

	// Start: WORKGROUPS ****************************************** //

	public WorkgroupRepresentation workgroupManagerCreate(WorkgroupCreationRequest request, String auth_data)
			throws MappingException, NoMappingModelFoundException, ServerErrorResponseException, APIErrorException,
			RestClientException, JsonParseException, JsonMappingException, IOException {
		String URL = baseWSUrl + "/accounting/workgroups/";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);
		BaseRestResponseResult result = restClient.putRequest(URL, headers, ge,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE, new PrismaRestResponseDecoder<WorkgroupRepresentation>(
						WorkgroupRepresentation.class), null);
		if (result.getStatus().getFamily() != Family.SUCCESSFUL) {
			throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
					((PrismaResponseWrapper) result.getResult()).getError());
		} else {
			return (WorkgroupRepresentation) ((PrismaResponseWrapper) result.getResult()).getResult();
		}

	}

	public PrivilegeRepresentation workgroupManagerCreatePrivilege(PrivilegeCreationRequest privilegeCreateRequest,
			String auth_data) throws RestClientException, NoMappingModelFoundException, MappingException,
			ServerErrorResponseException, JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/accounting/workgroups/privileges";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(privilegeCreateRequest);
		BaseRestResponseResult result = restClient.putRequest(URL, headers, ge,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE, new PrismaRestResponseDecoder<PrivilegeRepresentation>(
						PrivilegeRepresentation.class), null);
		if (result.getStatus().getFamily() != Family.SUCCESSFUL) {
			throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
					((PrismaResponseWrapper) result.getResult()).getError());
		} else {
			return (PrivilegeRepresentation) ((PrismaResponseWrapper) result.getResult()).getResult();
		}

	}

	public List<PrivilegeAllowsActionRepresentation> workgroupManagerGetWorkgroupPrivilegeAllowedActionsByPrivilegeId(
			PrismaMetaData meta, RestWSParamsContainer params, Long privilegeId, String auth_data)
			throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {

		String URL = baseWSUrl + "/accounting/workgroups/privileges/" + privilegeId + "/actions";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers,
				addRestWSParamsToQueryParams(params), null, null, null,
				new PrismaRestResponseDecoder<ArrayList<PrivilegeAllowsActionRepresentation>>(ArrayList.class,
						PrivilegeAllowsActionRepresentation.class), null);

		return handleResult(result, meta);

	}

	public PrivilegeRepresentation workgroupManagerGetWorkgroupPrivilegeById(Long privilegeId, String auth_data)
			throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {

		String URL = baseWSUrl + "/accounting/workgroups/privileges/" + privilegeId;
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<PrivilegeRepresentation>(PrivilegeRepresentation.class), null);
		if (result.getStatus() == Status.OK) {
			try {
				return ((PrismaResponseWrapper<PrivilegeRepresentation>) result.getResult()).getResult();
			} catch (Exception e) {
				throw new MappingException("Unexpected response type.", null, result.getOriginalRestMessage());
			}
		} else {
			throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
					((PrismaResponseWrapper) result.getResult()).getError());
		}

	}

	public List<PrivilegeRepresentation> workgroupManagerGetWorkgroupPrivileges(RestWSParamsContainer params,
			PrismaMetaData meta, String auth_data) throws MappingException, RestClientException,
			NoMappingModelFoundException, ServerErrorResponseException {

		String URL = baseWSUrl + "/accounting/workgroups/privileges";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers,
				addRestWSParamsToQueryParams(params), null, null, null,
				new PrismaRestResponseDecoder<ArrayList<PrivilegeRepresentation>>(ArrayList.class,
						PrivilegeRepresentation.class), null);

		return handleResult(result, meta);

	}

	public WorkgroupRepresentation workgroupManagerGetWorkgroupById(final Long workgroupId, String auth_data)
			throws MappingException, NoMappingModelFoundException, ServerErrorResponseException, APIErrorException,
			RestClientException, JsonParseException, JsonMappingException, IOException {
		String URL = baseWSUrl + "/accounting/workgroups/" + workgroupId;
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<WorkgroupRepresentation>(WorkgroupRepresentation.class), null);
		if (result.getStatus() == Status.OK) {
			try {
				return ((PrismaResponseWrapper<WorkgroupRepresentation>) result.getResult()).getResult();
			} catch (Exception e) {
				throw new MappingException("Unexpected response type.", null, result.getOriginalRestMessage());
			}
		} else {
			throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
					((PrismaResponseWrapper) result.getResult()).getError());
		}
	}

	public Collection<ActionRepresentation> workgroupManagerGetAllWorkgroupPrivilegeActions(String auth_data)
			throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {

		PrismaMetaData meta = new PrismaMetaData();

		String URL = baseWSUrl + "/accounting/workgroups/privileges/actions";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers, null, null, null, null,
				new PrismaRestResponseDecoder<ArrayList<ActionRepresentation>>(ArrayList.class,
						ActionRepresentation.class), null);
		return handleResult(result, meta);

	}

	public List<WorkgroupRepresentation> workgroupManagerGetAllWorkgroupsNotMembership(Long userAccountId,
			String auth_data) throws RestClientException, NoMappingModelFoundException, MappingException,
			ServerErrorResponseException {

		String URL = baseWSUrl + "/accounting/users/" + userAccountId + "/workgroups/notmemberships";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers, null, null, null, null,
				new PrismaRestResponseDecoder<ArrayList<WorkgroupRepresentation>>(ArrayList.class,
						WorkgroupRepresentation.class), null);
		return handleResult(result);

	}

	public List<WorkgroupRepresentation> workgroupManagerGetAllWorkgroups(String auth_data) throws MappingException,
			NoMappingModelFoundException, ServerErrorResponseException, APIErrorException, RestClientException,
			JsonParseException, JsonMappingException, IOException {

		PrismaMetaData meta = new PrismaMetaData();

		String URL = baseWSUrl + "/accounting/workgroups";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers, null, // addRestWSParamsToQueryParams(params),
				null, null, null, new PrismaRestResponseDecoder<ArrayList<WorkgroupRepresentation>>(ArrayList.class,
						WorkgroupRepresentation.class), null);
		return handleResult(result, meta);

	}

	public List<WorkgroupRepresentation> workgroupManagerGetAllWorkgroups(RestWSParamsContainer params,
			PrismaMetaData meta, String auth_data) throws MappingException, NoMappingModelFoundException,
			ServerErrorResponseException, APIErrorException, RestClientException, JsonParseException,
			JsonMappingException, IOException {

		String URL = baseWSUrl + "/accounting/workgroups";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers,
				addRestWSParamsToQueryParams(params), null, null, null,
				new PrismaRestResponseDecoder<ArrayList<WorkgroupRepresentation>>(ArrayList.class,
						WorkgroupRepresentation.class), null);
		return handleResult(result, meta);

	}

	public WorkgroupMembershipRepresentation workgroupManagerGetMembershipByWorkgroupIdAndUserAccountId(
			final Long workgroupId, final Long userAccountId, String auth_data) throws MappingException,
			NoMappingModelFoundException, ServerErrorResponseException, APIErrorException, RestClientException,
			JsonParseException, JsonMappingException, IOException {
		String URL = baseWSUrl + "/accounting/workgroups/" + workgroupId + "/memberships/users/" + userAccountId;
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<WorkgroupMembershipRepresentation>(
						WorkgroupMembershipRepresentation.class), null);
		if (result.getStatus() == Status.OK) {
			try {
				return ((PrismaResponseWrapper<WorkgroupMembershipRepresentation>) result.getResult()).getResult();
			} catch (Exception e) {
				throw new MappingException("Unexpected response type.", null, result.getOriginalRestMessage());
			}
		} else {
			Error error = ((PrismaResponseWrapper) result.getResult()).getError();
			if (error.getErrorCode() == OrchestratorErrorCode.ORC_NOT_AUTHORIZED.getCode()) {
				throw new UnauthorizedException(error.getErrorMsg());
			} else {
				throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
						((PrismaResponseWrapper) result.getResult()).getError());
			}
		}
	}

	public List<WorkgroupMembershipRepresentation> workgroupManagerGetAllMembershipsByWorkgroupId(
			final Long workgroupId, RestWSParamsContainer params, PrismaMetaData meta, String auth_data)
			throws MappingException, NoMappingModelFoundException, ServerErrorResponseException, APIErrorException,
			RestClientException, JsonParseException, JsonMappingException, IOException {
		String URL = baseWSUrl + "/accounting/workgroups/" + workgroupId + "/memberships";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers,
				addRestWSParamsToQueryParams(params), null, null, null,
				new PrismaRestResponseDecoder<ArrayList<WorkgroupMembershipRepresentation>>(ArrayList.class,
						WorkgroupMembershipRepresentation.class), null);

		return handleResult(result, meta);

	}

	public List<WorkgroupMembershipRepresentation> workgroupManagerGetAllMembershipsApplicationsByWorkgroupId(
			final Long workgroupId, PrismaMetaData meta, String auth_data) throws MappingException,
			NoMappingModelFoundException, ServerErrorResponseException, APIErrorException, RestClientException,
			JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/accounting/workgroups/" + workgroupId + "/memberships/applications";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<ArrayList<WorkgroupMembershipRepresentation>>(ArrayList.class,
						WorkgroupMembershipRepresentation.class), null);

		return handleResult(result, meta);
	}

	public List<WorkgroupMembershipRepresentation> workgroupManagerGetAllApprovedMembershipsByWorkgroupId(
			final Long workgroupId, PrismaMetaData meta, String auth_data) throws MappingException,
			NoMappingModelFoundException, ServerErrorResponseException, APIErrorException, RestClientException,
			JsonParseException, JsonMappingException, IOException {
		String URL = baseWSUrl + "/accounting/workgroups/" + workgroupId + "/memberships/approved";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<ArrayList<WorkgroupMembershipRepresentation>>(ArrayList.class,
						WorkgroupMembershipRepresentation.class), null);

		return handleResult(result, meta);

	}

	public List<WorkgroupMembershipRepresentation> workgroupManagerGetAllMemberships(PrismaMetaData meta,
			String auth_data) throws MappingException, NoMappingModelFoundException, ServerErrorResponseException,
			APIErrorException, RestClientException, JsonParseException, JsonMappingException, IOException {
		String URL = baseWSUrl + "/accounting/workgroups/memberships";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<ArrayList<WorkgroupMembershipRepresentation>>(ArrayList.class,
						WorkgroupMembershipRepresentation.class), null);

		return handleResult(result, meta);
	}

	public List<WorkgroupMembershipRepresentation> workgroupManagerGetAllMembershipsByUserAccountId(
			final Long userAccountId, PrismaMetaData meta, RestWSParamsContainer params, String auth_data)
			throws MappingException, NoMappingModelFoundException, ServerErrorResponseException, APIErrorException,
			RestClientException, JsonParseException, JsonMappingException, IOException {
		String URL = baseWSUrl + "/accounting/workgroups/memberships?user-id=" + userAccountId;
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers,
				addRestWSParamsToQueryParams(params), null, null, null,
				new PrismaRestResponseDecoder<ArrayList<WorkgroupMembershipRepresentation>>(ArrayList.class,
						WorkgroupMembershipRepresentation.class), null);

		return handleResult(result, meta);

	}

	public List<WorkgroupMembershipRepresentation> workgroupManagerGetAllMembershipApplicationsByUserAccountId(
			final Long userAccountId, PrismaMetaData meta, String auth_data) throws MappingException,
			NoMappingModelFoundException, ServerErrorResponseException, APIErrorException, RestClientException,
			JsonParseException, JsonMappingException, IOException {
		String URL = baseWSUrl + "/accounting/workgroups/memberships/users/" + userAccountId + "/applications";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<ArrayList<WorkgroupMembershipRepresentation>>(ArrayList.class,
						WorkgroupMembershipRepresentation.class), null);

		return handleResult(result, meta);
	}

	public List<WorkgroupMembershipRepresentation> workgroupManagerGetAllApprovedMembershipsByUserAccountId(
			final Long userAccountId, PrismaMetaData meta, String auth_data) throws MappingException,
			NoMappingModelFoundException, ServerErrorResponseException, APIErrorException, RestClientException,
			JsonParseException, JsonMappingException, IOException {
		String URL = baseWSUrl + "/accounting/workgroups/memberships/" + userAccountId + "/approved";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<ArrayList<WorkgroupMembershipRepresentation>>(ArrayList.class,
						WorkgroupMembershipRepresentation.class), null);

		if (!(result.getStatus().getFamily() == Status.Family.SUCCESSFUL)) {
			Error error = ((PrismaResponseWrapper) result.getResult()).getError();
			if (error.getErrorCode() == OrchestratorErrorCode.ORC_WG_MEMBERSHIP_NOT_FOUND.getCode()) {
				error.setErrorCode(AccountingErrorCode.ACC_WG_MEMBERSHIP_NOT_FOUND.getCode());
				throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(), error);
			} else {
				throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
						((PrismaResponseWrapper) result.getResult()).getError());
			}
		} else {
			try {
				return ((PrismaResponseWrapper<ArrayList<WorkgroupMembershipRepresentation>>) result.getResult())
						.getResult();
			} catch (Exception e) {
				throw new MappingException("Unexpected response type.", null, result.getOriginalRestMessage());
			}
		}

	}

	public List<WorkgroupMembershipRepresentation> workgroupManagerGetAllMembershipsApplications(PrismaMetaData meta,
			String auth_data) throws MappingException, NoMappingModelFoundException, ServerErrorResponseException,
			APIErrorException, RestClientException, JsonParseException, JsonMappingException, IOException {
		String URL = baseWSUrl + "/accounting/workgroups/memberships/applications";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<ArrayList<WorkgroupMembershipRepresentation>>(ArrayList.class,
						WorkgroupMembershipRepresentation.class), null);

		return handleResult(result, meta);

	}

	public List<WorkgroupMembershipRepresentation> workgroupManagerGetAllApprovedMemberships(PrismaMetaData meta,
			String auth_data) throws MappingException, NoMappingModelFoundException, ServerErrorResponseException,
			APIErrorException, RestClientException, JsonParseException, JsonMappingException, IOException {
		String URL = baseWSUrl + "/accounting/workgroups/memberships/approved";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<ArrayList<WorkgroupMembershipRepresentation>>(ArrayList.class,
						WorkgroupMembershipRepresentation.class), null);

		return handleResult(result, meta);

	}

	public void workgroupManagerApplicateForMembership(Long workgroupId, Long userAccountId, String auth_data)
			throws MappingException, NoMappingModelFoundException, ServerErrorResponseException, APIErrorException,
			RestClientException, JsonParseException, JsonMappingException, IOException {
		String URL = baseWSUrl + "/accounting/workgroups/" + workgroupId + "/memberships/users/" + userAccountId;
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.putRequest(URL, headers, null,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE, new PrismaRestResponseDecoder<String>(String.class),
				null);
		if (!(result.getStatus() == Status.OK)) {
			Error error = ((PrismaResponseWrapper) result.getResult()).getError();
			if (error.getErrorCode() == OrchestratorErrorCode.ORC_NOT_AUTHORIZED.getCode()) {
				throw new UnauthorizedException(error.getErrorMsg());
			} else {
				throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
						((PrismaResponseWrapper) result.getResult()).getError());
			}
		}
	}

	public void workgroupManagerSetWorkgroupPrivilege(WorkgroupPrivilegeRequest request, String auth_data)
			throws JsonParseException, JsonMappingException, IOException, RestClientException,
			NoMappingModelFoundException, MappingException, ServerErrorResponseException {

		String URL = baseWSUrl + "/accounting/workgroups/" + request.getWorkgroupId() + "/users/"
				+ request.getUserAccountId() + "/privilege";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);
		BaseRestResponseResult result = restClient.putRequest(URL, headers, ge,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE, new PrismaRestResponseDecoder<WorkgroupRepresentation>(
						WorkgroupRepresentation.class), null);
		if (result.getStatus().getFamily() != Family.SUCCESSFUL) {
			Error error = ((PrismaResponseWrapper) result.getResult()).getError();
			if (error.getErrorCode() == OrchestratorErrorCode.ORC_NOT_AUTHORIZED.getCode()) {
				throw new UnauthorizedException(error.getErrorMsg());
			} else {
				throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
						((PrismaResponseWrapper) result.getResult()).getError());
			}
		}

	}

	public void workgroupManagerUnapproveMembershipToWorkgroup(WorkgroupMembershipRequest request, String auth_data)
			throws JsonParseException, JsonMappingException, IOException, RestClientException,
			NoMappingModelFoundException, MappingException, ServerErrorResponseException {
		String URL = baseWSUrl + "/accounting/workgroups/" + request.getWorkgroupId() + "/memberships/unapprove";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);
		BaseRestResponseResult result = restClient.putRequest(URL, headers, ge,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE, new PrismaRestResponseDecoder<String>(String.class),
				null);
		handleResult(result);
	}

	public void workgroupManagerApproveMembershipToWorkgroup(WorkgroupMembershipRequest request, String auth_data)
			throws JsonParseException, JsonMappingException, IOException, RestClientException,
			NoMappingModelFoundException, MappingException, ServerErrorResponseException {
		String URL = baseWSUrl + "/accounting/workgroups/" + request.getWorkgroupId() + "/memberships/approve";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);
		BaseRestResponseResult result = restClient.putRequest(URL, headers, ge,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE, new PrismaRestResponseDecoder<String>(String.class),
				null);
		handleResult(result);
	}

	public List<UserRepresentation> workgroupManagerGetWorkgroupReferentsByWorkgroupId(final Long workgroupId,
			RestWSParamsContainer params, PrismaMetaData meta, String auth_data) throws MappingException,
			NoMappingModelFoundException, ServerErrorResponseException, APIErrorException, RestClientException,
			JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/accounting/workgroups/" + workgroupId + "/referents";

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient
				.doRequest(RestMethod.GET, URL, headers, addRestWSParamsToQueryParams(params), null, null, null,
						new PrismaRestResponseDecoder<ArrayList<UserRepresentation>>(ArrayList.class,
								UserRepresentation.class), null);

		return handleResult(result, meta);

	}

	public void workgroupManagerUpdateWorkgroupPrivilege(Long privilegeId, PrivilegeRepresentation privilege,
			String auth_data)

	throws JsonParseException, JsonMappingException, IOException, RestClientException, NoMappingModelFoundException,
			MappingException, ServerErrorResponseException {

		String URL = baseWSUrl + "/accounting/workgroups/privileges/" + privilegeId;

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(privilege);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.PUT, URL, headers, null, ge,
				MediaType.APPLICATION_JSON_TYPE, null, new PrismaRestResponseDecoder<PrivilegeRepresentation>(
						PrivilegeRepresentation.class), null);

		handleResult(result);

	}

	public WorkgroupRepresentation workgroupManagerUpdateWorkgroup(Long workgroupId,
			WorkgroupEditRequest workgroupEditRequest, String auth_data) throws RestClientException,
			NoMappingModelFoundException, MappingException, ServerErrorResponseException, JsonParseException,
			JsonMappingException, IOException, NotEnoughIaasTenantsConfiguredException {

		String URL = baseWSUrl + "/accounting/workgroups/" + workgroupId;

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(workgroupEditRequest);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.PUT, URL, headers, null, ge,
				MediaType.APPLICATION_JSON_TYPE, null, new PrismaRestResponseDecoder<WorkgroupRepresentation>(
						WorkgroupRepresentation.class), null);

		if (result.getStatus().getFamily() == Status.Family.SUCCESSFUL) {
			try {

				if (result.getResult() != null) {
					return ((PrismaResponseWrapper<WorkgroupRepresentation>) result.getResult()).getResult();
				} else {
					return null;
				}
			} catch (Exception e) {
				throw new APIErrorException("Unexpected response type.", e);
			}
		} else {
			Error error = ((PrismaResponseWrapper) result.getResult()).getError();

			if (error.getErrorCode() == OrchestratorErrorCode.ORC_NOT_AUTHORIZED.getCode()) {
				throw new UnauthorizedException(error.getErrorMsg());
			} else {
				throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
						((PrismaResponseWrapper) result.getResult()).getError());
			}

		}

	}

	public void workgroupManagerDeleteActionFromPrivilege(Long privilegeId, Long actionId, String auth_data)
			throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {

		String URL = baseWSUrl + "/accounting/workgroups/privileges/" + privilegeId + "/actions/" + actionId;

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.DELETE, URL, headers, null, null, null, null,
				new PrismaRestResponseDecoder<String>(String.class), null);

		handleResult(result);

	}

	public void workgroupManagerDeleteUserFromWorkgroup(Long workgroupId, Long userAccountId, String auth_data)
			throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {

		String URL = baseWSUrl + "/accounting/workgroups/" + workgroupId + "/users/" + userAccountId;

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.DELETE, URL, headers, null, null, null, null,
				new PrismaRestResponseDecoder<String>(String.class), null);

		handleResult(result);

	}

	public void workgroupManagerDeleteReferentFromWorkgroup(Long workgroupId, Long userAccountId, String auth_data)
			throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {

		String URL = baseWSUrl + "/accounting/workgroups/" + workgroupId + "/referents/" + userAccountId;

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.DELETE, URL, headers, null, null, null, null,
				new PrismaRestResponseDecoder<String>(String.class), null);

		handleResult(result);
	}

	public void workgroupManagerUnapproveWorkgroup(Long workgroupId, String auth_data) throws RestClientException,
			NoMappingModelFoundException, MappingException, ServerErrorResponseException {

		String URL = baseWSUrl + "/accounting/workgroups/" + workgroupId + "/approvation";

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.DELETE, URL, headers, null, null, null, null,
				new PrismaRestResponseDecoder<String>(String.class), null);

		handleResult(result);

	}

	public void workgroupManagerApproveWorkgroup(Long workgroupId, String auth_data) throws RestClientException,
			NoMappingModelFoundException, MappingException, ServerErrorResponseException, JsonParseException,
			JsonMappingException, IOException, NotEnoughIaasTenantsConfiguredException {

		String URL = baseWSUrl + "/accounting/workgroups/" + workgroupId + "/approvation";

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.PUT, URL, headers, null, null,
				MediaType.APPLICATION_JSON_TYPE, null, new PrismaRestResponseDecoder<WorkgroupRepresentation>(
						WorkgroupRepresentation.class), null);

		if (result.getStatus().getFamily() != Status.Family.SUCCESSFUL) {
			Error error = ((PrismaResponseWrapper) result.getResult()).getError();

			if (error.getErrorCode() == OrchestratorErrorCode.ORC_WG_NOT_ENOUGH_TENANTS_CONFIGURED.getCode()) {
				throw new NotEnoughIaasTenantsConfiguredException();
			} else {
				throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
						((PrismaResponseWrapper) result.getResult()).getError());
			}
		}
	}

	public List<ActionRepresentation> getAllPotentialActionsForWorkgroupPrivilege(Long workgroupPrivilegeId,
			RestWSParamsContainer params, PrismaMetaData meta, String auth_data) throws RestClientException,
			NoMappingModelFoundException, MappingException, ServerErrorResponseException

	{
		String URL = baseWSUrl + "/accounting/workgroups/privileges/" + workgroupPrivilegeId + "/potentialActions";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers,
				addRestWSParamsToQueryParams(params), null, null, null,
				new PrismaRestResponseDecoder<ArrayList<ActionRepresentation>>(ArrayList.class,
						ActionRepresentation.class), null);

		return handleResult(result, meta);

	}

	public List<UserRepresentation> workgroupManagerGetAllPotentialReferentsForWorkgroup(Long workgroupId,
			RestWSParamsContainer params, PrismaMetaData meta, String auth_data) throws RestClientException,
			NoMappingModelFoundException, MappingException, ServerErrorResponseException {
		String URL = baseWSUrl + "/accounting/workgroups/" + workgroupId + "/potentialReferents";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient
				.doRequest(RestMethod.GET, URL, headers, addRestWSParamsToQueryParams(params), null, null, null,
						new PrismaRestResponseDecoder<ArrayList<UserRepresentation>>(ArrayList.class,
								UserRepresentation.class), null);

		return handleResult(result, meta);

	}

	public List<UserRepresentation> workgroupManagerGetAllPotentialUsersForWorkgroup(Long workgroupId,
			RestWSParamsContainer params, PrismaMetaData meta, String auth_data) throws RestClientException,
			NoMappingModelFoundException, MappingException, ServerErrorResponseException {
		String URL = baseWSUrl + "/accounting/workgroups/" + workgroupId + "/potentialUsersForWG";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers,
				addRestWSParamsToQueryParams(params), null, null, null,
				new PrismaRestResponseDecoder<ArrayList<WorkgroupMembershipRepresentation>>(ArrayList.class,
						UserRepresentation.class), null);

		return handleResult(result, meta);

	}

	public void workgroupManagerAddActionsToPrivilege(PrivilegeAddActionRequest actionIds, String auth_data)
			throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException,
			JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/accounting/workgroups/privileges/" + actionIds.getPrivilegeId() + "/actions";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(actionIds);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.PUT, URL, headers, null, ge,
				MediaType.APPLICATION_JSON_TYPE, null, new PrismaRestResponseDecoder<String>(String.class), null);

	}

	public void workgroupManagerAddReferentsToWorkgroup(WorkgroupAddUserAccountRequest workgroupAddReferentsRequest,
			String auth_data) throws RestClientException, NoMappingModelFoundException, MappingException,
			ServerErrorResponseException, JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/accounting/workgroups/" + workgroupAddReferentsRequest.getWorkgroupId()
				+ "/referents";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(workgroupAddReferentsRequest);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.PUT, URL, headers, null, ge,
				MediaType.APPLICATION_JSON_TYPE, null, new PrismaRestResponseDecoder<String>(String.class), null);

		handleResult(result);

	}

	public void applicateMembershipForWorkgroup(WorkgroupAddUserAccountRequest workgroupAddReferentsRequest,
			String auth_data) throws JsonParseException, JsonMappingException, IOException, RestClientException,
			NoMappingModelFoundException, MappingException, ServerErrorResponseException {

		String URL = baseWSUrl + "/accounting/workgroups/" + workgroupAddReferentsRequest.getWorkgroupId()
				+ "/memberships/users/" + workgroupAddReferentsRequest.getAccountIds().get(0);
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(workgroupAddReferentsRequest);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.PUT, URL, headers, null, ge,
				MediaType.APPLICATION_JSON_TYPE, null, new PrismaRestResponseDecoder<String>(String.class), null);

	}

	public void workgroupManagerAddUsersToWorkgroup(WorkgroupAddUserAccountRequest workgroupAddReferentsRequest,
			String auth_data) throws RestClientException, NoMappingModelFoundException, MappingException,
			ServerErrorResponseException, JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/accounting/workgroups/" + workgroupAddReferentsRequest.getWorkgroupId()
				+ "/memberships";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(workgroupAddReferentsRequest);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.PUT, URL, headers, null, ge,
				MediaType.APPLICATION_JSON_TYPE, null, new PrismaRestResponseDecoder<String>(String.class), null);

		handleResult(result);
	}

	// End: WORKGROUPS ******************************************** //

	// Start: ORGANIZATION ****************************************** //

	public OrganizationRepresentation getOrganizationById(String organizationId, String auth_data)
			throws MappingException, NoMappingModelFoundException, ServerErrorResponseException, APIErrorException,
			RestClientException, JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/organizations/" + organizationId;
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<OrganizationRepresentation>(OrganizationRepresentation.class), null);

		if (result.getStatus() == Status.OK) {
			try {
				return ((PrismaResponseWrapper<OrganizationRepresentation>) result.getResult()).getResult();
			} catch (Exception e) {
				throw new MappingException("Unexpected response type.", null, result.getOriginalRestMessage());
			}
		} else {
			throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
					((PrismaResponseWrapper) result.getResult()).getError());
		}

	}

	public List<OrganizationRepresentation> getAllOrganizations(String auth_data) throws MappingException,
			NoMappingModelFoundException, ServerErrorResponseException, APIErrorException, RestClientException,
			JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/organizations";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<ArrayList<OrganizationRepresentation>>(ArrayList.class,
						OrganizationRepresentation.class), null);

		if (result.getStatus() == Status.OK) {
			try {
				return ((PrismaResponseWrapper<ArrayList<OrganizationRepresentation>>) result.getResult()).getResult();
			} catch (Exception e) {
				throw new MappingException("Unexpected response type.", null, result.getOriginalRestMessage());
			}
		} else {
			throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
					((PrismaResponseWrapper) result.getResult()).getError());
		}

	}

	// Start: TENANT MANAGEMENT ************************************//

	public IaasEnvironmentRepresentation getIaaSEnvironmentById(Long iaasEnvironmentId, String auth_data)
			throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {

		String URL = baseWSUrl + "/iaas/environments/" + iaasEnvironmentId;
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient
				.getRequest(URL, headers, new PrismaRestResponseDecoder<IaasEnvironmentRepresentation>(
						IaasEnvironmentRepresentation.class), null);

		if (result.getStatus() == Status.OK) {
			try {
				return ((PrismaResponseWrapper<IaasEnvironmentRepresentation>) result.getResult()).getResult();
			} catch (Exception e) {
				throw new MappingException("Unexpected response type.", null, result.getOriginalRestMessage());
			}
		} else {
			throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
					((PrismaResponseWrapper) result.getResult()).getError());
		}

	}

	public List<IaasEnvironmentRepresentation> getAllIaaSEnvironments(String auth_data) throws RestClientException,
			NoMappingModelFoundException, MappingException, ServerErrorResponseException {
		// TODO Auto-generated method stub

		String URL = baseWSUrl + "/iaas/environments";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<ArrayList<IaasEnvironmentRepresentation>>(ArrayList.class,
						IaasEnvironmentRepresentation.class), null);

		if (result.getStatus() == Status.OK) {
			try {
				return ((PrismaResponseWrapper<ArrayList<IaasEnvironmentRepresentation>>) result.getResult())
						.getResult();
			} catch (Exception e) {
				throw new MappingException("Unexpected response type.", null, result.getOriginalRestMessage());
			}
		} else {
			throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
					((PrismaResponseWrapper) result.getResult()).getError());
		}

	}

	public List<IaaSTenantRepresentation> getAllIaaSTenants(RestWSParamsContainer params, PrismaMetaData meta,
			String auth_data) throws RestClientException, NoMappingModelFoundException, MappingException,
			ServerErrorResponseException {

		String URL = baseWSUrl + "/iaas/tenants";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers,
				addRestWSParamsToQueryParams(params), null, null, null,
				new PrismaRestResponseDecoder<ArrayList<IaaSTenantRepresentation>>(ArrayList.class,
						IaaSTenantRepresentation.class), null);

		if (result.getStatus() == Status.OK) {
			try {
				return ((PrismaResponseWrapper<ArrayList<IaaSTenantRepresentation>>) result.getResult()).getResult();
			} catch (Exception e) {
				throw new MappingException("Unexpected response type.", null, result.getOriginalRestMessage());
			}
		} else {
			throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
					((PrismaResponseWrapper) result.getResult()).getError());
		}
	}

	public IaaSTenantRepresentation getIaaSTenantById(Long tenantId, String auth_data) throws RestClientException,
			NoMappingModelFoundException, MappingException, ServerErrorResponseException {

		String URL = baseWSUrl + "/iaas/tenants/" + tenantId;
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<IaaSTenantRepresentation>(IaaSTenantRepresentation.class), null);

		return handleResult(result);
	}

	public List<CloudifyInstanceRepresentation> getCloudifyInstancesByIaaSTenant(Long tenantId, String auth_data)
			throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {

		String URL = baseWSUrl + "/iaas/tenants/" + tenantId + "/cloudify";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<List<CloudifyInstanceRepresentation>>(ArrayList.class,
						CloudifyInstanceRepresentation.class), null);

		return handleResult(result);
	}

	public List<IaaSTenantRepresentation> getWorkgroupTenants(Long workgroupId, String auth_data) throws Exception {

		String URL = baseWSUrl + "/accounting/workgroups/" + workgroupId + "/tenants";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<ArrayList<IaaSTenantRepresentation>>(ArrayList.class,
						IaaSTenantRepresentation.class), null);

		if (result.getStatus() == Status.OK) {
			try {
				return ((PrismaResponseWrapper<ArrayList<IaaSTenantRepresentation>>) result.getResult()).getResult();
			} catch (Exception e) {
				throw new MappingException("Unexpected response type.", null, result.getOriginalRestMessage());
			}
		} else {
			throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
					((PrismaResponseWrapper) result.getResult()).getError());
		}
	}

	public void requestTenantCreationViaEmail(IaaSTenantSendEmailCreationRequest request, String auth_data)
			throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException,
			JsonParseException, JsonMappingException, IOException

	{
		String URL = baseWSUrl + "/iaas/tenants/requestTenantCreation";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);

		BaseRestResponseResult result = restClient.postRequest(URL, headers, ge,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE, new PrismaRestResponseDecoder<String>(String.class),
				null);

		if (result.getStatus().getFamily() != Family.SUCCESSFUL) {
			throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
					((PrismaResponseWrapper) result.getResult()).getError());
		}
	}

	public void createTenant(IaaSTenantCreationRequest request, String auth_data) throws RestClientException,
			NoMappingModelFoundException, MappingException, ServerErrorResponseException, JsonParseException,
			JsonMappingException, IOException {

		String URL = baseWSUrl + "/iaas/tenants";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);

		BaseRestResponseResult result = restClient.postRequest(URL, headers, ge,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE, new PrismaRestResponseDecoder<String>(String.class),
				null);

		if (!(result.getStatus().getFamily() == Status.Family.SUCCESSFUL)) {
			throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
					((PrismaResponseWrapper) result.getResult()).getError());
		}

		if (result.getStatus().getFamily() != Family.SUCCESSFUL) {
			throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
					((PrismaResponseWrapper) result.getResult()).getError());
		}

	}

	public CloudifyInstanceRepresentation createCloudifyInstance(CloudifyInstanceCreationRequest request,
			String auth_data) throws RestClientException, NoMappingModelFoundException, MappingException,
			ServerErrorResponseException, JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/iaas/tenants/" + request.getIaasTenantId() + "/cloudify";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);

		BaseRestResponseResult result = restClient.putRequest(URL, headers, ge,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE,
				new PrismaRestResponseDecoder<CloudifyInstanceRepresentation>(CloudifyInstanceRepresentation.class),
				null);

		return handleResult(result);

	}

	// End: TENANT MANAGEMENT ************************************//

	// Start: ACCOUNTING ****************************************** //

	public void userManagementSignUpOnPrismaIdentityProvider(SignUpUserRequest request, String auth_data)
			throws MappingException, NoMappingModelFoundException, ServerErrorResponseException, APIErrorException,
			RestClientException, JsonParseException, JsonMappingException, IOException {
		String URL = baseWSUrl + "/accounting/users/signup";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);
		BaseRestResponseResult result = restClient.putRequest(URL, headers, ge,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE, new PrismaRestResponseDecoder<String>(String.class),
				null);
		if (!(result.getStatus().getFamily() == Status.Family.SUCCESSFUL)) {
			Error error = ((PrismaResponseWrapper) result.getResult()).getError();
			if (error.getErrorCode() == OrchestratorErrorCode.ORC_ITEM_ALREADY_EXISTS.getCode()) {
				error.setErrorCode(AccountingErrorCode.ACC_USER_SIGNUP_CREDENTIAL_ALREADY_USED_ON_IDP.getCode());
				throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(), error);
			} else {
				throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
						((PrismaResponseWrapper) result.getResult()).getError());
			}
		}

	}

	public void userManagementSetPrismaUser(Long userAccountId, String auth_data) throws RestClientException,
			NoMappingModelFoundException, MappingException, ServerErrorResponseException {

		String URL = baseWSUrl + "/accounting/users/" + userAccountId + "/roles/prismaUser";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.putRequest(URL, headers, null,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE, new PrismaRestResponseDecoder<String>(String.class),
				null);
		if (!(result.getStatus().getFamily() == Status.Family.SUCCESSFUL)) {
			throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
					((PrismaResponseWrapper) result.getResult()).getError());
		}

	}

	public void userManagementSetPrismaAdmin(Long userAccountId, String auth_data) throws RestClientException,
			NoMappingModelFoundException, MappingException, ServerErrorResponseException {

		String URL = baseWSUrl + "/accounting/users/" + userAccountId + "/roles/prismaAdmin";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.putRequest(URL, headers, null,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE, new PrismaRestResponseDecoder<String>(String.class),
				null);
		if (!(result.getStatus().getFamily() == Status.Family.SUCCESSFUL)) {
			throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
					((PrismaResponseWrapper) result.getResult()).getError());
		}

	}

	public void userManagementSignUpFromThirdPartyIdentityProvider(SignUpUserRequest request, String auth_data)
			throws MappingException, NoMappingModelFoundException, ServerErrorResponseException, APIErrorException,
			RestClientException, JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/accounting/users/signup-third-party";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);
		BaseRestResponseResult result = restClient.putRequest(URL, headers, ge,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE, new PrismaRestResponseDecoder<String>(String.class),
				null);
		if (!(result.getStatus().getFamily() == Status.Family.SUCCESSFUL)) {
			Error error = ((PrismaResponseWrapper) result.getResult()).getError();
			if (error.getErrorCode() == OrchestratorErrorCode.ORC_ITEM_ALREADY_EXISTS.getCode()) {
				error.setErrorCode(AccountingErrorCode.ACC_USER_SIGNUP_CREDENTIAL_ALREADY_USED_ON_IDP.getCode());
				throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(), error);
			} else {
				throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
						((PrismaResponseWrapper) result.getResult()).getError());
			}
		}

	}

	public UserRepresentation userManagementGetUserById(Long userAccountId, String auth_data) throws MappingException,
			NoMappingModelFoundException, ServerErrorResponseException, APIErrorException, RestClientException,
			JsonParseException, JsonMappingException, IOException {
		String URL = baseWSUrl + "/accounting/users/" + userAccountId;
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<UserRepresentation>(UserRepresentation.class), null);
		if (result.getStatus() == Status.OK) {
			try {
				return ((PrismaResponseWrapper<UserRepresentation>) result.getResult()).getResult();
			} catch (Exception e) {
				throw new MappingException("Unexpected response type.", null, result.getOriginalRestMessage());
			}
		} else {
			throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
					((PrismaResponseWrapper) result.getResult()).getError());
		}
	}

	public ArrayList<UserRepresentation> userManagementGetAllUsers(RestWSParamsContainer params, PrismaMetaData meta,
			String auth_data) throws MappingException, NoMappingModelFoundException, ServerErrorResponseException,
			APIErrorException, RestClientException, JsonParseException, JsonMappingException, IOException {
		String URL = baseWSUrl + "/accounting/users";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient
				.doRequest(RestMethod.GET, URL, headers, addRestWSParamsToQueryParams(params), null, null, null,
						new PrismaRestResponseDecoder<ArrayList<UserRepresentation>>(ArrayList.class,
								UserRepresentation.class), null);

		return handleResult(result, meta);

	}

	public UserRepresentation userManagementGetUserByCredentialsOnIdentityProvider(Long identityProviderId,
			String nameId, String ipAddress, String auth_data) throws MappingException, NoMappingModelFoundException,
			ServerErrorResponseException, APIErrorException, RestClientException, JsonParseException,
			JsonMappingException, IOException {

		String URL = baseWSUrl + "/accounting/organizations/idps/" + identityProviderId + "/users/" + nameId;

		LOG.debug("getUserByCredentialsOnIdentityProvider: ", URL);

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		MultivaluedMap<String, Object> qp = new MultivaluedHashMap<String, Object>();
		qp.putSingle("ipAddress", ipAddress);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers, qp, null, null, null,
				new PrismaRestResponseDecoder<UserRepresentation>(UserRepresentation.class), null);

		if (!(result.getStatus().getFamily() == Status.Family.SUCCESSFUL)) {
			Error error = ((PrismaResponseWrapper) result.getResult()).getError();
			if (error.getErrorCode() == OrchestratorErrorCode.ORC_USER_ACCOUNT_NOT_FOUND.getCode()) {
				error.setErrorCode(AccountingErrorCode.ACC_USER_NOT_FOUND.getCode());
				throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(), error);
			} else {
				throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
						((PrismaResponseWrapper) result.getResult()).getError());
			}
		}

		if (result.getStatus() == Status.OK) {
			try {
				return ((PrismaResponseWrapper<UserRepresentation>) result.getResult()).getResult();
			} catch (Exception e) {
				throw new MappingException("Unexpected response type.", null, result.getOriginalRestMessage());
			}
		} else {
			throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
					((PrismaResponseWrapper) result.getResult()).getError());
		}
	}

	public void userManagementSuspendUserUserById(Long userAccountId, String auth_data) throws RestClientException,
			NoMappingModelFoundException, MappingException, ServerErrorResponseException {
		String URL = baseWSUrl + "/accounting/users/" + userAccountId + "/suspend";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.putRequest(URL, headers, null, null, null,
				new PrismaRestResponseDecoder<String>(String.class), null);
		if (result.getStatus().getFamily() != Family.SUCCESSFUL) {
			throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
					((PrismaResponseWrapper) result.getResult()).getError());
		}
	}

	public void userManagementActivateUserUserById(Long userAccountId, String auth_data) throws RestClientException,
			NoMappingModelFoundException, MappingException, ServerErrorResponseException {
		String URL = baseWSUrl + "/accounting/users/" + userAccountId + "/unsuspend";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.putRequest(URL, headers, null, null, null,
				new PrismaRestResponseDecoder<String>(String.class), null);
		if (result.getStatus().getFamily() != Family.SUCCESSFUL) {
			throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
					((PrismaResponseWrapper) result.getResult()).getError());
		}
	}

	public void userManagementEnableUserUserById(Long userAccountId, String auth_data) throws RestClientException,
			NoMappingModelFoundException, MappingException, ServerErrorResponseException {
		String URL = baseWSUrl + "/accounting/users/" + userAccountId + "/enable";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.putRequest(URL, headers, null, null, null,
				new PrismaRestResponseDecoder<String>(String.class), null);
		if (result.getStatus().getFamily() != Family.SUCCESSFUL) {
			throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
					((PrismaResponseWrapper) result.getResult()).getError());
		}
	}

	public WorkgroupMembershipRepresentation userManagementGetLastActiveWorkgroupByUserAccountId(Long userAccountId,
			String auth_data) throws RestClientException, NoMappingModelFoundException, MappingException,
			ServerErrorResponseException {
		String URL = baseWSUrl + "/accounting/users/" + userAccountId + "/lastActiveWorkgroup";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<WorkgroupMembershipRepresentation>(
						WorkgroupMembershipRepresentation.class), null);

		if (result.getStatus().getFamily() != Status.Family.SUCCESSFUL) {
			Error error = ((PrismaResponseWrapper) result.getResult()).getError();
			if (error.getErrorCode() == OrchestratorErrorCode.ORC_WG_NOT_FOUND.getCode()) {
				error.setErrorCode(AccountingErrorCode.ACC_WG_NOT_FOUND.getCode());
				throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(), error);
			} else {
				throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
						((PrismaResponseWrapper) result.getResult()).getError());
			}
		} else {
			if (result.getResult() == null) {
				return null;
			} else {
				return ((PrismaResponseWrapper<WorkgroupMembershipRepresentation>) result.getResult()).getResult();
			}
		}

	}

	public RetrieveUserProfileImageResponse userManagementRetrieveUserProfileImageFile(Long userAccountId,
			String auth_token) throws RestClientException, NoMappingModelFoundException, MappingException,
			ServerErrorResponseException {

		String URL = baseWSUrl + "/accounting/users/" + userAccountId + "/profileImage";

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_token);

		BaseRestResponseResult result = restClient
				.getRequest(URL, headers, new PrismaRestResponseDecoder<RetrieveUserProfileImageResponse>(
						RetrieveUserProfileImageResponse.class), null);

		if (result.getStatus().getFamily() != Family.SUCCESSFUL) {
			throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
					((PrismaResponseWrapper) result.getResult()).getError());
		} else {
			return ((PrismaResponseWrapper<RetrieveUserProfileImageResponse>) result.getResult()).getResult();
		}

	}

	public void userManagementUploadUserProfileImageFile(Long userAccountId, File file, String auth_token)
			throws JsonParseException, JsonMappingException, IOException, RestClientException,
			NoMappingModelFoundException, MappingException, ServerErrorResponseException {

		String URL = baseWSUrl + "/accounting/users/" + userAccountId + "/profileImage";

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_token);

		FormDataEntityBuilder formBuilder = new RestClientHelper.FormDataEntityBuilder();
		formBuilder.addFormData("file", file, RestClientHelper.FormDataEntityBuilder.MEDIA_TYPE, file.getName());
		formBuilder.addFormData("fileName", file.getName(), MediaType.TEXT_PLAIN_TYPE);

		GenericEntity<MultipartFormDataOutput> ge = formBuilder.build();

		BaseRestResponseResult result = restClient.putRequest(URL, headers, ge,
				RestClientHelper.FormDataEntityBuilder.MEDIA_TYPE, new PrismaRestResponseDecoder<String>(String.class),
				null);

		if (result.getStatus().getFamily() != Family.SUCCESSFUL) {
			throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
					((PrismaResponseWrapper) result.getResult()).getError());
		}

	}

	public void userManagementSetLastActiveWorkgroupByUserAccountId(Long userAccountId, Long workgroupId,
			String auth_data) throws RestClientException, NoMappingModelFoundException, MappingException,
			ServerErrorResponseException, JsonParseException, JsonMappingException, IOException {
		String URL = baseWSUrl + "/accounting/users/" + userAccountId + "/lastActiveWorkgroup";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(workgroupId);

		BaseRestResponseResult result = restClient.putRequest(URL, headers, ge,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE, new PrismaRestResponseDecoder<String>(String.class),
				null);

		if (result.getStatus().getFamily() != Family.SUCCESSFUL) {
			Error error = ((PrismaResponseWrapper) result.getResult()).getError();
			if (error.getErrorCode() == OrchestratorErrorCode.ORC_WG_NOT_FOUND.getCode()) {
				error.setErrorCode(AccountingErrorCode.ACC_WG_NOT_FOUND.getCode());
				throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(), error);
			} else {
				throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
						((PrismaResponseWrapper) result.getResult()).getError());
			}
		}
	}

	// START UserAccess
	public List<UserAccessRepresentation> getAllUserAccess(RestWSParamsContainer params, PrismaMetaData meta,
			String auth_data) throws RestClientException, ServerErrorResponseException, APIErrorException {

		try {

			String URL = baseWSUrl + "/accounting/users/access";

			PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

			BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers,
					addRestWSParamsToQueryParams(params), null, null, null,
					new PrismaRestResponseDecoder<List<UserAccessRepresentation>>(List.class,
							UserAccessRepresentation.class), null);

			return handleResult(result, meta);

		} catch (MappingException | NoMappingModelFoundException e) {
			throw new RestClientException(e);
		}

	}

	public List<UserAccessRepresentation> getUserAccessByUserId(Long userAccountId, RestWSParamsContainer params,
			PrismaMetaData meta, String auth_data) throws RestClientException, ServerErrorResponseException,
			APIErrorException {

		try {

			String URL = baseWSUrl + "/accounting/users/" + userAccountId + "/access";

			PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

			BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers,
					addRestWSParamsToQueryParams(params), null, null, null,
					new PrismaRestResponseDecoder<List<UserAccessRepresentation>>(List.class,
							UserAccessRepresentation.class), null);

			return handleResult(result, meta);

		} catch (MappingException | NoMappingModelFoundException e) {
			throw new RestClientException(e);
		}
	}

	public UserAccessRepresentation getLastUserAccessByUserId(Long userAccountId, String auth_data)
			throws RestClientException, ServerErrorResponseException, APIErrorException {

		try {

			String URL = baseWSUrl + "/accounting/users/" + userAccountId + "/access/last";

			PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

			BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers, null, null, null, null,
					new PrismaRestResponseDecoder<UserAccessRepresentation>(UserAccessRepresentation.class), null);

			return handleResult(result);

		} catch (MappingException | NoMappingModelFoundException e) {
			throw new RestClientException(e);
		}
	}

	// END UserAccess

	public UserRepresentation userManagementUpdateUser(UserRepresentation user, String auth_data)
			throws JsonParseException, JsonMappingException, IOException, RestClientException,
			NoMappingModelFoundException, MappingException, ServerErrorResponseException {

		String URL = baseWSUrl + "/accounting/users/" + user.getUserAccountId();

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(user);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.PUT, URL, headers, null, ge,
				MediaType.APPLICATION_JSON_TYPE, null, new PrismaRestResponseDecoder<UserRepresentation>(
						UserRepresentation.class), null);

		return handleResult(result);
	}

	// End: ACCOUNTING ****************************************** //

	public Boolean checkNameAvailable(Long workgroupId, Long zoneId, String name, String auth_data)
			throws MappingException, NoMappingModelFoundException, ServerErrorResponseException, APIErrorException,
			RestClientException, JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + "/zones/" + zoneId + "/name/available?name=" + name;

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers, null, null, null, null,
				new PrismaRestResponseDecoder<Boolean>(Boolean.class), null);

		return handleResult(result);
	}

	public Boolean checkDNSAvailable(Long workgroupId, String dn, String auth_data) throws MappingException,
			NoMappingModelFoundException, ServerErrorResponseException, APIErrorException, RestClientException,
			JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/misc/dns/available?dn=" + dn;

		MultivaluedMap<String, Object> queryParams = new MultivaluedHashMap<String, Object>();
		queryParams.add("dn", dn);

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers, queryParams, null, null,
				null, new PrismaRestResponseDecoder<Boolean>(Boolean.class), null);

		return handleResult(result);
	}

	// public Boolean chechDNDAvailable(String dnsName, String auth_data)
	// throws MappingException, NoMappingModelFoundException,
	// ServerErrorResponseException, APIErrorException,
	// RestClientException, JsonParseException, JsonMappingException,
	// IOException {
	//
	// String URL = baseWSUrl + "/misc/dns/" + dnsName + "/available";
	// PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(
	// auth_data);
	//
	// BaseRestResponseResult result = restClient.getRequest(URL, headers,
	// new PrismaRestResponseDecoder<Boolean>(Boolean.class), null);
	//
	// return handleResult(result);
	// }

	/**
	 * Consumes getLRTInfo API from Biz Layer.
	 * 
	 * @param lrtId
	 *            the id of the LRT to retrieve data about.
	 * @return the response of the API, an {@link LRTRepresentation}.
	 * @throws MappingException
	 *             if there
	 * @throws RestClientException
	 *             if there was an exception during the request.
	 * @throws APIErrorException
	 *             if the API reported an error.
	 * @throws ServerErrorResponseException
	 *             if the error responded with an error.
	 */

	public LRTRepresentation getLRTInfo(long lrtId, String auth_data) throws MappingException,
			NoMappingModelFoundException, ServerErrorResponseException, APIErrorException, RestClientException,
			JsonParseException, JsonMappingException, IOException {
		String URL = baseWSUrl + "/orc/info/lrtinfo/" + lrtId;
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<LRTRepresentation>(LRTRepresentation.class), null);
		if (result.getStatus() == Status.OK) {
			try {
				return ((PrismaResponseWrapper<LRTRepresentation>) result.getResult()).getResult();
			} catch (Exception e) {
				throw new MappingException("Unexpected response type.", null, result.getOriginalRestMessage());
			}
		} else {
			throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
					((PrismaResponseWrapper) result.getResult()).getError());
		}
	}

	/**
	 * Returns the list of all the {@link PrismaZoneRepresentation}s
	 * 
	 * @return
	 */
	public List<PrismaZoneRepresentation> getPrismaZones(RestWSParamsContainer params, String auth_data,
			PrismaMetaData meta) throws MappingException, NoMappingModelFoundException, ServerErrorResponseException,
			APIErrorException, RestClientException, JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/zones";

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers,
				addRestWSParamsToQueryParams(params), null, null, null,
				new PrismaRestResponseDecoder<List<PrismaZoneRepresentation>>(ArrayList.class,
						PrismaZoneRepresentation.class), null);

		return handleResult(result, meta);
	}

	/**
	 * Returns the requested {@link PrismaZoneRepresentation}
	 * 
	 * @return
	 */
	public PrismaZoneRepresentation getPrismaZoneById(Long id, String auth_data) throws MappingException,
			NoMappingModelFoundException, ServerErrorResponseException, APIErrorException, RestClientException,
			JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/zones/" + id;

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers, null, null, null, null,
				new PrismaRestResponseDecoder<PrismaZoneRepresentation>(PrismaZoneRepresentation.class), null);

		return handleResult(result);
	}

	/**
	 * Returns the list of all the {@link IaaSZoneRepresentation}s
	 * 
	 * @return
	 */
	public List<IaaSZoneRepresentation> getIaaSZones(RestWSParamsContainer params, String auth_data, PrismaMetaData meta)
			throws MappingException, NoMappingModelFoundException, ServerErrorResponseException, APIErrorException,
			RestClientException, JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/iaas/zones";

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers,
				addRestWSParamsToQueryParams(params), null, null, null,
				new PrismaRestResponseDecoder<List<IaaSZoneRepresentation>>(ArrayList.class,
						IaaSZoneRepresentation.class), null);

		return handleResult(result, meta);
	}

	/**
	 * Returns the requested {@link IaaSZoneRepresentation}
	 * 
	 * @return
	 */
	public IaaSZoneRepresentation getIaaSZoneById(Long id, String auth_data) throws MappingException,
			NoMappingModelFoundException, ServerErrorResponseException, APIErrorException, RestClientException,
			JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/iaas/zones/" + id;

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers, null, null, null, null,
				new PrismaRestResponseDecoder<IaaSZoneRepresentation>(IaaSZoneRepresentation.class), null);

		return handleResult(result);
	}

	/**
	 * Returns the list of all the {@link DNSZoneRepresentation}s
	 * 
	 * @return
	 */
	public List<DNSZoneRepresentation> getDNSZones(RestWSParamsContainer params, String auth_data, PrismaMetaData meta)
			throws MappingException, NoMappingModelFoundException, ServerErrorResponseException, APIErrorException,
			RestClientException, JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/zones/dns";

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient
				.doRequest(RestMethod.GET, URL, headers, addRestWSParamsToQueryParams(params), null, null, null,
						new PrismaRestResponseDecoder<List<DNSZoneRepresentation>>(ArrayList.class,
								DNSZoneRepresentation.class), null);

		return handleResult(result, meta);
	}

	/**
	 * Returns the requested {@link DNSZoneRepresentation}
	 * 
	 * @return
	 */
	public DNSZoneRepresentation getDNSZoneById(Long id, String auth_data) throws MappingException,
			NoMappingModelFoundException, ServerErrorResponseException, APIErrorException, RestClientException,
			JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/zones/dns/" + id;

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers, null, null, null, null,
				new PrismaRestResponseDecoder<DNSZoneRepresentation>(DNSZoneRepresentation.class), null);

		return handleResult(result);
	}

	/**
	 * Find all the {@link PrismaZone} associated with the given
	 * {@link Workgroup}
	 * 
	 * @param workgroupId
	 *            The id of the workgroup
	 * @return Returns the list of {@link PrismaZoneRepresentation}s
	 */
	public List<PrismaZoneRepresentation> getPrismaZones(final long workgroupId, RestWSParamsContainer params,
			String auth_data, PrismaMetaData meta) throws MappingException, NoMappingModelFoundException,
			ServerErrorResponseException, APIErrorException, RestClientException, JsonParseException,
			JsonMappingException, IOException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + "/zones";

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers,
				addRestWSParamsToQueryParams(params), null, null, null,
				new PrismaRestResponseDecoder<List<PrismaZoneRepresentation>>(ArrayList.class,
						PrismaZoneRepresentation.class), null);

		return handleResult(result, meta);
	}

	/**
	 * Returns the list of {@link IaaSAvailabilityZoneRepresentation}s
	 * 
	 * @return
	 */
	public Collection<IaaSAvailabilityZoneRepresentation> getIaaSAvailabilityZones(Long zoneId, String auth_data)
			throws MappingException, NoMappingModelFoundException, ServerErrorResponseException, APIErrorException,
			RestClientException, JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/zones/" + zoneId + "/availability-zones";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<List<IaaSAvailabilityZoneRepresentation>>(ArrayList.class,
						IaaSAvailabilityZoneRepresentation.class), null);

		return handleResult(result);
	}

	/**
	 * Returns the list of {@link IaaSFlavorRepresentation}s
	 * 
	 * @return
	 */
	public Collection<IaaSFlavorRepresentation> getIaaSFlavors(Long zoneId, String auth_data) throws MappingException,
			NoMappingModelFoundException, ServerErrorResponseException, APIErrorException, RestClientException,
			JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/zones/" + zoneId + "/iaas-flavors";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<List<IaaSFlavorRepresentation>>(ArrayList.class,
						IaaSFlavorRepresentation.class), null);

		return handleResult(result);
	}

	/**
	 * Returns the list of {@link IaaSQoSProfileRepresentation}s
	 * 
	 * @return
	 */
	public Collection<IaaSQoSProfileRepresentation> getIaaSQoSProfiles(Long zoneId, String auth_data)
			throws MappingException, NoMappingModelFoundException, ServerErrorResponseException, APIErrorException,
			RestClientException, JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/zones/" + zoneId + "/iaas-qos-profiles";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<List<IaaSQoSProfileRepresentation>>(ArrayList.class,
						IaaSQoSProfileRepresentation.class), null);

		return handleResult(result);
	}

	public WorkgroupQuota getWorkgroupQuota(Long workgroupId, Long zoneId, String auth_data)
			throws RestClientException, ServerErrorResponseException {
		try {
			String URL = baseWSUrl + "/workgroups/" + workgroupId + "/zones/" + zoneId + "/quotas";
			PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

			BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers, null, null, null, null,
					new PrismaRestResponseDecoder<WorkgroupQuota>(WorkgroupQuota.class), null);

			return handleResult(result);
		} catch (NoMappingModelFoundException | MappingException e) {
			throw new RestClientException("Error reading WorkgroupQuota", e);
		}
	}

	/**
	 * Consumes getPlatformConfiguration API from Biz Layer.
	 * 
	 * @param request
	 *            the request containing the list of key to retrieve.
	 * @return the response of the API, a list {@link PlatformConfiguration} if
	 *         no error occurred.
	 * @throws MappingException
	 *             if there
	 * @throws RestClientException
	 *             if there was an exception during the request.
	 * @throws APIErrorException
	 *             if the API reported an error.
	 * @throws ServerErrorResponseException
	 *             if the error responded with an error.
	 */

	public PlatformConfigurations getPlatformConfiguration(String[] request, String auth_data) throws MappingException,
			NoMappingModelFoundException, ServerErrorResponseException, APIErrorException, RestClientException,
			JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/config/platformconfig";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);

		BaseRestResponseResult result = restClient.postRequest(URL, headers, ge,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE, new PrismaRestResponseDecoder<PlatformConfigurations>(
						PlatformConfigurations.class), null);

		return handleResult(result);
	}

	public PlatformConfigurations getAllPlatformConfiguration(String auth_data) throws MappingException,
			NoMappingModelFoundException, ServerErrorResponseException, APIErrorException, RestClientException,
			JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/config/allplatformconfig";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<PlatformConfigurations>(PlatformConfigurations.class), null);

		return handleResult(result);
	}

	public List<APPaaSRepresentation> getAllAPPaaS(Long zoneId, Long workgroupId, RestWSParamsContainer params,
			PrismaMetaData meta, String auth_data) throws RestClientException, NoMappingModelFoundException,
			MappingException, ServerErrorResponseException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + addZone(zoneId) + "/paas/appaas/";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);
		// selectZone(zoneId, headers);

		BaseRestResponseResult result = restClient
				.doRequest(RestMethod.GET, URL, headers, addRestWSParamsToQueryParams(params), null, null, null,
						new PrismaRestResponseDecoder<List<APPaaSRepresentation>>(List.class,
								APPaaSRepresentation.class), null);

		return handleResult(result, meta);
	}

	public APPaaSRepresentation getAPPaaS(Long workgroupId, Long zoneId, Long appaasId, String auth_data)
			throws MappingException, NoMappingModelFoundException, ServerErrorResponseException, APIErrorException,
			RestClientException, JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + addZone(zoneId) + "/paas/appaas/" + appaasId;

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<APPaaSRepresentation>(APPaaSRepresentation.class), null);

		return handleResult(result);
	}

	public APPaaSRepresentation updateAPPaaS(Long workgroupId, Long zoneId, Long appaasId, APPaaSRepresentation app,
			String auth_data) throws RestClientException, NoMappingModelFoundException, MappingException,
			ServerErrorResponseException, JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + addZone(zoneId) + "/paas/appaas/" + appaasId;

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(app);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.POST, URL, headers, null, ge,
				MediaType.APPLICATION_JSON_TYPE, null, new PrismaRestResponseDecoder<APPaaSRepresentation>(
						APPaaSRepresentation.class), null);

		return handleResult(result);
	}

	public List<APPaaSEnvironmentRepresentation> getAllAPPaaSEnv(Long zoneId, Long workgroupId, Long appaasId,
			RestWSParamsContainer params, PrismaMetaData meta, String auth_data) throws MappingException,
			NoMappingModelFoundException, ServerErrorResponseException, APIErrorException, RestClientException,
			JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + addZone(zoneId) + "/paas/appaas/" + appaasId
				+ "/environments";

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);
		// selectZone(zoneId, headers);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers,
				addRestWSParamsToQueryParams(params), null, null, null,
				new PrismaRestResponseDecoder<List<APPaaSEnvironmentRepresentation>>(ArrayList.class,
						APPaaSEnvironmentRepresentation.class), null);

		return handleResult(result, meta);

	}

	public APPaaSEnvironmentRepresentation getAPPaaSEnv(Long workgroupId, Long zoneId, Long appId, Long envId,
			String auth_data) throws MappingException, NoMappingModelFoundException, ServerErrorResponseException,
			APIErrorException, RestClientException, JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + addZone(zoneId) + "/paas/appaas/" + appId
				+ "/environments/" + envId;

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<APPaaSEnvironmentRepresentation>(APPaaSEnvironmentRepresentation.class),
				null);

		return handleResult(result);
	}

	public List<VirtualMachineRepresentation> getVirtualMachines(final Long workgroupId, Long zoneId, final Long appId,
			final Long envId, String auth_data) throws RestClientException, NoMappingModelFoundException,
			MappingException, ServerErrorResponseException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + addZone(zoneId) + "/paas/appaas/" + appId
				+ "/environments/" + envId + "/vms";

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers, null, null, null, null,
				new PrismaRestResponseDecoder<List<VirtualMachineRepresentation>>(List.class,
						VirtualMachineRepresentation.class), null);

		return handleResult(result);
	}

	public List<PaaSServiceEventRepresentation> getAPPaaSEnvEvents(Long workgroupId, Long zoneId, Long appId,
			Long envId, String auth_data, RestWSParamsContainer params, PrismaMetaData meta) throws MappingException,
			NoMappingModelFoundException, ServerErrorResponseException, APIErrorException, RestClientException,
			JsonParseException, JsonMappingException, IOException {

		return getPaaSServiceEvents(workgroupId, zoneId, "paas", "appaas/" + appId + "/environments", envId, params,
				auth_data, meta);
	}

	/**
	 * 
	 * @param request
	 *            , mandatory params:
	 * 
	 *            <pre>
	 * {
	 *  "userId":1,
	 *  "tag":"tag2",
	 *  "appName":"appName",
	 *  "appDescription":"appDescription",
	 *  "fileName":"kunagi.war"
	 * }
	 * </pre>
	 * @param file
	 * @param auth_data
	 * @return
	 */
	public AddAppSrcFileResponse uploadAppSRCFile(AddAppSrcFileRequest request, File file, boolean isPublic,
			Long workgroupId, Long zoneId, String auth_data) throws MappingException, NoMappingModelFoundException,
			ServerErrorResponseException, APIErrorException, RestClientException, JsonParseException,
			JsonMappingException, IOException {

		String URL = baseWSUrl;
		if (!isPublic) {
			if (workgroupId == null || zoneId == null) {
				throw new IllegalArgumentException("workgroupId and zoneId must not be null");
			}
			URL += "/workgroups/" + workgroupId + addZone(zoneId);
		}
		URL += "/apprepo/appsrcfiles";

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		GenericEntity<MultipartFormDataOutput> ge = new RestClientHelper.FormDataEntityBuilder()
				.addFormData("file", file, RestClientHelper.FormDataEntityBuilder.MEDIA_TYPE, file.getName())
				.addFormData("requestJSON", JsonUtility.serializeJson(request), MediaType.APPLICATION_JSON_TYPE)
				.build();

		BaseRestResponseResult result = restClient.putRequest(URL, headers, ge,
				RestClientHelper.FormDataEntityBuilder.MEDIA_TYPE,
				new PrismaRestResponseDecoder<AddAppSrcFileResponse>(AddAppSrcFileResponse.class), null);

		if (result.getStatus() == Status.OK) {
			try {
				return ((PrismaResponseWrapper<AddAppSrcFileResponse>) result.getResult()).getResult();
			} catch (Exception e) {
				throw new MappingException("Unexpected response type.", null, result.getOriginalRestMessage());
			}
		} else {
			Error error = ((PrismaResponseWrapper) result.getResult()).getError();
			if (error.getErrorCode() == OrchestratorErrorCode.ORC_NOT_AUTHORIZED.getCode()) {
				throw new UnauthorizedException(error.getErrorMsg());
			} else {
				throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
						((PrismaResponseWrapper) result.getResult()).getError());
			}
		}
	}

	/**
	 * 
	 * @param request
	 *            , mandatory params:
	 * 
	 *            <pre>
	 * {
	 *  "userId":1,
	 *  "tag":"tag2",
	 *  "appName":"appName",
	 *  "appDescription":"appDescription",
	 *  "fileName":"kunagi.war"
	 *  "url":URL
	 * }
	 * </pre>
	 * @param auth_data
	 * @return
	 */
	public AddAppSrcFileResponse uploadAppSRCFileFromURL(AddAppSrcFileRequest request, boolean isPublic,
			Long workgroupId, Long zoneId, String auth_data) throws MappingException, NoMappingModelFoundException,
			ServerErrorResponseException, APIErrorException, RestClientException, JsonParseException,
			JsonMappingException, IOException {

		String URL = baseWSUrl;
		if (!isPublic) {
			if (workgroupId == null || zoneId == null) {
				throw new IllegalArgumentException("workgroupId and zoneId must not be null");
			}
			URL += "/workgroups/" + workgroupId + addZone(zoneId);
		}
		URL += "/apprepo/appsrcfiles/url";

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.PUT, URL, headers, null, ge,
				MediaType.APPLICATION_JSON_TYPE, null, new PrismaRestResponseDecoder<AddAppSrcFileResponse>(
						AddAppSrcFileResponse.class), null);

		if (result.getStatus() == Status.OK) {
			try {
				return ((PrismaResponseWrapper<AddAppSrcFileResponse>) result.getResult()).getResult();
			} catch (Exception e) {
				throw new MappingException("Unexpected response type.", null, result.getOriginalRestMessage());
			}
		} else if (result.getStatus() == Status.CONFLICT) {
			throw new APIErrorException("CONFLICT", null, result.getOriginalRestMessage(),
					((PrismaResponseWrapper) result.getResult()).getError());

		} else {
			Error error = ((PrismaResponseWrapper) result.getResult()).getError();
			if (error.getErrorCode() == OrchestratorErrorCode.ORC_NOT_AUTHORIZED.getCode()) {
				throw new UnauthorizedException(error.getErrorMsg());
			} else {
				throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
						((PrismaResponseWrapper) result.getResult()).getError());
			}
		}
	}

	public List<ApplicationRepositoryEntryRepresentation> getPublicAppSrcFiles(RestWSParamsContainer params,
			PrismaMetaData meta, String auth_data) throws Exception {

		String URL = baseWSUrl + "/apprepo/appsrcfiles";

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers,
				addRestWSParamsToQueryParams(params), null, null, null,
				new PrismaRestResponseDecoder<List<ApplicationRepositoryEntryRepresentation>>(ArrayList.class,
						ApplicationRepositoryEntryRepresentation.class), null);

		return handleResult(result, meta);
	}

	public List<ApplicationRepositoryEntryRepresentation> getPrivateAppSrcFiles(Long workgroupId, Long zoneId,
			RestWSParamsContainer params, PrismaMetaData meta, String auth_data) throws Exception {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + addZone(zoneId) + "/apprepo/appsrcfiles";

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers,
				addRestWSParamsToQueryParams(params), null, null, null,
				new PrismaRestResponseDecoder<List<ApplicationRepositoryEntryRepresentation>>(ArrayList.class,
						ApplicationRepositoryEntryRepresentation.class), null);

		return handleResult(result, meta);
	}

	/**
	 * Creates (and deploys to the IaaS if needed) the given PaaSService with
	 * related data.
	 * 
	 * @param deployRequest
	 * @param representationClass
	 *            the class of the {@link GenericPaaSServiceRepresentation} for
	 *            the response mapping
	 * @return
	 */
	public <SERVICE_REPRESENTATION_TYPE extends GenericPaaSServiceRepresentation> SERVICE_REPRESENTATION_TYPE deployPaaSService(
			Long zoneId, GenericPaaSServiceDeployRequest<SERVICE_REPRESENTATION_TYPE> request,
			Class<SERVICE_REPRESENTATION_TYPE> representationClass, String auth_data) throws Exception {

		String servicePath = "";
		if (request instanceof APPaaSDeployRequest) {
			servicePath = "/paas/appaas/";
		} else if (request instanceof APPaaSEnvironmentDeployRequest) {
			servicePath = "/paas/appaas/"
					+ ((APPaaSEnvironmentDeployRequest) request).getEnvironmentDetails().getAppId() + "/environments/";
		} else if (request instanceof BIaaSDeployRequest) {
			servicePath = "/paas/biaas/";
		} else if (request instanceof DBaaSDeployRequest) {
			servicePath = "/paas/dbaas/";
		} else if (request instanceof MQaaSDeployRequest) {
			servicePath = "/paas/mqaas/";
		} else if (request instanceof BPMaaSDeployRequest) {
			servicePath = "/paas/bpmaas/";
		} else if (request instanceof ODaaSDeployRequest) {
			servicePath = "/paas/odaas/";
		} else if (request instanceof VMDeployRequest) {
			servicePath = "/iaas/vmaas/";
		} else {
			throw new Exception("Class type <" + request.getClass() + "> not found");
		}

		String URL = baseWSUrl + "/workgroups/" + request.getAccount().getWorkgroupId() + addZone(zoneId) + servicePath;
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);
		// selectZone(zoneId, headers);
		request.setZoneId(zoneId);

		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);

		BaseRestResponseResult result = restClient.putRequest(URL, headers, ge,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE,
				new PrismaRestResponseDecoder<SERVICE_REPRESENTATION_TYPE>(representationClass), null);

		return handleResult(result);
	}

	public <SERVICE_REPRESENTATION_TYPE extends GenericPaaSServiceRepresentation> List<SERVICE_REPRESENTATION_TYPE> getAllServiceRepresentation(
			Long zoneId, Long workgroupId, Long appId, RestWSParamsContainer params, PrismaMetaData meta,
			Class<SERVICE_REPRESENTATION_TYPE> representationClass, String auth_data) throws Exception {

		String servicePath = getServicePathByClass(representationClass, appId);

		String URL = baseWSUrl + "/workgroups/" + workgroupId + addZone(zoneId) + servicePath;

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);
		// selectZone(zoneId, headers);

		BaseRestResponseResult result = restClient
				.doRequest(RestMethod.GET, URL, headers, addRestWSParamsToQueryParams(params), null, null, null,
						new PrismaRestResponseDecoder<List<SERVICE_REPRESENTATION_TYPE>>(List.class,
								representationClass), null);

		return handleResult(result, meta);
	}

	public <SERVICE_REPRESENTATION_TYPE extends GenericPaaSServiceRepresentation> SERVICE_REPRESENTATION_TYPE getServiceRepresentation(
			Long workgroupId, Long zoneId, Long appId, Long serviceId,
			Class<SERVICE_REPRESENTATION_TYPE> representationClass, String auth_data) throws Exception {

		String servicePath = getServicePathByClass(representationClass, appId);
		String URL = baseWSUrl + "/workgroups/" + workgroupId + addZone(zoneId) + servicePath + serviceId;

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers, null, null, null, null,
				new PrismaRestResponseDecoder<SERVICE_REPRESENTATION_TYPE>(representationClass), null);

		return handleResult(result);
	}

	public <E extends GenericPaaSServiceRepresentation> E getGenericServiceRepresentation(Long workgroupId,
			Long zoneId, Long appId, Long serviceId, Class<E> serviceClass, String auth_data) throws Exception {

		String servicePath = getServicePathByClass(serviceClass, appId);
		String URL = baseWSUrl + "/workgroups/" + workgroupId + addZone(zoneId) + servicePath + serviceId;

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers, null, null, null, null,
				new PrismaRestResponseDecoder<E>(serviceClass), null);

		return handleResult(result);
	}

	public List<VirtualMachineRepresentation> getGenericServiceVMs(Long workgroupId, Long zoneId, Long appId,
			Long serviceId, Class<?> serviceClass, String auth_data) throws Exception {

		String servicePath = getServicePathByClass(serviceClass, appId);
		String URL = baseWSUrl + "/workgroups/" + workgroupId + addZone(zoneId) + servicePath + serviceId + "/vms";

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers, null, null, null, null,
				new PrismaRestResponseDecoder<List<VirtualMachineRepresentation>>(List.class,
						VirtualMachineRepresentation.class), null);

		return handleResult(result);
	}

	public List<DeployablePaaSServiceActionRepresentation> getGenericServiceActions(Long workgroupId, Long zoneId,
			Long appId, Long serviceId, Class<?> serviceClass, RestWSParamsContainer params, PrismaMetaData meta,
			String auth_data) throws Exception {

		String servicePath = getServicePathByClass(serviceClass, appId);
		String URL = baseWSUrl + "/workgroups/" + workgroupId + addZone(zoneId) + servicePath + serviceId + "/actions";

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers,
				addRestWSParamsToQueryParams(params), null, null, null,
				new PrismaRestResponseDecoder<List<AuditingActionRepresentation>>(List.class,
						AuditingActionRepresentation.class), null);

		return handleResult(result, meta);
	}

	public List<AuditingActionRepresentation> getWorkgroupActions(Long workgroupId, RestWSParamsContainer params,
			PrismaMetaData meta, String auth_data) throws Exception {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + "/auditing-actions";

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers,
				addRestWSParamsToQueryParams(params), null, null, null,
				new PrismaRestResponseDecoder<List<AuditingActionRepresentation>>(List.class,
						AuditingActionRepresentation.class), null);

		return handleResult(result, meta);
	}

	/**
	 * MUST be Admin.
	 * 
	 * @param workgroupId
	 * @param params
	 * @param meta
	 * @param auth_data
	 * @return
	 * @throws Exception
	 */
	public List<AuditingActionRepresentation> getAllActions(RestWSParamsContainer params, PrismaMetaData meta,
			String auth_data) throws Exception {

		String URL = baseWSUrl + "/auditing/auditing-actions";

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers,
				addRestWSParamsToQueryParams(params), null, null, null,
				new PrismaRestResponseDecoder<List<AuditingActionRepresentation>>(List.class,
						AuditingActionRepresentation.class), null);

		return handleResult(result, meta);
	}

	/**
	 * Returns the list of the events associated with the given PaaSService
	 * 
	 * @param workgroupId
	 * @param serviceType
	 *            the type of the PaaS Service.
	 * @param serviceId
	 *            the Id of the PaaS Service.
	 * @return
	 */
	public List<PaaSServiceEventRepresentation> getPaaSServiceEvents(Long workgroupId, Long zoneId, String service,
			String serviceType, Long serviceId, RestWSParamsContainer params, String auth_data, PrismaMetaData meta)
			throws MappingException, NoMappingModelFoundException, ServerErrorResponseException, APIErrorException,
			RestClientException, JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + addZone(zoneId) + "/" + service + "/"
				+ serviceType.toLowerCase() + "/" + serviceId + "/events";

		return getEvents(URL, addRestWSParamsToQueryParams(params), meta, auth_data);
	}

	// public List<PaaSServiceEventRepresentation> getPaaSSAppEnvEvents(
	// String workgroupId, String appId, String id, String auth_data,
	// PrismaMetaData meta)
	// throws MappingException, NoMappingModelFoundException,
	// ServerErrorResponseException, APIErrorException,
	// RestClientException, JsonParseException, JsonMappingException,
	// IOException {
	//
	// String URL = baseWSUrl + "/workgroups/" + workgroupId + "/paas/appaas/"
	// + appId + "/environments/" + id + "/events";
	//
	// MultivaluedMap<String, Object> queryParams=new MultivaluedHashMap<String,
	// Object>();
	//
	// return getEvents(URL,queryParams, meta);
	// }

	private List<PaaSServiceEventRepresentation> getEvents(String URL, MultivaluedMap<String, Object> queryParams,
			PrismaMetaData meta, String auth_data) throws MappingException, NoMappingModelFoundException,
			ServerErrorResponseException, APIErrorException, RestClientException, JsonParseException,
			JsonMappingException, IOException {

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers, queryParams, null, null,
				null, new PrismaRestResponseDecoder<List<PaaSServiceEventRepresentation>>(ArrayList.class,
						PaaSServiceEventRepresentation.class), null);

		return handleResult(result, meta);

	}

	public List<PaaSServiceEventRepresentation> getMainEvents(Long workgroupId, Long zoneId, PrismaMetaData meta,
			RestWSParamsContainer params, String auth_data) throws RestClientException, NoMappingModelFoundException,
			MappingException, ServerErrorResponseException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + "/zones/" + zoneId + "/events";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers,
				addRestWSParamsToQueryParams(params), null, null, null,
				new PrismaRestResponseDecoder<List<PaaSServiceEventRepresentation>>(ArrayList.class,
						PaaSServiceEventRepresentation.class), null);

		return handleResult(result, meta);

	}

	/**
	 * 
	 * Start or stop a VM
	 * 
	 * @param workgroup
	 *            the id of the workgroup
	 * @param serviceType
	 *            cloud be paas or iaas
	 * @param service
	 *            the service: dbaas, biaas....
	 * @param id
	 *            id of the service
	 * @param action
	 *            cloud be start or stop
	 * 
	 * @throws MappingException
	 * @throws NoMappingModelFoundException
	 * @throws ServerErrorResponseException
	 * @throws APIErrorException
	 * @throws RestClientException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */
	public boolean startAndStopVM(Long workgroup, Long zoneId, String serviceType, String service, String id,
			String action, String auth_data) throws MappingException, NoMappingModelFoundException,
			ServerErrorResponseException, APIErrorException, RestClientException, JsonParseException,
			JsonMappingException, IOException {

		String URL = baseWSUrl + "/workgroups/" + workgroup + addZone(zoneId) + "/" + serviceType + "/" + service + "/"
				+ id + "/" + action;

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		RestMessage result = restClient.doRequest(RestMethod.POST, URL, headers, null, null, null, null);
		if (Status.fromStatusCode(result.getHttpStatusCode()).getFamily() == Status.ACCEPTED.getFamily()) {
			return true;
		} else {
			BaseRestResponseResult errorResult = null;
			try {
				errorResult = new PrismaRestResponseDecoder<Void>(Void.class).decode(result);
			} catch (Exception e) {
				throw new RestClientException((String) result.getBody());
			}

			Error error = ((PrismaResponseWrapper) errorResult.getResult()).getError();
			if (error.getErrorCode() == OrchestratorErrorCode.ORC_NOT_AUTHORIZED.getCode()) {
				throw new UnauthorizedException(error.getErrorMsg());
			} else {
				throw new APIErrorException("API_ERROR", null, errorResult.getOriginalRestMessage(),
						((PrismaResponseWrapper) errorResult.getResult()).getError());
			}
		}
	}

	public void undeployPaaSService(GenericPaaSServiceRepresentation paaSServiceRepresentation, String auth_data)
			throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException,
			IllegalArgumentException, APIErrorException {

		if (paaSServiceRepresentation.getId() == null || paaSServiceRepresentation.getWorkgroupId() == null) {
			throw new IllegalArgumentException("PaaS_ID and Workgroup_ID must not be null");
		}
		StringBuilder URL = new StringBuilder();
		URL.append(baseWSUrl).append("/workgroups/").append(paaSServiceRepresentation.getWorkgroupId())
				.append(addZone(paaSServiceRepresentation.getZoneId()));

		getServicePathByClass(paaSServiceRepresentation.getClass(), null);

		if (paaSServiceRepresentation instanceof APPaaSEnvironmentRepresentation) {
			Long APPaaSid = ((APPaaSEnvironmentRepresentation) paaSServiceRepresentation).getAppaasId();
			if (APPaaSid == null) {
				throw new IllegalArgumentException("APPaaS_ID must not be null");
			}
			URL.append("/paas/appaas/").append(APPaaSid).append("/environments/");
		} else if (paaSServiceRepresentation instanceof MQaaSRepresentation) {
			URL.append("/paas/mqaas/");
		} else if (paaSServiceRepresentation instanceof BPMaaSRepresentation) {
			URL.append("/paas/bpmaas/");
		} else if (paaSServiceRepresentation instanceof ODaaSRepresentation) {
			URL.append("/paas/odaas/");
		} else if (paaSServiceRepresentation instanceof DBaaSRepresentation) {
			URL.append("/paas/dbaas/");
		} else if (paaSServiceRepresentation instanceof BIaaSRepresentation) {
			URL.append("/paas/biaas/");
		} else if (paaSServiceRepresentation instanceof VMRepresentation) {
			URL.append("/iaas/vmaas/");
		} else if (paaSServiceRepresentation instanceof APPaaSRepresentation) {
			URL.append("/paas/appaas/");
		} else {
			throw new IllegalArgumentException("PaaSService type not supported: "
					+ paaSServiceRepresentation.getClass().getCanonicalName() + ".");
		}
		URL.append(paaSServiceRepresentation.getId());

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		RestMessage result = restClient.doRequest(RestMethod.DELETE, URL.toString(), headers, null, null, null, null);
		if (Status.fromStatusCode(result.getHttpStatusCode()).getFamily() == Status.ACCEPTED.getFamily()) {
			return;
		} else {
			BaseRestResponseResult errorResult = null;
			try {
				errorResult = new PrismaRestResponseDecoder<Void>(Void.class).decode(result);
			} catch (Exception e) {
				throw new RestClientException((String) result.getBody());
			}
			Error error = ((PrismaResponseWrapper) errorResult.getResult()).getError();
			if (error.getErrorCode() == OrchestratorErrorCode.ORC_NOT_AUTHORIZED.getCode()) {
				throw new UnauthorizedException(error.getErrorMsg());
			} else {
				throw new APIErrorException("API_ERROR", null, errorResult.getOriginalRestMessage(),
						((PrismaResponseWrapper) errorResult.getResult()).getError());
			}
		}
	}

	/**************************************************
	 * CA *
	 **************************************************/

	/**
	 * 
	 * @param username
	 * @param subjectDN
	 * @param auth_data
	 * @return
	 * @throws MappingException
	 * @throws NoMappingModelFoundException
	 * @throws ServerErrorResponseException
	 * @throws APIErrorException
	 * @throws RestClientException
	 * @throws JsonParseException
	 * @throws JsonMappingException
	 * @throws IOException
	 */

	public String caCreateCertificate(Long workgroupId, String subjectDN, String auth_data) throws MappingException,
			NoMappingModelFoundException, ServerErrorResponseException, APIErrorException, RestClientException,
			JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + "/paas/caaas/" + subjectDN;
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.putRequest(URL, headers, null,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE, new PrismaRestResponseDecoder<String>(String.class),
				null);

		return handleResult(result);
	}

	public String caRevokeCertificate(Long workgroupId, String serialNumber, int reason, String auth_data)
			throws MappingException, NoMappingModelFoundException, ServerErrorResponseException, APIErrorException,
			RestClientException, JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + "/paas/caaas/" + serialNumber + "/revoke/" + reason;

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.postRequest(URL, headers, null,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE, new PrismaRestResponseDecoder<String>(String.class),
				null);

		return handleResult(result);
	}

	public String caGetCertificateBySerial(Long workgroupId, String serialNumber, String auth_data)
			throws MappingException, NoMappingModelFoundException, ServerErrorResponseException, APIErrorException,
			RestClientException, JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + "/paas/caaas/" + serialNumber;

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers, new PrismaRestResponseDecoder<String>(
				String.class), null);

		return handleResult(result);
	}

	public List<CertificateInfoRepresentation> caGetAllCertificates(Long workgroupId, RestWSParamsContainer params,
			PrismaMetaData meta, String auth_data) throws MappingException, NoMappingModelFoundException,
			ServerErrorResponseException, APIErrorException, RestClientException, JsonParseException,
			JsonMappingException, IOException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + "/paas/caaas/";

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers,
				addRestWSParamsToQueryParams(params), null, null, null,
				new PrismaRestResponseDecoder<List<CertificateInfoRepresentation>>(List.class,
						CertificateInfoRepresentation.class), null);

		return handleResult(result, meta);
	}

	public String caDownloadCertificate(Long workgroupId, String serialNumber, String auth_data)
			throws MappingException, NoMappingModelFoundException, ServerErrorResponseException, APIErrorException,
			RestClientException, JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + "/paas/caaas/" + serialNumber + "/download";

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers, new PrismaRestResponseDecoder<String>(
				String.class), null);

		return handleResult(result);
	}

	/**************************************************
	 * SMS *
	 **************************************************/

	public IsActiveStatus getStatus(Long workgroupId, String auth_data) throws MappingException,
			NoMappingModelFoundException, ServerErrorResponseException, APIErrorException, RestClientException,
			JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + "/paas/smsaas/status";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<IsActiveStatus>(IsActiveStatus.class), null);

		return handleResult(result);
	}

	public SMSNotificationsRepresentation smsGetNotifications(Long workgroupId, String auth_data)
			throws MappingException, NoMappingModelFoundException, ServerErrorResponseException, APIErrorException,
			RestClientException, JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + "/paas/smsaas/getNotify";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<SMSNotificationsRepresentation>(SMSNotificationsRepresentation.class),
				null);

		return handleResult(result);
	}

	public Boolean setStatus(Long workgroupId, IsActiveStatus status, String auth_data) throws MappingException,
			NoMappingModelFoundException, ServerErrorResponseException, APIErrorException, RestClientException,
			JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + "/paas/smsaas/status";

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);
		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(status);
		BaseRestResponseResult result = restClient.postRequest(URL, headers, ge,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE, new PrismaRestResponseDecoder<Boolean>(Boolean.class),
				null);

		return handleResult(result);
	}

	public BigDecimal smsGetCredit(Long workgroupId, String auth_data) throws MappingException,
			NoMappingModelFoundException, ServerErrorResponseException, APIErrorException, RestClientException,
			JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + "/paas/smsaas/billing";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers, new PrismaRestResponseDecoder<BigDecimal>(
				BigDecimal.class), null);

		return handleResult(result);
	}

	public History getSMSHistory(Long workgroupId, boolean filter, String filterData, int limit, int offset,
			String auth_data) throws MappingException, NoMappingModelFoundException, ServerErrorResponseException,
			APIErrorException, RestClientException, JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + "/paas/smsaas/sms/" + filter + "/" + filterData
				+ "?limit=" + limit + "&offset=" + offset;

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);
		headers.putSingle("X-Workgroup-Id", workgroupId);

		BaseRestResponseResult result = restClient.getRequest(URL, headers, new PrismaRestResponseDecoder<History>(
				History.class), null);

		return handleResult(result);
	}

	public SMSNotificationsRepresentation smsSetDailyNotifications(SMSNotificationsRepresentation request,
			Long workgroupId, String auth_data) throws MappingException, NoMappingModelFoundException,
			ServerErrorResponseException, APIErrorException, RestClientException, JsonParseException,
			JsonMappingException, IOException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + "/paas/smsaas/setDailyNotifications";

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);

		BaseRestResponseResult result = restClient.putRequest(URL, headers, ge,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE, new PrismaRestResponseDecoder<String>(String.class),
				null);

		if (result.getStatus() == Status.OK) {
			try {

				return smsGetNotifications(workgroupId, auth_data);

			} catch (Exception e) {
				throw new MappingException("Unexpected response type.", null, result.getOriginalRestMessage());
			}
		} else {
			Error error = ((PrismaResponseWrapper) result.getResult()).getError();
			if (error.getErrorCode() == OrchestratorErrorCode.ORC_NOT_AUTHORIZED.getCode()) {
				throw new UnauthorizedException(error.getErrorMsg());
			} else {
				throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
						((PrismaResponseWrapper) result.getResult()).getError());
			}
		}
	}

	public SMSNotificationsRepresentation smsSetMonthlyNotifications(SMSNotificationsRepresentation request,
			Long workgroupId, String auth_data) throws MappingException, NoMappingModelFoundException,
			ServerErrorResponseException, APIErrorException, RestClientException, JsonParseException,
			JsonMappingException, IOException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + "/paas/smsaas/setMonthlyNotifications";

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);

		BaseRestResponseResult result = restClient.putRequest(URL, headers, ge,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE, new PrismaRestResponseDecoder<String>(String.class),
				null);

		if (result.getStatus() == Status.OK) {
			try {

				return smsGetNotifications(workgroupId, auth_data);

			} catch (Exception e) {
				throw new MappingException("Unexpected response type.", null, result.getOriginalRestMessage());
			}
		} else {
			Error error = ((PrismaResponseWrapper) result.getResult()).getError();
			if (error.getErrorCode() == OrchestratorErrorCode.ORC_NOT_AUTHORIZED.getCode()) {
				throw new UnauthorizedException(error.getErrorMsg());
			} else {
				throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
						((PrismaResponseWrapper) result.getResult()).getError());
			}
		}
	}

	/**************************************************
	 * EMAIL *
	 **************************************************/

	public Status emailGetEmailAccount(String email, Long workgroupId, String auth_data) throws MappingException,
			NoMappingModelFoundException, ServerErrorResponseException, APIErrorException, RestClientException,
			JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + "/paas/emailaas/accounts/" + email;
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers, new PrismaRestResponseDecoder<Status>(
				Status.class), null);

		return handleResult(result);
	}

	// public boolean isListAccountVoid(Long workgroupId, String auth_data)
	// throws MappingException, NoMappingModelFoundException,
	// ServerErrorResponseException, APIErrorException,
	// RestClientException, JsonParseException, JsonMappingException,
	// IOException {
	//
	// String URL = baseWSUrl + "/EMAILaaS/isListAccountVoid/" + workgroupId;
	// PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(
	// auth_data);
	//
	// BaseRestResponseResult result = restClient.getRequest(URL, headers,
	// new PrismaRestResponseDecoder<Boolean>(Boolean.class), null);
	//
	// return handleResult(result);
	// }

	public List<RowEmail> emailGetAllEmailAccounts(Long workgroupId, RestWSParamsContainer params, PrismaMetaData meta,
			String auth_data) throws Exception {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + "/paas/emailaas/accounts/";

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<List<RowEmail>>(ArrayList.class, RowEmail.class), null);

		return handleResult(result, meta);

	}

	public List<EmailDomains> emailGetAllEmailDomains(Long workgroupId, String auth_data) throws MappingException,
			NoMappingModelFoundException, ServerErrorResponseException, APIErrorException, RestClientException,
			JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + "/paas/emailaas/domains";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<List<EmailDomains>>(List.class, EmailDomains.class), null);

		return handleResult(result);
	}

	public boolean emailChangeEmailAccountPassword(Long workgroupId, String accountFullAddress, Credential credential,
			String auth_data) throws MappingException,

	NoMappingModelFoundException, ServerErrorResponseException, APIErrorException, RestClientException,
			JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + "/paas/emailaas/accounts/" + accountFullAddress
				+ "/password";

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(credential);

		BaseRestResponseResult result = restClient.putRequest(URL, headers, ge,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE, new PrismaRestResponseDecoder<Boolean>(Boolean.class),
				null);

		return handleResult(result);
	}

	public boolean emailDeleteEmailAccount(Long workgroupId, String accountFullAddress, String auth_data)
			throws MappingException, NoMappingModelFoundException, ServerErrorResponseException, APIErrorException,
			RestClientException, JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + "/paas/emailaas/accounts/" + accountFullAddress;

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.deleteRequest(URL, headers, null,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE, new PrismaRestResponseDecoder<Boolean>(Boolean.class),
				null);
		return handleResult(result);
	}

	public Status emailCreateEmailAccount(Long workgroupId, EmailInfoRepresentation request, String auth_data)
			throws MappingException, NoMappingModelFoundException, ServerErrorResponseException, APIErrorException,
			RestClientException, JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + "/paas/emailaas/accounts";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);
		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(request);
		BaseRestResponseResult result = restClient.putRequest(URL, headers, ge,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE, new PrismaRestResponseDecoder<String>(String.class),
				null);

		if (result.getStatus() == Status.OK) {
			try {
				return emailGetEmailAccount(((PrismaResponseWrapper<String>) result.getResult()).getResult(),
						workgroupId, auth_data);
			} catch (Exception e) {
				throw new MappingException("Unexpected response type.", null, result.getOriginalRestMessage());
			}
		} else {
			Error error = ((PrismaResponseWrapper) result.getResult()).getError();
			if (error.getErrorCode() == OrchestratorErrorCode.ORC_NOT_AUTHORIZED.getCode()) {
				throw new UnauthorizedException(error.getErrorMsg());
			} else {
				throw new APIErrorException("API_ERROR", null, result.getOriginalRestMessage(),
						((PrismaResponseWrapper) result.getResult()).getError());
			}
		}
	}

	/**********************************
	 * NETWORK *
	 * *******************************/

	public List<NetworkRepresentation> getAllNetworks(Long zoneId, Long workgroupId, RestWSParamsContainer params,
			PrismaMetaData meta, String auth_data) throws MappingException, NoMappingModelFoundException,
			ServerErrorResponseException, APIErrorException, RestClientException, JsonParseException,
			JsonMappingException, IOException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + addZone(zoneId) + "/iaas/networks/";

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);
		// selectZone(zoneId, headers);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers,
				addRestWSParamsToQueryParams(params), null, null, null,
				new PrismaRestResponseDecoder<List<NetworkRepresentation>>(List.class, NetworkRepresentation.class),
				null);

		return handleResult(result, meta);
	}

	/**********************************
	 * DNS Zones *
	 **********************************/

	public List<DNSZoneRepresentation> getAvailableDNSZones(Long zoneId, RestWSParamsContainer params,
			PrismaMetaData meta, String auth_data) throws MappingException, NoMappingModelFoundException,
			ServerErrorResponseException, APIErrorException, RestClientException, JsonParseException,
			JsonMappingException, IOException {

		String URL = baseWSUrl + addZone(zoneId) + "/dns";

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);
		// selectZone(zoneId, headers);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers,
				addRestWSParamsToQueryParams(params), null, null, null,
				new PrismaRestResponseDecoder<List<DNSZoneRepresentation>>(List.class, DNSZoneRepresentation.class),
				null);

		return handleResult(result, meta);
	}

	/**********************************
	 * IMAGEs *
	 **********************************/

	public List<IaaSImageRepresentation> getAvailableImages(Long zoneId, Long workgroupId,
			RestWSParamsContainer params, PrismaMetaData meta, String auth_data) throws MappingException,
			NoMappingModelFoundException, ServerErrorResponseException, APIErrorException, RestClientException,
			JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + addZone(zoneId) + "/iaas/vmaas/images";

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);
		// selectZone(zoneId, headers);

		BaseRestResponseResult result = restClient
				.doRequest(RestMethod.GET, URL, headers, addRestWSParamsToQueryParams(params), null, null, null,
						new PrismaRestResponseDecoder<List<IaaSImageRepresentation>>(List.class,
								IaaSImageRepresentation.class), null);

		return handleResult(result, meta);
	}

	/**********************************
	 * KEYPAIRS *
	 * *******************************/

	/**
	 * Utilizzato nella creazione delle vm
	 */
	@Deprecated
	public List<KeypairRepresentation> getAllkeypairs(Long zoneId, Long workgroupId, String auth_data)
			throws MappingException, NoMappingModelFoundException, ServerErrorResponseException, APIErrorException,
			RestClientException, JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + addZone(zoneId) + "/iaas/keys";

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);
		// selectZone(zoneId, headers);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers, null, null, null, null,
				new PrismaRestResponseDecoder<List<KeypairRepresentation>>(List.class, KeypairRepresentation.class),
				null);

		return handleResult(result);
	}

	public List<KeypairRepresentation> getAllkeypairs(Long zoneId, Long workgroupId, RestWSParamsContainer params,
			PrismaMetaData meta, String auth_data) throws Exception {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + addZone(zoneId) + "/iaas/keys";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);
		// selectZone(zoneId, headers);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers,
				addRestWSParamsToQueryParams(params), null, null, null,
				new PrismaRestResponseDecoder<List<KeypairRepresentation>>(List.class, KeypairRepresentation.class),
				null);

		return handleResult(result, meta);
	}

	public String generateKeypairs(Long zoneId, String workgroupId, String keyName, String auth_data)
			throws MappingException, NoMappingModelFoundException, ServerErrorResponseException, APIErrorException,
			RestClientException, JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + addZone(zoneId) + "/iaas/keys/" + keyName;

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);
		// selectZone(zoneId, headers);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.POST, URL, headers, null, null, null, null,
				new PrismaRestResponseDecoder<String>(String.class), null);

		return handleResult(result);
	}

	public boolean deleteKeypair(Long zoneId, String keyName, Long workgroupId, String auth_data)
			throws MappingException, NoMappingModelFoundException, ServerErrorResponseException, APIErrorException,
			RestClientException, JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + addZone(zoneId) + "/iaas/keys/" + keyName;

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);
		// selectZone(zoneId, headers);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.DELETE, URL, headers, null, null, null, null,
				new PrismaRestResponseDecoder<Boolean>(Boolean.class), null);

		return handleResult(result);
	}

	public KeypairRepresentation importKeypair(Long zoneId, KeypairRequest keypair, Long workgroupId, String auth_data)
			throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException,
			JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + addZone(zoneId) + "/iaas/keys/";

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);
		// selectZone(zoneId, headers);

		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(keypair);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.POST, URL, headers, null, ge,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE, null,
				new PrismaRestResponseDecoder<KeypairRepresentation>(KeypairRepresentation.class), null);

		return handleResult(result);
	}

	public KeypairRepresentation createKeypair(Long zoneId, String name, Long workgroupId, String auth_data)
			throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {

		String URL = baseWSUrl + "/workgroups/" + workgroupId + addZone(zoneId) + "/iaas/keys/" + name;

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);
		// selectZone(zoneId, headers);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.POST, URL, headers, null, null, null, null,
				new PrismaRestResponseDecoder<KeypairRepresentation>(KeypairRepresentation.class), null);

		return handleResult(result);
	}

	/******************************
	 * MISC
	 *******************************/
	public DebugInformations getDebugInformations() throws RestClientException, NoMappingModelFoundException,
			MappingException, ServerErrorResponseException {

		String URL = baseWSUrl + "/misc/debug";

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, null, null, null, null, null,
				new PrismaRestResponseDecoder<DebugInformations>(DebugInformations.class), null);

		return handleResult(result);
	}

	public void sendMail(EmailRequest emailRequest, String auth_data) throws RestClientException,
			NoMappingModelFoundException, MappingException, ServerErrorResponseException, JsonParseException,
			JsonMappingException, IOException {

		String URL = baseWSUrl + "/misc/sendMail";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);
		GenericEntity<String> ge = new RestClientHelper.JsonEntityBuilder().build(emailRequest);

		RestMessage result = restClient.doRequest(RestMethod.POST, URL.toString(), headers, null, ge,
				RestClientHelper.JsonEntityBuilder.MEDIA_TYPE, null);
		if (Status.fromStatusCode(result.getHttpStatusCode()).getFamily() == Status.OK.getFamily()) {
			return;
		} else {
			BaseRestResponseResult errorResult = null;
			try {
				errorResult = new PrismaRestResponseDecoder<Void>(Void.class).decode(result);
			} catch (Exception e) {
				throw new RestClientException((String) result.getBody());
			}
			Error error = ((PrismaResponseWrapper) errorResult.getResult()).getError();
			if (error.getErrorCode() == OrchestratorErrorCode.ORC_NOT_AUTHORIZED.getCode()) {
				throw new UnauthorizedException(error.getErrorMsg());
			} else {
				throw new APIErrorException("API_ERROR", null, errorResult.getOriginalRestMessage(),
						((PrismaResponseWrapper) errorResult.getResult()).getError());
			}
		}
	}

	/******************************
	 * MONITORING
	 *******************************/

	public IaaSHealth getIaaSStatus(String auth_data) throws MappingException, NoMappingModelFoundException,
			ServerErrorResponseException, APIErrorException, RestClientException, JsonParseException,
			JsonMappingException, IOException {

		String URL = baseWSUrl + "/monitoring/iaas-status";

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers, new PrismaRestResponseDecoder<IaaSHealth>(
				IaaSHealth.class), null);

		return handleResult(result);
	}

	public MonitoringWrappedResponseIaas getPrismaIaaSMonitoringStatus(String endpoint, String auth_data)
			throws MappingException, NoMappingModelFoundException, ServerErrorResponseException, APIErrorException,
			RestClientException, JsonParseException, JsonMappingException, IOException {
		String URL = baseWSUrl + endpoint;
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient
				.getRequest(URL, headers, new PrismaRestResponseDecoder<MonitoringWrappedResponseIaas>(
						MonitoringWrappedResponseIaas.class), null);

		return handleResult(result);
	}

	public MonitoringWrappedResponsePaas getPrismaGraphByServiceId(String auth_data, String groupName, Long serviceId,
			String metricName, String htmlGraphSelectedDate) throws MappingException, NoMappingModelFoundException,
			ServerErrorResponseException, APIErrorException, RestClientException, JsonParseException,
			JsonMappingException, IOException {

		String URL = baseWSUrl + "/monitoring/groups/" + groupName + "/services/" + serviceId + "/metrics/"
				+ metricName + "/" + htmlGraphSelectedDate;
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient
				.getRequest(URL, headers, new PrismaRestResponseDecoder<MonitoringWrappedResponsePaas>(
						MonitoringWrappedResponsePaas.class), null);

		return handleResult(result);
	}

	public MonitoringWrappedResponsePaas getPrismaGraphByIaasHostId(String auth_data, String groupName,
			Long iaasHostId, String metricName, String htmlGraphSelectedDate) throws MappingException,
			NoMappingModelFoundException, ServerErrorResponseException, APIErrorException, RestClientException,
			JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/monitoring/groups/" + groupName + "/iaasHostId/" + iaasHostId + "/metrics/"
				+ metricName + "/" + htmlGraphSelectedDate;
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient
				.getRequest(URL, headers, new PrismaRestResponseDecoder<MonitoringWrappedResponsePaas>(
						MonitoringWrappedResponsePaas.class), null);

		return handleResult(result);
	}

	public MonitoringWrappedResponsePaas getPrismaGraphByHostName(String auth_data, String groupName, String hostName,
			String metricName, String htmlGraphSelectedDate) throws MappingException, NoMappingModelFoundException,
			ServerErrorResponseException, APIErrorException, RestClientException, JsonParseException,
			JsonMappingException, IOException {

		String URL = baseWSUrl + "/monitoring/groups/" + groupName + "/hosts/" + hostName + "/metrics/" + metricName
				+ "/" + htmlGraphSelectedDate;
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient
				.getRequest(URL, headers, new PrismaRestResponseDecoder<MonitoringWrappedResponsePaas>(
						MonitoringWrappedResponsePaas.class), null);

		return handleResult(result);
	}

	public List<Hypervisor> getPrismaHypervisor(String auth_data) throws MappingException,
			NoMappingModelFoundException, ServerErrorResponseException, APIErrorException, RestClientException,
			JsonParseException, JsonMappingException, IOException {

		String URL = baseWSUrl + "/monitoring/hypervisor";
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.getRequest(URL, headers,
				new PrismaRestResponseDecoder<List<Hypervisor>>(List.class, Hypervisor.class), null);

		return handleResult(result);
	}

	public void refreshService(GenericPaaSServiceRepresentation service, String auth_data) throws RestClientException,
			NoMappingModelFoundException, MappingException, ServerErrorResponseException {

		if (service.getId() == null || service.getWorkgroupId() == null) {
			throw new IllegalArgumentException("PaaS_ID and Workgroup_ID must not be null");
		}
		StringBuilder URL = new StringBuilder();
		URL.append(baseWSUrl).append("/workgroups/").append(service.getWorkgroupId())
				.append(addZone(service.getZoneId()));

		if (service instanceof APPaaSEnvironmentRepresentation) {
			Long APPaaSid = ((APPaaSEnvironmentRepresentation) service).getAppaasId();
			if (APPaaSid == null) {
				throw new IllegalArgumentException("APPaaS_ID must not be null");
			}
			URL.append("/paas/appaas/").append(APPaaSid).append("/environments/");
		} else if (service instanceof MQaaSRepresentation) {
			URL.append("/paas/mqaas/");
		} else if (service instanceof BPMaaSRepresentation) {
			URL.append("/paas/bpmaas/");
		} else if (service instanceof ODaaSRepresentation) {
			URL.append("/paas/odaas/");
		} else if (service instanceof DBaaSRepresentation) {
			URL.append("/paas/dbaas/");
		} else if (service instanceof BIaaSRepresentation) {
			URL.append("/paas/biaas/");
		} else if (service instanceof VMRepresentation) {
			URL.append("/iaas/vmaas/");
		} else if (service instanceof APPaaSRepresentation) {
			URL.append("/paas/appaas/");
		} else {
			throw new IllegalArgumentException("PaaSService type not supported: "
					+ service.getClass().getCanonicalName() + ".");
		}
		URL.append(service.getId()).append("/refreshStatus");

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		RestMessage result = restClient.doRequest(RestMethod.PUT, URL.toString(), headers, null, null, null, null);
		if (Status.fromStatusCode(result.getHttpStatusCode()).getFamily() == Status.ACCEPTED.getFamily()) {
			return;
		} else {
			BaseRestResponseResult errorResult = null;
			try {
				errorResult = new PrismaRestResponseDecoder<Void>(Void.class).decode(result);
			} catch (Exception e) {
				throw new RestClientException((String) result.getBody());
			}
			Error error = ((PrismaResponseWrapper) errorResult.getResult()).getError();
			if (error.getErrorCode() == OrchestratorErrorCode.ORC_NOT_AUTHORIZED.getCode()) {
				throw new UnauthorizedException(error.getErrorMsg());
			} else {
				throw new APIErrorException("API_ERROR", null, errorResult.getOriginalRestMessage(),
						((PrismaResponseWrapper) errorResult.getResult()).getError());
			}
		}
	}

	public void refreshServices(long workgroupId, long zoneId, String type, String auth_data)
			throws RestClientException, NoMappingModelFoundException, MappingException, ServerErrorResponseException {

		StringBuilder URL = new StringBuilder();
		URL.append(baseWSUrl).append("/workgroups/").append(workgroupId).append(addZone(zoneId));

		switch (type) {
		case "appaas":
			URL.append("/paas/appaas/");
			break;
		case "biaas":
			URL.append("/paas/biaas/");
			break;
		case "dbaas":
			URL.append("/paas/dbaas/");
			break;
		case "mqaas":
			URL.append("/paas/mqaas/");
			break;
		case "bpmaas":
			URL.append("/paas/bpmaas/");
			break;
		case "odaas":
			URL.append("/paas/odaas/");
			break;
		case "vmaas":
			URL.append("/iaas/vmaas/");
			break;
		// case APPaaSEnvironment:
		default:
			throw new IllegalArgumentException("PaaSService type not supported: " + type + ".");

		}
		URL.append("refreshStatus");

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		RestMessage result = restClient.doRequest(RestMethod.PUT, URL.toString(), headers, null, null, null, null);
		if (Status.fromStatusCode(result.getHttpStatusCode()).getFamily() == Status.ACCEPTED.getFamily()) {
			return;
		} else {
			BaseRestResponseResult errorResult = null;
			try {
				errorResult = new PrismaRestResponseDecoder<Void>(Void.class).decode(result);
			} catch (Exception e) {
				throw new RestClientException((String) result.getBody());
			}
			Error error = ((PrismaResponseWrapper) errorResult.getResult()).getError();
			if (error.getErrorCode() == OrchestratorErrorCode.ORC_NOT_AUTHORIZED.getCode()) {
				throw new UnauthorizedException(error.getErrorMsg());
			} else {
				throw new APIErrorException("API_ERROR", null, errorResult.getOriginalRestMessage(),
						((PrismaResponseWrapper) errorResult.getResult()).getError());
			}
		}
	}

	private String getServicePathByClass(Class c, Long appId) throws IllegalArgumentException {

		if (c.equals(APPaaSRepresentation.class)) {
			return "/paas/appaas/";

		} else if (c.equals(APPaaSEnvironmentRepresentation.class)) {
			return "/paas/appaas/" + appId + "/environments/";

		} else if (c.equals(BIaaSRepresentation.class)) {
			return "/paas/biaas/";

		} else if (c.equals(DBaaSRepresentation.class)) {
			return "/paas/dbaas/";

		} else if (c.equals(MQaaSRepresentation.class)) {
			return "/paas/mqaas/";

		} else if (c.equals(BPMaaSRepresentation.class)) {
			return "/paas/bpmaas/";

		} else if (c.equals(ODaaSRepresentation.class)) {
			return "/paas/odaas/";

		} else if (c.equals(VMRepresentation.class)) {
			return "/iaas/vmaas/";
		}

		throw new IllegalArgumentException("Class type <" + c.getCanonicalName() + "> not found");
	}

	public static Class<? extends GenericPaaSServiceRepresentation> getServiceClassByType(String type)
			throws IllegalArgumentException {

		switch (type) {
		case "vmaas":
			return VMRepresentation.class;
		case "biaas":
			return BIaaSRepresentation.class;
		case "mqaas":
			return MQaaSRepresentation.class;
		case "bpmaas":
			return BPMaaSRepresentation.class;
		case "odaas":
			return ODaaSRepresentation.class;
		case "dbaas":
			return DBaaSRepresentation.class;
		case "appaas":
			return APPaaSRepresentation.class;
		case "appaasenv":
			return APPaaSEnvironmentRepresentation.class;
		}

		throw new IllegalArgumentException("Type <type> not found");
	}

	/** DNSaaS **/

	public Boolean isPublicDNAvailable(Long zoneId, Long workgroupId, String domainName, String auth_data)
			throws Exception {

		String URL = buildURL(replacePaaSServiceBase(WSPathParam.SERVICE_DNSAAS, workgroupId, zoneId)
				+ "/available?name=" + domainName);
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.GET, URL, headers, null, null, null, null,
				new PrismaRestResponseDecoder<Boolean>(Boolean.class), null);

		return handleResult(result);
	}

	public List<DNSRecordRepresentation> getAllDNSRecords(Long zoneId, Long workgroupId, boolean group,
			RestWSParamsContainer params, PrismaMetaData meta, String auth_data) throws Exception {

		String URL = buildURL(replacePaaSServiceBase(WSPathParam.SERVICE_DNSAAS, workgroupId, zoneId) + "/");
		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		MultivaluedMap<String, Object> qp = addRestWSParamsToQueryParams(params);
		if (group)
			qp.putSingle("group", group);

		BaseRestResponseResult result = restClient
				.doRequest(RestMethod.GET, URL, headers, qp, null, null, null,
						new PrismaRestResponseDecoder<List<DNSRecordRepresentation>>(List.class,
								DNSRecordRepresentation.class), null);

		return handleResult(result, meta);
	}

	public List<DNSRecordRepresentation> registerPaaSServiceToPublicDNS(Long zoneId, Long workgroupId,
			PaaSServiceDNRequest request, String auth_data) throws Exception {

		String URL = buildURL(replacePaaSServiceBase(WSPathParam.SERVICE_DNSAAS, workgroupId, zoneId) + "/paas-service");

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.PUT, URL, headers, null,
				new RestClientHelper.JsonEntityBuilder().build(request), RestClientHelper.JsonEntityBuilder.MEDIA_TYPE,
				null, new PrismaRestResponseDecoder<List<DNSRecordRepresentation>>(List.class,
						DNSRecordRepresentation.class), null);

		return handleResult(result);
	}

	public List<DNSRecordRepresentation> unregisterPaaSServiceFromPublicDNS(Long zoneId, Long workgroupId,
			PaaSServiceDNRequest request, String auth_data) throws Exception {

		String URL = buildURL(replacePaaSServiceBase(WSPathParam.SERVICE_DNSAAS, workgroupId, zoneId) + "/paas-service");

		PrismaAuthenticatedHeader headers = new PrismaAuthenticatedHeader(auth_data);

		BaseRestResponseResult result = restClient.doRequest(RestMethod.DELETE, URL, headers, null,
				new RestClientHelper.JsonEntityBuilder().build(request), RestClientHelper.JsonEntityBuilder.MEDIA_TYPE,
				null, new PrismaRestResponseDecoder<List<DNSRecordRepresentation>>(List.class,
						DNSRecordRepresentation.class), null);

		return handleResult(result);
	}
}