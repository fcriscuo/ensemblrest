package edu.jhu.fcriscu1.reactive.subject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import edu.jhu.fcriscu1.vep.database.AnnotationDatabaseService;
import edu.jhu.fcriscu1.vep.model.AnnotatorServiceMessage;
import edu.jhu.fcriscu1.vep.model.AnnotatorServiceMessage.AnnotationMessage;
import edu.jhu.fcriscu1.vep.database.dao.VepAnnotation;
import edu.jhu.fcriscu1.vep.model.json.Annotation;
import lombok.extern.log4j.Log4j;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;
import rx.subjects.Subject;

import javax.annotation.Nullable;

/**
 * Created by fcriscuo on 8/29/15.
 */
@Log4j
public enum VariationQueryServiceSubject {
    INSTANCE;

    final PublishSubject<AnnotationMessage> variationQuerySubject = PublishSubject.create();
    public Subject subject() { return this.variationQuerySubject;}
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void queryVariationDatabase(String variation, @Nullable String isoform){
        // query local variation database
        Optional<VepAnnotation> annotationOpt = AnnotationDatabaseService.INSTANCE.findAnnotationInDatabase(variation);
        AnnotationMessage message= null;
        try {
            if (annotationOpt.isPresent()){
                Annotation annotation = objectMapper.readValue(annotationOpt.get().getAnnotation(), Annotation.class);
                message = AnnotatorServiceMessage.AnnotationMessage.create(variation, isoform, annotation);
            } else {
                message = AnnotatorServiceMessage.AnnotationMessage.create(variation, isoform,null);
            }
        } catch (Exception e){
            log.error(e.getMessage());
        }

        subject().onNext(message);
    }

    public static void main (String...args){
       Observable<AnnotatorServiceMessage.AnnotationMessage>obs1 =  VariationQueryServiceSubject.INSTANCE.subject();
        obs1.subscribeOn(Schedulers.newThread());
        obs1.subscribe(new Subscriber<AnnotatorServiceMessage.AnnotationMessage>() {
            @Override
            public void onCompleted() {
                log.info("FINIS");
            }

            @Override
            public void onError(Throwable throwable) {
                log.error(throwable.getMessage());
                throwable.printStackTrace();
            }

            @Override
            public void onNext(AnnotatorServiceMessage.AnnotationMessage message) {
                log.info("===> query results for variation " +message.hgvsVariation() +" annotation " +message.vepAnnotation().toString());
            }
        });


        Observable<String> variationObservable= Observable.just("10:g.100001483G>C","xxxxx");
        variationObservable
                 .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        VariationQueryServiceSubject.INSTANCE.queryVariationDatabase(s,null);
                    }
                });



    }

}
