package it.prisma.domain.dsl.paas.services.biaas.request;

import it.prisma.domain.dsl.paas.BaseGenericPaaSServiceDeployRequest;
import it.prisma.domain.dsl.paas.services.PaaSServiceRepresentation.PaaSServiceType;
import it.prisma.domain.dsl.paas.services.biaas.BIaaSRepresentation;
import it.prisma.domain.dsl.paas.services.generic.request.GenericPaaSServiceDeployRequest;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "account", "environment", "biaas" })
public class BIaaSDeployRequest extends
		BaseGenericPaaSServiceDeployRequest<BIaaSRepresentation> implements
		GenericPaaSServiceDeployRequest<BIaaSRepresentation> {

	private static final long serialVersionUID = -6699532613602153950L;

	@JsonProperty("biaasDetails")
	private BIaaSDetails biaasDetails;

	@Override
	public PaaSServiceType getPaaSServiceType() {
		return PaaSServiceType.BIaaS;
	}

	@JsonProperty("biaasDetails")
	public BIaaSDetails getBIaasDetails() {
		return biaasDetails;
	}

	@JsonProperty("biaasDetails")
	public void setBIaasDetails(BIaaSDetails biaasDetails) {
		this.biaasDetails = biaasDetails;
	}

}