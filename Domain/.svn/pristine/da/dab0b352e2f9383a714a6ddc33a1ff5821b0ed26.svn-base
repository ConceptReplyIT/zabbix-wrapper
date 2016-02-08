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
@JsonPropertyOrder({ "selectInventory" })
public class ZabbixParamSearchInventory {

	@JsonProperty("selectInventory")
	private List<String> selectInventory = new ArrayList<String>();
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The selectInventory
	 */
	@JsonProperty("selectInventory")
	public List<String> getSelectInventory() {
		return selectInventory;
	}

	/**
	 * 
	 * @param selectInventory
	 *            The selectInventory
	 */
	@JsonProperty("selectInventory")
	public void setSelectInventory(List<String> selectInventory) {
		this.selectInventory = selectInventory;
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
