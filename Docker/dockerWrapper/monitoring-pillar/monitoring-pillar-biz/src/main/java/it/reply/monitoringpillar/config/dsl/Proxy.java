
package it.reply.monitoringpillar.config.dsl;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Proxy {

	@JsonProperty("name")
	private String name;
	@JsonProperty("nameTemplate")
	private String nameTemplate;

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

	@JsonProperty("nameTemplate")
	public String getNameTemplate() {
		return nameTemplate;
	}

	@JsonProperty("nameTemplate")
	public void setNameTemplate(String nameTemplate) {
		this.nameTemplate = nameTemplate;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
