package it.prisma.domain.dsl.accounting.workgroups.requests;

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
@JsonPropertyOrder({ "label", "description", "organization", "createdByUserAccountId", "firstAdministratorUserAccountId",
	"firstAdministratorPrivilegeId"})
public class WorkgroupCreationRequest {

	@JsonProperty("label")
	private String label;
	@JsonProperty("description")
	private String description;
	@JsonProperty("organization")
	private Long organization;	
	@JsonProperty("createdByUserAccountId")
	private Long createdByUserAccountId;	
 
	
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The label
	 */
	@JsonProperty("label")
	public String getLabel() {
		return label;
	}

	/**
	 * 
	 * @param label
	 *            The label
	 */
	@JsonProperty("label")
	public void setLabel(String label) {
		this.label = label;
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
	 * @return The organization
	 */
	@JsonProperty("organization")
	public Long getOrganization() {
		return organization;
	}

	/**
	 * 
	 * @param organization
	 *            The organization
	 */
	@JsonProperty("organization")
	public void setOrganization(Long organization) {
		this.organization = organization;
	}
	

	/**
	 * 
	 * @return The createdByUserAccountId
	 */
	@JsonProperty("createdByUserAccountId")
	public Long getCreatedByUserAccountId() {
		return createdByUserAccountId;
	}

	/**
	 * 
	 * @param createdByUserAccountId
	 *            The createdByUserAccountId
	 */
	@JsonProperty("createdByUserAccountId")
	public void setCreatedByUserAccountId(Long createdByUserAccountId) {
		this.createdByUserAccountId = createdByUserAccountId;
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
		return new HashCodeBuilder().append(label).append(description)
				.append(createdByUserAccountId).append(additionalProperties)
				.toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof WorkgroupCreationRequest) == false) {
			return false;
		}
		WorkgroupCreationRequest rhs = ((WorkgroupCreationRequest) other);
		return new EqualsBuilder().append(label, rhs.label)
				.append(description, rhs.description)
				.append(createdByUserAccountId, rhs.createdByUserAccountId)
				.append(additionalProperties, rhs.additionalProperties)
				.isEquals();
	}

}