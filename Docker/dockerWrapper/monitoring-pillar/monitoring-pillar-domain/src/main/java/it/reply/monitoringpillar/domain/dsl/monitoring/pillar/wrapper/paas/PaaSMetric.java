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
@JsonPropertyOrder({ "metricName", "metricKey", "metricValue", "metricTime", "metricUnit",
		"metricStatus", "paasThresholds", "historyClocks", "historyValues" })
public class PaaSMetric {

	@JsonProperty("metricName")
	private String metricName;
	@JsonProperty("metricKey")
	private String metricKey;
	@JsonProperty("metricValue")
	private Float metricValue;
	@JsonProperty("metricTime")
	private Object metricTime;
	
	@JsonProperty("metricUnit")
	private String metricUnit;

	@JsonProperty("paasThresholds")
	private List<PaasThreshold> paasThresholds = new ArrayList<PaasThreshold>();
	@JsonProperty("historyClocks")
	private List<String> historyClocks = new ArrayList<String>();
	@JsonProperty("historyValues")
	private List<Float> historyValues = new ArrayList<>();

//	@JsonIgnore
//	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
	 * @return The metricKey
	 */
	@JsonProperty("metricKey")
	public String getMetricKey() {
		return metricKey;
	}

	/**
	 * 
	 * @param metricKey
	 *            The metricKey
	 */
	@JsonProperty("metricKey")
	public void setMetricKey(String metricKey) {
		this.metricKey = metricKey;
	}

	/**
	 * 
	 * @return The metricValue
	 */
	@JsonProperty("metricValue")
	public Float getMetricValue() {
		return metricValue;
	}

	/**
	 * 
	 * @param metricValue
	 *            The metricValue
	 */
	@JsonProperty("metricValue")
	public void setMetricValue(Float metricValue) {
		this.metricValue = metricValue;
	}

	/**
	 * 
	 * @return The metricTime
	 */
	@JsonProperty("metricTime")
	public Object getMetricTime() {
		return metricTime;
	}

	/**
	 * 
	 * @param metricInstant
	 *            The metricInstant
	 */
	@JsonProperty("metricTime")
	public void setMetricTime(Object metricTime) {
		this.metricTime = metricTime;
	}


	/**
	 * 
	 * @return The paasThresholds
	 */
	@JsonProperty("paasThresholds")
	public List<PaasThreshold> getPaasThresholds() {
		return paasThresholds;
	}

	/**
	 * 
	 * @param paasThresholdsList
	 *            The paasThresholdsList
	 */
	@JsonProperty("paasThresholds")
	public void setPaasThresholds(
			List<PaasThreshold> paasThresholds) {
		this.paasThresholds = paasThresholds;
	}

	// USEFUL FOR HISTORY and creating GRAPHS

	/**
	 * 
	 * @return The historyClocks
	 */
	@JsonProperty("historyClocks")
	public List<String> getHistoryClocks() {
		return historyClocks;
	}

	/**
	 * 
	 * @param historyClocks
	 *            The historyClocks
	 */
	@JsonProperty("historyClocks")
	public void setHistoryClock(List<String> historyClocks) {
		this.historyClocks = historyClocks;
	}

	/**
	 * 
	 * @return The historyValues
	 */
	@JsonProperty("historyValues")
	public List<Float> getHistoryValues() {
		return historyValues;
	}

	/**
	 * 
	 * @param historyValues
	 *            The historyValues
	 */
	@JsonProperty("historyValues")
	public void setHistoryValues(List<Float> historyValues) {
		this.historyValues = historyValues;
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}