
package edu.jhu.fcriscu1.vep.model.cvr;

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
        "cnv-intragenic-variants",
        "cnv-variants",
        "meta-data",
        "snp-indel-exonic",
        "snp-indel-exonic-np",
        "snp-indel-silent",
        "snp-indel-silent-np",
        "sv-variants",
        "segment-data"
})
public class Result {
    @JsonProperty("cnv-intragenic-variants")
    private List<CnvIntragenicVariant> cnvIntragenicVariants = new ArrayList<CnvIntragenicVariant>();
    @JsonProperty("cnv-variants")
    private List<CnvVariant> cnvVariants = new ArrayList<CnvVariant>();
    @JsonProperty("meta-data")
    private MetaData metaData;
    @JsonProperty("snp-indel-exonic")
    private List<SnpIndelExonic> snpIndelExonic = new ArrayList<SnpIndelExonic>();
    @JsonProperty("snp-indel-exonic-np")
    private List<SnpIndelExonicNP> snpIndelExonicNP = new ArrayList<SnpIndelExonicNP>();
    @JsonProperty("snp-indel-silent")
    private List<SnpIndelSilent> snpIndelSilent = new ArrayList<SnpIndelSilent>();
    @JsonProperty("snp-indel-silent-np")
    private List<SnpIndelSilentNP> snpIndelSilentNP = new ArrayList<SnpIndelSilentNP>();
    @JsonProperty("sv-variants")
    private List<StructuralVariant> svVariants = new ArrayList<StructuralVariant>();
    @JsonProperty("segment-data")
    private List<SegmentDatum> segmentData = new ArrayList<SegmentDatum>();
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     *     The cnvIntragenicVariants
     */
    @JsonProperty("cnv-intragenic-variants")
    public List<CnvIntragenicVariant> getCnvIntragenicVariants() {
        return cnvIntragenicVariants;
    }

    /**
     *
     * @param cnvIntragenicVariants
     *     The cnv-intragenic-variants
     */
    @JsonProperty("cnv-intragenic-variants")
    public void setCnvIntragenicVariants(List<CnvIntragenicVariant> cnvIntragenicVariants) {
        this.cnvIntragenicVariants = cnvIntragenicVariants;
    }

    /**
     *
     * @return
     *     The cnvVariants
     */
    @JsonProperty("cnv-variants")
    public List<CnvVariant> getCnvVariants() {
        return cnvVariants;
    }

    /**
     *
     * @param cnvVariants
     *     The cnv-variants
     */
    @JsonProperty("cnv-variants")
    public void setCnvVariants(List<CnvVariant> cnvVariants) {
        this.cnvVariants = cnvVariants;
    }

    /**
     *
     * @return
     *     The metaData
     */
    @JsonProperty("meta-data")
    public MetaData getMetaData() {
        return metaData;
    }

    /**
     *
     * @param metaData
     *     The meta-data
     */
    @JsonProperty("meta-data")
    public void setMetaData(MetaData metaData) {
        this.metaData = metaData;
    }

    /**
     *
     * @return
     *     The snpIndelExonic
     */
    @JsonProperty("snp-indel-exonic")
    public List<SnpIndelExonic> getSnpIndelExonic() {
        return snpIndelExonic;
    }

    /**
     *
     * @param snpIndelExonic
     *     The snp-indel-exonic
     */
    @JsonProperty("snp-indel-exonic")
    public void setSnpIndelExonic(List<SnpIndelExonic> snpIndelExonic) {
        this.snpIndelExonic = snpIndelExonic;
    }

    // non-panel exonic
    /**
     *
     * @return
     *     The snpIndelExonicNP
     */
    @JsonProperty("snp-indel-exonic-np")
    public List<SnpIndelExonicNP> getSnpIndelExonicNP() {
        return snpIndelExonicNP;
    }

    /**
     *
     * @param snpIndelExonicNP
     *     The snp-indel-exonic-np
     */
    @JsonProperty("snp-indel-exonic-np")
    public void setSnpIndelExonicNP(List<SnpIndelExonicNP> snpIndelExonicNP) {
        this.snpIndelExonicNP = snpIndelExonicNP;
    }

    /**
     *
     * @return
     *     The snpIndelSilent
     */
    @JsonProperty("snp-indel-silent")
    public List<SnpIndelSilent> getSnpIndelSilent() {
        return snpIndelSilent;
    }

    /**
     *
     * @param snpIndelSilent
     *     The snp-indel-silent
     */
    @JsonProperty("snp-indel-silent")
    public void setSnpIndelSilent(List<SnpIndelSilent> snpIndelSilent) {
        this.snpIndelSilent = snpIndelSilent;
    }

    // non-panel silent
    /**
     *
     * @return
     *     The snpIndelSilentNP
     */
    @JsonProperty("snp-indel-silent-np")
    public List<SnpIndelSilentNP> getSnpIndelSilentNP() {
        return snpIndelSilentNP;
    }

    /**
     *
     * @param snpIndelSilentNP
     *     The snp-indel-silent-np
     */
    @JsonProperty("snp-indel-silent-np")
    public void setSnpIndelSilentNP(List<SnpIndelSilentNP> snpIndelSilentNP) {
        this.snpIndelSilentNP = snpIndelSilentNP;
    }

    /**
     *
     * @return
     *     The svVariants
     */
    @JsonProperty("sv-variants")
    public List<StructuralVariant> getSvVariants() {
        return svVariants;
    }

    /**
     *
     * @param svVariants
     *     The sv-variants
     */
    @JsonProperty("sv-variants")
    public void setSvVariants(List<StructuralVariant> svVariants) {
        this.svVariants = svVariants;
    }

    /**
     *
     * @return
     *     The segmentData
     */
    @JsonProperty("segment-data")
    public List<SegmentDatum> getSegmentData() {
        return segmentData;
    }

    /**
     *
     * @param segmentData
     *     The segment-data
     */
    @JsonProperty("segment-data")
    public void setSegmentData(List<SegmentDatum> segmentData) {
        this.segmentData = segmentData;
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

    // add support for non-panel exonic and silent
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(cnvIntragenicVariants).append(cnvVariants).append(metaData)
                .append(snpIndelExonic).append(snpIndelSilent)
                .append(snpIndelExonicNP).append(snpIndelSilentNP)
                .append(svVariants).append(segmentData)
                .append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof Result) == false) {
            return false;
        }
        Result rhs = ((Result) other);
        return new EqualsBuilder().append(cnvIntragenicVariants, rhs.cnvIntragenicVariants).append(cnvVariants, rhs.cnvVariants)
                .append(metaData, rhs.metaData)
                .append(snpIndelExonic, rhs.snpIndelExonic).append(snpIndelSilent, rhs.snpIndelSilent)
                .append(snpIndelExonicNP, rhs.snpIndelExonicNP).append(snpIndelSilentNP, rhs.snpIndelSilentNP)
                .append(svVariants, rhs.svVariants).append(segmentData, rhs.segmentData)
                .append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
