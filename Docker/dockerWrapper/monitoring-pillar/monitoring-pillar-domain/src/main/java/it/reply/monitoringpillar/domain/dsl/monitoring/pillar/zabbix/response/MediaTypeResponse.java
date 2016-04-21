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
@JsonPropertyOrder({ "mediatypeid", "type", "description", "smtp_server", "smtp_helo", "smtp_email", "exec_path",
		"gsm_modem", "username", "passwd", "status" })
public class MediaTypeResponse {

	@JsonProperty("mediatypeid")
	private String mediatypeid;
	@JsonProperty("type")
	private String type;
	@JsonProperty("description")
	private String description;
	@JsonProperty("smtp_server")
	private String smtpServer;
	@JsonProperty("smtp_helo")
	private String smtpHelo;
	@JsonProperty("smtp_email")
	private String smtpEmail;
	@JsonProperty("exec_path")
	private String execPath;
	@JsonProperty("gsm_modem")
	private String gsmModem;
	@JsonProperty("username")
	private String username;
	@JsonProperty("passwd")
	private String passwd;
	@JsonProperty("status")
	private String status;
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
	 * @return The type
	 */
	@JsonProperty("type")
	public String getType() {
		return type;
	}

	/**
	 * 
	 * @param type
	 *            The type
	 */
	@JsonProperty("type")
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 * @return The description
	 */
	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @param description
	 *            The description
	 */
	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 * @return The smtpServer
	 */
	@JsonProperty("smtp_server")
	public String getSmtpServer() {
		return smtpServer;
	}

	/**
	 * 
	 * @param smtpServer
	 *            The smtp_server
	 */
	@JsonProperty("smtp_server")
	public void setSmtpServer(String smtpServer) {
		this.smtpServer = smtpServer;
	}

	/**
	 * 
	 * @return The smtpHelo
	 */
	@JsonProperty("smtp_helo")
	public String getSmtpHelo() {
		return smtpHelo;
	}

	/**
	 * 
	 * @param smtpHelo
	 *            The smtp_helo
	 */
	@JsonProperty("smtp_helo")
	public void setSmtpHelo(String smtpHelo) {
		this.smtpHelo = smtpHelo;
	}

	/**
	 * 
	 * @return The smtpEmail
	 */
	@JsonProperty("smtp_email")
	public String getSmtpEmail() {
		return smtpEmail;
	}

	/**
	 * 
	 * @param smtpEmail
	 *            The smtp_email
	 */
	@JsonProperty("smtp_email")
	public void setSmtpEmail(String smtpEmail) {
		this.smtpEmail = smtpEmail;
	}

	/**
	 * 
	 * @return The execPath
	 */
	@JsonProperty("exec_path")
	public String getExecPath() {
		return execPath;
	}

	/**
	 * 
	 * @param execPath
	 *            The exec_path
	 */
	@JsonProperty("exec_path")
	public void setExecPath(String execPath) {
		this.execPath = execPath;
	}

	/**
	 * 
	 * @return The gsmModem
	 */
	@JsonProperty("gsm_modem")
	public String getGsmModem() {
		return gsmModem;
	}

	/**
	 * 
	 * @param gsmModem
	 *            The gsm_modem
	 */
	@JsonProperty("gsm_modem")
	public void setGsmModem(String gsmModem) {
		this.gsmModem = gsmModem;
	}

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
	 * @return The passwd
	 */
	@JsonProperty("passwd")
	public String getPasswd() {
		return passwd;
	}

	/**
	 * 
	 * @param passwd
	 *            The passwd
	 */
	@JsonProperty("passwd")
	public void setPasswd(String passwd) {
		this.passwd = passwd;
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
