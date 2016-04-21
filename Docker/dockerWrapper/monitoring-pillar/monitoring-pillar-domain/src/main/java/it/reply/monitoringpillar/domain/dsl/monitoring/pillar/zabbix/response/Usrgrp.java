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
@JsonPropertyOrder({ "usrgrpid", "name", "gui_access", "users_status", "debug_mode" })
public class Usrgrp {

	@JsonProperty("usrgrpid")
	private String usrgrpid;
	@JsonProperty("name")
	private String name;
	@JsonProperty("gui_access")
	private String guiAccess;
	@JsonProperty("users_status")
	private String usersStatus;
	@JsonProperty("debug_mode")
	private String debugMode;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The usrgrpid
	 */
	@JsonProperty("usrgrpid")
	public String getUsrgrpid() {
		return usrgrpid;
	}

	/**
	 * 
	 * @param usrgrpid
	 *            The usrgrpid
	 */
	@JsonProperty("usrgrpid")
	public void setUsrgrpid(String usrgrpid) {
		this.usrgrpid = usrgrpid;
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
	 * @return The guiAccess
	 */
	@JsonProperty("gui_access")
	public String getGuiAccess() {
		return guiAccess;
	}

	/**
	 * 
	 * @param guiAccess
	 *            The gui_access
	 */
	@JsonProperty("gui_access")
	public void setGuiAccess(String guiAccess) {
		this.guiAccess = guiAccess;
	}

	/**
	 * 
	 * @return The usersStatus
	 */
	@JsonProperty("users_status")
	public String getUsersStatus() {
		return usersStatus;
	}

	/**
	 * 
	 * @param usersStatus
	 *            The users_status
	 */
	@JsonProperty("users_status")
	public void setUsersStatus(String usersStatus) {
		this.usersStatus = usersStatus;
	}

	/**
	 * 
	 * @return The debugMode
	 */
	@JsonProperty("debug_mode")
	public String getDebugMode() {
		return debugMode;
	}

	/**
	 * 
	 * @param debugMode
	 *            The debug_mode
	 */
	@JsonProperty("debug_mode")
	public void setDebugMode(String debugMode) {
		this.debugMode = debugMode;
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
