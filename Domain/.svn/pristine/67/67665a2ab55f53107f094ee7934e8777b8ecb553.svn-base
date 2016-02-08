package it.prisma.domain.dsl.iaas.vmaas.request;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
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
@JsonPropertyOrder({ "imageName", "volume", "networks", "key",
		"securityGroups", "script", "diskPartition" })
public class VmDetails implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("imageId")
	private Long imageId;
	@JsonProperty("volume")
	private int volume;
	@JsonProperty("networks")
	private List<Long> networks;
	@JsonProperty("keyPairId")
	private Long keyPairId;
	@JsonProperty("securityGroups")
	private String securityGroups;
	@JsonProperty("script")
	private String script;
	@JsonProperty("diskPartition")
	private String diskPartition;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The imageName
	 */
	@JsonProperty("imageId")
	public Long getImageId() {
		return imageId;
	}

	/**
	 * 
	 * @param imageName
	 *            The imageName
	 */
	@JsonProperty("imageId")
	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	/**
	 * 
	 * @return The volume
	 */
	@JsonProperty("volume")
	public int getVolume() {
		return volume;
	}

	/**
	 * 
	 * @param volume
	 *            The volume
	 */
	@JsonProperty("volume")
	public void setVolume(int volume) {
		this.volume = volume;
	}

	/**
	 * 
	 * @return The networks
	 */
	@JsonProperty("networks")
	public List<Long> getNetworks() {
		return networks;
	}

	/**
	 * 
	 * @param networks
	 *            The networks
	 */
	@JsonProperty("networks")
	public void setNetworks(List<Long> networks) {
		this.networks = networks;
	}

	/**
	 * 
	 * @return The key
	 */
	@JsonProperty("keyPairId")
	public Long getKeyPairId() {
		return keyPairId;
	}

	/**
	 * 
	 * @param key
	 *            The key
	 */
	@JsonProperty("keyPairId")
	public void setKeyPairId(Long keyPairId) {
		this.keyPairId = keyPairId;
	}

	/**
	 * 
	 * @return The securityGroups
	 */
	@JsonProperty("securityGroups")
	public String getSecurityGroups() {
		return securityGroups;
	}

	/**
	 * 
	 * @param securityGroups
	 *            The securityGroups
	 */
	@JsonProperty("securityGroups")
	public void setSecurityGroups(String securityGroups) {
		this.securityGroups = securityGroups;
	}

	/**
	 * 
	 * @return The script
	 */
	@JsonProperty("script")
	public String getScript() {
		return script;
	}

	/**
	 * 
	 * @param script
	 *            The script
	 */
	@JsonProperty("script")
	public void setScript(String script) {
		this.script = script;
	}

	/**
	 * 
	 * @return The diskPartition
	 */
	@JsonProperty("diskPartition")
	public String getDiskPartition() {
		return diskPartition;
	}

	/**
	 * 
	 * @param diskPartition
	 *            The diskPartition
	 */
	@JsonProperty("diskPartition")
	public void setDiskPartition(String diskPartition) {
		this.diskPartition = diskPartition;
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
		return new HashCodeBuilder().append(imageId).append(volume)
				.append(networks).append(keyPairId).append(securityGroups)
				.append(script).append(diskPartition)
				.append(additionalProperties).toHashCode();
	}

	@Override
	public boolean equals(Object other) {
		if (other == this) {
			return true;
		}
		if ((other instanceof VmDetails) == false) {
			return false;
		}
		VmDetails rhs = ((VmDetails) other);
		return new EqualsBuilder().append(imageId, rhs.imageId)
				.append(volume, rhs.volume).append(networks, rhs.networks)
				.append(keyPairId, rhs.keyPairId)
				.append(securityGroups, rhs.securityGroups)
				.append(script, rhs.script)
				.append(diskPartition, rhs.diskPartition)
				.append(additionalProperties, rhs.additionalProperties)
				.isEquals();
	}

}