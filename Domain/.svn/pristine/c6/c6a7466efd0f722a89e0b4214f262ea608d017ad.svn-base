package it.prisma.domain.dsl.paas.services.odaas;

import it.prisma.domain.dsl.paas.BaseGenericPaaSServiceDeployRequest;
import it.prisma.domain.dsl.paas.services.PaaSServiceRepresentation.PaaSServiceType;
import it.prisma.domain.dsl.paas.services.generic.request.GenericPaaSServiceDeployRequest;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
public class ODaaSDeployRequest extends
		BaseGenericPaaSServiceDeployRequest<ODaaSRepresentation> implements
		GenericPaaSServiceDeployRequest<ODaaSRepresentation> {

	private static final long serialVersionUID = -6699532613602153950L;

	@JsonProperty("odaasDetails")
	private ODaaSDetails odaasDetails;

	@Override
	public PaaSServiceType getPaaSServiceType() {
		return PaaSServiceType.ODaaS;
	}

	@JsonProperty("odaasDetails")
	public ODaaSDetails getODaasDetails() {
		return odaasDetails;
	}

	@JsonProperty("odaasDetails")
	public void setODaasDetails(ODaaSDetails odaasDetails) {
		this.odaasDetails = odaasDetails;
	}

}