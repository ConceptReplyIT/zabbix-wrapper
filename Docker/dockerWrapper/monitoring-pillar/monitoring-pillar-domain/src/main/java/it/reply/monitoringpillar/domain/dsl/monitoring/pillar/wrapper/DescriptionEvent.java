package it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
	"gruopName",
	"hostName",
	"metricName"
})
public class DescriptionEvent {

	@JsonProperty("gruopName")
	private String gruopName;
	@JsonProperty("hostName")
	private String hostName;
	@JsonProperty("metricName")
	private String metricName;
//	@JsonIgnore
//	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return
	 * The gruopName
	 */
	@JsonProperty("gruopName")
	public String getGruopName() {
		return gruopName;
	}

	/**
	 * 
	 * @param gruopName
	 * The gruopName
	 */
	@JsonProperty("gruopName")
	public void setGruopName(String gruopName) {
		this.gruopName = gruopName;
	}

	/**
	 * 
	 * @return
	 * The hostName
	 */
	@JsonProperty("hostName")
	public String getHostName() {
		return hostName;
	}

	/**
	 * 
	 * @param hostName
	 * The hostName
	 */
	@JsonProperty("hostName")
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	/**
	 * 
	 * @return
	 * The metricName
	 */
	@JsonProperty("metricName")
	public String getMetricName() {
		return metricName;
	}

	/**
	 * 
	 * @param metricName
	 * The metricName
	 */
	@JsonProperty("metricName")
	public void setMetricName(String metricName) {
		this.metricName = metricName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

//	@JsonAnyGetter
//	public Map<String, Object> getAdditionalProperties() {
//		return this.additionalProperties;
//	}
//
//	@JsonAnySetter
//	public void setAdditionalProperty(String name, Object value) {
//		this.additionalProperties.put(name, value);
//	}

}