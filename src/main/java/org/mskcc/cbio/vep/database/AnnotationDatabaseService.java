package org.mskcc.cbio.vep.database;

import autovalue.shaded.com.google.common.common.base.Preconditions;
import com.github.davidmoten.rx.jdbc.Database;
import com.github.davidmoten.rx.jdbc.ResultSetMapper;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import org.apache.log4j.Logger;
import org.mskcc.cbio.vep.database.dao.VepAnnotation;
import org.mskcc.cbio.vep.database.dao.VepJsonCacheMapper;
import org.mskcc.cbio.vep.database.model.VepJsonCache;
import org.mskcc.cbio.vep.database.model.VepJsonCacheExample;
import org.mskcc.cbio.vep.util.VEPSessionManager;
import rx.Observable;
import rx.functions.Action1;

import java.util.List;

/**
 * Created by fcriscuo on 8/30/15.
 */
public enum AnnotationDatabaseService {
    INSTANCE;
    private static final Logger logger = Logger.getLogger(AnnotationDatabaseService.class);
    private final Database db = MySqlDatabaseService.INSTANCE.database();
    private static final String queryString = "select annotation from cgds_mskimpact.vep_annotation where hgvs_variation = :variation";
    private static final String insertString = "insert into cgds_mskimpact.vep_annotation (hgvs_variation, annotation) values(?,?)";

    /*
    Public method to find a cached annotation in the local database
     */
    public Optional<VepAnnotation> findAnnotationInDatabase(final String variation){
        Preconditions.checkArgument(!Strings.isNullOrEmpty(variation),
                "A variation is required");
        final List<VepAnnotation>  vepList =
             db.select(queryString)
                .parameter("variation",variation)
                .get((ResultSetMapper<VepAnnotation>) (rs) -> {return new VepAnnotation(variation, rs.getString(1));} ).toList().toBlocking().single();

        if(vepList.isEmpty()){
            logger.info("HGVS variation: " +variation +" was not found in the database");
            return Optional.absent();
        }
        return Optional.of(vepList.get(0));
    }

    private boolean isVariationPersisted(String variation){
        if(this.findAnnotationInDatabase(variation).isPresent()) {
            return true;
        }
        return false;
    }

    /*
    public method to insert annotation into database using the variation as a key
     */
    public Observable<Integer> insertAnnotationIntoDatabase(final VepAnnotation vep){
        Preconditions.checkArgument(null != vep,
                "A VepAnnotation instance is required");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(vep.getHgvsVariation()),
                "A variation in HGVS format is required");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(vep.getAnnotation()),
                "An annotation in JSON format is required");
        if( !this.isVariationPersisted(vep.getHgvsVariation())) {
            return db.update(insertString)
                    .parameters(vep.getHgvsVariation(), vep.getAnnotation())
                    .count();
        }
        logger.error("====>ERROR: The variation: " +vep.getHgvsVariation() +" is already persisted");
        return Observable.just(0);
    }

    public static void main(String...args){
        VepAnnotation vep01 = new VepAnnotation("10:g.100001483G>C","[{\"colocated_variants\":[{\"seq_region_name\":\"10\",\"eas_allele\":\"C\",\"amr_maf\":0,\"strand\":1,\"sas_allele\":\"C\",\"id\":\"rs528478084\",\"allele_string\":\"G/C\",\"sas_maf\":0,\"amr_allele\":\"C\",\"minor_allele_freq\":0.0004,\"afr_allele\":\"C\",\"eas_maf\":0,\"afr_maf\":0.0015,\"end\":100001483,\"eur_maf\":0,\"eur_allele\":\"C\",\"minor_allele\":\"C\",\"start\":100001483}],\"assembly_name\":\"GRCh37\",\"end\":100001483,\"seq_region_name\":\"10\",\"transcript_consequences\":[{\"gene_id\":\"ENSG00000166024\",\"variant_allele\":\"C\",\"biotype\":\"protein_coding\",\"gene_symbol_source\":\"HGNC\",\"consequence_terms\":[\"intron_variant\"],\"strand\":1,\"hgnc_id\":23512,\"gene_symbol\":\"R3HCC1L\",\"transcript_id\":\"ENST00000314594\",\"impact\":\"MODIFIER\"},{\"gene_id\":\"ENSG00000166024\",\"variant_allele\":\"C\",\"biotype\":\"protein_coding\",\"gene_symbol_source\":\"HGNC\",\"consequence_terms\":[\"intron_variant\"],\"strand\":1,\"hgnc_id\":23512,\"gene_symbol\":\"R3HCC1L\",\"transcript_id\":\"ENST00000298999\",\"impact\":\"MODIFIER\"},{\"gene_id\":\"ENSG00000166024\",\"variant_allele\":\"C\",\"biotype\":\"protein_coding\",\"gene_symbol_source\":\"HGNC\",\"consequence_terms\":[\"intron_variant\"],\"strand\":1,\"hgnc_id\":23512,\"gene_symbol\":\"R3HCC1L\",\"transcript_id\":\"ENST00000370586\",\"impact\":\"MODIFIER\"},{\"gene_id\":\"ENSG00000166024\",\"variant_allele\":\"C\",\"biotype\":\"protein_coding\",\"gene_symbol_source\":\"HGNC\",\"consequence_terms\":[\"intron_variant\"],\"strand\":1,\"hgnc_id\":23512,\"gene_symbol\":\"R3HCC1L\",\"transcript_id\":\"ENST00000370584\",\"impact\":\"MODIFIER\"}],\"strand\":1,\"id\":\"10:g.100001483G>C\",\"allele_string\":\"G/C\",\"most_severe_consequence\":\"intron_variant\",\"start\":100001483}]\n");
        Observable<Integer> res = AnnotationDatabaseService.INSTANCE.insertAnnotationIntoDatabase(vep01);
        res.subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                logger.info("Insert results = " +integer);
            }
        });
        Optional<VepAnnotation> vepOpt = AnnotationDatabaseService.INSTANCE.findAnnotationInDatabase("10:g.100001483G>C");
        if(vepOpt.isPresent()){
            logger.info("====Fouund " +vepOpt.get().toString());
        } else {
            logger.info("Failed to find variation ");
        }

    }

}
