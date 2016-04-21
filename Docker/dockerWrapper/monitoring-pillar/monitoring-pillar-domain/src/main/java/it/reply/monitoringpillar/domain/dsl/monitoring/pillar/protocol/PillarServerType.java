package it.reply.monitoringpillar.domain.dsl.monitoring.pillar.protocol;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "servers" })
public class PillarServerType {

    @JsonProperty("servers")
    private List<String> servers = new ArrayList<>();

    /**
     * 
     * @return The servers
     */
    @JsonProperty("servers")
    public List<String> getServers() {
	return servers;
    }

    /**
     * 
     * @param servers
     *            The servers
     */
    @JsonProperty("servers")
    public void setServers(List<String> servers) {
	this.servers = servers;
    }

    @Override
    public String toString() {
	return ToStringBuilder.reflectionToString(this);
    }

    public PillarServerType withServers(List<String> servers) {
	this.servers = servers;
	return this;
    }

}
