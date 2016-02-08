package it.prisma.domain.dsl.accounting.users;

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
@JsonPropertyOrder({ "userAccountId", "firstName", "lastName", "access" })
public class UserAccessRepresentation {

	@JsonProperty("userAccountId")
	private Long userAccountId;
	@JsonProperty("firstName")
	private String firstName;
	@JsonProperty("lastName")
	private String lastName;
	@JsonProperty("access")
	private String access;
	@JsonProperty("ipAddress")
	private String ipAddress;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The userAccountId
	 */
	@JsonProperty("userAccountId")
	public Long getUserAccountId() {
		return userAccountId;
	}

	/**
	 * 
	 * @param userAccountId
	 *            The userAccountId
	 */
	@JsonProperty("userAccountId")
	public void setUserAccountId(Long userAccountId) {
		this.userAccountId = userAccountId;
	}

	public UserAccessRepresentation withUserAccountId(Long userAccountId) {
		this.userAccountId = userAccountId;
		return this;
	}

	@JsonProperty("ipAddress")
	public String getIpAddress() {
		return ipAddress;
	}

	@JsonProperty("ipAddress")
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public UserAccessRepresentation withIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
		return this;
	}

	/**
	 * 
	 * @return The firstName
	 */
	@JsonProperty("firstName")
	public String getFirstName() {
		return firstName;
	}

	/**
	 * 
	 * @param firstName
	 *            The firstName
	 */
	@JsonProperty("firstName")
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public UserAccessRepresentation withFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}

	/**
	 * 
	 * @return The lastName
	 */
	@JsonProperty("lastName")
	public String getLastName() {
		return lastName;
	}

	/**
	 * 
	 * @param lastName
	 *            The lastName
	 */
	@JsonProperty("lastName")
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public UserAccessRepresentation withLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}

	/**
	 * 
	 * @return The access
	 */
	@JsonProperty("access")
	public String getAccess() {
		return access;
	}

	/**
	 * 
	 * @param access
	 *            The access
	 */
	@JsonProperty("access")
	public void setAccess(String access) {
		this.access = access;
	}

	public UserAccessRepresentation withAccess(String access) {
		this.access = access;
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

	public UserAccessRepresentation withAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
		return this;
	}

}