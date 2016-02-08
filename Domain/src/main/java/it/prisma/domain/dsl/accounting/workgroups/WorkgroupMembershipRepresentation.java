package it.prisma.domain.dsl.accounting.workgroups;

import java.io.Serializable;
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
@JsonPropertyOrder({ "workgroupId", "workgroupName", "memberUserAccountId", "workgroupPrivilege", "workgroupApproved",
		"membershipApproved", "workgroupReferent", "membershipApprovedByUserAccountId" })
public class WorkgroupMembershipRepresentation implements Serializable {

	private static final long serialVersionUID = 3245906718086291800L;

	@JsonProperty("workgroupId")
	private Long workgroupId;
	@JsonProperty("workgroupName")
	private String workgroupName;
	@JsonProperty("memberUserAccountId")
	private Long memberUserAccountId;
	@JsonProperty("workgroupPrivilege")
	private PrivilegeRepresentation workgroupPrivilege;
	@JsonProperty("workgroupReferent")
	private Boolean workgroupReferent;
	@JsonProperty("workgroupApproved")
	private Boolean workgroupApproved;
	@JsonProperty("membershipApproved")
	private Boolean membershipApproved;
	@JsonProperty("membershipApprovedByUserAccountId")
	private Long membershipApprovedByUserAccountId;

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	public WorkgroupMembershipRepresentation() {
		super();
	}

	/**
	 * @param workgroupId
	 * @param memberUserAccountId
	 * @param workgroupPrivilege
	 * @param workgroupReferent
	 * @param approved
	 * @param approvedByUserAccountId
	 */
	public WorkgroupMembershipRepresentation(Long workgroupId, String workgroupName, Long memberUserAccountId,
			PrivilegeRepresentation workgroupPrivilege, Boolean workgroupReferent, Boolean workgroupApproved,
			Boolean membershipApproved, Long membershipApprovedByUserAccountId) {
		super();
		this.workgroupId = workgroupId;
		this.workgroupName = workgroupName;
		this.memberUserAccountId = memberUserAccountId;
		this.workgroupPrivilege = workgroupPrivilege;
		this.workgroupReferent = workgroupReferent;
		this.workgroupApproved = workgroupApproved;
		this.membershipApproved = membershipApproved;
		this.membershipApprovedByUserAccountId = membershipApprovedByUserAccountId;
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
	 * @return The workgroupName
	 */
	@JsonProperty("workgroupName")
	public String getWorkgroupName() {
		return workgroupName;
	}

	/**
	 * 
	 * @param workgroupName
	 *            The workgroupName
	 */
	@JsonProperty("workgroupName")
	public void setWorkgroupName(String workgroupName) {
		this.workgroupName = workgroupName;
	}

	/**
	 * 
	 * @return The memberUserAccountId
	 */
	@JsonProperty("memberUserAccountId")
	public Long getMemberUserAccountId() {
		return memberUserAccountId;
	}

	/**
	 * 
	 * @param memberUserAccountId
	 *            The memberUserAccountId
	 */
	@JsonProperty("memberUserAccountId")
	public void setMemberUserAccountId(Long memberUserAccountId) {
		this.memberUserAccountId = memberUserAccountId;
	}

	/**
	 * 
	 * @return The workgroupPrivilege
	 */
	@JsonProperty("workgroupPrivilege")
	public PrivilegeRepresentation getWorkgroupPrivilege() {
		return workgroupPrivilege;
	}

	/**
	 * 
	 * @param workgroupPrivilege
	 *            The workgroupPrivilege
	 */
	@JsonProperty("workgroupPrivilege")
	public void setWorkgroupPrivilege(PrivilegeRepresentation workgroupPrivilege) {
		this.workgroupPrivilege = workgroupPrivilege;
	}

	/**
	 * 
	 * @return The approved
	 */
	@JsonProperty("workgroupApproved")
	public Boolean isWorkgroupApproved() {
		return this.workgroupApproved;
	}

	/**
	 * 
	 * @param approved
	 *            The approved
	 */
	@JsonProperty("workgroupApproved")
	public void setWorkgroupApproved(Boolean approved) {
		this.workgroupApproved = approved;
	}

	/**
	 * 
	 * @return The approved
	 */
	@JsonProperty("membershipApproved")
	public Boolean isMembershipApproved() {
		return this.membershipApproved;
	}

	/**
	 * 
	 * @param approved
	 *            The approved
	 */
	@JsonProperty("membershipApproved")
	public void setMembershipApproved(Boolean approved) {
		this.membershipApproved = approved;
	}

	/**
	 * 
	 * @return The membershipApprovedByUserAccountId
	 */
	@JsonProperty("membershipApprovedByUserAccountId")
	public Long getMembershipApprovedByUserAccountId() {
		return membershipApprovedByUserAccountId;
	}

	/**
	 * 
	 * @param membershipApprovedByUserAccountId
	 *            The membershipApprovedByUserAccountId
	 */
	@JsonProperty("membershipApprovedByUserAccountId")
	public void setMembershipApprovedByUserAccountId(Long membershipApprovedByUserAccountId) {
		this.membershipApprovedByUserAccountId = membershipApprovedByUserAccountId;
	}

	@JsonProperty("workgroupReferent")
	public Boolean isWorkgroupReferent() {
		return workgroupReferent;
	}

	@JsonProperty("workgroupReferent")
	public void setWorkgroupReferent(Boolean workgroupReferent) {
		this.workgroupReferent = workgroupReferent;
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
		return new HashCodeBuilder().append(workgroupId).append(workgroupName).append(memberUserAccountId)
				.append(workgroupPrivilege).append(workgroupApproved).append(membershipApproved)
				.append(membershipApprovedByUserAccountId).append(additionalProperties).append(workgroupReferent)
				.toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof WorkgroupMembershipRepresentation) == false) {
			return false;
		}
		WorkgroupMembershipRepresentation rhs = ((WorkgroupMembershipRepresentation) other);
		return new EqualsBuilder().append(workgroupId, rhs.workgroupId).append(workgroupName, rhs.workgroupName)
				.append(memberUserAccountId, rhs.memberUserAccountId)
				.append(workgroupPrivilege, rhs.workgroupPrivilege).append(workgroupApproved, rhs.workgroupApproved)
				.append(workgroupReferent, rhs.workgroupReferent).append(membershipApproved, rhs.membershipApproved)
				.append(membershipApprovedByUserAccountId, rhs.membershipApprovedByUserAccountId)
				.append(additionalProperties, rhs.additionalProperties).isEquals();
	}

}
