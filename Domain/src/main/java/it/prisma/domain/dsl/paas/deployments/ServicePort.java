package it.prisma.domain.dsl.paas.deployments;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "service", "port" })
public class ServicePort {

	@JsonProperty("service")
	private String service;
	@JsonProperty("port")
	private String port;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The service
	 */
	@JsonProperty("service")
	public String getService() {
		return service;
	}

	/**
	 * 
	 * @param service
	 *            The service
	 */
	@JsonProperty("service")
	public void setService(String service) {
		this.service = service;
	}

	/**
	 * 
	 * @return The port
	 */
	@JsonProperty("port")
	public String getPort() {
		return port;
	}

	/**
	 * 
	 * @param port
	 *            The port
	 */
	@JsonProperty("port")
	public void setPort(String port) {
		this.port = port;
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

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(service).append(port)
				.append(additionalProperties).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof ServicePort) == false) {
			return false;
		}
		ServicePort rhs = ((ServicePort) other);
		return new EqualsBuilder().append(service, rhs.service)
				.append(port, rhs.port)
				.append(additionalProperties, rhs.additionalProperties)
				.isEquals();
	}

	public static ServicePort findByServiceName(Collection<ServicePort> list,
			String name) {
		for (ServicePort port : list)
			if (port.getService().equals(name))
				return port;
		return null;
	}

}