package it.monitoringpillar.adapter.zabbix;

import it.monitoringpillar.adapter.zabbix.exception.IllegalArgumentZabbixException;
import it.monitoringpillar.adapter.zabbix.exception.ZabbixException;
import it.monitoringpillar.adapter.zabbix.handler.ZabbixFeatures.ZabbixMethods;
import it.monitoringpillar.config.Configuration;
import it.prisma.domain.dsl.monitoring.InfoType;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.JSONRPCRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.ZabbixAuthenticationRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.request.ZabbixProxyInfoRequest;
import it.prisma.domain.dsl.monitoring.pillar.zabbix.response.ZabbixProxyInfoResponse;
import it.prisma.utils.web.ws.rest.apiclients.zabbix.ZabbixAPIClient;
import it.prisma.utils.web.ws.rest.apiclients.zabbix.exception.ZabbixClientException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.naming.NameNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This bean MUST be the only point of access to zabbix server Endpoint and
 * Token
 */
@Startup
@Singleton
public class ZabbixHelperBean extends ZabbixBase implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LogManager.getLogger(ZabbixHelperBean.class);

	@Inject
	private Configuration config;

	private ConcurrentMap<InfoType, String> tokens;

	private ConcurrentMap<InfoType, List<String>> proxyIdMap;

	private ConcurrentMap<InfoType, List<String>> proxyNameMap;

	private ConcurrentMap<InfoType, List<Map<String, String>>> proxyMap;

	@PostConstruct
	private void init() throws ZabbixException, NameNotFoundException {
		tokens = new ConcurrentHashMap<InfoType, String>();
		getAllTokens();
		proxyMap = new ConcurrentHashMap<InfoType, List<Map<String, String>>>();
		if (config.isProxyEnabled())
			getProxyMap();
	}

	@Schedule(hour = "*", persistent = false)
	private void refreshTokens() {
		try {
			LOG.info("refreshing Tokens");
			getAllTokens();
		} catch (Exception e) {
			LOG.error("Error during token refresh", e);
		}
	}

	private void getAllTokens() throws ZabbixException {
		tokens.put(InfoType.INFRASTRUCTURE, getZabbixToken(InfoType.INFRASTRUCTURE.getInfoType()));
		tokens.put(InfoType.SERVICE, getZabbixToken(InfoType.SERVICE.getInfoType()));
		tokens.put(InfoType.WATCHER, getZabbixToken(InfoType.WATCHER.getInfoType()));
	}

	void getAllProxyIds() throws ZabbixException, NameNotFoundException {
		proxyIdMap.put(InfoType.INFRASTRUCTURE, getProxyId(InfoType.INFRASTRUCTURE));
		proxyIdMap.put(InfoType.SERVICE, getProxyId(InfoType.SERVICE));
		proxyIdMap.put(InfoType.WATCHER, getProxyId(InfoType.WATCHER));
	}

	public void getAllProxyNames() throws ZabbixException, NameNotFoundException {
		proxyNameMap.put(InfoType.INFRASTRUCTURE, getProxyName(InfoType.INFRASTRUCTURE));
		proxyNameMap.put(InfoType.SERVICE, getProxyName(InfoType.SERVICE));
		proxyNameMap.put(InfoType.WATCHER, getProxyName(InfoType.WATCHER));
	}

	public void getProxyMap() throws ZabbixException, NameNotFoundException {
		proxyMap.put(InfoType.INFRASTRUCTURE, getProxy(InfoType.INFRASTRUCTURE));
		proxyMap.put(InfoType.SERVICE, getProxy(InfoType.SERVICE));
		proxyMap.put(InfoType.WATCHER, getProxy(InfoType.WATCHER));
	}

	public String getZabbixURL(String serverType) throws IllegalArgumentZabbixException {

		return config.getZabbixServerURL(serverType);
	}

	public String getZabbixToken(String serverType) throws ZabbixException {

		if (tokens.containsKey(InfoType.lookupFromName(serverType))) {
			return tokens.get(InfoType.lookupFromName(serverType));
		}
		ZabbixAPIClient zabClient = new ZabbixAPIClient(config.getZabbixServerURL(serverType));
		ZabbixAuthenticationRequest auth = new ZabbixAuthenticationRequest();
		auth.setUser(config.getUsernameZabbixServer());
		auth.setPassword(config.getPswdZabbixServer());

		JSONRPCRequest<ZabbixAuthenticationRequest> authRequest = new JSONRPCRequest<ZabbixAuthenticationRequest>();

		authRequest.setJsonrpc(config.getZabbixRPCVersion());
		authRequest.setMethod(ZabbixConstant.METHOD_USER_LOGIN);

		authRequest.setParams(auth);
		authRequest.setId(ZabbixConstant.ID);
		try {
			return zabClient.authentication(authRequest);
		} catch (ZabbixClientException e) {
			throw new ZabbixException(e);
		}
	}

	public String getZabbixRPCVersion() {
		return config.getZabbixRPCVersion();
	}

	@Deprecated
	public List<String> getProxyId(InfoType proxyType) throws ZabbixException, NameNotFoundException {

		String proxyId = null;
		List<String> proxyIds = new ArrayList<>();

		if (config.isProxyEnabled()) {
			try {
				ZabbixAPIClient zabClient = new ZabbixAPIClient(getZabbixURL(proxyType.toString()));
				JSONRPCRequest<ZabbixProxyInfoRequest> request = new JSONRPCRequest<>();
				ZabbixProxyInfoRequest paramRequest = new ZabbixProxyInfoRequest();
				request.setJsonrpc(getZabbixRPCVersion());
				request.setMethod(ZabbixMethods.GETPROXY.getzabbixMethod());

				paramRequest.setOutput(ZabbixConstant.EXTEND);
				paramRequest.setSelectInterface(ZabbixConstant.EXTEND);
				request.setParams(paramRequest);
				request.setAuth(getZabbixToken(proxyType.toString()));
				request.setId(ZabbixConstant.ID);
				ArrayList<ZabbixProxyInfoResponse> proxyResponses = zabClient.getProxyInfoClient(request);
				for (ZabbixProxyInfoResponse proxyResponse : proxyResponses) {
					proxyId = proxyResponse.getProxyid();
					proxyIds.add(proxyId);
				}
				if (proxyIds.isEmpty() && config.isProxyEnabled())
					throw new NameNotFoundException(
							"No Proxies present into monitoring platforms: be sure to configure it properly or set proxy_configuration=false in monitoring.properties file ");
			} catch (ZabbixClientException e) {
				throw handleException(e);
			}
		}
		return proxyIds;
	}

	/**
	 * Method for returning the list of proxy names set to zabbix platform
	 *
	 * @param proxyType
	 * @return proxy names
	 * @throws ZabbixException
	 * @throws NameNotFoundException
	 */
	@Deprecated
	public List<String> getProxyName(InfoType proxyType) throws ZabbixException, NameNotFoundException {

		String proxyName = null;
		List<String> proxyNames = new ArrayList<>();

		if (config.isProxyEnabled()) {
			try {
				ZabbixAPIClient zabClient = new ZabbixAPIClient(getZabbixURL(proxyType.toString()));
				JSONRPCRequest<ZabbixProxyInfoRequest> request = new JSONRPCRequest<>();
				ZabbixProxyInfoRequest paramRequest = new ZabbixProxyInfoRequest();
				request.setJsonrpc(getZabbixRPCVersion());
				request.setMethod(ZabbixMethods.GETPROXY.getzabbixMethod());

				paramRequest.setOutput(ZabbixConstant.EXTEND);
				paramRequest.setSelectInterface(ZabbixConstant.EXTEND);
				request.setParams(paramRequest);
				request.setAuth(getZabbixToken(proxyType.toString()));
				request.setId(ZabbixConstant.ID);
				ArrayList<ZabbixProxyInfoResponse> proxyResponses = zabClient.getProxyInfoClient(request);
				for (ZabbixProxyInfoResponse proxyResponse : proxyResponses) {
					proxyName = proxyResponse.getHost();
					proxyNames.add(proxyName);
				}
				if (proxyNames.isEmpty() && config.isProxyEnabled())
					throw new NameNotFoundException(
							"No Proxies present into monitoring platforms: be sure to configure it properly or set proxy_configuration=false in monitoring.properties file ");
			} catch (ZabbixClientException e) {
				throw handleException(e);
			}
		}
		return proxyNames;
	}

	/**
	 * This method returns for every platform all the proxy's info
	 * 
	 * @param proxyType
	 * @return proxies' info
	 * @throws ZabbixException
	 * @throws NameNotFoundException
	 */
	public List<Map<String, String>> getProxy(InfoType proxyType) throws ZabbixException, NameNotFoundException {

		String proxyName = null;
		String proxyId = null;
		Map<String, String> proxyMap = new HashMap<>();
		List<Map<String, String>> proxies = new ArrayList<Map<String, String>>();

		if (config.isProxyEnabled()) {
			try {
				ZabbixAPIClient zabClient = new ZabbixAPIClient(getZabbixURL(proxyType.toString()));
				JSONRPCRequest<ZabbixProxyInfoRequest> request = new JSONRPCRequest<>();
				ZabbixProxyInfoRequest paramRequest = new ZabbixProxyInfoRequest();
				request.setJsonrpc(getZabbixRPCVersion());
				request.setMethod(ZabbixMethods.GETPROXY.getzabbixMethod());

				paramRequest.setOutput(ZabbixConstant.EXTEND);
				paramRequest.setSelectInterface(ZabbixConstant.EXTEND);
				request.setParams(paramRequest);
				request.setAuth(getZabbixToken(proxyType.toString()));
				request.setId(ZabbixConstant.ID);
				ArrayList<ZabbixProxyInfoResponse> proxyResponses = zabClient.getProxyInfoClient(request);
				for (ZabbixProxyInfoResponse proxyResponse : proxyResponses) {
					proxyName = proxyResponse.getHost();
					proxyId = proxyResponse.getProxyid();
					proxyMap.put(proxyName, proxyId);
					proxies.add(proxyMap);
				}
				if (proxies.isEmpty() && config.isProxyEnabled())
					throw new NameNotFoundException(
							"No Proxies present into monitoring platforms: be sure to configure it properly or set proxy_configuration=false in monitoring.properties file ");
			} catch (ZabbixClientException e) {
				throw handleException(e);
			}
		}
		return proxies;
	}

	public List<String> getStoredProxyId(InfoType proxyType) {
		return proxyIdMap.get(proxyType);
	}

	public List<String> getStoredProxyName(InfoType proxyType) {
		return proxyNameMap.get(proxyType);
	}

	public List<Map<String, String>> getStoredProxy(InfoType proxyType) {
		return proxyMap.get(proxyType);
	}

}