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
public class MonitoringWrappedResponsePaas4Metrics {

	@JsonProperty("groups")
	private List<Group4WrappedMetric> groups = new ArrayList<>();

	/**
	 * 
	 * @return The groups
	 */
	@JsonProperty("groups")
	public List<Group4WrappedMetric> getGroups() {
		return groups;
	}

	/**
	 * 
	 * @param groups
	 *            The groups
	 */
	@JsonProperty("groups")
	public void setGroups(List<Group4WrappedMetric> groups) {
		this.groups = groups;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
