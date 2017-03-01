package edu.jhu.fcriscu1.reactive.consumer;

import com.google.common.base.Strings;
import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import edu.jhu.fcriscu1.reactive.subject.EnsemblRestServiceSubject;
import edu.jhu.fcriscu1.vep.database.AnnotationDatabaseService;
import edu.jhu.fcriscu1.vep.database.dao.VepAnnotation;
import edu.jhu.fcriscu1.vep.model.AnnotatorServiceMessage;
import rx.Scheduler;
import rx.Subscriber;
import rx.functions.Action0;
import rx.schedulers.Schedulers;
import rx.subjects.Subject;

/**
 * Subject consumer that will react to events published by the EnsemblRestServiceSubject
 * The message package represents the results returned from the ensembl RESTful Web service
 * to annotate a variation
 * If the subject message contains an annotation it is inserted into the database,
 * otherwise, it is ignored
 *
 * Created by fcriscuo on 8/30/15.
 */
@Log4j
public class AnnotationPersistenceConsumer {


    private final Subject subject = EnsemblRestServiceSubject.INSTANCE.subject();
    protected Subject subject() { return this.subject;}
    public AnnotationPersistenceConsumer() {
        this.initialize();
    }

    private void initialize() {
        this.subject().subscribe(new Subscriber<AnnotatorServiceMessage.AnnotationMessage>() {

            @Override
            public void onCompleted() {
                log.info(this.getClass().getName() +" completed");
            }

            @Override
            public void onError(Throwable throwable) {
                log.error(throwable.getMessage());
                throwable.printStackTrace();
            }

            /*
            Insert variation and annotation into database
             */
            @Override
            public void onNext(AnnotatorServiceMessage.AnnotationMessage annotationMessage) {
                Scheduler.Worker worker = Schedulers.io().createWorker();  // run the insert on the I/O thread pool
                worker.schedule(new Action0() {
                    @Override
                    public void call() {
                        if(!Strings.isNullOrEmpty(annotationMessage.vepAnnotation().toString())){
                            AnnotationDatabaseService.INSTANCE.insertAnnotationIntoDatabase(new VepAnnotation(annotationMessage.hgvsVariation(),
                                    annotationMessage.vepAnnotation().toString()));
                            log.info("Annotation for variation " +annotationMessage.hgvsVariation() +" inserted into database");
                        }

                    }
                });


            }
        });
    }

}
