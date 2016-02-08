package it.prisma.domain.dsl.iaas.configurations;

import java.io.Serializable;

public class DNSRecordRepresentation implements Serializable {

	public enum DNSRecordType {
		A, PTR
	}

	private static final long serialVersionUID = -8519085359034507914L;

	private String type;
	private String data;
	private String domainName;
	private Long paasServiceId;
	private Long dnsZoneId;
	private boolean isPrivateDNS;

	public boolean isPrivateDNS() {
		return isPrivateDNS;
	}
	
	public void setPrivateDNS(boolean isPrivateDNS) {
		this.isPrivateDNS = isPrivateDNS;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getDomainName() {
		return domainName;
	}

	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}

	public Long getPaasServiceId() {
		return paasServiceId;
	}

	public void setPaasServiceId(Long paasServiceId) {
		this.paasServiceId = paasServiceId;
	}

	public Long getDnsZoneId() {
		return dnsZoneId;
	}

	public void setDnsZoneId(Long dnsZoneId) {
		this.dnsZoneId = dnsZoneId;
	}
}
