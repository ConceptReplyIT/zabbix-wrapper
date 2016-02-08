package it.prisma.domain.dsl.paas.services.odaas;

import java.io.Serializable;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "productType", "productVersion", "username",
		"userPassword" })
public class ODaaSDetails implements Serializable {

	private static final long serialVersionUID = 4377419958022624682L;

	@JsonProperty("productType")
	private String productType;
	@JsonProperty("productVersion")
	private String productVersion;
	@JsonProperty("username")
	private String username;
	@JsonProperty("password")
	private String password;

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductVersion() {
		return productVersion;
	}

	public void setProductVersion(String productVersion) {
		this.productVersion = productVersion;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}