package it.prisma.domain.dsl.monitoring.pillar.zabbix.response;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
	"groupid",
	"name",
	"internal",
	"flags"
})
public class ZabbixGroup {

	@JsonProperty("groupid")
	private String groupid;
	@JsonProperty("name")
	private String name;
	@JsonProperty("internal")
	private String internal;
	@JsonProperty("flags")
	private String flags;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
	 * The internal
	 */
	@JsonProperty("internal")
	public String getInternal() {
		return internal;
	}

	/**
	 * 
	 * @param internal
	 * The internal
	 */
	@JsonProperty("internal")
	public void setInternal(String internal) {
		this.internal = internal;
	}

	/**
	 * 
	 * @return
	 * The flags
	 */
	@JsonProperty("flags")
	public String getFlags() {
		return flags;
	}

	/**
	 * 
	 * @param flags
	 * The flags
	 */
	@JsonProperty("flags")
	public void setFlags(String flags) {
		this.flags = flags;
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
