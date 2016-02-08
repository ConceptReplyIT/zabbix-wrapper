package it.prisma.domain.dsl.iaas.configurations;

import it.prisma.domain.dsl.iaas.tenant.IaaSTenantRepresentation;

import java.io.Serializable;
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
@JsonPropertyOrder({ "id", "username", "url", "version", "iaasTenantId", "iaasZoneId", "iaasZone", "iaasTenant" })
public class CloudifyInstanceRepresentation implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@JsonProperty("id")
	private Long id;
	@JsonProperty("username")
	private String username;
	@JsonProperty("url")
	private String url;
	@JsonProperty("version")
	private String version;
	@JsonProperty("iaasTenantId")
	private Long iaasTenantId;
	@JsonProperty("iaasZoneId")
	private Long iaasZoneId;
	@JsonProperty("iaasZone")
	private IaaSZoneRepresentation iaasZone;
	@JsonProperty("iaasTenant")
	private IaaSTenantRepresentation iaasTenant;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

	/**
	 * 
	 * @return The username
	 */
	@JsonProperty("username")
	public String getUsername() {
		return username;
	}

	/**
	 * 
	 * @param username
	 *            The username
	 */
	@JsonProperty("username")
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 
	 * @return The url
	 */
	@JsonProperty("url")
	public String getUrl() {
		return url;
	}

	/**
	 * 
	 * @param url
	 *            The url
	 */
	@JsonProperty("url")
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 
	 * @return The version
	 */
	@JsonProperty("version")
	public String getVersion() {
		return version;
	}

	/**
	 * 
	 * @param version
	 *            The version
	 */
	@JsonProperty("version")
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * 
	 * @return The iaasTenantId
	 */
	@JsonProperty("iaasTenantId")
	public Long getIaasTenantId() {
		return iaasTenantId;
	}

	/**
	 * 
	 * @param iaasTenantId
	 *            The iaasTenantId
	 */
	@JsonProperty("iaasTenantId")
	public void setIaasTenantId(Long iaasTenantId) {
		this.iaasTenantId = iaasTenantId;
	}

	/**
	 * 
	 * @return The iaasZoneId
	 */
	@JsonProperty("iaasZoneId")
	public Long getIaasZoneId() {
		return iaasZoneId;
	}

	/**
	 * 
	 * @param iaasZoneId
	 *            The iaasZoneId
	 */
	@JsonProperty("iaasZoneId")
	public void setIaasZoneId(Long iaasZoneId) {
		this.iaasZoneId = iaasZoneId;
	}

	/**
	 * 
	 * @return The iaasZone
	 */
	@JsonProperty("iaasZone")
	public IaaSZoneRepresentation getIaasZone() {
		return iaasZone;
	}

	/**
	 * 
	 * @param iaasZone
	 *            The iaasZone
	 */
	@JsonProperty("iaasZone")
	public void setIaasZone(IaaSZoneRepresentation iaasZone) {
		this.iaasZone = iaasZone;
	}

	/**
	 * 
	 * @return The iaasTenant
	 */
	@JsonProperty("iaasTenant")
	public IaaSTenantRepresentation getIaasTenant() {
		return iaasTenant;
	}

	/**
	 * 
	 * @param iaasTenant
	 *            The iaasTenant
	 */
	@JsonProperty("iaasTenant")
	public void setIaasTenant(IaaSTenantRepresentation iaasTenant) {
		this.iaasTenant = iaasTenant;
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
		return new HashCodeBuilder().append(id).append(username).append(url).append(version).append(iaasTenantId)
				.append(iaasZoneId).append(iaasZone).append(iaasTenant).append(additionalProperties).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof CloudifyInstanceRepresentation) == false) {
			return false;
		}
		CloudifyInstanceRepresentation rhs = ((CloudifyInstanceRepresentation) other);
		return new EqualsBuilder().append(id, rhs.id).append(username, rhs.username).append(url, rhs.url)
				.append(version, rhs.version).append(iaasTenantId, rhs.iaasTenantId).append(iaasZoneId, rhs.iaasZoneId)
				.append(iaasZone, rhs.iaasZone).append(iaasTenant, rhs.iaasTenant)
				.append(additionalProperties, rhs.additionalProperties).isEquals();
	}

}