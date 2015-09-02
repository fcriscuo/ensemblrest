
package org.mskcc.cbio.vep.model.json;

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
    "gene_id",
    "distance",
    "variant_allele",
    "biotype",
    "gene_symbol_source",
    "consequence_terms",
    "strand",
    "hgnc_id",
    "gene_symbol",
    "transcript_id",
    "impact"
})
public class TranscriptConsequence {

    @JsonProperty("gene_id")
    private String gene_id;
    @JsonProperty("distance")
    private Integer distance;
    @JsonProperty("variant_allele")
    private String variant_allele;
    @JsonProperty("biotype")
    private String biotype;
    @JsonProperty("gene_symbol_source")
    private String gene_symbol_source;
    @JsonProperty("consequence_terms")
    private List<String> consequence_terms = new ArrayList<String>();
    @JsonProperty("strand")
    private Integer strand;
    @JsonProperty("hgnc_id")
    private Integer hgnc_id;
    @JsonProperty("gene_symbol")
    private String gene_symbol;
    @JsonProperty("transcript_id")
    private String transcript_id;
    @JsonProperty("impact")
    private String impact;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The gene_id
     */
    @JsonProperty("gene_id")
    public String getGeneId() {
        return gene_id;
    }

    /**
     * 
     * @param gene_id
     *     The gene_id
     */
    @JsonProperty("gene_id")
    public void setGeneId(String gene_id) {
        this.gene_id = gene_id;
    }

    /**
     * 
     * @return
     *     The distance
     */
    @JsonProperty("distance")
    public Integer getDistance() {
        return distance;
    }

    /**
     * 
     * @param distance
     *     The distance
     */
    @JsonProperty("distance")
    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    /**
     * 
     * @return
     *     The variant_allele
     */
    @JsonProperty("variant_allele")
    public String getVariantAllele() {
        return variant_allele;
    }

    /**
     * 
     * @param variant_allele
     *     The variant_allele
     */
    @JsonProperty("variant_allele")
    public void setVariantAllele(String variant_allele) {
        this.variant_allele = variant_allele;
    }

    /**
     * 
     * @return
     *     The biotype
     */
    @JsonProperty("biotype")
    public String getBiotype() {
        return biotype;
    }

    /**
     * 
     * @param biotype
     *     The biotype
     */
    @JsonProperty("biotype")
    public void setBiotype(String biotype) {
        this.biotype = biotype;
    }

    /**
     * 
     * @return
     *     The gene_symbol_source
     */
    @JsonProperty("gene_symbol_source")
    public String getGeneSymbolSource() {
        return gene_symbol_source;
    }

    /**
     * 
     * @param gene_symbol_source
     *     The gene_symbol_source
     */
    @JsonProperty("gene_symbol_source")
    public void setGeneSymbolSource(String gene_symbol_source) {
        this.gene_symbol_source = gene_symbol_source;
    }

    /**
     * 
     * @return
     *     The consequence_terms
     */
    @JsonProperty("consequence_terms")
    public List<String> getConsequenceTerms() {
        return consequence_terms;
    }

    /**
     * 
     * @param consequence_terms
     *     The consequence_terms
     */
    @JsonProperty("consequence_terms")
    public void setConsequenceTerms(List<String> consequence_terms) {
        this.consequence_terms = consequence_terms;
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
     *     The hgnc_id
     */
    @JsonProperty("hgnc_id")
    public Integer getHgncId() {
        return hgnc_id;
    }

    /**
     * 
     * @param hgnc_id
     *     The hgnc_id
     */
    @JsonProperty("hgnc_id")
    public void setHgncId(Integer hgnc_id) {
        this.hgnc_id = hgnc_id;
    }

    /**
     * 
     * @return
     *     The gene_symbol
     */
    @JsonProperty("gene_symbol")
    public String getGeneSymbol() {
        return gene_symbol;
    }

    /**
     * 
     * @param gene_symbol
     *     The gene_symbol
     */
    @JsonProperty("gene_symbol")
    public void setGeneSymbol(String gene_symbol) {
        this.gene_symbol = gene_symbol;
    }

    /**
     * 
     * @return
     *     The transcript_id
     */
    @JsonProperty("transcript_id")
    public String getTranscriptId() {
        return transcript_id;
    }

    /**
     * 
     * @param transcript_id
     *     The transcript_id
     */
    @JsonProperty("transcript_id")
    public void setTranscriptId(String transcript_id) {
        this.transcript_id = transcript_id;
    }

    /**
     * 
     * @return
     *     The impact
     */
    @JsonProperty("impact")
    public String getImpact() {
        return impact;
    }

    /**
     * 
     * @param impact
     *     The impact
     */
    @JsonProperty("impact")
    public void setImpact(String impact) {
        this.impact = impact;
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
        return new HashCodeBuilder().append(gene_id).append(distance).append(variant_allele).append(biotype).append(gene_symbol_source).append(consequence_terms).append(strand).append(hgnc_id).append(gene_symbol).append(transcript_id).append(impact).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof TranscriptConsequence) == false) {
            return false;
        }
        TranscriptConsequence rhs = ((TranscriptConsequence) other);
        return new EqualsBuilder().append(gene_id, rhs.gene_id).append(distance, rhs.distance).append(variant_allele, rhs.variant_allele).append(biotype, rhs.biotype).append(gene_symbol_source, rhs.gene_symbol_source).append(consequence_terms, rhs.consequence_terms).append(strand, rhs.strand).append(hgnc_id, rhs.hgnc_id).append(gene_symbol, rhs.gene_symbol).append(transcript_id, rhs.transcript_id).append(impact, rhs.impact).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

}
