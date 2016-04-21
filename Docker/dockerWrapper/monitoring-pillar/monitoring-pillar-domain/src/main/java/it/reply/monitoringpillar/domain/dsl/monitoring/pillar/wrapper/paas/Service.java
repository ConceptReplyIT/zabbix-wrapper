package it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "serviceName", "paasMetrics" })
public class Service {

	@JsonProperty("serviceName")
	private String serviceName;
	@JsonProperty("paasMetrics")
	private List<PaaSMetric> paasMetrics = new ArrayList<PaaSMetric>();
	// @JsonIgnore
	// private Map<String, Object> additionalProperties = new HashMap<String,
	// Object>();

	/**
	 * 
	 * @return The serviceName
	 */
	@JsonProperty("serviceName")
	public String getServiceName() {
		return serviceName;
	}

	/**
	 * 
	 * @param serviceName
	 *            The serviceName
	 */
	@JsonProperty("serviceName")
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	/**
	 * 
	 * @return The paasMetrics
	 */
	@JsonProperty("paasMetrics")
	public List<PaaSMetric> getPaasMetrics() {
		return paasMetrics;
	}

	/**
	 * 
	 * @param paasMetrics
	 *            The paasMetrics
	 */
	@JsonProperty("paasMetrics")
	public void setPaasMetrics(List<PaaSMetric> paasMetrics) {
		this.paasMetrics = paasMetrics;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
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
	public int hashCode() {
		return new HashCodeBuilder().append(serviceName).append(paasMetrics).
				// append(additionalProperties).
				toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof Service) == false) {
			return false;
		}
		Service rhs = ((Service) other);
		return new EqualsBuilder().append(serviceName, rhs.serviceName).append(paasMetrics, rhs.paasMetrics).
				// append(additionalProperties, rhs.additionalProperties).
				isEquals();
	}

}