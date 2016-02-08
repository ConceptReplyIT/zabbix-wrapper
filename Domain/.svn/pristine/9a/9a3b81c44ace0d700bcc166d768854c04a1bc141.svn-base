package it.prisma.domain.dsl.iaas.tenant.request;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "id", "username", "url", "version", "iaasTenantId", "iaasZoneId", "iaasZone", "iaasTenant" })
public class CloudifyInstanceCreationRequest {

	@JsonProperty("id")
	private Long id;
	@JsonProperty("username")
	private String username;
	@JsonProperty("password")
	private String password;
	@JsonProperty("url")
	private String url;
	@JsonProperty("version")
	private String version;
	@JsonProperty("iaasTenantId")
	private Long iaasTenantId;
	@JsonProperty("iaasZoneId")
	private Long iaasZoneId;

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
	 * @return The password
	 */
	@JsonProperty("password")
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * @param password
	 *            The password
	 */
	@JsonProperty("password")
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(id).append(username).append(url).append(version).append(iaasTenantId)
				.append(iaasZoneId).append(password).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof CloudifyInstanceCreationRequest) == false) {
			return false;
		}
		CloudifyInstanceCreationRequest rhs = ((CloudifyInstanceCreationRequest) other);
		return new EqualsBuilder().append(id, rhs.id).append(username, rhs.username).append(url, rhs.url)
				.append(version, rhs.version).append(iaasTenantId, rhs.iaasTenantId).append(iaasZoneId, rhs.iaasZoneId)
				.append(password, rhs.password).isEquals();
	}

}