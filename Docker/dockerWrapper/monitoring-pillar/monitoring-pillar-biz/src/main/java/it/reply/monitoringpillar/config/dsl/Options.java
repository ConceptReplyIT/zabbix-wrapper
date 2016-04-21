package it.reply.monitoringpillar.config.dsl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "wsDebug", "distributedArchitecture", "iaasMonitoring", "multipleServers", "proxyArchitecture" })
public class Options {

	@JsonProperty("wsDebug")
	private boolean wsDebug;
	@JsonProperty("distributedArchitecture")
	private boolean distributedArchitecture;
	@JsonProperty("iaasMonitoring")
	private boolean iaasMonitoring;
	@JsonProperty("multipleServers")
	private boolean multipleServers;
	@JsonProperty("proxyArchitecture")
	private boolean proxyArchitecture;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The wsDebug
	 */
	@JsonProperty("wsDebug")
	public boolean isWsDebug() {
		return wsDebug;
	}

	/**
	 * 
	 * @param wsDebug
	 *            The wsDebug
	 */
	@JsonProperty("wsDebug")
	public void setWsDebug(boolean wsDebug) {
		this.wsDebug = wsDebug;
	}

	/**
	 * 
	 * @return The iaasMonitoring
	 */
	@JsonProperty("iaasMonitoring")
	public boolean isIaasMonitoring() {
		return iaasMonitoring;
	}

	/**
	 * 
	 * @param iaasMonitoring
	 *            The iaasMonitoring
	 */
	@JsonProperty("iaasMonitoring")
	public void setIaasMonitoring(boolean iaasMonitoring) {
		this.iaasMonitoring = iaasMonitoring;
	}

	/**
	 * 
	 * @return The multipleServers
	 */
	@JsonProperty("multipleServers")
	public boolean isMultipleServers() {
		return multipleServers;
	}

	/**
	 * 
	 * @param multipleServers
	 *            The multipleServers
	 */
	@JsonProperty("multipleServers")
	public void setMultipleServers(boolean multipleServers) {
		this.multipleServers = multipleServers;
	}

	/**
	 * 
	 * @return The proxyArchitecture
	 */
	@JsonProperty("proxyArchitecture")
	public boolean isProxyArchitecture() {
		return proxyArchitecture;
	}

	/**
	 * 
	 * @param proxyArchitecture
	 *            The proxyArchitecture
	 */
	@JsonProperty("proxyArchitecture")
	public void setProxyArchitecture(boolean proxyArchitecture) {
		this.proxyArchitecture = proxyArchitecture;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
