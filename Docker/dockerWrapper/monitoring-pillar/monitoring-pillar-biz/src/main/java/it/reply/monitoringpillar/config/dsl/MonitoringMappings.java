
package it.reply.monitoringpillar.config.dsl;

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
@JsonPropertyOrder({ "serviceMonitoring" })
public class MonitoringMappings {

	@JsonProperty("serviceMonitoring")
	private ServiceMonitoring serviceMonitoring;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The serviceMonitoring
	 */
	@JsonProperty("serviceMonitoring")
	public ServiceMonitoring getServiceMonitoring() {
		return serviceMonitoring;
	}

	/**
	 * 
	 * @param serviceMonitoring
	 *            The serviceMonitoring
	 */
	@JsonProperty("serviceMonitoring")
	public void setServiceMonitoring(ServiceMonitoring serviceMonitoring) {
		this.serviceMonitoring = serviceMonitoring;
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