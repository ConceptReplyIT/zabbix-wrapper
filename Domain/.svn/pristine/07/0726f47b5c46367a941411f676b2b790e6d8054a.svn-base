package it.prisma.domain.dsl.paas.services;

import java.io.Serializable;
import java.util.Date;
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
@JsonPropertyOrder({ "eventId", "paasServiceId", "paaSServiceName", "paaSServiceType", "type", "createdAt", "details",
	"verbose" })
public class PaaSServiceEventRepresentation implements Serializable {

    private static final long serialVersionUID = 3476394069123404739L;

    @JsonProperty("eventId")
    private Long eventId;
    @JsonProperty("paaSServiceId")
    private Long paaSServiceId;
    @JsonProperty("paaSServiceName")
    private String paaSServiceName;
    @JsonProperty("paaSServiceType")
    private String paaSServiceType;
    @JsonProperty("createdAt")
    private Date createdAt;
    @JsonProperty("type")
    private String type;
    @JsonProperty("details")
    private String details;
    @JsonProperty("verbose")
    private String verbose;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("eventId")
    public Long getEventId() {
	return eventId;
    }

    @JsonProperty("eventId")
    public void setEventId(Long id) {
	eventId = id;
    }

    public Long getPaaSServiceId() {
	return paaSServiceId;
    }

    public void setPaaSServiceId(Long paaSServiceId) {
	this.paaSServiceId = paaSServiceId;
    }

    public String getPaaSServiceName() {
	return paaSServiceName;
    }

    public void setPaaSServiceName(String paaSServiceName) {
	this.paaSServiceName = paaSServiceName;
    }

    public String getPaaSServiceType() {
	return paaSServiceType;
    }

    public void setPaaSServiceType(String paaSServiceType) {
	this.paaSServiceType = paaSServiceType;
    }

    public Date getCreatedAt() {
	return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
	this.createdAt = createdAt;
    }

    public String getType() {
	return type;
    }

    public void setType(String type) {
	this.type = type;
    }

    public String getDetails() {
	return details;
    }

    public void setDetails(String details) {
	this.details = details;
    }

    public String getVerbose() {
	return verbose;
    }

    public void setVerbose(String verbose) {
	this.verbose = verbose;
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
	return additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
	additionalProperties.put(name, value);
    }

}
