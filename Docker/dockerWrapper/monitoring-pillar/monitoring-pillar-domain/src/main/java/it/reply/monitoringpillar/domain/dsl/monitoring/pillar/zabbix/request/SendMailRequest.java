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
@JsonPropertyOrder({ "username", "password", "scriptName", "sendmailTo" })
public class SendMailRequest {

	@JsonProperty("username")
	private String username;
	@JsonProperty("password")
	private String password;
	@JsonProperty("scriptName")
	private String scriptName;
	@JsonProperty("sendmailTo")
	private String sendmailTo;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The username
	 */
	@JsonProperty("username")
	public String getUsername() {
		return username;
	}

	/**
	 * 
	 * @param username
	 *            The username
	 */
	@JsonProperty("username")
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 
	 * @return The password
	 */
	@JsonProperty("password")
	public String getPassword() {
		return password;
	}

	/**
	 * 
	 * @param password
	 *            The password
	 */
	@JsonProperty("password")
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * 
	 * @return The scriptName
	 */
	@JsonProperty("scriptName")
	public String getScriptName() {
		return scriptName;
	}

	/**
	 * 
	 * @param scriptName
	 *            The scriptName
	 */
	@JsonProperty("scriptName")
	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}

	/**
	 * 
	 * @return The sendmailTo
	 */
	@JsonProperty("sendmailTo")
	public String getSendmailTo() {
		return sendmailTo;
	}

	/**
	 * 
	 * @param sendmailTo
	 *            The sendmailTo
	 */
	@JsonProperty("sendmailTo")
	public void setSendmailTo(String sendmailTo) {
		this.sendmailTo = sendmailTo;
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