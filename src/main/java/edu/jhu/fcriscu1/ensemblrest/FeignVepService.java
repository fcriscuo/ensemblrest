package edu.jhu.fcriscu1.ensemblrest;

/**
 * Created by Fred Criscuolo on 9/1/15.
 *
 */

import feign.*;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import feign.gson.GsonDecoder;
import edu.jhu.fcriscu1.vep.model.json.Annotation;

import java.io.IOException;
import java.util.List;

/**
 * Inspired by {@code com.example.retrofit.GitHubClient}
 */
public class FeignVepService {

    public static final String ENSEMBL_URL ="http://grch37.rest.ensembl.org";
    public interface Ensembl {
        @RequestLine("GET /vep/human/hgvs/{genepos}/?content-type=application/json")
        @Headers("Content-Type: application/json")
        List<Annotation> annotations(@Param("genepos") String genepos);
    }

    static class VepClientError extends RuntimeException {
        private String message; // parsed from json
        @Override
        public String getMessage() {
            return message;
        }
    }

    public static void main(String... args) {
        Decoder decoder = new GsonDecoder();
        Ensembl ensembl = Feign.builder()
                .decoder(decoder)
                .errorDecoder(new VepErrorDecoder(decoder))
                .logger(new Logger.ErrorLogger())
                .logLevel(Logger.Level.BASIC)
                .target(Ensembl.class, ENSEMBL_URL);
        String testVariation = "17:g.41270062G>A";
        List<Annotation> annotations = ensembl.annotations(testVariation);
       for(Annotation annotation : annotations){
           System.out.println(annotation.toString());
       }
    }

    static class VepErrorDecoder implements ErrorDecoder {
        final Decoder decoder;
        final ErrorDecoder defaultDecoder = new Default();
        VepErrorDecoder(Decoder decoder) {
            this.decoder = decoder;
        }
        @Override
        public Exception decode(String methodKey, Response response) {
            try {
                return (Exception) decoder.decode(response, VepClientError.class);
            } catch (IOException fallbackToDefault) {
                return defaultDecoder.decode(methodKey, response);
            }
        }
    }
}
