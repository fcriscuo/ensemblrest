
package edu.jhu.fcriscu1.vep.model.json;

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
    "assembly_name",
    "end",
    "seq_region_name",
    "transcript_consequences",
    "strand",
    "id",
    "allele_string",
    "most_severe_consequence",
    "start"
})
public class Annotation {

    @JsonProperty("assembly_name")
    private String assembly_name;
    @JsonProperty("end")
    private Integer end;
    @JsonProperty("seq_region_name")
    private String seq_region_name;
    @JsonProperty("transcript_consequences")
    private List<TranscriptConsequence> transcript_consequences = new ArrayList<TranscriptConsequence>();
    @JsonProperty("strand")
    private Integer strand;
    @JsonProperty("id")
    private String id;
    @JsonProperty("allele_string")
    private String allele_string;
    @JsonProperty("most_severe_consequence")
    private String most_severe_consequence;
    @JsonProperty("start")
    private Integer start;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The assemblyName
     */
    @JsonProperty("assembly_name")
    public String getAssemblyName() {
        return assembly_name;
    }

    /**
     * 
     * @param assemblyName
     *     The assembly_name
     */
    @JsonProperty("assembly_name")
    public void setAssemblyName(String assemblyName) {
        this.assembly_name = assemblyName;
    }

    /**
     * 
     * @return
     *     The end
     */
    @JsonProperty("end")
    public Integer getEnd() {
        return end;
    }

    /**
     * 
     * @param end
     *     The end
     */
    @JsonProperty("end")
    public void setEnd(Integer end) {
        this.end = end;
    }

    /**
     * 
     * @return
     *     The seqRegionName
     */
    @JsonProperty("seq_region_name")
    public String getSeqRegionName() {
        return seq_region_name;
    }

    /**
     * 
     * @param seqRegionName
     *     The seq_region_name
     */
    @JsonProperty("seq_region_name")
    public void setSeqRegionName(String seqRegionName) {
        this.seq_region_name = seqRegionName;
    }

    /**
     * 
     * @return
     *     The transcriptConsequences
     */
    @JsonProperty("transcript_consequences")
    public List<TranscriptConsequence> getTranscriptConsequences() {
        return transcript_consequences;
    }

    /**
     * 
     * @param transcriptConsequences
     *     The transcript_consequences
     */
    @JsonProperty("transcript_consequences")
    public void setTranscriptConsequences(List<TranscriptConsequence> transcriptConsequences) {
        this.transcript_consequences = transcriptConsequences;
    }

    /**
     * 
     * @return
     *     The strand
     */
    @JsonProperty("strand")
    public Integer getStrand() {
        return strand;
    }

    /**
     * 
     * @param strand
     *     The strand
     */
    @JsonProperty("strand")
    public void setStrand(Integer strand) {
        this.strand = strand;
    }

    /**
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The alleleString
     */
    @JsonProperty("allele_string")
    public String getAlleleString() {
        return allele_string;
    }

    /**
     * 
     * @param alleleString
     *     The allele_string
     */
    @JsonProperty("allele_string")
    public void setAlleleString(String alleleString) {
        this.allele_string = alleleString;
    }

    /**
     * 
     * @return
     *     The mostSevereConsequence
     */
    @JsonProperty("most_severe_consequence")
    public String getMostSevereConsequence() {
        return most_severe_consequence;
    }

    /**
     * 
     * @param mostSevereConsequence
     *     The most_severe_consequence
     */
    @JsonProperty("most_severe_consequence")
    public void setMostSevereConsequence(String mostSevereConsequence) {
        this.most_severe_consequence = mostSevereConsequence;
    }

    /**
     * 
     * @return
     *     The start
     */
    @JsonProperty("start")
    public Integer getStart() {
        return start;
    }

    /**
     * 
     * @param start
     *     The start
     */
    @JsonProperty("start")
    public void setStart(Integer start) {
        this.start = start;
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
        return new HashCodeBuilder().append(assembly_name).append(end).append(seq_region_name)
                .append(transcript_consequences).append(strand).append(id).append(allele_string).
                        append(most_severe_consequence).append(start).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Annotation) == false) {
            return false;
        }
        Annotation rhs = ((Annotation) other);
        return new EqualsBuilder().append(assembly_name, rhs.assembly_name).append(end, rhs.end).append(seq_region_name, rhs.seq_region_name)
                .append(transcript_consequences, rhs.transcript_consequences).append(strand,
                        rhs.strand).append(id, rhs.id).append(allele_string,
                        rhs.allele_string).append(most_severe_consequence, rhs.most_severe_consequence).append(start, rhs.start).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
