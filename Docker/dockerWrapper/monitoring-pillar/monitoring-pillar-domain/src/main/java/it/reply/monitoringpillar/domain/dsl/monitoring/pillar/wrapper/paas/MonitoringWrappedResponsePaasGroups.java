package it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
// "environment",
"groups" })
public class MonitoringWrappedResponsePaasGroups {

	// @JsonProperty("environment")
	// private String environment;
	@JsonProperty("groups")
	private List<GroupsWrappedName> groups = new ArrayList<GroupsWrappedName>();

	// @JsonIgnore
	// private Map<String, Object> additionalProperties = new HashMap<String,
	// Object>();

	// /**
	// *
	// * @return
	// * The environment
	// */
	// @JsonProperty("environment")
	// public String getEnvironment() {
	// return environment;
	// }
	//
	// /**
	// *
	// * @param environment
	// * The environment
	// */
	// @JsonProperty("environment")
	// public void setEnvironment(String environment) {
	// this.environment = environment;
	// }

	/**
	 * 
	 * @return The groups
	 */
	@JsonProperty("groups")
	public List<GroupsWrappedName> getGroups() {
		return groups;
	}

	/**
	 * 
	 * @param groups
	 *            The groups
	 */
	@JsonProperty("groups")
	public void setGroups(List<GroupsWrappedName> groups) {
		this.groups = groups;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}