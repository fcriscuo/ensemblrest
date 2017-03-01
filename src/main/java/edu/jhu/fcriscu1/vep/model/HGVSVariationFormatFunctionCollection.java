package edu.jhu.fcriscu1.vep.model;

import com.google.common.base.Function;
import org.apache.commons.csv.CSVRecord;

import javax.annotation.Nullable;

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
