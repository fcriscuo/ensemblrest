package edu.jhu.fcriscu1.vep.client;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.apache.log4j.Logger;
import java.net.URLEncoder;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;



/**
 * Created by fcriscuo on 8/23/15.
 * prototype client for ensembl VEP RESTful service
 */
public class VepAnnotationClient {
    private static final Logger logger = Logger.getLogger(VepAnnotationClient.class);
    private static String baseURL = "http://localhost:8080/vep?hgvs=";

    public static VariationAnnotation resolveVepAnnotation(String hgvsVariation){
        Preconditions.checkArgument(!Strings.isNullOrEmpty(hgvsVariation),"A genomic variation in HGVS format ids required");




        try {
            String encodedVariation = URLEncoder.encode(hgvsVariation,"UTF-8");
            String url = baseURL +encodedVariation;
            logger.info("URL: " +url);
            ClientConfig clientConfig = new DefaultClientConfig();
            clientConfig.getFeatures().put(
                    JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

            Client client = Client.create(clientConfig);


            WebResource webResource = client
                    .resource(url);

            ClientResponse response = webResource.accept("application/json")
                    .get(ClientResponse.class);

            if (response.getStatus() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + response.getStatus());
            }

            VariationAnnotation output = response.getEntity(VariationAnnotation.class);
            //String output = response.getEntity(String.class);
            logger.info("Received: " +output.allele_string) ;
            return output;
        } catch (Exception e ){
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        return null;


    }

   public static void main (String...args) {
       String variation = "10:g.63573762G>C";
       VariationAnnotation va = VepAnnotationClient.resolveVepAnnotation(variation);
      // String out = VepAnnotationClient.resolveVepAnnotation(variation);
       logger.info("Variation " +variation +" " +va.most_severe_consequence);
   }

}
