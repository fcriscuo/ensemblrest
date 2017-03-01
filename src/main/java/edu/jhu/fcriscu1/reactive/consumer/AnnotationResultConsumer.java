package edu.jhu.fcriscu1.reactive.consumer;

import edu.jhu.fcriscu1.reactive.subject.EnsemblRestServiceSubject;
import edu.jhu.fcriscu1.reactive.subject.VariationQueryServiceSubject;
import edu.jhu.fcriscu1.vep.model.AnnotatorServiceMessage.AnnotationMessage;
import lombok.extern.log4j.Log4j;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subjects.Subject;

/**
 * Represents a Subject consumer responsible for outputting VEP annotation results
 * This class consumes messages from two (2) Subjects:
 * 1, VariationQueryServiceSubject - for annotations already in the local database
 * 2. EsnsemblRestServiceSubject - for annotations provided by the ensembl RESTful service
 * Created by fcriscuo on 8/30/15.
 */
@Log4j
public class AnnotationResultConsumer {
   
    private final Subject ensemblSubject = EnsemblRestServiceSubject.INSTANCE.subject();
    protected Subject ensemblSubject() { return this.ensemblSubject;}
    private final Subject querySubject = VariationQueryServiceSubject.INSTANCE.subject();
    protected Subject querySubject() { return this.querySubject;}

    public AnnotationResultConsumer() {
       // process results from ensembl web service
        this.ensemblSubject().subscribe(new Subscriber<AnnotationMessage>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable throwable) {
                log.error(throwable.getMessage());
            }

            @Override
            public void onNext(final AnnotationMessage message) {
                Scheduler.Worker worker = Schedulers.newThread().createWorker();
                worker.schedule(new Action0() {
                    @Override
                    public void call() {
                        if(null == message.vepAnnotation()){
                            log.info("Annotation for variation " +message.vepAnnotation() +" was not provided by ensembl web service");
                        } else {
                            log.info(message.toString());
                        }

                    }
                });

            }
        });

        // process results received from initial database query
        this.querySubject().subscribe(new Subscriber<AnnotationMessage>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                log.error(throwable.getMessage());
            }

            @Override
            public void onNext(AnnotationMessage message) {
                Scheduler.Worker worker = Schedulers.newThread().createWorker();
                worker.schedule(new Action0() {
                    @Override
                    public void call() {
                        if(null == message.vepAnnotation()){
                            log.info("Annotation for variation " +message.vepAnnotation() +" is being submitted to ensembl");
                        } else {
                            log.info(message.toString());
                        }

                    }
                });
            }
        });
    }

}
