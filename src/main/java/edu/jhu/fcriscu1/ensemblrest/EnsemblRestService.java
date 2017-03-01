package edu.jhu.fcriscu1.ensemblrest;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by fcriscuo on 4/11/15.
 */
@Log4j
public class EnsemblRestService {

    private static final Logger logger = Logger.getLogger(EnsemblRestService.class);
    private static final String ENSEMBL_URL_TEMPLATE = "http://grch37.rest.ensembl.org/vep/human/hgvs/GENPOS?";

    public EnsemblRestService() {}
/*
public method to retrieve ensembl VEP annotations for a specified variant
returns resultS IN JSON FORMAT
 */
    public String resolveVEPByGenomicLocation (String genomicLocation){
        Preconditions.checkArgument(!Strings.isNullOrEmpty(genomicLocation),
                "A genomic location in HGVS notation is required");
        try {
            URL url = new URL(ENSEMBL_URL_TEMPLATE.replace("GENPOS",genomicLocation));
            URLConnection connection = url.openConnection();
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            httpConnection.setRequestProperty("Content-Type", "application/json");
            InputStream response = connection.getInputStream();
            int responseCode = httpConnection.getResponseCode();
            if(responseCode != 200) {
                if(responseCode == 429 && httpConnection.getHeaderField("Retry-After") != null) {
                    double sleepFloatingPoint = Double.valueOf(httpConnection.getHeaderField("Retry-After"));
                    double sleepMillis = 1000 * sleepFloatingPoint;
                    Thread.sleep((long)sleepMillis);
                    return resolveVEPByGenomicLocation(genomicLocation);
                }
                logger.error("Response code was not 200. Detected response was " + responseCode +
                        " for genomic location: " + genomicLocation);
            }

            try(Reader reader  = new BufferedReader(new InputStreamReader(response, "UTF-8"))) {
                StringBuilder builder = new StringBuilder();
                char[] buffer = new char[8192];
                int read;
                while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
                    builder.append(buffer, 0, read);
                }
                return builder.toString();

            } catch (IOException ex) {
                    logger.error(ex.getMessage());
            }

        } catch ( InterruptedException |IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
        return "";

    }

    /*
    main method for stand alone testing
     */
    public static void main(String[] args) throws Exception {
        EnsemblRestService service = new EnsemblRestService();
        String genomicLocation1 = "17:g.41270062G>A";
        System.out.println(service.resolveVEPByGenomicLocation(genomicLocation1));
        String genomicLocation2 = "10:g.42936755A>C";
        System.out.println(service.resolveVEPByGenomicLocation(genomicLocation2));
        String genomicLocation3 = "MLL2:c.16022C>A";
        System.out.println(service.resolveVEPByGenomicLocation(genomicLocation3));
    }
}