package it.prisma.domain.dsl.accounting.users.responses;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)

@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "imageFound", "imageName", "imageContent" })
public class RetrieveUserProfileImageResponse {

	@JsonProperty("imageFound")
	private boolean imageFound;
	
	@JsonProperty("imageName")
	private String imageName;

	@JsonProperty("imageContent")
	private byte[] imageContent;

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

	public byte[] getImageContent() {
		return imageContent;
	}

	public void setImageContent(byte[] imageContent) {
		this.imageContent = imageContent;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public boolean isImageFound() {
		return imageFound;
	}

	public void setImageFound(boolean imageFound) {
		this.imageFound = imageFound;
	}

}