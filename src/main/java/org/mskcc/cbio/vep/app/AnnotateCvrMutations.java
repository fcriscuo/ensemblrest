package org.mskcc.cbio.vep.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.log4j.Logger;
import org.mskcc.cbio.vep.client.VepAnnotationServiceClient;
import org.mskcc.cbio.vep.model.cvr.DmpData;
import org.mskcc.cbio.vep.model.cvr.Result;
import org.mskcc.cbio.vep.model.cvr.SnpIndelExonic;
import org.mskcc.cbio.vep.model.json.Vep;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Copyright (c) 2015 Memorial Sloan-Kettering Cancer Center.
 * <p/>
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY, WITHOUT EVEN THE IMPLIED WARRANTY OF
 * MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.  The software and
 * documentation provided hereunder is on an "as is" basis, and
 * Memorial Sloan-Kettering Cancer Center
 * has no obligations to provide maintenance, support,
 * updates, enhancements or modifications.  In no event shall
 * Memorial Sloan-Kettering Cancer Center
 * be liable to any party for direct, indirect, special,
 * incidental or consequential damages, including lost profits, arising
 * out of the use of this software and its documentation, even if
 * Memorial Sloan-Kettering Cancer Center
 * has been advised of the possibility of such damage.
 * <p/>
 * Created by Fred Criscuolo on 8/24/15.
 * criscuof@mskcc.org
 */
/*
Represents a prototype application that tests the use of a RESTful service for
VEP annotation. This prototype reads in CVR mutations from a specified JSON file,
formats each variation in HGVS formats, and issues a VEP annotation request. The VEP
results are displayed on the console
TODO: filter the results for the canonical transcript
 */
public class AnnotateCvrMutations {
    private static final Logger logger = Logger.getLogger(AnnotateCvrMutations.class);
    private final Path jsonFilePath;

    public AnnotateCvrMutations (Path jsonPath){
        Preconditions.checkArgument(null != jsonPath,
               "A Path to a JSON file of CVR data is required" );
        this.jsonFilePath = jsonPath;
        logger.info("CVR mutations from " +jsonPath.toString() +" will be annotated");
    }

    private void annotateMutations(){
        ObjectMapper OBJECT_MAPPER = new ObjectMapper();
        try {
            DmpData data = OBJECT_MAPPER.readValue(this.jsonFilePath.toFile(), DmpData.class);
            final List<SnpIndelExonic> snpList = Lists.newArrayList();
            Observable<Result> resultObservable = Observable.from(data.getResults());
            resultObservable.subscribe(new Subscriber<Result>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable throwable) {
                    logger.error(throwable.getMessage());
                }

                @Override
                public void onNext(Result result) {
                    final String sampleId = result.getMetaData().getDmpSampleId();
                   Observable.from(result.getSnpIndelExonic())
                           .subscribe(new Action1<SnpIndelExonic>() {
                               @Override
                               public void call(SnpIndelExonic snpIndelExonic) {
                                   // standardize on genomic based variation string
                                   Optional<Vep> vepOpt = VepAnnotationServiceClient.annotateVariation(resolveGenomicVariationString(snpIndelExonic));
                                   if(vepOpt.isPresent()){
                                       logger.info(sampleId +" " + vepOpt.toString());
                                   }
                               }
                           });

                }
            });

        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

    }

    /*
    Private method to format the mutation into HGVS format using chromosomal positions and
    reference and tumor alleles
    This method should prove more consistent than using the cDNA change
    ensembl Web service accepts endPosition = startPosition
     */
    private String resolveGenomicVariationString(SnpIndelExonic snp) {
        Integer endPosition = snp.getStartPosition() + snp.getRefAllele().length() -1;
        return snp.getChromosome() +":g." + snp.getStartPosition() + "_"
                +endPosition +snp.getRefAllele() +">"
                +snp.getAltAllele();
    }


    /*
    main method accepts the full name of a JSON file withe CVR data
     */
    public static void main(String...args){
        if (args.length < 1){
            args = new String[]{"/tmp/cvr_data.json"}; // default value
        }
        AnnotateCvrMutations app = new AnnotateCvrMutations((Paths.get(args[0])));
        app.annotateMutations();
    }

}
