package it.reply.monitoringpillar.domain.dsl.monitoring;

import java.util.ArrayList;
import java.util.List;

public enum Zone {

	INFN_BA("INFN_Bari"), INFN_PG("INFN_Perugia");

	private String zone;

	private Zone(String zone) {
		this.zone = zone;
	}

	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public static Zone findByName(String name) {
		for (Zone a : values()) {
			if (a.getZone().equals(name)) {
				return a;
			}
		}
		throw new IllegalArgumentException("Cannot find [" + name + "] for " + Zone.class.getCanonicalName());
	}

	public static List<String> getAllZone() {
		List<String> zones = new ArrayList<String>();
		for (Zone a : values()) {
			zones.add(a.getZone());
		}
		return zones;
	}

}
