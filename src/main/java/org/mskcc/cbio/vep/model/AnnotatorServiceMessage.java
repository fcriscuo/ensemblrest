package org.mskcc.cbio.vep.model;

import com.google.auto.value.AutoValue;

import javax.annotation.Nullable;

/**
 * Created by fcriscuo on 8/29/15.
 */
public class AnnotatorServiceMessage {
    @AutoValue
    public abstract static class AnnotationMessage {
        AnnotationMessage() {}

        public static AnnotationMessage create (String hgvsVariation, @Nullable String isoformId, @Nullable String vepAnnotation) {
            return new AutoValue_AnnotatorServiceMessage_AnnotationMessage(hgvsVariation, isoformId, vepAnnotation);
        }

        public abstract String hgvsVariation();
        @Nullable
        public abstract String isoformId();
        @Nullable
        public abstract String vepAnnotation();

    }

    public static void main (String...args){
        AnnotationMessage message01 = AnnotationMessage.create("hgvsAnnotation01", "isoform01" ,"vep001");
        System.out.println(message01.toString());
        AnnotationMessage message02 = AnnotationMessage.create("hgvsAnnotation02", "isoform02",null );
        System.out.println(message02.toString());
        AnnotationMessage message03 = AnnotationMessage.create("hgvsAnnotation03", null,null);
        System.out.println(message03.toString());
        AnnotationMessage message04 = AnnotationMessage.create("hgvsAnnotation04", null,"vep004" );
        System.out.println(message04.toString());
        AnnotationMessage message06 = AnnotationMessage.create("hgvsAnnotation02", "isoform02",null ); // same as mesage02
        System.out.println("Should be true: " +message02.equals(message06));
    }
}
