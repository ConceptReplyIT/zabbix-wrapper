package it.prisma.domain.dsl.monitoring.pillar.zabbix.response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
@JsonPropertyOrder({ "interface", "host", "status", "lastaccess", "proxyid", "description" })
public class ZabbixProxyInfoResponse {

	@JsonProperty("interface")
	private List<Object> _interface = new ArrayList<Object>();
	@JsonProperty("host")
	private String host;
	@JsonProperty("status")
	private String status;
	@JsonProperty("lastaccess")
	private String lastaccess;
	@JsonProperty("proxyid")
	private String proxyid;
	@JsonProperty("description")
	private String description;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The _interface
	 */
	@JsonProperty("interface")
	public List<Object> getInterface() {
		return _interface;
	}

	/**
	 * 
	 * @param _interface
	 *            The interface
	 */
	@JsonProperty("interface")
	public void setInterface(List<Object> _interface) {
		this._interface = _interface;
	}

	/**
	 * 
	 * @return The host
	 */
	@JsonProperty("host")
	public String getHost() {
		return host;
	}

	/**
	 * 
	 * @param host
	 *            The host
	 */
	@JsonProperty("host")
	public void setHost(String host) {
		this.host = host;
	}

	/**
	 * 
	 * @return The status
	 */
	@JsonProperty("status")
	public String getStatus() {
		return status;
	}

	/**
	 * 
	 * @param status
	 *            The status
	 */
	@JsonProperty("status")
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 
	 * @return The lastaccess
	 */
	@JsonProperty("lastaccess")
	public String getLastaccess() {
		return lastaccess;
	}

	/**
	 * 
	 * @param lastaccess
	 *            The lastaccess
	 */
	@JsonProperty("lastaccess")
	public void setLastaccess(String lastaccess) {
		this.lastaccess = lastaccess;
	}

	/**
	 * 
	 * @return The proxyid
	 */
	@JsonProperty("proxyid")
	public String getProxyid() {
		return proxyid;
	}

	/**
	 * 
	 * @param proxyid
	 *            The proxyid
	 */
	@JsonProperty("proxyid")
	public void setProxyid(String proxyid) {
		this.proxyid = proxyid;
	}

	/**
	 * 
	 * @return The description
	 */
	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @param description
	 *            The description
	 */
	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
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