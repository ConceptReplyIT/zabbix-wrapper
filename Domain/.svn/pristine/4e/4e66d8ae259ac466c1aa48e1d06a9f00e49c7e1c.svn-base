package it.prisma.domain.dsl.paas.services.dbaas.request;

import it.prisma.domain.dsl.paas.BaseGenericPaaSServiceDeployRequest;
import it.prisma.domain.dsl.paas.services.PaaSServiceRepresentation.PaaSServiceType;
import it.prisma.domain.dsl.paas.services.dbaas.DBaaSRepresentation;
import it.prisma.domain.dsl.paas.services.generic.request.GenericPaaSServiceDeployRequest;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "account", "environment", "dbaas" })
public class DBaaSDeployRequest extends
		BaseGenericPaaSServiceDeployRequest<DBaaSRepresentation> implements
		GenericPaaSServiceDeployRequest<DBaaSRepresentation> {

	private static final long serialVersionUID = -6699532613602153950L;

	@JsonProperty("dbaasDetails")
	private DBaaSDetails dbaasDetails;

	@Override
	public PaaSServiceType getPaaSServiceType() {
		return PaaSServiceType.DBaaS;
	}

	@JsonProperty("dbaasDetails")
	public DBaaSDetails getDBaasDetails() {
		return dbaasDetails;
	}

	@JsonProperty("dbaasDetails")
	public void setDBaasDetails(DBaaSDetails dbaasDetails) {
		this.dbaasDetails = dbaasDetails;
	}

}