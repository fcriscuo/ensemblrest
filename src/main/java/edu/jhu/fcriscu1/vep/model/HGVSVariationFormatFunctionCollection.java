package edu.jhu.fcriscu1.vep.model;

import com.google.common.base.Function;
import org.apache.commons.csv.CSVRecord;

import javax.annotation.Nullable;

/**

 * Created by Fred Criscuolo on 4/12/15.
 *
 */
public enum HGVSVariationFormatFunctionCollection {
    INSTANCE;
    private static final String SNP_VARIATION_SYMBOL= ">";

    private Function<CSVRecord,String> formatHgvsVariationFromMafFunction = new Function<CSVRecord, String>() {
        @Nullable
        @Override
        public String apply(CSVRecord record) {
            StringBuilder sb = new StringBuilder()
                    .append(record.get("Chromosome"))
                    .append(":g.")
                    .append(record.get("Start_Position"))
                    .append(record.get("Reference_Allele"))
                    .append(SNP_VARIATION_SYMBOL)
                    .append(record.get("Tumor_Seq_Allele2"));  // variation is in tumor allele2
            return sb.toString();
        }
    };
    public Function<CSVRecord,String>  snpFormatFunction() { return formatHgvsVariationFromMafFunction;}
}
