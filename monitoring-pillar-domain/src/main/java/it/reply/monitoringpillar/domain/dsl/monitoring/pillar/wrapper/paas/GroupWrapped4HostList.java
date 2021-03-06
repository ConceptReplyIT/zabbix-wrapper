package it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "groupName", "paasMachines" })
public class GroupWrapped4HostList implements Serializable {

  private static final long serialVersionUID = -8425673865503069848L;

  @JsonProperty("groupName")
  private String groupName;
  @JsonProperty("paasMachines")
  private List<PaasMachineListWrapped> paasMachines = new ArrayList<PaasMachineListWrapped>();

  @JsonProperty("groupName")
  public String getGroupName() {
    return groupName;
  }

  @JsonProperty("groupName")
  public void setGroupName(String groupName) {
    this.groupName = groupName;
  }

  @JsonProperty("paasMachines")
  public List<PaasMachineListWrapped> getPaasMachines() {
    return paasMachines;
  }

  @JsonProperty("paasMachines")
  public void setPaasMachines(List<PaasMachineListWrapped> paasMachines) {
    this.paasMachines = paasMachines;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}