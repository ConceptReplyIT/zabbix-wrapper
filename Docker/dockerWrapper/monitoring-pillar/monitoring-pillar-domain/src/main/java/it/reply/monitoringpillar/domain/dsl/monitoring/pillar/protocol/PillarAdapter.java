package it.reply.monitoringpillar.domain.dsl.monitoring.pillar.protocol;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({ "adapters" })
public class PillarAdapter {

    @JsonProperty("adapters")
    private List<String> adapters = new ArrayList<String>();

    /**
     * 
     * @return The adapters
     */
    @JsonProperty("adapters")
    public List<String> getAdapters() {
	return adapters;
    }

    /**
     * 
     * @param list
     *            The adapters
     */
    @JsonProperty("adapters")
    public void setAdapters(List<String> list) {
	this.adapters = list;
    }

    public PillarAdapter withAdapters(List<String> adapters) {
	this.adapters = adapters;
	return this;
    }

}