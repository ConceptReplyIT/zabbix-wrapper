package it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.response;

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
@JsonPropertyOrder({ "actionid", "name", "eventsource", "status", "esc_period", "def_shortdata", "def_longdata",
		"recovery_msg", "r_shortdata", "r_longdata" })
public class ZabbixGetActionResponse {

	@JsonProperty("actionid")
	private String actionid;
	@JsonProperty("name")
	private String name;
	@JsonProperty("eventsource")
	private String eventsource;
	@JsonProperty("status")
	private String status;
	@JsonProperty("esc_period")
	private String escPeriod;
	@JsonProperty("def_shortdata")
	private String defShortdata;
	@JsonProperty("def_longdata")
	private String defLongdata;
	@JsonProperty("recovery_msg")
	private String recoveryMsg;
	@JsonProperty("r_shortdata")
	private String rShortdata;
	@JsonProperty("r_longdata")
	private String rLongdata;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The actionid
	 */
	@JsonProperty("actionid")
	public String getActionid() {
		return actionid;
	}

	/**
	 * 
	 * @param actionid
	 *            The actionid
	 */
	@JsonProperty("actionid")
	public void setActionid(String actionid) {
		this.actionid = actionid;
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

	/**
	 * 
	 * @return The eventsource
	 */
	@JsonProperty("eventsource")
	public String getEventsource() {
		return eventsource;
	}

	/**
	 * 
	 * @param eventsource
	 *            The eventsource
	 */
	@JsonProperty("eventsource")
	public void setEventsource(String eventsource) {
		this.eventsource = eventsource;
	}

	/**
	 * 
	 * @return The status
	 */
	@JsonProperty("status")
	public String getStatus() {
		return status;
	}

	/**
	 * 
	 * @param status
	 *            The status
	 */
	@JsonProperty("status")
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 
	 * @return The escPeriod
	 */
	@JsonProperty("esc_period")
	public String getEscPeriod() {
		return escPeriod;
	}

	/**
	 * 
	 * @param escPeriod
	 *            The esc_period
	 */
	@JsonProperty("esc_period")
	public void setEscPeriod(String escPeriod) {
		this.escPeriod = escPeriod;
	}

	/**
	 * 
	 * @return The defShortdata
	 */
	@JsonProperty("def_shortdata")
	public String getDefShortdata() {
		return defShortdata;
	}

	/**
	 * 
	 * @param defShortdata
	 *            The def_shortdata
	 */
	@JsonProperty("def_shortdata")
	public void setDefShortdata(String defShortdata) {
		this.defShortdata = defShortdata;
	}

	/**
	 * 
	 * @return The defLongdata
	 */
	@JsonProperty("def_longdata")
	public String getDefLongdata() {
		return defLongdata;
	}

	/**
	 * 
	 * @param defLongdata
	 *            The def_longdata
	 */
	@JsonProperty("def_longdata")
	public void setDefLongdata(String defLongdata) {
		this.defLongdata = defLongdata;
	}

	/**
	 * 
	 * @return The recoveryMsg
	 */
	@JsonProperty("recovery_msg")
	public String getRecoveryMsg() {
		return recoveryMsg;
	}

	/**
	 * 
	 * @param recoveryMsg
	 *            The recovery_msg
	 */
	@JsonProperty("recovery_msg")
	public void setRecoveryMsg(String recoveryMsg) {
		this.recoveryMsg = recoveryMsg;
	}

	/**
	 * 
	 * @return The rShortdata
	 */
	@JsonProperty("r_shortdata")
	public String getRShortdata() {
		return rShortdata;
	}

	/**
	 * 
	 * @param rShortdata
	 *            The r_shortdata
	 */
	@JsonProperty("r_shortdata")
	public void setRShortdata(String rShortdata) {
		this.rShortdata = rShortdata;
	}

	/**
	 * 
	 * @return The rLongdata
	 */
	@JsonProperty("r_longdata")
	public String getRLongdata() {
		return rLongdata;
	}

	/**
	 * 
	 * @param rLongdata
	 *            The r_longdata
	 */
	@JsonProperty("r_longdata")
	public void setRLongdata(String rLongdata) {
		this.rLongdata = rLongdata;
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
