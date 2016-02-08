package it.prisma.domain.dsl.iaas.openstack.keypairs;

import it.prisma.domain.dsl.iaas.BaseIaaSRepresentation;

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
@JsonPropertyOrder({ "name", "publicKey", "fingerprint", "privateKey" })
public class KeypairRepresentation extends BaseIaaSRepresentation {

	@JsonProperty("id")
	private Long id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("publicKey")
	private String publicKey;
	@JsonProperty("fingerprint")
	private String fingerprint;
	@JsonProperty("privateKey")
	private String privateKey;
	@JsonProperty("zoneId")
	private Long zoneId;
	@JsonProperty("paasServiceKey")
	private boolean paasServiceKey;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public KeypairRepresentation() {
	}

	public KeypairRepresentation(Long id, Long zoneId, String name, String publicKey, String fingerprint,
			String privateKey, boolean paasServiceKey) {
		this.id = id;
		this.name = name;
		this.publicKey = publicKey;
		this.fingerprint = fingerprint;
		this.privateKey = privateKey;
		this.zoneId = zoneId;
		this.paasServiceKey = paasServiceKey;
	}

	/**
	 * 
	 * @return The name
	 */
	@JsonProperty("name")
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 *            The name
	 */
	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	public KeypairRepresentation withName(String name) {
		this.name = name;
		return this;
	}

	@JsonProperty("paasServiceKey")
	public boolean isPaasServiceKey() {
		return paasServiceKey;
	}

	@JsonProperty("paasServiceKey")
	public void setPaasServiceKey(boolean paasServiceKey) {
		this.paasServiceKey = paasServiceKey;
	}

	/**
	 * 
	 * @return The name
	 */
	@JsonProperty("zoneId")
	public Long getZoneId() {
		return zoneId;
	}

	/**
	 * 
	 * @param name
	 *            The name
	 */
	@JsonProperty("zoneId")
	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
	}

	public KeypairRepresentation withZoneId(Long zoneId) {
		this.zoneId = zoneId;
		return this;
	}

	/**
	 * 
	 * @return The id
	 */
	@JsonProperty("id")
	public Long getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 *            The id
	 */
	@JsonProperty("id")
	public void setId(Long id) {
		this.id = id;
	}

	public KeypairRepresentation withId(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * 
	 * @return The publicKey
	 */
	@JsonProperty("publicKey")
	public String getPublicKey() {
		return publicKey;
	}

	/**
	 * 
	 * @param publicKey
	 *            The publicKey
	 */
	@JsonProperty("publicKey")
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public KeypairRepresentation withPublicKey(String publicKey) {
		this.publicKey = publicKey;
		return this;
	}

	/**
	 * 
	 * @return The fingerprint
	 */
	@JsonProperty("fingerprint")
	public String getFingerprint() {
		return fingerprint;
	}

	/**
	 * 
	 * @param fingerprint
	 *            The fingerprint
	 */
	@JsonProperty("fingerprint")
	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}

	public KeypairRepresentation withFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
		return this;
	}

	/**
	 * 
	 * @return The privateKey
	 */
	@JsonProperty("privateKey")
	public String getPrivateKey() {
		return privateKey;
	}

	/**
	 * 
	 * @param privateKey
	 *            The privateKey
	 */
	@JsonProperty("privateKey")
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public KeypairRepresentation withPrivateKey(String privateKey) {
		this.privateKey = privateKey;
		return this;
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

	public KeypairRepresentation withAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
		return this;
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(name).append(publicKey).append(fingerprint).append(privateKey)
				.append(additionalProperties).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof KeypairRepresentation) == false) {
			return false;
		}
		KeypairRepresentation rhs = ((KeypairRepresentation) other);
		return new EqualsBuilder().append(name, rhs.name).append(publicKey, rhs.publicKey)
				.append(fingerprint, rhs.fingerprint).append(privateKey, rhs.privateKey)
				.append(additionalProperties, rhs.additionalProperties).isEquals();
	}

}