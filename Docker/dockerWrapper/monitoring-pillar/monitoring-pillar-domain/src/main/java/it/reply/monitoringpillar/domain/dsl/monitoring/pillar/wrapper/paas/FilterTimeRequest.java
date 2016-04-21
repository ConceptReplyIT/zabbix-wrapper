package it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas;

import java.util.HashMap;
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
@JsonPropertyOrder({ "from", "to", "dateFrom", "dateTo" })
public class FilterTimeRequest {

	// @JsonProperty("hostUuids")
	// private List<String> hostUuids = new ArrayList<String>();
	@JsonProperty("from")
	private Long from;
	@JsonProperty("to")
	private Long to;
	@JsonProperty("dateFrom")
	private DateFromParamRequest dateFrom;
	@JsonProperty("dateTo")
	private DateToParamRequest dateTo;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	// /**
	// *
	// * @return
	// * The hostUuids
	// */
	// @JsonProperty("hostUuids")
	// public List<String> getHostUuids() {
	// return hostUuids;
	// }
	//
	// /**
	// *
	// * @param hostUuids
	// * The hostUuids
	// */
	// @JsonProperty("hostUuids")
	// public void setHostUuids(List<String> hostUuids) {
	// this.hostUuids = hostUuids;
	// }

	/**
	 * 
	 * @return The dateFrom
	 */
	@JsonProperty("dateFrom")
	public DateFromParamRequest getDateFrom() {
		return dateFrom;
	}

	/**
	 * 
	 * @param dateFrom
	 *            The dateFrom
	 */
	@JsonProperty("dateFrom")
	public void setDateFrom(DateFromParamRequest dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * 
	 * @return The dateTo
	 */
	@JsonProperty("dateTo")
	public DateToParamRequest getDateTo() {
		return dateTo;
	}

	/**
	 * 
	 * @param dateTo
	 *            The dateTo
	 */
	@JsonProperty("dateTo")
	public void setDateTo(DateToParamRequest dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * 
	 * @return The from
	 */
	@JsonProperty("from")
	public Long getFrom() {
		return from;
	}

	/**
	 * 
	 * @param from
	 *            The from
	 */
	@JsonProperty("from")
	public void setFrom(Long from) {
		this.from = from;
	}

	/**
	 * 
	 * @return The to
	 */
	@JsonProperty("to")
	public Long getTo() {
		return to;
	}

	/**
	 * 
	 * @param to
	 *            The to
	 */
	@JsonProperty("to")
	public void setTo(Long to) {
		this.to = to;
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
