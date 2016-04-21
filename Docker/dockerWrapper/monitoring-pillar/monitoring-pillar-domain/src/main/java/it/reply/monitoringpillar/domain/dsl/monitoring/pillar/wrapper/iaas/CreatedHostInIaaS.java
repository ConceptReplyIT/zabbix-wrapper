package it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.iaas;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
	"hostIDInIaaS"
})
public class CreatedHostInIaaS {

	@JsonProperty("hostIDInIaaS")
	private List<String> hostIDInIaaS = new ArrayList<String>();

	/**
	 * 
	 * @return
	 * The hostIDInIaaS
	 */
	@JsonProperty("hostIDInIaaS")
	public List<String> getHostIDInIaaS() {
		return hostIDInIaaS;
	}

	/**
	 * 
	 * @param hostIDInIaaS
	 * The hostIDInIaaS
	 */
	@JsonProperty("hostIDInIaaS")
	public void setHostIDInIaaS(List<String> hostIDInIaaS) {
		this.hostIDInIaaS = hostIDInIaaS;
	}
}