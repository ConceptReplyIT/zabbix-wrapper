package it.prisma.domain.dsl.monitoring.webui;

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
@JsonPropertyOrder({
	"values",
	"clocks"
})
public class GraphValueResponse {

	@JsonProperty("valueUnit")
	private String valueUnit;
	@JsonProperty("htmlGraphIndex")
	private int htmlGraphIndex;
	@JsonProperty("htmlElementId")
	private String htmlElementId;
	@JsonProperty("values")
	private List<Float> values = new ArrayList<Float>();
	@JsonProperty("clocks")
	private List<String> clocks = new ArrayList<String>();
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return
	 * The values
	 */
	@JsonProperty("values")
	public List<Float> getValues() {
		return values;
	}

	/**
	 * 
	 * @param values
	 * The values
	 */
	@JsonProperty("values")
	public void setValues(List<Float> values) {
		this.values = values;
	}

	/**
	 * 
	 * @return
	 * The clocks
	 */
	@JsonProperty("clocks")
	public List<String> getClocks() {
		return clocks;
	}

	/**
	 * 
	 * @param clocks
	 * The clocks
	 */
	@JsonProperty("clocks")
	public void setClocks(List<String> clocks) {
		this.clocks = clocks;
	}

	
	public String getValueUnit() {
		return valueUnit;
	}

	public void setValueUnit(String valueUnit) {
		this.valueUnit = valueUnit;
	}
	
	
	public String getHtmlElementId() {
		return htmlElementId;
	}

	public void setHtmlElementId(String htmlElementId) {
		this.htmlElementId = htmlElementId;
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

	public int getHtmlGraphIndex() {
		return htmlGraphIndex;
	}

	public void setHtmlGraphIndex(int htmlGraphIndex) {
		this.htmlGraphIndex = htmlGraphIndex;
	}

}