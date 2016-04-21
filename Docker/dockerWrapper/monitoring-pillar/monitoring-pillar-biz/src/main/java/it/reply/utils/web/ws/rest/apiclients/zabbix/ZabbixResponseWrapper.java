package it.reply.utils.web.ws.rest.apiclients.zabbix;

import java.io.Serializable;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import it.reply.monitoringpillar.domain.dsl.monitoring.pillar.zabbix.response.JSONRPCError;

/**
 * This class is the wrapper for every response of Prisma Rest Protocol.
 * 
 * @author l.biava
 * 
 * @param <ResultType>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "meta", "result", "error" })
public class ZabbixResponseWrapper<ResultType> implements Serializable {

	private static final long serialVersionUID = -2767269124571953296L;
	@JsonProperty("id")
	private Integer id;
	@JsonProperty("result")
	private ResultType result;
	@JsonProperty("error")
	private JSONRPCError error;


	@JsonProperty("id")
	public void setMeta(Integer id) {
		this.id = id;
	}

	@JsonProperty("result")
	public ResultType getResult() {
		return result;
	}

	@JsonProperty("result")
	public void setResult(ResultType result) {
		this.result = result;
	}

	@JsonProperty("error")
	public JSONRPCError getError() {
		return error;
	}

	@JsonProperty("error")
	public void setError(JSONRPCError error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object other) {
		return EqualsBuilder.reflectionEquals(this, other);
	}

}