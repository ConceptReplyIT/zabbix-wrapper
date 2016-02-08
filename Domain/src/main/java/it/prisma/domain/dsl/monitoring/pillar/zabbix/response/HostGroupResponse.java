package it.prisma.domain.dsl.monitoring.pillar.zabbix.response;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
	"groupids"
})
public class HostGroupResponse {

	@JsonProperty("groupids")
	private List<String> groupids = new ArrayList<String>();
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return
	 * The groupids
	 */
	@JsonProperty("groupids")
	public List<String> getGroupids() {
		return groupids;
	}

	/**
	 * 
	 * @param groupids
	 * The groupids
	 */
	@JsonProperty("groupids")
	public void setGroupids(List<String> groupids) {
		this.groupids = groupids;
	}



	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}