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
@JsonPropertyOrder({ "serviceId", "status", "vmuuid", "vmName", "tenant", "ip",
		"time", "description" })
public class MonitoringPillarEventCallbackResponse {

	@JsonProperty("serviceId")
	private String serviceId;
	@JsonProperty("status")
	private String status;
	@JsonProperty("vmuuid")
	private String vmuuid;
	@JsonProperty("vmName")
	private String vmName;
	@JsonProperty("tenant")
	private String tenant;
	@JsonProperty("ip")
	private String ip;
	@JsonProperty("metricName")
	private String metricName;
	@JsonProperty("triggerName")
	private String triggerName;
	@JsonProperty("description")
	private String description;
	@JsonProperty("time")
	private Long time;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The serviceId
	 */
	@JsonProperty("serviceId")
	public String getServiceId() {
		return serviceId;
	}

	/**
	 * 
	 * @param serviceId
	 *            The serviceId
	 */
	@JsonProperty("serviceId")
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	/**
	 * 
	 * @return The status
	 */
	@JsonProperty("status")
	public String getStatus() {
		return status;
	}

	/**
	 * 
	 * @param status
	 *            The status
	 */
	@JsonProperty("status")
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 
	 * @return The vmuuid
	 */
	@JsonProperty("vmuuid")
	public String getVmuuid() {
		return vmuuid;
	}

	/**
	 * 
	 * @param vmuuid
	 *            The vmuuid
	 */
	@JsonProperty("vmuuid")
	public void setVmuuid(String vmuuid) {
		this.vmuuid = vmuuid;
	}

	/**
	 * 
	 * @return The vmName
	 */
	@JsonProperty("vmName")
	public String getVmName() {
		return vmName;
	}

	/**
	 * 
	 * @param vmName
	 *            The vmName
	 */
	@JsonProperty("vmName")
	public void setVmName(String vmName) {
		this.vmName = vmName;
	}

	/**
	 * 
	 * @return The tenant
	 */
	@JsonProperty("tenant")
	public String getTenant() {
		return tenant;
	}

	/**
	 * 
	 * @param tenant
	 *            The tenant
	 */
	@JsonProperty("tenant")
	public void setTenant(String tenant) {
		this.tenant = tenant;
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

	/**
	 * 
	 * @return The metricName
	 */
	@JsonProperty("metricName")
	public String getMetricName() {
		return metricName;
	}

	/**
	 * 
	 * @param metricName
	 *            The metricName
	 */
	@JsonProperty("metricName")
	public void setMetricName(String metricName) {
		this.metricName = metricName;
	}

	/**
	 * 
	 * @return The triggerName
	 */
	@JsonProperty("triggerName")
	public String geTriggerName() {
		return triggerName;
	}

	/**
	 * 
	 * @param triggerName
	 *            The triggerName
	 */
	@JsonProperty("triggerName")
	public void setTriggerName(String triggerName) {
		this.triggerName = triggerName;
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
	 * @return The time
	 */
	@JsonProperty("time")
	public Long getTime() {
		return time;
	}

	/**
	 * 
	 * @param time
	 *            The time
	 */
	@JsonProperty("time")
	public void setTime(Long time) {
		this.time = time;
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
