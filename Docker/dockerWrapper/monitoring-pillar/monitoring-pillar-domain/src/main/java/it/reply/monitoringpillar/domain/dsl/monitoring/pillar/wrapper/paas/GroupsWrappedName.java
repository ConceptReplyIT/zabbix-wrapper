package it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
	"groupName"
})

public class GroupsWrappedName {

	@JsonProperty("groupName")
	private String groupName;

	/**
	 * 
	 * @return
	 * The categoryName
	 */
	@JsonProperty("groupName")
	public String getGroupName() {
		return groupName;
	}

	/**
	 * 
	 * @param groupName
	 * The groupName
	 */
	@JsonProperty("groupName")
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}