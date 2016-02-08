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
@JsonPropertyOrder({ "id", "name", "description" })
public class PrismaZoneRepresentation {

    @JsonProperty("id")
    private Long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("iaasZoneId")
    private Long iaasZoneId;
    @JsonProperty("iaasZone")
    private IaaSZoneRepresentation iaasZone;
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

    @JsonProperty("iaasZoneId")
    public Long getIaasZoneId() {
	return iaasZoneId;
    }

    @JsonProperty("iaasZoneId")
    public void setIaasZoneId(Long iaasZoneId) {
	this.iaasZoneId = iaasZoneId;
    }

    @JsonProperty("iaasZone")
    public IaaSZoneRepresentation getIaasZone() {
	return iaasZone;
    }

    @JsonProperty("iaasZone")
    public void setIaasZone(IaaSZoneRepresentation iaasZone) {
	this.iaasZone = iaasZone;
    }

    @Override
    public String toString() {
	return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
	return additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
	additionalProperties.put(name, value);
    }

    @Override
    public int hashCode() {
	return new HashCodeBuilder().append(id).append(name).append(description).append(additionalProperties)
		.toHashCode();
    }

    @Override
    public boolean equals(Object other) {
	if (other == this) {
	    return true;
	}
	if ((other instanceof PrismaZoneRepresentation) == false) {
	    return false;
	}
	PrismaZoneRepresentation rhs = ((PrismaZoneRepresentation) other);
	return new EqualsBuilder().append(id, rhs.id).append(name, rhs.name).append(description, rhs.description)
		.append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}