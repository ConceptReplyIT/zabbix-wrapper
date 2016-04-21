package it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
	"hh",
	"mm",
	"ss"
})
public class TimeParamRequest {

	@JsonProperty("hh")
	private String hh;
	@JsonProperty("mm")
	private String mm;
	@JsonProperty("ss")
	private String ss;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return
	 * The hh
	 */
	@JsonProperty("hh")
	public String getHh() {
		return hh;
	}

	/**
	 * 
	 * @param hh
	 * The hh
	 */
	@JsonProperty("hh")
	public void setHh(String hh) {
		this.hh = hh;
	}

	/**
	 * 
	 * @return
	 * The mm
	 */
	@JsonProperty("mm")
	public String getMm() {
		return mm;
	}

	/**
	 * 
	 * @param mm
	 * The mm
	 */
	@JsonProperty("mm")
	public void setMm(String mm) {
		this.mm = mm;
	}

	/**
	 * 
	 * @return
	 * The ss
	 */
	@JsonProperty("ss")
	public String getSs() {
		return ss;
	}

	/**
	 * 
	 * @param ss
	 * The ss
	 */
	@JsonProperty("ss")
	public void setSs(String ss) {
		this.ss = ss;
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

}