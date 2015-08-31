package org.mskcc.cbio.reactive.consumer;

import org.apache.log4j.Logger;
import org.mskcc.cbio.reactive.subject.EnsemblRestServiceSubject;
import org.mskcc.cbio.reactive.subject.VariationQueryServiceSubject;
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

    AnnotationResultConsumer() {

    }

}
