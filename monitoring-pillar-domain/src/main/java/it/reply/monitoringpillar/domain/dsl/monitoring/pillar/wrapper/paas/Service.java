package it.reply.monitoringpillar.domain.dsl.monitoring.pillar.wrapper.paas;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "serviceName", "paasMetrics" })
public class Service implements Serializable {

  private static final long serialVersionUID = -8425673865503069848L;

  @JsonProperty("serviceName")
  private String serviceName;
  @JsonProperty("paasMetrics")
  private List<PaaSMetric> paasMetrics = new ArrayList<PaaSMetric>();

  @JsonProperty("serviceName")
  public String getServiceName() {
    return serviceName;
  }

  @JsonProperty("serviceName")
  public void setServiceName(String serviceName) {
    this.serviceName = serviceName;
  }

  @JsonProperty("paasMetrics")
  public List<PaaSMetric> getPaasMetrics() {
    return paasMetrics;
  }

  @JsonProperty("paasMetrics")
  public void setPaasMetrics(List<PaaSMetric> paasMetrics) {
    this.paasMetrics = paasMetrics;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(serviceName).append(paasMetrics).toHashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if ((other instanceof Service) == false) {
      return false;
    }
    Service rhs = ((Service) other);
    return new EqualsBuilder().append(serviceName, rhs.serviceName)
        .append(paasMetrics, rhs.paasMetrics).isEquals();
  }

}