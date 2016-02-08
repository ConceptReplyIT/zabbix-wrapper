package it.prisma.domain.dsl.monitoring.pillar.wrapper;

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
	"description",
	"key",
	"clock", 
	"descriptionEvent"
})
public class MonitPillarEventDescriptor {

	@JsonProperty("description")
	private String description;
	@JsonProperty("key")
	private String key;
	@JsonProperty("clock")
	private String clock;
	@JsonProperty("descriptionEvent")
	private DescriptionEvent descriptionEvent;
//	@JsonIgnore
//	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return
	 * The description
	 */
	@JsonProperty("descriptionEvent")
	public DescriptionEvent getDescriptionEvent() {
		return descriptionEvent;
	}

	/**
	 * 
	 * @param description
	 * The description
	 */
	@JsonProperty("descriptionEvent")
	public void setDescription(DescriptionEvent descriptionEvent) {
		this.descriptionEvent = descriptionEvent;
	}

	/**
	 * 
	 * @return
	 * The key
	 */
	@JsonProperty("key")
	public String getKey() {
		return key;
	}

	/**
	 * 
	 * @param key
	 * The key
	 */
	@JsonProperty("key")
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * 
	 * @return
	 * The clock
	 */
	@JsonProperty("clock")
	public String getClock() {
		return clock;
	}

	/**
	 * 
	 * @param clock
	 * The clock
	 */
	@JsonProperty("clock")
	public void setClock(String clock) {
		this.clock = clock;
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
		return new HashCodeBuilder().append(description).append(key).append(clock).
//				append(additionalProperties).
				toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof MonitPillarEventDescriptor) == false) {
			return false;
		}
		MonitPillarEventDescriptor rhs = ((MonitPillarEventDescriptor) other);
		return new EqualsBuilder().append(description, rhs.description).append(key, rhs.key).append(clock, rhs.clock).
//				append(additionalProperties, rhs.additionalProperties).
				isEquals();
	}

}