package it.prisma.domain.dsl.accounting.workgroups.responses;

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
@JsonPropertyOrder({ "workgroupId", "userAccountId", "approved", "approvedByUserAccountId" })
public class UserIsApprovedInWorkgroupResponse {

	@JsonProperty("workgroupId")
	private Long workgroupId;
	@JsonProperty("userAccountId")
	private Long userAccountId;
	@JsonProperty("approved")
	private boolean approved;
	@JsonProperty("approvedByUserAccountId")
	private Long approvedByUserAccountId;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	
	public UserIsApprovedInWorkgroupResponse(Long workgroupId, Long userAccountId, 
			boolean approved, Long approvedByUserAccountId) {
		
		this.approved = approved;
		this.approvedByUserAccountId = approvedByUserAccountId;
		this.userAccountId = userAccountId;
		this.workgroupId = workgroupId;
		
	}
	
	/**
	 * 
	 * @return The workgroupId
	 */
	@JsonProperty("workgroupId")
	public Long getWorkgroupId() {
		return workgroupId;
	}

	/**
	 * 
	 * @param workgroupId
	 *            The workgroupId
	 */
	@JsonProperty("workgroupId")
	public void setWorkgroupId(Long workgroupId) {
		this.workgroupId = workgroupId;
	}

	/**
	 * 
	 * @return The userAccountId
	 */
	@JsonProperty("userAccountId")
	public Long getUserAccountId() {
		return userAccountId;
	}

	/**
	 * 
	 * @param userAccountId
	 *            The userAccountId
	 */
	@JsonProperty("userAccountId")
	public void setUserAccountId(Long userAccountId) {
		this.userAccountId = userAccountId;
	}

	
	/**
	 * 
	 * @return The approved
	 */
	@JsonProperty("approved")
	public boolean isApproved() {
		return approved;
	}

	/**
	 * 
	 * @param approved
	 *            The approved
	 */
	@JsonProperty("approved")
	public void setIsApproved(boolean approved) {
		this.approved = approved;
	}
	
	
	/**
	 * 
	 * @return The approvedByUserAccountId
	 */
	@JsonProperty("approvedByUserAccountId")
	public Long getApprovedByUserAccountId() {
		return approvedByUserAccountId;
	}

	/**
	 * 
	 * @param approvedByUserAccountId
	 *            The approvedByUserAccountId
	 */
	@JsonProperty("approvedByUserAccountId")
	public void setApprovedByUserAccountId(Long approvedByUserAccountId) {
		this.approvedByUserAccountId = approvedByUserAccountId;
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
		return new HashCodeBuilder().append(workgroupId)
				.append(approvedByUserAccountId).append(additionalProperties)
				.toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof UserIsApprovedInWorkgroupResponse) == false) {
			return false;
		}
		UserIsApprovedInWorkgroupResponse rhs = ((UserIsApprovedInWorkgroupResponse) other);
		return new EqualsBuilder().append(workgroupId, rhs.workgroupId)
				.append(approvedByUserAccountId, rhs.approvedByUserAccountId)
				.append(additionalProperties, rhs.additionalProperties)
				.isEquals();
	}

}
