package it.prisma.domain.dsl.paas.services.mqaas;

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
public class MQaaSDeployRequest extends
		BaseGenericPaaSServiceDeployRequest<MQaaSRepresentation> implements
		GenericPaaSServiceDeployRequest<MQaaSRepresentation> {

	private static final long serialVersionUID = -6699532613602153950L;

	@JsonProperty("mqaasDetails")
	private MQaaSDetails mqaasDetails;

	@Override
	public PaaSServiceType getPaaSServiceType() {
		return PaaSServiceType.MQaaS;
	}

	@JsonProperty("mqaasDetails")
	public MQaaSDetails getMQaasDetails() {
		return mqaasDetails;
	}

	@JsonProperty("mqaasDetails")
	public void setMQaasDetails(MQaaSDetails queueDetails) {
		this.mqaasDetails = queueDetails;
	}

}