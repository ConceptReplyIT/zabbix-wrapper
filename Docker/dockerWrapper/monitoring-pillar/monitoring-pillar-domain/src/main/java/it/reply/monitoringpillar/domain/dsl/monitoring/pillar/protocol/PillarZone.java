package it.reply.monitoringpillar.domain.dsl.monitoring.pillar.protocol;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PillarZone {

    @JsonProperty("zones")
    private List<String> zones = new ArrayList<String>();

    /**
     * 
     * @return The adapters
     */
    @JsonProperty("zones")
    public List<String> getZones() {
	return zones;
    }

    /**
     * 
     * @param list
     *            The zones
     */
    @JsonProperty("zones")
    public void setZones(List<String> list) {
	this.zones = list;
    }

    public PillarZone withZones (List<String> zones) {
	this.zones = zones;
	return this;
    }
	
}
