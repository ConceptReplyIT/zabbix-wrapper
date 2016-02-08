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
@JsonPropertyOrder({ "service", "url" })
public class ServiceEndpoint {

	@JsonProperty("name")
	private String name;
	@JsonProperty("url")
	private String url;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The service
	 */
	@JsonProperty("name")
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param service
	 *            The service
	 */
	@JsonProperty("name")
	public void setName(String service) {
		this.name = service;
	}

	/**
	 * 
	 * @return The url
	 */
	@JsonProperty("url")
	public String getURL() {
		return url;
	}

	/**
	 * 
	 * @param url
	 *            The url
	 */
	@JsonProperty("url")
	public void setURL(String url) {
		this.url = url;
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
		return new HashCodeBuilder().append(name).append(url)
				.append(additionalProperties).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof ServiceEndpoint) == false) {
			return false;
		}
		ServiceEndpoint rhs = ((ServiceEndpoint) other);
		return new EqualsBuilder().append(name, rhs.name)
				.append(url, rhs.url)
				.append(additionalProperties, rhs.additionalProperties)
				.isEquals();
	}

	public static ServiceEndpoint findByServiceName(Collection<ServiceEndpoint> list,
			String name) {
		for (ServiceEndpoint ep : list)
			if (ep.getName().equals(name))
				return ep;
		return null;
	}
}