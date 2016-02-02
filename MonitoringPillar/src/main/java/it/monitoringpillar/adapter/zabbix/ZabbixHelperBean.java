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

	private ConcurrentMap<InfoType, String> proxyIds;

	@PostConstruct
	private void init() throws ZabbixException, NameNotFoundException {
		tokens = new ConcurrentHashMap<InfoType, String>();
		getAllTokens();
		proxyIds = new ConcurrentHashMap<InfoType, String>();
		if (config.isProxyEnabled())
			getAllProxyIds();
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

	public void getAllProxyIds() throws ZabbixException, NameNotFoundException {
		proxyIds.put(InfoType.INFRASTRUCTURE, getProxyId(InfoType.INFRASTRUCTURE));
		proxyIds.put(InfoType.SERVICE, getProxyId(InfoType.SERVICE));
		proxyIds.put(InfoType.WATCHER, getProxyId(InfoType.WATCHER));
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

	public String getProxyId(InfoType proxyType) throws ZabbixException, NameNotFoundException {

		String proxyId = null;

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
					if (proxyResponse.getHost().equalsIgnoreCase(config.getZabbixProxyName(proxyType))) {
						proxyId = proxyResponse.getProxyid();
						break;
					}
				}
				if (proxyId == null)
					throw new NameNotFoundException("Wrong ProxyName inserted: not Existing in Properties file");
			} catch (ZabbixClientException e) {
				throw handleException(e);
			}
		}
		return proxyId;
	}

	public String getStoredProxyId(InfoType proxyType) {
		return proxyIds.get(proxyType);
	}

}