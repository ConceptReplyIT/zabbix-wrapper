package it.prisma.domain.dsl.paas.services.smsaas;

import javax.annotation.Generated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "text", "num", "scheduling", "sendDate", "from" })
public class SMSMessageRepresentation {

    @NotNull
    @JsonProperty("text")
    private String text;
    @NotNull
    @Size(min = 50, max = 51)
    @JsonProperty("num")
    private String num;
    @NotNull
    @JsonProperty("scheduling")
    private Boolean scheduling;
    @JsonProperty("sendDate")
    private String sendDate;
    @NotNull
    @JsonProperty("from")
    private String from;

    /**
     * 
     * @return The text
     */
    @JsonProperty("text")
    public String getText() {
	return text;
    }

    /**
     * 
     * @param text
     *            The text
     */
    @JsonProperty("text")
    public void setText(String text) {
	this.text = text;
    }

    /**
     * 
     * @return The num
     */
    @JsonProperty("num")
    public String getNum() {
	return num;
    }

    /**
     * 
     * @param num
     *            The num
     */
    @JsonProperty("num")
    public void setNum(String num) {
	this.num = num;
    }

    /**
     * 
     * @return The scheduling
     */
    @JsonProperty("scheduling")
    public Boolean getScheduling() {
	return scheduling;
    }

    /**
     * 
     * @param scheduling
     *            The scheduling
     */
    @JsonProperty("scheduling")
    public void setScheduling(Boolean scheduling) {
	this.scheduling = scheduling;
    }

    /**
     * 
     * @return The sendDate
     */
    @JsonProperty("sendDate")
    public String getSendDate() {
	return sendDate;
    }

    /**
     * 
     * @param sendDate
     *            The sendDate
     */
    @JsonProperty("sendDate")
    public void setSendDate(String sendDate) {
	this.sendDate = sendDate;
    }

    /**
     * 
     * @return The from
     */
    @JsonProperty("from")
    public String getFrom() {
	return from;
    }

    /**
     * 
     * @param from
     *            The from
     */
    @JsonProperty("from")
    public void setFrom(String from) {
	this.from = from;
    }

    @Override
    public String toString() {
	return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public int hashCode() {
	return new HashCodeBuilder().append(text).append(num).append(scheduling).append(sendDate).append(from)
		.toHashCode();
    }

    @Override
    public boolean equals(Object other) {
	if (other == this) {
	    return true;
	}
	if ((other instanceof SMSMessageRepresentation) == false) {
	    return false;
	}
	SMSMessageRepresentation rhs = ((SMSMessageRepresentation) other);
	return new EqualsBuilder().append(text, rhs.text).append(num, rhs.num).append(scheduling, rhs.scheduling)
		.append(sendDate, rhs.sendDate).append(from, rhs.from).isEquals();
    }

}