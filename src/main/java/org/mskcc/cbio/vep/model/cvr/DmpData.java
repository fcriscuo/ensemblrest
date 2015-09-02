
package org.mskcc.cbio.vep.model.cvr;

import com.fasterxml.jackson.annotation.*;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({
    "sample-count",
    "disclaimer",
    "results"
})
public class DmpData {

    @JsonProperty("sample-count")
    private String sampleCount;
    @JsonProperty("disclaimer")
    private String disclaimer;
    @JsonProperty("results")
    private List<Result> results = new ArrayList<Result>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The sampleCount
     */
    @JsonProperty("sample-count")
    public String getSampleCount() {
        return sampleCount;
    }

    /**
     * 
     * @param sampleCount
     *     The sample-count
     */
    @JsonProperty("sample-count")
    public void setSampleCount(String sampleCount) {
        this.sampleCount = sampleCount;
    }

    /**
     * 
     * @return
     *     The disclaimer
     */
    @JsonProperty("disclaimer")
    public String getDisclaimer() {
        return disclaimer;
    }

    /**
     * 
     * @param disclaimer
     *     The disclaimer
     */
    @JsonProperty("disclaimer")
    public void setDisclaimer(String disclaimer) {
        this.disclaimer = disclaimer;
    }

    /**
     * 
     * @return
     *     The results
     */
    @JsonProperty("results")
    public List<Result> getResults() {
        return results;
    }

    /**
     * 
     * @param results
     *     The results
     */
    @JsonProperty("results")
    public void setResults(List<Result> results) {
        this.results = results;
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
        return new HashCodeBuilder().append(sampleCount).append(disclaimer).append(results).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DmpData) == false) {
            return false;
        }
        DmpData rhs = ((DmpData) other);
        return new EqualsBuilder().append(sampleCount, rhs.sampleCount).append(disclaimer, rhs.disclaimer).append(results, rhs.results).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
