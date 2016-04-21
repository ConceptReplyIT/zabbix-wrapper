package it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MonitoringWrappedResponsePaasGroups4MetricList {

	// @JsonProperty("environment")
	// private String environment;
	@JsonProperty("groups")
	private List<GroupWrapped4MetricList> groups = new ArrayList<GroupWrapped4MetricList>();

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
	public List<GroupWrapped4MetricList> getGroups() {
		return groups;
	}

	/**
	 * 
	 * @param groups
	 *            The groups
	 */
	@JsonProperty("groups")
	public void setGroups(List<GroupWrapped4MetricList> groups) {
		this.groups = groups;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}