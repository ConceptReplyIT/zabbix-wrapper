package it.prisma.domain.dsl.paas;

import it.prisma.domain.dsl.paas.services.GenericPaaSServiceRepresentation;
import it.prisma.domain.dsl.paas.services.generic.request.Account;
import it.prisma.domain.dsl.paas.services.generic.request.GenericPaaSServiceDeployRequest;
import it.prisma.domain.dsl.paas.services.generic.request.ServiceDetails;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public abstract class BaseGenericPaaSServiceDeployRequest<T extends GenericPaaSServiceRepresentation> implements
		GenericPaaSServiceDeployRequest<T> {

	private static final long serialVersionUID = 1L;
	
	protected Long zoneId;
	protected Account account;
	protected ServiceDetails serviceDetails;

	@Override
	public Long getZoneId() {
		return zoneId;
	}

	@Override
	public void setZoneId(Long zoneId) {
		this.zoneId = zoneId;
	}

	@Override
	public Account getAccount() {
		return account;
	}

	@Override
	public void setAccount(Account account) {
		this.account = account;
	}

	@Override
	public ServiceDetails getServiceDetails() {
		return serviceDetails;
	}

	@Override
	public void setServiceDetails(ServiceDetails serviceDetails) {
		this.serviceDetails = serviceDetails;
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
}
