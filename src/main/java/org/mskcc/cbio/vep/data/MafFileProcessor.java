package org.mskcc.cbio.vep.data;


import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Stopwatch;
import com.google.common.collect.FluentIterable;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import org.mskcc.cbio.ensemblrest.EnsemblRestService;
import org.mskcc.cbio.vep.model.HGVSVariationFormatFunctionCollection;


import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
 * Created by Fred Criscuolo on 4/12/15.
 * criscuof@mskcc.org
 */
public class MafFileProcessor {
    private static final Logger logger = Logger.getLogger(MafFileProcessor.class);
    private final Path mafFilePath;
    private final List<String> supportedVariationTypes = Arrays.asList("SNP");

    public MafFileProcessor(Path aPath){
        Preconditions.checkArgument(null != aPath,
                "A Path object pointing to a MAF File is required");
        this.mafFilePath = aPath;
    }

    List<String> generateJsonList() {
        try (BufferedReader reader = Files.newBufferedReader(this.mafFilePath, Charset.defaultCharset())) {
            final CSVParser parser = new CSVParser(reader, CSVFormat.TDF.withHeader().withCommentMarker('#'));
            final VepAnnotator annotator = new VepAnnotator();
            Function<CSVRecord, String> transformationFunction = HGVSVariationFormatFunctionCollection.INSTANCE.snpFormatFunction();
            return FluentIterable.from(parser)
                    .filter(new Predicate<CSVRecord>() {
                        @Override
                        public boolean apply(CSVRecord record) {
                            return supportedVariationTypes.contains(record.get("Variant_Type"));
                        }
                    })
                    .transform(transformationFunction)
                    .transform(new Function<String,String>() {
                        @Nullable
                        @Override
                        public String apply(String genomicLocation) {
                            return annotator.annotateVariation(genomicLocation);
                        }
                    })
                    .toList();

        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return new ArrayList<String>();  // return empty list as default
    }

    public static void main(String...args){
        Path filePath = Paths.get("/tmp/data_mutations_extended.txt");
        MafFileProcessor processor = new MafFileProcessor(filePath);
        Stopwatch sw = Stopwatch.createStarted();
        int count = 0;
        for (String s : processor.generateJsonList()){
            logger.info(s);
            count++;
        }
        sw.stop();
        long seconds = sw.elapsed(TimeUnit.SECONDS);
        logger.info("Time to annotate " +count +" maf records = " + seconds +" seconds");
    }
}