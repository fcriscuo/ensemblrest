package edu.jhu.fcriscu1.vep.model.json;

/**
 * Created by fcriscuolo on 3/1/17.
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "variation",
    "annotation"
})
public class Vep {

  @JsonProperty("variation")
  private String variation;
  @JsonProperty("annotation")
  private List<Annotation> annotation = new ArrayList<Annotation>();
  @JsonIgnore
  private Map<String, Object> additionalProperties = new HashMap<String, Object>();

  /**
   *
   * @return
   *     The variation
   */
  @JsonProperty("variation")
  public String getVariation() {
    return variation;
  }

  /**
   *
   * @param variation
   *     The variation
   */
  @JsonProperty("variation")
  public void setVariation(String variation) {
    this.variation = variation;
  }

  /**
   *
   * @return
   *     The annotation
   */
  @JsonProperty("annotation")
  public List<Annotation> getAnnotation() {
    return annotation;
  }

  /**
   *
   * @param annotation
   *     The annotation
   */
  @JsonProperty("annotation")
  public void setAnnotation(List<Annotation> annotation) {
    this.annotation = annotation;
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
    return new HashCodeBuilder().append(variation).append(annotation).append(additionalProperties).toHashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (other == this) {
      return true;
    }
    if ((other instanceof Vep) == false) {
      return false;
    }
    Vep rhs = ((Vep) other);
    return new EqualsBuilder().append(variation, rhs.variation).append(annotation, rhs.annotation).append(additionalProperties, rhs.additionalProperties).isEquals();
  }

}
