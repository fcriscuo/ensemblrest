package edu.jhu.fcriscu1.vep.database.dao;

/**
 * Created by fcriscuo on 10/19/15.
 */

/*
Represents a row in the vep_annotation table
 */
public class VepAnnotation {

    public VepAnnotation(String aVariation, String anAnnoation){
        this.hgvsVariation = aVariation;
        this.annotation = anAnnoation;
    }

    private String hgvsVariation;
    private String annotation;


    public String getHgvsVariation() {
        return hgvsVariation;
    }

    public void setHgvsVariation(String hgvsVariation) {
        this.hgvsVariation = hgvsVariation;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String toString() {
        return new StringBuilder()
                .append("{hgvs_variation: ")
                .append(this.getHgvsVariation())
                .append(", annotation: ")
                .append(this.getAnnotation())
                .append(" }").toString();
    }
}
