package it.prisma.domain.dsl.accounting.users;

import it.prisma.domain.dsl.accounting.workgroups.PrivilegeRepresentation;

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
@JsonPropertyOrder({ "userAccountId", "workgroupPrivilege", "approvedByUserAccountId", "workgroupId",
		"approved" })
public class UserAccountHasWorkgroupRepresentation implements java.io.Serializable {


	private static final long serialVersionUID = -4439827868973166292L;
	
	
	@JsonProperty("userAccountId")
	private Long userAccountId;
	@JsonProperty("workgroupPrivilege")
	private PrivilegeRepresentation workgroupPrivilege;
	@JsonProperty("approvedByUserAccountId")
	private Long approvedByUserAccountId;
	@JsonProperty("workgroupId")
	private Long workgroupId;
	@JsonProperty("approved")
	private boolean approved;
	
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("userAccountId")
	public Long getUserAccountId() {
		return userAccountId;
	}

	@JsonProperty("userAccountId")
	public void setUserAccountId(Long userAccountId) {
		this.userAccountId = userAccountId;
	}
	
	@JsonProperty("workgroupPrivilege")
	public PrivilegeRepresentation getWorkgroupPrivilege() {
		return workgroupPrivilege;
	}

	@JsonProperty("workgroupPrivilege")
	public void setWorkgroupPrivilege(PrivilegeRepresentation workgroupPrivilege) {
		this.workgroupPrivilege = workgroupPrivilege;
	}

	@JsonProperty("approvedByUserAccountId")
	public Long getApprovedByUserAccountId() {
		return approvedByUserAccountId;
	}

	@JsonProperty("approvedByUserAccountId")
	public void setApprovedByUserAccountId(Long approvedByUserAccountId) {
		this.approvedByUserAccountId = approvedByUserAccountId;
	}
	
	@JsonProperty("workgroupId")
	public Long getWorkgroupId() {
		return workgroupId;
	}

	@JsonProperty("workgroupId")
	public void setWorkgroupId(Long workgroupId) {
		this.workgroupId = workgroupId;
	}
	
	@JsonProperty("approved")
	public boolean getApproved() {
		return approved;
	}

	@JsonProperty("approved")
	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object other) {
		return EqualsBuilder.reflectionEquals(this, other);
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}
