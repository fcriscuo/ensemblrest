package edu.jhu.fcriscu1.vep.model;

import com.google.auto.value.AutoValue;
import edu.jhu.fcriscu1.vep.model.json.Annotation;

import javax.annotation.Nullable;

/**
 * Created by fcriscuo on 8/29/15.
 */
public class AnnotatorServiceMessage {
    @AutoValue
    public abstract static class AnnotationMessage {
        AnnotationMessage() {}

        public static AnnotationMessage create (String hgvsVariation, @Nullable String isoformId, @Nullable Annotation vepAnnotation) {
            return new AutoValue_AnnotatorServiceMessage_AnnotationMessage(hgvsVariation, isoformId, vepAnnotation);
        }

        public abstract String hgvsVariation();
        @Nullable
        public abstract String isoformId();
        @Nullable
        public abstract Annotation vepAnnotation();

    }


}
