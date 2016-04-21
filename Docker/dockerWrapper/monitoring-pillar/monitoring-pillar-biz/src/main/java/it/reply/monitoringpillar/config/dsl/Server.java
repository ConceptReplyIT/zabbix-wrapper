
package it.reply.monitoringpillar.config.dsl;

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

import it.reply.monitoringpillar.domain.exception.MonitoringException;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "url", "username", "password", "type", "proxy" })
public class Server {

	@JsonProperty("url")
	private String url;
	@JsonProperty("username")
	private String username;
	@JsonProperty("password")
	private String password;
	@JsonProperty("type")
	private String type;
	@JsonProperty("proxy")
	private Proxy proxy;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
	 * @return The proxy
	 */
	@JsonProperty("proxy")
	public Proxy getProxy() {
		return proxy;
	}

	/**
	 * 
	 * @param proxy
	 *            The proxy
	 */
	@JsonProperty("proxy")
	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
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

	public Proxy getProxyName(String proxyName) {
		boolean proxyNameFound = false;

		if (proxy.getName().equalsIgnoreCase(proxyName) || proxy.getNameTemplate().equalsIgnoreCase(proxyName)) {
			proxyNameFound = true;
			return proxy;
		}

		if (!proxyNameFound) {
			throw new MonitoringException(
					"Unable to find proxy " + proxyName + "one of these: " + getProxy().toString());
		}
		return null;
	}

}
