package it.prisma.domain.dsl.iaas.vmaas.request;

import it.prisma.domain.dsl.iaas.vmaas.VMRepresentation;
import it.prisma.domain.dsl.paas.BaseGenericPaaSServiceDeployRequest;
import it.prisma.domain.dsl.paas.services.PaaSServiceRepresentation.PaaSServiceType;
import it.prisma.domain.dsl.paas.services.generic.request.GenericPaaSServiceDeployRequest;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "account", "serviceDetails", "vmDetails" })
public class VMDeployRequest extends
		BaseGenericPaaSServiceDeployRequest<VMRepresentation> implements
		GenericPaaSServiceDeployRequest<VMRepresentation> {

	private static final long serialVersionUID = 5543407260029304641L;

	@JsonProperty("vmDetails")
	private VmDetails vmDetails;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@Override
	public PaaSServiceType getPaaSServiceType() {
		return PaaSServiceType.VMaaS;
	}

	/**
	 * 
	 * @return The vmDetails
	 */
	@JsonProperty("vmDetails")
	public VmDetails getVmDetails() {
		return vmDetails;
	}

	/**
	 * 
	 * @param vmDetails
	 *            The vmDetails
	 */
	@JsonProperty("vmDetails")
	public void setVmDetails(VmDetails vmDetails) {
		this.vmDetails = vmDetails;
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