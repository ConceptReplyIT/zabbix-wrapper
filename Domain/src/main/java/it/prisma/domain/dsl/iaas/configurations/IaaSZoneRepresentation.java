package it.prisma.domain.dsl.iaas.configurations;

import it.prisma.domain.dsl.iaas.BaseIaaSRepresentation;
import it.prisma.domain.dsl.iaas.tenant.IaasEnvironmentRepresentation;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
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
@JsonPropertyOrder({ "prismaZone", "iaasEnvironment", "repositoryBaseURL", "monitoringPillarBaseURL",
	"privateNetworksEnabled", "managementNetworkEnabled", "services" })
public class IaaSZoneRepresentation extends BaseIaaSRepresentation implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("prismaZoneId")
    private Long prismaZoneId;
    @JsonProperty("iaasEnvironmentId")
    private Long iaasEnvironmentId;
    @JsonProperty("iaasEnvironment")
    private IaasEnvironmentRepresentation iaasEnvironment;
    @JsonProperty("repositoryBaseURL")
    private String repositoryBaseURL;
    @JsonProperty("monitoringPillarBaseURL")
    private String monitoringPillarBaseURL;
    @JsonProperty("privateNetworksEnabled")
    private Boolean privateNetworksEnabled;
    @JsonProperty("managementNetworkEnabled")
    private Boolean managementNetworkEnabled;
    @JsonProperty("availabilityZones")
    private Collection<IaaSAvailabilityZoneRepresentation> iaasAvailabilityZones = new HashSet<IaaSAvailabilityZoneRepresentation>(
	    0);
    /**
     * The list of the active services in the zone
     */
    @JsonProperty("services")
    private Collection<String> services = new HashSet<String>(0);

    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("services")
    public Collection<String> getServices() {
	return services;
    }

    @JsonProperty("services")
    public void setServices(Collection<String> services) {
	this.services = services;
    }

    @JsonProperty("availabilityZones")
    public Collection<IaaSAvailabilityZoneRepresentation> getIaasAvailabilityZones() {
	return iaasAvailabilityZones;
    }

    @JsonProperty("availabilityZones")
    public void setIaasAvailabilityZones(Collection<IaaSAvailabilityZoneRepresentation> iaasAvailabilityZones) {
	this.iaasAvailabilityZones = iaasAvailabilityZones;
    }

    @JsonProperty("iaasEnvironment")
    public IaasEnvironmentRepresentation getIaasEnvironment() {
	return iaasEnvironment;
    }

    @JsonProperty("iaasEnvironment")
    public void setIaasEnvironment(IaasEnvironmentRepresentation iaasEnvironment) {
	this.iaasEnvironment = iaasEnvironment;
    }

    /**
     * 
     * @return The prismaZone
     */
    @JsonProperty("prismaZoneId")
    public Long getPrismaZoneId() {
	return prismaZoneId;
    }

    /**
     * 
     * @param prismaZone
     *            The prismaZone
     */
    @JsonProperty("prismaZoneId")
    public void setPrismaZone(Long prismaZoneId) {
	this.prismaZoneId = prismaZoneId;
    }

    /**
     * 
     * @return The iaasEnvironment
     */
    @JsonProperty("iaasEnvironmentId")
    public Long getIaasEnvironmentId() {
	return iaasEnvironmentId;
    }

    /**
     * 
     * @param iaasEnvironment
     *            The iaasEnvironment
     */
    @JsonProperty("iaasEnvironmentId")
    public void setIaasEnvironmentId(Long iaasEnvironmentId) {
	this.iaasEnvironmentId = iaasEnvironmentId;
    }

    /**
     * 
     * @return The repositoryBaseURL
     */
    @JsonProperty("repositoryBaseURL")
    public String getRepositoryBaseURL() {
	return repositoryBaseURL;
    }

    /**
     * 
     * @param repositoryBaseURL
     *            The repositoryBaseURL
     */
    @JsonProperty("repositoryBaseURL")
    public void setRepositoryBaseURL(String repositoryBaseURL) {
	this.repositoryBaseURL = repositoryBaseURL;
    }

    /**
     * 
     * @return The monitoringPillarBaseURL
     */
    @JsonProperty("monitoringPillarBaseURL")
    public String getMonitoringPillarBaseURL() {
	return monitoringPillarBaseURL;
    }

    /**
     * 
     * @param monitoringPillarBaseURL
     *            The monitoringPillarBaseURL
     */
    @JsonProperty("monitoringPillarBaseURL")
    public void setMonitoringPillarBaseURL(String monitoringPillarBaseURL) {
	this.monitoringPillarBaseURL = monitoringPillarBaseURL;
    }

    /**
     * 
     * @return The privateNetworksEnabled
     */
    @JsonProperty("privateNetworksEnabled")
    public Boolean getPrivateNetworksEnabled() {
	return privateNetworksEnabled;
    }

    /**
     * 
     * @param privateNetworksEnabled
     *            The privateNetworksEnabled
     */
    @JsonProperty("privateNetworksEnabled")
    public void setPrivateNetworksEnabled(Boolean privateNetworksEnabled) {
	this.privateNetworksEnabled = privateNetworksEnabled;
    }

    /**
     * 
     * @return The managementNetworkEnabled
     */
    @JsonProperty("managementNetworkEnabled")
    public Boolean getManagementNetworkEnabled() {
	return managementNetworkEnabled;
    }

    /**
     * 
     * @param managementNetworkEnabled
     *            The managementNetworkEnabled
     */
    @JsonProperty("managementNetworkEnabled")
    public void setManagementNetworkEnabled(Boolean managementNetworkEnabled) {
	this.managementNetworkEnabled = managementNetworkEnabled;
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
	return new HashCodeBuilder().append(prismaZoneId).append(iaasEnvironmentId).append(repositoryBaseURL)
		.append(monitoringPillarBaseURL).append(privateNetworksEnabled).append(managementNetworkEnabled)
		.append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
	if (other == this) {
	    return true;
	}
	if ((other instanceof IaaSZoneRepresentation) == false) {
	    return false;
	}
	IaaSZoneRepresentation rhs = ((IaaSZoneRepresentation) other);
	return new EqualsBuilder().append(prismaZoneId, rhs.prismaZoneId)
		.append(iaasEnvironmentId, rhs.iaasEnvironmentId).append(repositoryBaseURL, rhs.repositoryBaseURL)
		.append(monitoringPillarBaseURL, rhs.monitoringPillarBaseURL)
		.append(privateNetworksEnabled, rhs.privateNetworksEnabled)
		.append(managementNetworkEnabled, rhs.managementNetworkEnabled)
		.append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}