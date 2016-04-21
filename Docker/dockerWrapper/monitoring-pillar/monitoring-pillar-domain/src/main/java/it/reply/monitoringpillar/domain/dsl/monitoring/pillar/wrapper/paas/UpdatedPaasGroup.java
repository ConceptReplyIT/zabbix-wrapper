package it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
	"groupsIDInMetrics",
	"groupsIDinWatcher", 
	"groupsIDinIaaS"
})
public class UpdatedPaasGroup {

	@JsonProperty("groupsIdInMetrics")
	private List<String> groupsIDInMetrics = new ArrayList<String>();
	@JsonProperty("groupsIDinWatcher")
	private List<String> groupsIDinWatcher = new ArrayList<String>();
	@JsonProperty("groupsIDinIaaS")
	private List<String> groupsIDinIaaS = new ArrayList<String>();

	/**
	 * 
	 * @return
	 * The groupsIDInMetrics
	 */
	@JsonProperty("groupsIDInMetrics")
	public List<String> getGroupsIDInMetrics() {
		return groupsIDInMetrics;
	}

	/**
	 * 
	 * @param groupsIdInMetrics
	 * The groupsIdInMetrics
	 */
	@JsonProperty("groupsIDInMetrics")
	public void setGroupsIDInMetrics(List<String> groupsIDInMetrics) {
		this.groupsIDInMetrics = groupsIDInMetrics;
	}

	/**
	 * 
	 * @return
	 * The groupsIDinWatcher
	 */
	@JsonProperty("groupsIDinWatcher")
	public List<String> getGroupsIDinWatcher() {
		return groupsIDinWatcher;
	}

	/**
	 * 
	 * @param hostIDinWatcher
	 * The hostIDinWatcher
	 */
	@JsonProperty("groupsIDinWatcher")
	public void setGroupsIDinWatcher(List<String> groupsIDinWatcher) {
		this.groupsIDinWatcher = groupsIDinWatcher;
	}


	/**
	 * 
	 * @return
	 * The groupsIDinIaaS
	 */
	@JsonProperty("groupsIDinIaaS")
	public List<String> getgroupsIDinIaaS() {
		return groupsIDinIaaS;
	}

	/**
	 * 
	 * @param groupsIDinIaaS
	 * The groupsIDinIaaS
	 */
	@JsonProperty("groupsIDinWatcher")
	public void setGroupsIDinIaaS(List<String> groupsIDinIaaS) {
		this.groupsIDinIaaS = groupsIDinIaaS;
	}

}

