package it.prisma.domain.dsl.iaas.configurations;

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
@JsonPropertyOrder({ "id", "name", "description", "domainName" })
public class DNSZoneRepresentation {

	@JsonProperty("id")
	private Long id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("description")
	private String description;
	@JsonProperty("domainName")
	private String domainName;
	@JsonProperty("dnsServerURL")
	private String dnsServerURL;
	@JsonProperty("apiType")
	private String apiType;
	@JsonProperty("username")
	private String username;
	@JsonProperty("privateDNS")
	private boolean privateDNS;

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

	@JsonProperty("privateDNS")
	public boolean isPrivateDNS() {
		return privateDNS;
	}

	@JsonProperty("privateDNS")
	public void setPrivateDNS(boolean privateDNS) {
		this.privateDNS = privateDNS;
	}
	
	@JsonProperty("dnsServerURL")
	public String getDnsServerURL() {
		return dnsServerURL;
	}

	@JsonProperty("dnsServerURL")
	public void setDnsServerURL(String dnsServerURL) {
		this.dnsServerURL = dnsServerURL;
	}

	@JsonProperty("apiType")
	public String getApiType() {
		return apiType;
	}

	@JsonProperty("apiType")
	public void setApiType(String apiType) {
		this.apiType = apiType;
	}

	@JsonProperty("username")
	public String getUsername() {
		return username;
	}

	@JsonProperty("username")
	public void setUsername(String username) {
		this.username = username;
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

	/**
	 * 
	 * @return The description
	 */
	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @param description
	 *            The description
	 */
	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 * @return The domainName
	 */
	@JsonProperty("domainName")
	public String getDomainName() {
		return domainName;
	}

	/**
	 * 
	 * @param domainName
	 *            The domainName
	 */
	@JsonProperty("domainName")
	public void setDomainName(String domainName) {
		this.domainName = domainName;
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
		return new HashCodeBuilder().append(id).append(name).append(description).append(domainName)
				.append(additionalProperties).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof DNSZoneRepresentation) == false) {
			return false;
		}
		DNSZoneRepresentation rhs = ((DNSZoneRepresentation) other);
		return new EqualsBuilder().append(id, rhs.id).append(name, rhs.name).append(description, rhs.description)
				.append(domainName, rhs.domainName).append(additionalProperties, rhs.additionalProperties).isEquals();
	}

}