package org.mskcc.cbio.vep.service;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by fcriscuo on 8/22/15.
 */
public class VepAnnotationString {
    private String variation;
    private String annotation;

    public VepAnnotationString(){}

    public VepAnnotationString(String variation, String annotation){
        this.variation = variation;
        this.annotation = annotation;
    }

    @JsonProperty
    public String getVariation () { return this.variation;}

    @JsonProperty
    public String getAnnotation() { return this.annotation;}



}
