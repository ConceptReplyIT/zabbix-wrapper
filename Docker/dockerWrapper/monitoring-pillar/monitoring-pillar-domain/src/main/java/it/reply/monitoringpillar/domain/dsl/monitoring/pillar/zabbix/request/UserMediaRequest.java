package it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.request;

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
@JsonPropertyOrder({ "mediatypeid", "sendto", "active", "severity", "period" })
public class UserMediaRequest {

	@JsonProperty("mediatypeid")
	private String mediatypeid;
	@JsonProperty("sendto")
	private String sendto;
	@JsonProperty("active")
	private Integer active;
	@JsonProperty("severity")
	private Integer severity;
	@JsonProperty("period")
	private String period;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The mediatypeid
	 */
	@JsonProperty("mediatypeid")
	public String getMediatypeid() {
		return mediatypeid;
	}

	/**
	 * 
	 * @param mediatypeid
	 *            The mediatypeid
	 */
	@JsonProperty("mediatypeid")
	public void setMediatypeid(String mediatypeid) {
		this.mediatypeid = mediatypeid;
	}

	/**
	 * 
	 * @return The sendto
	 */
	@JsonProperty("sendto")
	public String getSendto() {
		return sendto;
	}

	/**
	 * 
	 * @param sendto
	 *            The sendto
	 */
	@JsonProperty("sendto")
	public void setSendto(String sendto) {
		this.sendto = sendto;
	}

	/**
	 * 
	 * @return The active
	 */
	@JsonProperty("active")
	public Integer getActive() {
		return active;
	}

	/**
	 * 
	 * @param active
	 *            The active
	 */
	@JsonProperty("active")
	public void setActive(Integer active) {
		this.active = active;
	}

	/**
	 * 
	 * @return The severity
	 */
	@JsonProperty("severity")
	public Integer getSeverity() {
		return severity;
	}

	/**
	 * 
	 * @param severity
	 *            The severity
	 */
	@JsonProperty("severity")
	public void setSeverity(Integer severity) {
		this.severity = severity;
	}

	/**
	 * 
	 * @return The period
	 */
	@JsonProperty("period")
	public String getPeriod() {
		return period;
	}

	/**
	 * 
	 * @param period
	 *            The period
	 */
	@JsonProperty("period")
	public void setPeriod(String period) {
		this.period = period;
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
