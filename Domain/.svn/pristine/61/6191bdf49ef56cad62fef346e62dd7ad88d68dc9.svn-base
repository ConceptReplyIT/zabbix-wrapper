package it.prisma.domain.dsl.monitoring.pillar.wrapper.paas;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
	"groupName",
	"paasMachines"
})
public class GroupWrapped4HostList {

	@JsonProperty("groupName")
	private String groupName;
	@JsonProperty("paasMachines")
	private List<PaasMachineListWrapped> paasMachines = new ArrayList<PaasMachineListWrapped>();

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
	
	/**
	 * 
	 * @return
	 * The paasMachines
	 */
	@JsonProperty("paasMachines")
	public List<PaasMachineListWrapped> getPaasMachines() {
		return paasMachines;
	}

	/**
	 * 
	 * @param paasMachines
	 * The paasMachines
	 */
	@JsonProperty("paasMachines")
	public void setPaasMachines(List<PaasMachineListWrapped> paasMachines) {
		this.paasMachines = paasMachines;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}