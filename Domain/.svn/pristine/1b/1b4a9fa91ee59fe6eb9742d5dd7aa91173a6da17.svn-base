package it.prisma.domain.dsl.accounting.workgroups;

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
@JsonPropertyOrder({ "privilege", "action"})
public class PrivilegeAllowsActionRepresentation implements java.io.Serializable {


	private static final long serialVersionUID = -6550354822918226798L;
	
	@JsonProperty("action")
	private ActionRepresentation action;
	@JsonProperty("privilege")
	private PrivilegeRepresentation privilege;
	
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	
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

	public ActionRepresentation getAction() {
		return action;
	}

	public void setAction(ActionRepresentation action) {
		this.action = action;
	}

	public PrivilegeRepresentation getPrivilege() {
		return privilege;
	}

	public void setPrivilege(PrivilegeRepresentation privilege) {
		this.privilege = privilege;
	}

}