package it.prisma.domain.dsl.paas.services.appaas.request;

import it.prisma.domain.dsl.paas.BaseGenericPaaSServiceDeployRequest;
import it.prisma.domain.dsl.paas.services.PaaSServiceRepresentation.PaaSServiceType;
import it.prisma.domain.dsl.paas.services.appaas.response.APPaaSEnvironmentRepresentation;
import it.prisma.domain.dsl.paas.services.generic.request.GenericPaaSServiceDeployRequest;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * @author Reply
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "account", "serviceDetails", "envDetails" })
public class APPaaSEnvironmentDeployRequest extends
		BaseGenericPaaSServiceDeployRequest<APPaaSEnvironmentRepresentation>
		implements
		GenericPaaSServiceDeployRequest<APPaaSEnvironmentRepresentation> {

	private static final long serialVersionUID = 4377419958022624682L;

	@JsonProperty("environmentDetails")
	private EnvironmentDetails environmentDetails;

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@Override
	public PaaSServiceType getPaaSServiceType() {
		return PaaSServiceType.APPaaSEnvironment;
	}

	public EnvironmentDetails getEnvironmentDetails() {
		return environmentDetails;
	}

	public void setEnvironmentDetails(EnvironmentDetails environmentDetails) {
		this.environmentDetails = environmentDetails;
	}

}
