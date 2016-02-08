package it.prisma.domain.dsl.paas.services.bpmaas;

import it.prisma.domain.dsl.paas.BaseGenericPaaSServiceDeployRequest;
import it.prisma.domain.dsl.paas.services.PaaSServiceRepresentation.PaaSServiceType;
import it.prisma.domain.dsl.paas.services.generic.request.GenericPaaSServiceDeployRequest;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "account", "environment", "queue" })
public class BPMaaSDeployRequest extends
		BaseGenericPaaSServiceDeployRequest<BPMaaSRepresentation> implements
		GenericPaaSServiceDeployRequest<BPMaaSRepresentation> {

	private static final long serialVersionUID = -6699532613602153950L;

	@JsonProperty("bpmaasDetails")
	private BPMaaSDetails bpmaasDetails;

	@Override
	public PaaSServiceType getPaaSServiceType() {
		return PaaSServiceType.BPMaaS;
	}

	@JsonProperty("bpmaasDetails")
	public BPMaaSDetails getBPMaasDetails() {
		return bpmaasDetails;
	}

	@JsonProperty("bpmaasDetails")
	public void setBPMaasDetails(BPMaaSDetails bpmaasDetails) {
		this.bpmaasDetails = bpmaasDetails;
	}

}