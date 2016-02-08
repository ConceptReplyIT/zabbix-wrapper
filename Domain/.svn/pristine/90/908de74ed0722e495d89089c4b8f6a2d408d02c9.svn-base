package it.prisma.domain.dsl.accounting.workgroups.requests;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "userAccountId", "workgroupId", "privilegeId", "approved", "approvedByUserAccountId"})
public class WorkgroupPrivilegeRequest {
	 
	@JsonProperty("userAccountId")
	private Long userAccountId;
	@JsonProperty("workgroupId")
	private Long workgroupId;
	@JsonProperty("privilegeId")
	private Long privilegeId;
	@JsonProperty("approved")
	private boolean approved;
	@JsonProperty("approvedByUserAccountId")
	private Long approvedByUserAccountId;
	
	
	
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
		return new HashCodeBuilder().append(workgroupId).append(privilegeId)
				.append(userAccountId).append(additionalProperties)
				.toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof WorkgroupPrivilegeRequest) == false) {
			return false;
		}
		WorkgroupPrivilegeRequest rhs = ((WorkgroupPrivilegeRequest) other);
		return new EqualsBuilder().append(workgroupId, rhs.workgroupId)
				.append(userAccountId, rhs.userAccountId)
				.append(privilegeId, rhs.privilegeId)
				.append(additionalProperties, rhs.additionalProperties)
				.isEquals();
	}

	public Long getUserAccountId() {
		return userAccountId;
	}

	public void setUserAccountId(Long userAccountId) {
		this.userAccountId = userAccountId;
	}

	public Long getWorkgroupId() {
		return workgroupId;
	}

	public void setWorkgroupId(Long workgroupId) {
		this.workgroupId = workgroupId;
	}

	public Long getPrivilegeId() {
		return privilegeId;
	}

	public void setPrivilegeId(Long privilegeId) {
		this.privilegeId = privilegeId;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public Long getApprovedByUserAccountId() {
		return approvedByUserAccountId;
	}

	public void setApprovedByUserAccountId(Long approvedByUserAccountId) {
		this.approvedByUserAccountId = approvedByUserAccountId;
	}

}