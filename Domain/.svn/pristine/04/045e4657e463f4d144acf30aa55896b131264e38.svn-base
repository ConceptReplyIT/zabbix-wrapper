package it.prisma.domain.dsl.iaas;

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
@JsonPropertyOrder({ "id", "shared", "reference", "name", "description",
		"workgroupId" })
public class IaaSImageRepresentation extends BaseIaaSRepresentation {

	@JsonProperty("id")
	private Long id;
	@JsonProperty("shared")
	private Boolean shared;
	@JsonProperty("reference")
	private String reference;
	@JsonProperty("name")
	private String name;
	@JsonProperty("description")
	private String description;
	@JsonProperty("workgroupId")
	private Long workgroupId;
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
	 * @return The shared
	 */
	@JsonProperty("shared")
	public Boolean getShared() {
		return shared;
	}

	/**
	 * 
	 * @param shared
	 *            The shared
	 */
	@JsonProperty("shared")
	public void setShared(Boolean shared) {
		this.shared = shared;
	}

	/**
	 * 
	 * @return The reference
	 */
	@JsonProperty("reference")
	public String getReference() {
		return reference;
	}

	/**
	 * 
	 * @param reference
	 *            The reference
	 */
	@JsonProperty("reference")
	public void setReference(String reference) {
		this.reference = reference;
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
	 * @return The workgroupId
	 */
	@JsonProperty("workgroupId")
	public Long getWorkgroupId() {
		return workgroupId;
	}

	/**
	 * 
	 * @param workgroupId
	 *            The workgroupId
	 */
	@JsonProperty("workgroupId")
	public void setWorkgroupId(Long workgroupId) {
		this.workgroupId = workgroupId;
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
		return new HashCodeBuilder().append(id).append(shared)
				.append(reference).append(name).append(description)
				.append(workgroupId).append(additionalProperties).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof IaaSImageRepresentation) == false) {
			return false;
		}
		IaaSImageRepresentation rhs = ((IaaSImageRepresentation) other);
		return new EqualsBuilder().append(id, rhs.id)
				.append(shared, rhs.shared).append(reference, rhs.reference)
				.append(name, rhs.name).append(description, rhs.description)
				.append(workgroupId, rhs.workgroupId)
				.append(additionalProperties, rhs.additionalProperties)
				.isEquals();
	}

}