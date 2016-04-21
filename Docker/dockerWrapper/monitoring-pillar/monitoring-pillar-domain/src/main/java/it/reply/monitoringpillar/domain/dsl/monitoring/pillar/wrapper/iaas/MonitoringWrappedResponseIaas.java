package it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.iaas;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ // "testbed",
"iaasMachineGroups" })
public class MonitoringWrappedResponseIaas {

	// @JsonProperty("testbed")
	// private String testbed;
	@JsonProperty("iaasMachineGroups")
	private List<IaasGroupOfMachine> iaasMachineGroups = new ArrayList<IaasGroupOfMachine>();

	// @JsonIgnore
	// private Map<String, Object> additionalProperties = new HashMap<String,
	// Object>();
	// /**
	// *
	// * @return The testbed
	// */
	// @JsonProperty("testbed")
	// public String getTestbed() {
	// return testbed;
	// }
	//
	// /**
	// *
	// * @param testbed
	// * The testbed
	// */
	// @JsonProperty("testbed")
	// public void setTestbed(String testbed) {
	// this.testbed = testbed;
	// }

	/**
	 * 
	 * @return The iaasMachineGroups
	 */
	@JsonProperty("iaasMachineGroups")
	public List<IaasGroupOfMachine> getIaasMachineGroups() {
		return iaasMachineGroups;
	}

	/**
	 * 
	 * @param iaasMachineGroups
	 *            The iaasMachineGroups
	 */
	@JsonProperty("iaasMachineGroups")
	public void setIaasMachineGroups(List<IaasGroupOfMachine> iaasMachineGroups) {
		this.iaasMachineGroups = iaasMachineGroups;
	}

	// @JsonAnyGetter
	// public Map<String, Object> getAdditionalProperties() {
	// return this.additionalProperties;
	// }
	//
	// @JsonAnySetter
	// public void setAdditionalProperty(String name, Object value) {
	// this.additionalProperties.put(name, value);
	// }

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
