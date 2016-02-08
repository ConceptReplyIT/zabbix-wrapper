package it.prisma.domain.dsl.iaas.openstack.compute.response.listKey;

import it.prisma.domain.dsl.iaas.openstack.compute.response.listKey.Keypair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
@JsonPropertyOrder({ "keypairs" })
public class OpenstackStackListPairResponse {

	@JsonProperty("keypairs")
	private List<Keypair> keypairs = new ArrayList<Keypair>();
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The keypairs
	 */
	@JsonProperty("keypairs")
	public List<Keypair> getKeypairs() {
		return keypairs;
	}

	/**
	 * 
	 * @param keypairs
	 *            The keypairs
	 */
	@JsonProperty("keypairs")
	public void setKeypairs(List<Keypair> keypairs) {
		this.keypairs = keypairs;
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
		return new HashCodeBuilder().append(keypairs)
				.append(additionalProperties).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof OpenstackStackListPairResponse) == false) {
			return false;
		}
		OpenstackStackListPairResponse rhs = ((OpenstackStackListPairResponse) other);
		return new EqualsBuilder().append(keypairs, rhs.keypairs)
				.append(additionalProperties, rhs.additionalProperties)
				.isEquals();
	}
}