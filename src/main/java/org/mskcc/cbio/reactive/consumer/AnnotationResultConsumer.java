package org.mskcc.cbio.reactive.consumer;

import org.apache.log4j.Logger;
import org.mskcc.cbio.reactive.subject.EnsemblRestServiceSubject;
import org.mskcc.cbio.reactive.subject.VariationQueryServiceSubject;
import org.mskcc.cbio.vep.model.AnnotatorServiceMessage;
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
public class AnnotationResultConsumer {
    private static final Logger logger = Logger.getLogger(AnnotationResultConsumer.class);
    private final Subject ensemblSubject = EnsemblRestServiceSubject.INSTANCE.subject();
    protected Subject ensemblSubject() { return this.ensemblSubject;}
    private final Subject querySubject = VariationQueryServiceSubject.INSTANCE.subject();
    protected Subject querySubject() { return this.querySubject;}

    public AnnotationResultConsumer() {
       // process results from ensembl web service
        this.ensemblSubject().subscribe(new Subscriber<AnnotatorServiceMessage.AnnotationMessage>() {
            @Override
            public void onCompleted() {
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
                        if(null == message.vepAnnotation()){
                            logger.info("Annotation for variation " +message.vepAnnotation() +" was not provided by ensembl web service");
                        } else {
                            logger.info(message.toString());
                        }

                    }
                });

            }
        });

        // process results received from initial database query
        this.querySubject().subscribe(new Subscriber<AnnotatorServiceMessage.AnnotationMessage>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable throwable) {
                logger.error(throwable.getMessage());
            }

            @Override
            public void onNext(AnnotatorServiceMessage.AnnotationMessage message) {
                Scheduler.Worker worker = Schedulers.newThread().createWorker();
                worker.schedule(new Action0() {
                    @Override
                    public void call() {
                        if(null == message.vepAnnotation()){
                            logger.info("Annotation for variation " +message.vepAnnotation() +" is being submitted to ensembl");
                        } else {
                            logger.info(message.toString());
                        }

                    }
                });
            }
        });
    }

}
