package it.prisma.domain.dsl.monitoring.pillar.zabbix.request;

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
@JsonPropertyOrder({ "proxyid", "hosts" })
public class ZabbixUpdateProxyRequest {

	@JsonProperty("proxyid")
	private String proxyid;
	@JsonProperty("hosts")
	private List<String> hosts = new ArrayList<String>();
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
	 * @return The hosts
	 */
	@JsonProperty("hosts")
	public List<String> getHosts() {
		return hosts;
	}

	/**
	 * 
	 * @param hosts
	 *            The hosts
	 */
	@JsonProperty("hosts")
	public void setHosts(List<String> hosts) {
		this.hosts = hosts;
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