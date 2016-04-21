package it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas;

import java.util.List;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "machineName", "ip", "serviceCategory", "enabled", "serviceId", "metrics" })
public class PaasMachineWrapped4MetricList {

	@JsonProperty("machineName")
	private String machineName;
	@JsonProperty("ip")
	private String ip;
	@JsonProperty("enabled")
	private boolean enabled;
	@JsonProperty("metrics")
	private List<MonitoringWrappedMetric> metrics;

	/**
	 * @return The machineName
	 */
	@JsonProperty("machineName")
	public String getMachineName() {
		return machineName;
	}

	/**
	 * 
	 * @param machineName
	 *            The machineName
	 */
	@JsonProperty("machineName")
	public void setMachineName(String machineName) {
		this.machineName = machineName;
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
	 * @return The enabled
	 */
	@JsonProperty("enabled")
	public boolean getEnabled() {
		return enabled;
	}

	/**
	 * 
	 * @param enabled
	 *            The enabled
	 */
	@JsonProperty("enabled")
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * 
	 * @return The metrics
	 */
	@JsonProperty("metrics")
	public List<MonitoringWrappedMetric> getMetrics() {
		return metrics;
	}

	/**
	 * 
	 * @param metrics
	 *            The metrics
	 */
	@JsonProperty("metrics")
	public void setMetrics(List<MonitoringWrappedMetric> metrics) {
		this.metrics = metrics;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}