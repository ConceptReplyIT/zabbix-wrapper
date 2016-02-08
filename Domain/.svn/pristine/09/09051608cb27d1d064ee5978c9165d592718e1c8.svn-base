package it.prisma.domain.dsl.iaas.configurations;

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
@JsonPropertyOrder({ "id", "name", "description", "reference", "iaasZoneID",
		"iaasType" })
public class IaaSAvailabilityZoneRepresentation extends BaseIaaSRepresentation {

	@JsonProperty("id")
	private Long id;
	@JsonProperty("name")
	private String name;
	@JsonProperty("description")
	private String description;
	@JsonProperty("reference")
	private String reference;
	@JsonProperty("iaasZoneID")
	private Long iaasZoneID;
	@JsonProperty("iaasType")
	private String iaasType;
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
	 * @return The iaasZoneID
	 */
	@JsonProperty("iaasZoneID")
	public Long getIaasZoneID() {
		return iaasZoneID;
	}

	/**
	 * 
	 * @param iaasZoneID
	 *            The iaasZoneID
	 */
	@JsonProperty("iaasZoneID")
	public void setIaasZoneID(Long iaasZoneID) {
		this.iaasZoneID = iaasZoneID;
	}

	/**
	 * 
	 * @return The iaasType
	 */
	@JsonProperty("iaasType")
	public String getIaasType() {
		return iaasType;
	}

	/**
	 * 
	 * @param iaasType
	 *            The iaasType
	 */
	@JsonProperty("iaasType")
	public void setIaasType(String iaasType) {
		this.iaasType = iaasType;
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
		return new HashCodeBuilder().append(id).append(name)
				.append(description).append(reference).append(iaasZoneID)
				.append(iaasType).append(additionalProperties).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof IaaSAvailabilityZoneRepresentation) == false) {
			return false;
		}
		IaaSAvailabilityZoneRepresentation rhs = ((IaaSAvailabilityZoneRepresentation) other);
		return new EqualsBuilder().append(id, rhs.id).append(name, rhs.name)
				.append(description, rhs.description)
				.append(reference, rhs.reference)
				.append(iaasZoneID, rhs.iaasZoneID)
				.append(iaasType, rhs.iaasType)
				.append(additionalProperties, rhs.additionalProperties)
				.isEquals();
	}

}