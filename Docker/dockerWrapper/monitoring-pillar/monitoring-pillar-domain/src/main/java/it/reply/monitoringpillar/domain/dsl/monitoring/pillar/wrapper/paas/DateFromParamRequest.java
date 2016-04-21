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
	"year",
	"month",
	"day",
	"startTime",
	"time"
})
public class DateFromParamRequest {

	@JsonProperty("year")
	private String year;
	@JsonProperty("month")
	private String month;
	@JsonProperty("day")
	private String day;
	@JsonProperty("startTime")
	private Boolean startTime;
	@JsonProperty("time")
	private TimeParamRequest time;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return
	 * The year
	 */
	@JsonProperty("year")
	public String getYear() {
		return year;
	}

	/**
	 * 
	 * @param year
	 * The year
	 */
	@JsonProperty("year")
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * 
	 * @return
	 * The month
	 */
	@JsonProperty("month")
	public String getMonth() {
		return month;
	}

	/**
	 * 
	 * @param month
	 * The month
	 */
	@JsonProperty("month")
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * 
	 * @return
	 * The day
	 */
	@JsonProperty("day")
	public String getDay() {
		return day;
	}

	/**
	 * 
	 * @param day
	 * The day
	 */
	@JsonProperty("day")
	public void setDay(String day) {
		this.day = day;
	}

	/**
	 * 
	 * @return
	 * The startTime
	 */
	@JsonProperty("startTime")
	public Boolean getStartTime() {
		return startTime;
	}

	/**
	 * 
	 * @param startTime
	 * The startTime
	 */
	@JsonProperty("startTime")
	public void setStartTime(Boolean startTime) {
		this.startTime = startTime;
	}

	/**
	 * 
	 * @return
	 * The time
	 */
	@JsonProperty("time")
	public TimeParamRequest getTime() {
		return time;
	}

	/**
	 * 
	 * @param time
	 * The time
	 */
	@JsonProperty("time")
	public void setTime(TimeParamRequest time) {
		this.time = time;
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