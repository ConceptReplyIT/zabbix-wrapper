package it.prisma.domain.dsl.accounting.workgroups;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "actionId", "name", "description"})
public class ActionRepresentation implements java.io.Serializable {


	private static final long serialVersionUID = 6069356500869675812L;
	
	@JsonProperty("actionId")
	private Long actionId;
	@JsonProperty("name")
	private String name;
	@JsonProperty("description")
	private String description;
	
	private boolean defaultAction;
	
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public boolean isDefaultAction() {
		return defaultAction;
	}

	public void setDefaultAction(boolean defaultAction) {
		this.defaultAction = defaultAction;
	}

	@JsonProperty("name")
	public String getName() {
		return name;
	}

	@JsonProperty("name")
	public void setName(String name) {
		this.name = name;
	}


	@JsonProperty("actionId")
	public Long getActionId() {
		return actionId;
	}

	@JsonProperty("actionId")
	public void setActionId(Long actionId) {
		this.actionId = actionId;
	}

	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
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

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(actionId).append(name)
				.append(description)
				.toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof ActionRepresentation) == false) {
			return false;
		}
		ActionRepresentation rhs = ((ActionRepresentation) other);
		return new EqualsBuilder().append(actionId, rhs.actionId)
				.append(name, rhs.name).append(description, rhs.description)
				.isEquals();
	}

}