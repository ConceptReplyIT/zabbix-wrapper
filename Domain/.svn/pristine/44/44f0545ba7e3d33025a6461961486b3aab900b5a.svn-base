package it.prisma.domain.dsl.monitoring.pillar.zabbix.request;

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
@JsonPropertyOrder({ "output", "selectInterface" })
public class ZabbixProxyInfoRequest {

	@JsonProperty("output")
	private String output;
	@JsonProperty("selectInterface")
	private String selectInterface;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The output
	 */
	@JsonProperty("output")
	public String getOutput() {
		return output;
	}

	/**
	 * 
	 * @param output
	 *            The output
	 */
	@JsonProperty("output")
	public void setOutput(String output) {
		this.output = output;
	}

	/**
	 * 
	 * @return The selectInterface
	 */
	@JsonProperty("selectInterface")
	public String getSelectInterface() {
		return selectInterface;
	}

	/**
	 * 
	 * @param selectInterface
	 *            The selectInterface
	 */
	@JsonProperty("selectInterface")
	public void setSelectInterface(String selectInterface) {
		this.selectInterface = selectInterface;
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