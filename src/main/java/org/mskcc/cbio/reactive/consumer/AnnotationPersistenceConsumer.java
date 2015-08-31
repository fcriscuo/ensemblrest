package org.mskcc.cbio.reactive.consumer;

import com.google.common.base.Strings;
import org.apache.log4j.Logger;
import org.mskcc.cbio.reactive.subject.EnsemblRestServiceSubject;
import org.mskcc.cbio.vep.database.AnnotationDatabaseService;
import org.mskcc.cbio.vep.model.AnnotatorServiceMessage;
import rx.Subscriber;
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
public class AnnotationPersistenceConsumer {

    private static final Logger logger = Logger.getLogger(AnnotationPersistenceConsumer.class);
    private final Subject subject = EnsemblRestServiceSubject.INSTANCE.subject();
    protected Subject subject() { return this.subject;}
    public AnnotationPersistenceConsumer() {
        this.initialize();
    }

    private void initialize() {
        this.subject().subscribe(new Subscriber<AnnotatorServiceMessage.AnnotationMessage>() {

            @Override
            public void onCompleted() {
                logger.info(this.getClass().getName() +" completed");
            }

            @Override
            public void onError(Throwable throwable) {
                logger.error(throwable.getMessage());
                throwable.printStackTrace();
            }
            /*
            Insert variation and annotation into database
             */
            @Override
            public void onNext(AnnotatorServiceMessage.AnnotationMessage annotationMessage) {
                // verify that the variation was annotated successfully
                if(!Strings.isNullOrEmpty(annotationMessage.vepAnnotation())){
                    AnnotationDatabaseService.INSTANCE.insertAnnotationIntoDatabase(annotationMessage.hgvsVariation(),
                            annotationMessage.vepAnnotation());
                    logger.info("Annotation for variation " +annotationMessage.hgvsVariation() +" inserted into database");
                }
            }
        });
    }

}