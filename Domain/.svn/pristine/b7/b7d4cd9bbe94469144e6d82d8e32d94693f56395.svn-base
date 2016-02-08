package it.prisma.domain.dsl.monitoring.businesslayer.paas.trigger;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "paaSServiceId", "triggerStatus", "hostId" })
public class TriggerStatusUpdate {

	@JsonProperty("paaSServiceId")
	private Long paaSServiceId;
	@JsonProperty("triggerStatus")
	private TriggerStatus triggerStatus;
	@JsonProperty("hostId")
	private String hostId;

	/**
	 * 
	 * @return The id
	 */
	@JsonProperty("paaSServiceId")
	public Long getId() {
		return paaSServiceId;
	}

	/**
	 * 
	 * @param id
	 *            The id
	 */
	@JsonProperty("paaSServiceId")
	public void setId(Long id) {
		this.paaSServiceId = id;
	}

	public TriggerStatusUpdate withId(Long id) {
		this.paaSServiceId = id;
		return this;
	}

	/**
	 * 
	 * @return The trigger Status
	 */
	@JsonProperty("triggerStatus")
	public TriggerStatus getStatus() {
		return this.triggerStatus;
	}

	/**
	 * 
	 * @param status
	 *            The trigger Status
	 */
	@JsonProperty("triggerStatus")
	public void setStatus(TriggerStatus status) {
		this.triggerStatus = status;
	}

	public TriggerStatusUpdate withStatus(TriggerStatus status) {
		this.triggerStatus = status;
		return this;
	}
	
	/**
	 * 
	 * @return The host Id
	 */
	@JsonProperty("hostId")
	public String getHostId() {
		return this.hostId;
	}

	/**
	 * 
	 * @param status
	 *            The host Id
	 */
	@JsonProperty("hostId")
	public void setHostId(String hostId) {
		this.hostId = hostId;
	}

	public TriggerStatusUpdate withHostId(String hostId) {
		this.hostId = hostId;
		return this;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}


	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(paaSServiceId).append(triggerStatus).append(hostId).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof TriggerStatusUpdate) == false) {
			return false;
		}
		TriggerStatusUpdate rhs = ((TriggerStatusUpdate) other);
		return new EqualsBuilder().append(paaSServiceId, rhs.paaSServiceId)
				.append(triggerStatus, rhs.triggerStatus)
				.append(hostId, rhs.hostId)
				.isEquals();
	}
	
}