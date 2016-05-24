package it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas;

import java.util.List;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "machineName", "ip", "serviceCategory", "serviceId", "metrics"

})
public class PaasMachineWrapped {

	@JsonProperty("machineName")
	private String machineName;
	@JsonProperty("ip")
	private String ip;
	@JsonProperty("serviceCategory")
	private String serviceCategory;
	@JsonProperty("serviceId")
	private String serviceId;
	@JsonProperty("metrics")
	private List<MetricsWrapped> metrics;

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
	 * @return The serviceCategory
	 */
	@JsonProperty("serviceCategory")
	public String getServiceCategory() {
		return serviceCategory;
	}

	/**
	 * 
	 * @param serviceCategories
	 *            The serviceCategories
	 */
	@JsonProperty("serviceCategory")
	public void setServiceCategory(String serviceCategory) {
		this.serviceCategory = serviceCategory;
	}

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
	 * @return The metrics
	 */
	@JsonProperty("metrics")
	public List<MetricsWrapped> getMetrics() {
		return metrics;
	}

	/**
	 * 
	 * @param metrics
	 *            The metrics
	 */
	@JsonProperty("metrics")
	public void setMetrics(List<MetricsWrapped> metrics) {
		this.metrics = metrics;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}