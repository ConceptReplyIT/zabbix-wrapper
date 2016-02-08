package it.prisma.domain.dsl.accounting;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
public class AuthTokenChangeExpirationTimeRequest implements java.io.Serializable {

	
	private static final long serialVersionUID = -2633900403900180387L;
	
	@JsonProperty("token")
	private String token;
	@JsonProperty("newExpiration")
	private String newExpiration;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getNewExpiration() {
		return newExpiration;
	}
	public void setNewExpiration(String newExpiration) {
		this.newExpiration = newExpiration;
	}



}
