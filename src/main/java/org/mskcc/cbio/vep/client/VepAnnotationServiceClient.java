package org.mskcc.cbio.vep.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import org.apache.log4j.Logger;
import org.mskcc.cbio.vep.model.json.Vep;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import java.net.URLEncoder;

/**
 * Created by fcriscuo on 8/23/15.
 */
/*
Represents a RESTful Web service client that accepts variations in HGVS format
and invokes a local Web service to invoke VEP annotation. Removes extraneous
characters from the Web service response to facilitate mapping the JSON objects
to Java objects.
 */
public class VepAnnotationServiceClient {
    private static final Logger logger = Logger.getLogger(VepAnnotationServiceClient.class);
    private  static final String baseUrl = "http://localhost:9090/vep";

    public static Optional<Vep> annotateVariation(String variation){
        ObjectMapper OBJECT_MAPPER = new ObjectMapper();

        if(!Strings.isNullOrEmpty(variation)){
            logger.info("Annotating: " + variation);
            try {
                Client client = ClientBuilder.newClient();
                WebTarget target = client.target(baseUrl);
                // the JSON string returned by VEP must be cleaned up to map to Java objects
                String vepAnnotation = target.queryParam("hgvs",
                        URLEncoder.encode(variation,"UTF-8" ))
                        .request()
                        .get(String.class).replaceAll("\\\\", "")
                        .replace("\"[","[")
                        .replace("]\"", "]");
                logger.info(vepAnnotation);
                Vep vep = OBJECT_MAPPER.readValue(vepAnnotation,Vep.class);
                if (null != vep){
                    return Optional.of(vep);
                }
                logger.info("Failed to annotate "+ variation);
            } catch (Exception e) {
                logger.error(e.getMessage());
                e.printStackTrace();
            }

        }
        return Optional.absent();
    }

    public static void main (String...args) {
        String variation = "10:g.29046452C>T";
        Optional<Vep> vaOpt = VepAnnotationServiceClient.annotateVariation(variation);
        if(vaOpt.isPresent()){
            logger.info("Variation: " +variation +" allele: "
                    +vaOpt.get().getAnnotation().get(0).getAlleleString());
        }
    }
}
