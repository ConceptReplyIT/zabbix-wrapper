package it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.response;

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
@JsonPropertyOrder({ "userid", "alias", "name", "surname", "url", "autologin", "autologout", "lang", "refresh", "type",
		"theme", "attempt_failed", "attempt_ip", "attempt_clock", "rows_per_page", "usrgrps", "mediatypes" })

public class ZabbixGetUserResponse {

	@JsonProperty("userid")
	private String userid;
	@JsonProperty("alias")
	private String alias;
	@JsonProperty("name")
	private String name;
	@JsonProperty("surname")
	private String surname;
	@JsonProperty("url")
	private String url;
	@JsonProperty("autologin")
	private String autologin;
	@JsonProperty("autologout")
	private String autologout;
	@JsonProperty("lang")
	private String lang;
	@JsonProperty("refresh")
	private String refresh;
	@JsonProperty("type")
	private String type;
	@JsonProperty("theme")
	private String theme;
	@JsonProperty("attempt_failed")
	private String attemptFailed;
	@JsonProperty("attempt_ip")
	private String attemptIp;
	@JsonProperty("attempt_clock")
	private String attemptClock;
	@JsonProperty("rows_per_page")
	private String rowsPerPage;
	@JsonProperty("usrgrps")
	private List<Usrgrp> usrgrps = new ArrayList<Usrgrp>();
	@JsonProperty("mediatypes")
	private List<MediaTypeResponse> mediatypes = new ArrayList<MediaTypeResponse>();
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The userid
	 */
	@JsonProperty("userid")
	public String getUserid() {
		return userid;
	}

	/**
	 * 
	 * @param userid
	 *            The userid
	 */
	@JsonProperty("userid")
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * 
	 * @return The alias
	 */
	@JsonProperty("alias")
	public String getAlias() {
		return alias;
	}

	/**
	 * 
	 * @param alias
	 *            The alias
	 */
	@JsonProperty("alias")
	public void setAlias(String alias) {
		this.alias = alias;
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
	 * @return The surname
	 */
	@JsonProperty("surname")
	public String getSurname() {
		return surname;
	}

	/**
	 * 
	 * @param surname
	 *            The surname
	 */
	@JsonProperty("surname")
	public void setSurname(String surname) {
		this.surname = surname;
	}

	/**
	 * 
	 * @return The url
	 */
	@JsonProperty("url")
	public String getUrl() {
		return url;
	}

	/**
	 * 
	 * @param url
	 *            The url
	 */
	@JsonProperty("url")
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * 
	 * @return The autologin
	 */
	@JsonProperty("autologin")
	public String getAutologin() {
		return autologin;
	}

	/**
	 * 
	 * @param autologin
	 *            The autologin
	 */
	@JsonProperty("autologin")
	public void setAutologin(String autologin) {
		this.autologin = autologin;
	}

	/**
	 * 
	 * @return The autologout
	 */
	@JsonProperty("autologout")
	public String getAutologout() {
		return autologout;
	}

	/**
	 * 
	 * @param autologout
	 *            The autologout
	 */
	@JsonProperty("autologout")
	public void setAutologout(String autologout) {
		this.autologout = autologout;
	}

	/**
	 * 
	 * @return The lang
	 */
	@JsonProperty("lang")
	public String getLang() {
		return lang;
	}

	/**
	 * 
	 * @param lang
	 *            The lang
	 */
	@JsonProperty("lang")
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * 
	 * @return The refresh
	 */
	@JsonProperty("refresh")
	public String getRefresh() {
		return refresh;
	}

	/**
	 * 
	 * @param refresh
	 *            The refresh
	 */
	@JsonProperty("refresh")
	public void setRefresh(String refresh) {
		this.refresh = refresh;
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
	 * @return The theme
	 */
	@JsonProperty("theme")
	public String getTheme() {
		return theme;
	}

	/**
	 * 
	 * @param theme
	 *            The theme
	 */
	@JsonProperty("theme")
	public void setTheme(String theme) {
		this.theme = theme;
	}

	/**
	 * 
	 * @return The attemptFailed
	 */
	@JsonProperty("attempt_failed")
	public String getAttemptFailed() {
		return attemptFailed;
	}

	/**
	 * 
	 * @param attemptFailed
	 *            The attempt_failed
	 */
	@JsonProperty("attempt_failed")
	public void setAttemptFailed(String attemptFailed) {
		this.attemptFailed = attemptFailed;
	}

	/**
	 * 
	 * @return The attemptIp
	 */
	@JsonProperty("attempt_ip")
	public String getAttemptIp() {
		return attemptIp;
	}

	/**
	 * 
	 * @param attemptIp
	 *            The attempt_ip
	 */
	@JsonProperty("attempt_ip")
	public void setAttemptIp(String attemptIp) {
		this.attemptIp = attemptIp;
	}

	/**
	 * 
	 * @return The attemptClock
	 */
	@JsonProperty("attempt_clock")
	public String getAttemptClock() {
		return attemptClock;
	}

	/**
	 * 
	 * @param attemptClock
	 *            The attempt_clock
	 */
	@JsonProperty("attempt_clock")
	public void setAttemptClock(String attemptClock) {
		this.attemptClock = attemptClock;
	}

	/**
	 * 
	 * @return The rowsPerPage
	 */
	@JsonProperty("rows_per_page")
	public String getRowsPerPage() {
		return rowsPerPage;
	}

	/**
	 * 
	 * @param rowsPerPage
	 *            The rows_per_page
	 */
	@JsonProperty("rows_per_page")
	public void setRowsPerPage(String rowsPerPage) {
		this.rowsPerPage = rowsPerPage;
	}

	/**
	 * 
	 * @return The usrgrps
	 */
	@JsonProperty("usrgrps")
	public List<Usrgrp> getUsrgrps() {
		return usrgrps;
	}

	/**
	 * 
	 * @param usrgrps
	 *            The usrgrps
	 */
	@JsonProperty("usrgrps")
	public void setUsrgrps(List<Usrgrp> usrgrps) {
		this.usrgrps = usrgrps;
	}

	/**
	 * 
	 * @return The mediatypes
	 */
	@JsonProperty("mediatypes")
	public List<MediaTypeResponse> getMediatypes() {
		return mediatypes;
	}

	/**
	 * 
	 * @param mediatypes
	 *            The mediatypes
	 */
	@JsonProperty("mediatypes")
	public void setMediatypes(List<MediaTypeResponse> mediatypes) {
		this.mediatypes = mediatypes;
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