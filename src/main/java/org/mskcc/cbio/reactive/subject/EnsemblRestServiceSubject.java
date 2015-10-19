package org.mskcc.cbio.reactive.subject;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import feign.*;
import feign.codec.Decoder;
import feign.codec.ErrorDecoder;
import feign.gson.GsonDecoder;
import org.apache.log4j.Logger;
import org.mskcc.cbio.vep.model.AnnotatorServiceMessage;
import org.mskcc.cbio.vep.model.json.Annotation;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

import javax.annotation.Nullable;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;


/**
 * Created by fcriscuo on 8/30/15.
 */
public enum EnsemblRestServiceSubject {
    INSTANCE;
    private final static Logger logger = Logger.getLogger(EnsemblRestServiceSubject.class);
    final PublishSubject<AnnotatorServiceMessage.AnnotationMessage> ensemblRestSubject = PublishSubject.create();
    private static final String ENSEMBL_URL_TEMPLATE = "http://grch37.rest.ensembl.org/vep/human/hgvs/GENPOS?";

    public Subject subject() {
        return this.ensemblRestSubject;
    }

    /*
    subscribe to VariationQueryServiceSubject
     */
    public void init() {
        Observable<AnnotatorServiceMessage.AnnotationMessage> messageObs = VariationQueryServiceSubject.INSTANCE.subject();
        messageObs.subscribe(new Action1<AnnotatorServiceMessage.AnnotationMessage>() {
            @Override
            public void call(AnnotatorServiceMessage.AnnotationMessage message) {
                if (message.vepAnnotation() == null){
                    ensemblVepAnnotation(message.hgvsVariation(), message.isoformId());
                    logger.info("Invoking ensembl VEP annotation for " +message.hgvsVariation());
                } else {
                    logger.info("Annotation for " +message.hgvsVariation() +" was found in database");
                }
            }
        });
    }




    public void ensemblVepAnnotation(String variation, @Nullable String isoform) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(variation),
                "A variation is required");
        subject().onNext(AnnotatorServiceMessage.AnnotationMessage.create(variation, isoform,
                this.invokeEnsemblVepAnnotation(variation)));
    }

    /*
    private method to invoke ensembl REST service to annotate a genomic variation in HGVS format
     */
    private Annotation invokeEnsemblVepAnnotation(String variation) {

            Decoder decoder = new GsonDecoder();
            Ensembl ensembl = Feign.builder()
                    .decoder(decoder)
                    .errorDecoder(new VepErrorDecoder(decoder))
                    .logger(new feign.Logger.ErrorLogger())
                    .logLevel(feign.Logger.Level.BASIC)
                    .target(Ensembl.class, ENSEMBL_URL);

            List<Annotation> annotations = ensembl.annotations(variation);
            if (annotations.size() > 0 ){
                return annotations.get(0);
            }

        return null;
    }

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
    public static void main (String...args){
        EnsemblRestServiceSubject.INSTANCE.subject().subscribe(new Action1<AnnotatorServiceMessage.AnnotationMessage>() {
            @Override
            public void call(AnnotatorServiceMessage.AnnotationMessage message) {
                logger.info("====> variation " + message.hgvsVariation() +" allele  " +message.vepAnnotation().getAlleleString());


            }
        });
        Observable<AnnotatorServiceMessage.AnnotationMessage> obs1 = EnsemblRestServiceSubject.INSTANCE.subject();


        obs1.subscribe(new Subscriber<AnnotatorServiceMessage.AnnotationMessage>() {
                           @Override
                           public void onCompleted() {
                               logger.info("Completed");
                           }

                           @Override
                           public void onError(Throwable throwable) {
                                logger.error(throwable.getMessage());
                           }

                           @Override
                           public void onNext(final AnnotatorServiceMessage.AnnotationMessage message) {
                               Scheduler.Worker worker = Schedulers.newThread().createWorker();
                               worker.schedule(new Action0() {
                                   @Override
                                   public void call() {
                                       logger.info("====> " + message.hgvsVariation() + " " + message.vepAnnotation());

                                   }
                               });

                           }
                       });


        EnsemblRestServiceSubject.INSTANCE.ensemblVepAnnotation("17:g.41270062G>A",null);
        EnsemblRestServiceSubject.INSTANCE.ensemblVepAnnotation("10:g.42936755A>C",null);
        EnsemblRestServiceSubject.INSTANCE.ensemblVepAnnotation("7:g.140453999G>T",null);
        EnsemblRestServiceSubject.INSTANCE.subject().onCompleted();

    }


}
