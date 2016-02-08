package it.prisma.domain.dsl.iaas.tenant;

import it.prisma.domain.dsl.iaas.BaseIaaSRepresentation;

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
@JsonPropertyOrder({ "id", "name", "version", "description", 
					 "identityURL", "identityVersion", "domain"})
public class IaasEnvironmentRepresentation extends BaseIaaSRepresentation implements Serializable {

	private static final long serialVersionUID = 5614939406220856109L;
	
	@JsonProperty("version")
	private String version;
	@JsonProperty("identityURL")
	private String identityURL;
	@JsonProperty("identityVersion")
	private String identityVersion;
	@JsonProperty("domain")
	private String domain;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	
	@JsonProperty("version")
	public String getVersion() {
		return version;
	}

	@JsonProperty("version")
	public void setVersion(String version) {
		this.version = version;
	}
	
	@JsonProperty("identityURL")
	public String getIdentityURL() {
		return identityURL;
	}

	@JsonProperty("identityURL")
	public void setIdentityURL(String identityURL) {
		this.identityURL = identityURL;
	}
	
	@JsonProperty("identityVersion")
	public String getIdentityVersion() {
		return identityVersion;
	}

	@JsonProperty("identityVersion")
	public void setIdentityVersion(String identityVersion) {
		this.identityVersion = identityVersion;
	}
	
	@JsonProperty("domain")
	public String getDomain() {
		return domain;
	}

	@JsonProperty("domain")
	public void setDomain(String domain) {
		this.domain = domain;
	}
	

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object other) {
		return EqualsBuilder.reflectionEquals(this, other);
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}