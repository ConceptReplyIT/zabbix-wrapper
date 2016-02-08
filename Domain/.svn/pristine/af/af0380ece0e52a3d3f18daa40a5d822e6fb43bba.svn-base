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
@JsonPropertyOrder({ "network", "ip" })
public class InstanceIP {

	@JsonProperty("network")
	private String network;
	@JsonProperty("ip")
	private String ip;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The network
	 */
	@JsonProperty("network")
	public String getnetwork() {
		return network;
	}

	/**
	 * 
	 * @param network
	 *            The network
	 */
	@JsonProperty("network")
	public void setnetwork(String network) {
		this.network = network;
	}

	/**
	 * 
	 * @return The ip
	 */
	@JsonProperty("ip")
	public String getIP() {
		return ip;
	}

	/**
	 * 
	 * @param ip
	 *            The ip
	 */
	@JsonProperty("ip")
	public void setIP(String ip) {
		this.ip = ip;
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
		return new HashCodeBuilder().append(network).append(ip)
				.append(additionalProperties).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof InstanceIP) == false) {
			return false;
		}
		InstanceIP rhs = ((InstanceIP) other);
		return new EqualsBuilder().append(network, rhs.network)
				.append(ip, rhs.ip)
				.append(additionalProperties, rhs.additionalProperties)
				.isEquals();
	}

	public static InstanceIP findBynetworkName(Collection<InstanceIP> list,
			String name) {
		for (InstanceIP ep : list)
			if (ep.getnetwork().equals(name))
				return ep;
		return null;
	}
}