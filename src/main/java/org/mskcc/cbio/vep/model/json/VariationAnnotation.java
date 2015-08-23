package org.mskcc.cbio.vep.model.json;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by fcriscuo on 8/23/15.
 */
public class VariationAnnotation {

    public final String variation;
    public final String assembly_name;
    public final long end;
    public final String seq_region_name;
    public final Transcript_consequence transcript_consequences[];
    public final long strand;
    public final String id;
    public final String allele_string;
    public final String most_severe_consequence;
    public final long start;



    @JsonCreator
    public VariationAnnotation(@JsonProperty ("variation") String variation, @JsonProperty("assembly_name") String assembly_name, @JsonProperty("end") long end, @JsonProperty("seq_region_name") String seq_region_name, @JsonProperty("transcript_consequences") Transcript_consequence[] transcript_consequences, @JsonProperty("strand") long strand, @JsonProperty("id") String id, @JsonProperty("allele_string") String allele_string, @JsonProperty("most_severe_consequence") String most_severe_consequence, @JsonProperty("start") long start){
        this.variation = variation;
        this.assembly_name = assembly_name;
        this.end = end;
        this.seq_region_name = seq_region_name;
        this.transcript_consequences = transcript_consequences;
        this.strand = strand;
        this.id = id;
        this.allele_string = allele_string;
        this.most_severe_consequence = most_severe_consequence;
        this.start = start;
    }

    public static final class Transcript_consequence {
        public final String gene_id;
        public final String variant_allele;
        public final String biotype;
        public final String gene_symbol_source;
        public final String[] consequence_terms;
        public final long strand;
        public final String gene_symbol;
        public final String transcript_id;
        public final String impact;

        @JsonCreator
        public Transcript_consequence(@JsonProperty("gene_id") String gene_id, @JsonProperty("variant_allele") String variant_allele, @JsonProperty("biotype") String biotype, @JsonProperty("gene_symbol_source") String gene_symbol_source, @JsonProperty("consequence_terms") String[] consequence_terms, @JsonProperty("strand") long strand, @JsonProperty("gene_symbol") String gene_symbol, @JsonProperty("transcript_id") String transcript_id, @JsonProperty("impact") String impact){
            this.gene_id = gene_id;
            this.variant_allele = variant_allele;
            this.biotype = biotype;
            this.gene_symbol_source = gene_symbol_source;
            this.consequence_terms = consequence_terms;
            this.strand = strand;
            this.gene_symbol = gene_symbol;
            this.transcript_id = transcript_id;
            this.impact = impact;
        }
    }
}
