package edu.jhu.fcriscu1.vep.data;


import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.base.Predicate;
import com.google.common.base.Stopwatch;
import com.google.common.collect.FluentIterable;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.log4j.Logger;
import edu.jhu.fcriscu1.vep.model.HGVSVariationFormatFunctionCollection;


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
 * Created by Fred Criscuolo on 4/12/15.
 *
 */
/*
Represents a Java application that will read in a somatic mutation file in MAF format
and invoke the remote ensembl VEP Web service for mutation annotation. Primary purpose is
test the reliability and performance of using the remote ensembl service to annotate
large MAF files.
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

    /*
    Read in mutation records from a file in MAF format, filter out non-SNP variants,
    use MAF record attributes to create a variation in HGVS format, and invoke VEP
    annotation.
     */
    List<String> generateJsonList() {
        try (BufferedReader reader = Files.newBufferedReader(this.mafFilePath, Charset.defaultCharset())) {
            final CSVParser parser = new CSVParser(reader, CSVFormat.TDF.withHeader().withCommentMarker('#'));
            final VepAnnotator annotator = new VepAnnotator();
            Function<CSVRecord, String> transformationFunction = HGVSVariationFormatFunctionCollection
                    .INSTANCE.snpFormatFunction();
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
        if (args.length < 1){
            args = new String[]{"/tmp/data_mutations_extended.txt"}; // default value
        }
        Path filePath = Paths.get(args[0]);
        MafFileProcessor processor = new MafFileProcessor(filePath);
        // time VEP processing and count the number of somatic mutations annotated
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
