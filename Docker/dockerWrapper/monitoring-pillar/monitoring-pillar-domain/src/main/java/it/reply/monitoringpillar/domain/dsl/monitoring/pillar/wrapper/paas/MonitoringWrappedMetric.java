package it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "metricName", "metricValue", "metricUnit", "thresholds" })
public class MonitoringWrappedMetric {

	@JsonProperty("metricName")
	private String metricName;
	@JsonProperty("metricValue")
	private String metricValue;
	@JsonProperty("metricUnit")
	private String metricUnit;
	@JsonProperty("metricTime")
	private String metricTime;

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
	 * @return The metricValue
	 */
	@JsonProperty("metricValue")
	public String getMetricValue() {
		return metricValue;
	}

	/**
	 * 
	 * @param metricValue
	 *            The metricValue
	 */
	@JsonProperty("metricValue")
	public void setMetricValue(String metricValue) {
		this.metricValue = metricValue;
	}

	/**
	 * 
	 * @return The metricUnit
	 */
	@JsonProperty("metricUnit")
	public String getMetricUnit() {
		return metricUnit;
	}

	/**
	 * 
	 * @param metricUnit
	 *            The metricUnit
	 */
	@JsonProperty("metricUnit")
	public void setMetricUnit(String metricUnit) {
		this.metricUnit = metricUnit;
	}

	/**
	 * 
	 * @return The metricTime
	 */
	@JsonProperty("metricTime")
	public String getMetricTime() {
		return metricTime;
	}

	/**
	 * 
	 * @param metricTime
	 *            The metricTime
	 */
	@JsonProperty("metricTime")
	public void setMetricTime(String metricTime) {
		this.metricTime = metricTime;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
