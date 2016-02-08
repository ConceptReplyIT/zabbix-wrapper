package it.prisma.domain.dsl.auditing;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(  
	    use = JsonTypeInfo.Id.NAME,  
	    include = JsonTypeInfo.As.PROPERTY,  
	    visible = true,
	    property = "actionType")  
	@JsonSubTypes({  
	    @Type(value = DeployablePaaSServiceActionRepresentation.class, name = "DEPLOYABLE_PAAS_SERVICE_ACTION"),  
	    @Type(value = DNSPublishingActionRepresentation.class, name = "DNS_PUBLISHING_ACTION") })  
public class AuditingActionRepresentation implements Serializable {

	public enum AuditingActionType {
		DEPLOYABLE_PAAS_SERVICE_ACTION, DNS_PUBLISHING_ACTION
	}
	
	private static final long serialVersionUID = -5445727075089756074L;

	protected Long id;
	protected Date time;
	protected Long userAccountId;
	protected AuditingActionType actionType;
	protected String userFullName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Long getUserAccountId() {
		return userAccountId;
	}

	public void setUserAccountId(Long userAccountId) {
		this.userAccountId = userAccountId;
	}

	public AuditingActionType getActionType() {
		return actionType;
	}

	public void setActionType(AuditingActionType actionType) {
		this.actionType = actionType;
	}
	
	public void setActionTypeFromString(String actionType) {
		this.actionType = AuditingActionType.valueOf(actionType);
	}

	public String getUserFullName() {
		return userFullName;
	}

	public void setUserFullName(String userFullName) {
		this.userFullName = userFullName;
	}
	
}
