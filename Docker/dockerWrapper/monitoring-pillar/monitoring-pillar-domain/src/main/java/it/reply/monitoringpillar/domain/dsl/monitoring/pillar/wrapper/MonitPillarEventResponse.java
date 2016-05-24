package it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
	"events"
})
public class MonitPillarEventResponse {

	@JsonProperty("events")
	private List<MonitPillarEventDescriptor> events = new ArrayList<MonitPillarEventDescriptor>();
//	@JsonIgnore
//	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return
	 * The events
	 */
	@JsonProperty("events")
	public List<MonitPillarEventDescriptor> getEvents() {
		return events;
	}

	/**
	 * 
	 * @param events
	 * The events
	 */
	@JsonProperty("events")
	public void setEvents(List<MonitPillarEventDescriptor> events) {
		this.events = events;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

//	@JsonAnyGetter
//	public Map<String, Object> getAdditionalProperties() {
//		return this.additionalProperties;
//	}
//
//	@JsonAnySetter
//	public void setAdditionalProperty(String name, Object value) {
//		this.additionalProperties.put(name, value);
//	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(events).
//				append(additionalProperties).
				toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof MonitPillarEventResponse) == false) {
			return false;
		}
		MonitPillarEventResponse rhs = ((MonitPillarEventResponse) other);
		return new EqualsBuilder().append(events, rhs.events).
//				append(additionalProperties, rhs.additionalProperties).
				isEquals();
	}

}