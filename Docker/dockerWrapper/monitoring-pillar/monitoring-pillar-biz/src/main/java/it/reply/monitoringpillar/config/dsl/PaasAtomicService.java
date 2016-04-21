package it.reply.monitoringpillar.config.dsl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "active", "passive", "portsMacros", "name" })
public class PaasAtomicService {

	@JsonProperty("active")
	private String active;
	@JsonProperty("passive")
	private String passive;
	@JsonProperty("portsMacros")
	private List<PortsMacro> portsMacros = new ArrayList<PortsMacro>();
	@JsonProperty("name")
	private String name;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The active
	 */
	@JsonProperty("active")
	public String getActive() {
		return active;
	}

	/**
	 * 
	 * @param active
	 *            The active
	 */
	@JsonProperty("active")
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * 
	 * @return The passive
	 */
	@JsonProperty("passive")
	public String getPassive() {
		return passive;
	}

	/**
	 * 
	 * @param passive
	 *            The passive
	 */
	@JsonProperty("passive")
	public void setPassive(String passive) {
		this.passive = passive;
	}

	/**
	 * 
	 * @return The portsMacros
	 */
	@JsonProperty("portsMacros")
	public List<PortsMacro> getPortsMacros() {
		return portsMacros;
	}

	/**
	 * 
	 * @param portsMacros
	 *            The portsMacros
	 */
	@JsonProperty("portsMacros")
	public void setPortsMacros(List<PortsMacro> portsMacros) {
		this.portsMacros = portsMacros;
	}

	/**
	 * 
	 * @return The name
	 */
	@JsonProperty("name")
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name
	 *            The name
	 */
	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	public PortsMacro getPortsMacro(String name) {
		for (PortsMacro portsMacro : getPortsMacros()) {
			if (portsMacro.getName().equalsIgnoreCase(name)) {
				return portsMacro;
			}
		}
		return null;
	}

}
