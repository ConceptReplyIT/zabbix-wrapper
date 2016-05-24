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
@JsonPropertyOrder({ "name", "iaasAtomicServices" })
public class IaasCategoryService {

	@JsonProperty("name")
	private String name;
	@JsonProperty("iaasAtomicServices")
	private List<IaasAtomicService> iaasAtomicServices = new ArrayList<IaasAtomicService>();
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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

	/**
	 * 
	 * @return The iaasAtomicServices
	 */
	@JsonProperty("iaasAtomicServices")
	public List<IaasAtomicService> getIaasAtomicServices() {
		return iaasAtomicServices;
	}

	/**
	 * 
	 * @param iaasAtomicServices
	 *            The iaasAtomicServices
	 */
	@JsonProperty("iaasAtomicServices")
	public void setIaasAtomicServices(List<IaasAtomicService> iaasAtomicServices) {
		this.iaasAtomicServices = iaasAtomicServices;
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

	public IaasAtomicService getIaasAtomicService(String name) {
		for (IaasAtomicService iaasAtomicService : getIaasAtomicServices()) {
			if (iaasAtomicService.getName().equalsIgnoreCase(name)) {
				return iaasAtomicService;
			}
		}
		return null;
	}

}