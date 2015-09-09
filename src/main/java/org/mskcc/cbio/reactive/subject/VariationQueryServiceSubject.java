package org.mskcc.cbio.reactive.subject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import org.apache.log4j.Logger;
import org.mskcc.cbio.vep.database.AnnotationDatabaseService;
import org.mskcc.cbio.vep.model.AnnotatorServiceMessage;
import org.mskcc.cbio.vep.model.json.Annotation;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

import javax.annotation.Nullable;

/**
 * Created by fcriscuo on 8/29/15.
 */
public enum VariationQueryServiceSubject {
    INSTANCE;
    private final static Logger logger = Logger.getLogger(VariationQueryServiceSubject.class);
    final PublishSubject<AnnotatorServiceMessage.AnnotationMessage> variationQuerySubject = PublishSubject.create();
    public Subject subject() { return this.variationQuerySubject;}
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void queryVariationDatabase(String variation, @Nullable String isoform){
        // query local variation database
        Optional<String> annotationOpt = AnnotationDatabaseService.INSTANCE.findAnnotationInDatabase(variation);
        AnnotatorServiceMessage.AnnotationMessage message= null;
        try {
            if (annotationOpt.isPresent()){
                Annotation annotation = objectMapper.readValue(annotationOpt.get(), Annotation.class);
                message = AnnotatorServiceMessage.AnnotationMessage.create(variation, isoform, annotation);
            } else {
                message = AnnotatorServiceMessage.AnnotationMessage.create(variation, isoform,null);
            }
        } catch (Exception e){
            logger.error(e.getMessage());
        }

        subject().onNext(message);
    }

    public static void main (String...args){
       Observable<AnnotatorServiceMessage.AnnotationMessage>obs1 =  VariationQueryServiceSubject.INSTANCE.subject();
        obs1.subscribeOn(Schedulers.newThread());
        obs1.subscribe(new Subscriber<AnnotatorServiceMessage.AnnotationMessage>() {
            @Override
            public void onCompleted() {
                logger.info("FINIS");
            }

            @Override
            public void onError(Throwable throwable) {
                logger.error(throwable.getMessage());
                throwable.printStackTrace();
            }

            @Override
            public void onNext(AnnotatorServiceMessage.AnnotationMessage message) {
                logger.info("===> query results for variation " +message.hgvsVariation() +" annotation " +message.vepAnnotation().toString());
            }
        });




     //   VariationQueryServiceSubject.INSTANCE.subject().subscribe(new Action1<AnnotatorServiceMessage.AnnotationMessage>() {
//
     //       @Override
      //      public void call(AnnotatorServiceMessage.AnnotationMessage message) {
      //          logger.info("===> query results for variation " +message.hgvsVariation() +" annotation " +message.vepAnnotation().toString());
      //      }
     //   });
        Observable<String> variationObservable= Observable.just("17:g.41270062G>A","xxxxx");
        variationObservable
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        VariationQueryServiceSubject.INSTANCE.queryVariationDatabase(s,null);
                    }
                });



    }

}
