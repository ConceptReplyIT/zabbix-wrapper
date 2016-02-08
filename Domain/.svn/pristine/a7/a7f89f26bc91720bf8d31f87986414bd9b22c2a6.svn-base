package it.prisma.domain.dsl.paas.services.appaas.request;

import it.prisma.domain.dsl.paas.BaseGenericPaaSServiceDeployRequest;
import it.prisma.domain.dsl.paas.services.PaaSServiceRepresentation.PaaSServiceType;
import it.prisma.domain.dsl.paas.services.appaas.response.APPaaSRepresentation;
import it.prisma.domain.dsl.paas.services.generic.request.GenericPaaSServiceDeployRequest;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "userId", "tenantId", "applicationParams",
		"environmentParams" })
public class APPaaSDeployRequest extends
		BaseGenericPaaSServiceDeployRequest<APPaaSRepresentation> implements
		GenericPaaSServiceDeployRequest<APPaaSRepresentation> {

	private static final long serialVersionUID = 4377419958022624682L;

	@JsonProperty("environmentDetails")
	private EnvironmentDetails environmentDetails;

	@Override
	public PaaSServiceType getPaaSServiceType() {
		return PaaSServiceType.APPaaS;
	}

}
