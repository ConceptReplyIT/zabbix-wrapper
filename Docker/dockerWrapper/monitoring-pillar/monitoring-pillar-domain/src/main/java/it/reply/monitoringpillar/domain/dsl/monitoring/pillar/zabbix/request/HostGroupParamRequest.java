package it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.request;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
	"name", 
	"groupid"
})
public class HostGroupParamRequest {

	@JsonProperty("name")
	private String name;
	@JsonProperty("groupid")
	private String groupid;
//	@JsonIgnore
//	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return
	 * The name
	 */
	@JsonProperty("name")
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 * The name
	 */
	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}
	
	
	/**
	 * 
	 * @return
	 * The groupid
	 */
	@JsonProperty("groupid")
	public String getGroupid() {
		return groupid;
	}

	/**
	 * 
	 * @param groupid
	 * The groupid
	 */
	@JsonProperty("groupid")
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
//
//	@JsonAnyGetter
//	public Map<String, Object> getAdditionalProperties() {
//		return this.additionalProperties;
//	}
//
//	@JsonAnySetter
//	public void setAdditionalProperty(String name, Object value) {
//		this.additionalProperties.put(name, value);
//	}
}