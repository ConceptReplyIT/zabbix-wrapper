package it.prisma.domain.dsl.service;

public enum PrismaServiceType {

	APPaaS("APPaaS"), BIaaS("BIaaS"), CAaaS("CAaaS"), DBaaS("DBaaS"), EMAILaaS("EMAILaaS"), MQaaS("MQaaS"), SMSaaS(
			"SMSaaS"), VMaaS("VMaaS"), BPMaaS("BPMaaS"), ODaaS("ODaaS");
	String type;

	PrismaServiceType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public static PrismaServiceType findByType(String type) {
		for (PrismaServiceType serviceType : values()) {
			if (serviceType.type == type) {
				return serviceType;
			}
		}
		throw new IllegalArgumentException("No matching constant for [" + type + "]");
	}

	@Override
	public String toString() {
		return type;
	}
}