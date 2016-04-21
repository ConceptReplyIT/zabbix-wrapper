package it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas;

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
@JsonPropertyOrder({ "paaSServiceId", "triggerStatus", "hostId", "hostName",
		"group", "metric", "threshold", "ip", "description" })
public class EventCallbackRequest {

	@JsonProperty("paaSServiceId")
	private String paaSServiceId;
	@JsonProperty("triggerStatus")
	private String triggerStatus;
	@JsonProperty("hostId")
	private String hostId;
	@JsonProperty("hostName")
	private String hostName;
	@JsonProperty("group")
	private String group;
	@JsonProperty("metric")
	private String metric;
	@JsonProperty("threshold")
	private String threshold;
	@JsonProperty("description")
	private String description;
	@JsonProperty("ip")
	private String ip;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The paaSServiceId
	 */
	@JsonProperty("paaSServiceId")
	public String getPaaSServiceId() {
		return paaSServiceId;
	}

	/**
	 * 
	 * @param paaSServiceId
	 *            The paaSServiceId
	 */
	@JsonProperty("paaSServiceId")
	public void setPaaSServiceId(String paaSServiceId) {
		this.paaSServiceId = paaSServiceId;
	}

	/**
	 * 
	 * @return The triggerStatus
	 */
	@JsonProperty("triggerStatus")
	public String getTriggerStatus() {
		return triggerStatus;
	}

	/**
	 * 
	 * @param triggerStatus
	 *            The triggerStatus
	 */
	@JsonProperty("triggerStatus")
	public void setTriggerStatus(String triggerStatus) {
		this.triggerStatus = triggerStatus;
	}

	/**
	 * 
	 * @return The hostId
	 */
	@JsonProperty("hostId")
	public String getHostId() {
		return hostId;
	}

	/**
	 * 
	 * @param hostId
	 *            The hostId
	 */
	@JsonProperty("hostId")
	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	/**
	 * 
	 * @return The hostName
	 */
	@JsonProperty("hostName")
	public String getHostName() {
		return hostName;
	}

	/**
	 * 
	 * @param hostName
	 *            The hostName
	 */
	@JsonProperty("hostName")
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	/**
	 * 
	 * @return The group
	 */
	@JsonProperty("group")
	public String getGroup() {
		return group;
	}

	/**
	 * 
	 * @param group
	 *            The group
	 */
	@JsonProperty("group")
	public void setGroup(String group) {
		this.group = group;
	}

	/**
	 * 
	 * @return The metric
	 */
	@JsonProperty("metric")
	public String getMetric() {
		return metric;
	}

	/**
	 * 
	 * @param metric
	 *            The metric
	 */
	@JsonProperty("metric")
	public void setMetric(String metric) {
		this.metric = metric;
	}

	/**
	 * 
	 * @return The threshold
	 */
	@JsonProperty("threshold")
	public String getThreshold() {
		return threshold;
	}

	/**
	 * 
	 * @param threshold
	 *            The threshold
	 */
	@JsonProperty("threshold")
	public void setThreshold(String threshold) {
		this.threshold = threshold;
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

	/**
	 * 
	 * @return The ip
	 */
	@JsonProperty("ip")
	public String getIp() {
		return ip;
	}

	/**
	 * 
	 * @param ip
	 *            The ip
	 */
	@JsonProperty("ip")
	public void setIp(String ip) {
		this.ip = ip;
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
