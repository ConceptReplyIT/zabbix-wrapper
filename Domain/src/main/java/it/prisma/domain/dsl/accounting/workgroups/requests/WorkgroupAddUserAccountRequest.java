package it.prisma.domain.dsl.accounting.workgroups.requests;

import java.util.ArrayList;
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
@JsonPropertyOrder({ "accountIds", "workgroupId", "userAccountApprovedBy" })
public class WorkgroupAddUserAccountRequest {
	 
	@JsonProperty("accountIds")
	private ArrayList<Long> accountIds;
	@JsonProperty("workgroupId")
	private Long workgroupId;
	@JsonProperty("userAccountApprovedBy")
	private Long userAccountApprovedBy;
	
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

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
	 * @return The accountIds
	 */
	@JsonProperty("accountIds")
	public ArrayList<Long> getAccountIds() {
		return accountIds;
	}

	/**
	 * 
	 * @param accountIds
	 *            The accountIds
	 */
	@JsonProperty("accountIds")
	public void setAccountIds(ArrayList<Long> accountIds) {
		this.accountIds = accountIds;
	}

	
	/**
	 * 
	 * @return The userAccountApprovedBy
	 */
	@JsonProperty("userAccountApprovedBy")
	public Long getUserAccountApprovedBy() {
		return userAccountApprovedBy;
	}

	/**
	 * 
	 * @param userAccountApprovedBy
	 *            The userAccountApprovedBy
	 */
	@JsonProperty("userAccountApprovedBy")
	public void setUserAccountApprovedBy(Long userAccountApprovedBy) {
		this.userAccountApprovedBy = userAccountApprovedBy;
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
				.append(accountIds).append(additionalProperties).append(userAccountApprovedBy)
				.toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof WorkgroupAddUserAccountRequest) == false) {
			return false;
		}
		WorkgroupAddUserAccountRequest rhs = ((WorkgroupAddUserAccountRequest) other);
		return new EqualsBuilder().append(workgroupId, rhs.workgroupId)
				.append(accountIds, rhs.accountIds)
				.append(userAccountApprovedBy, rhs.userAccountApprovedBy)
				.append(additionalProperties, rhs.additionalProperties)
				.isEquals();
	}

}