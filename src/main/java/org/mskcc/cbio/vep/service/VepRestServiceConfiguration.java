package org.mskcc.cbio.vep.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import org.hibernate.validator.constraints.NotEmpty;
/**
 * Created by fcriscuo on 8/22/15.
 */
public class VepRestServiceConfiguration extends Configuration {

    @NotEmpty
    private String template1 =" ";
    @NotEmpty
    private String template2 = " ";


    @NotEmpty
    private String defaultHgvs = "Missing or invalid";
    @NotEmpty
    private String defaultResponse = "Unable to annotate";

    @JsonProperty
    public String getTemplate1() { return template1;}
    @JsonProperty
    public void setTemplate1(String template1) {this.template1 = template1;}

    @JsonProperty
    public String getTemplate2() { return template2;}
    @JsonProperty
    public void setTemplate2(String template2) {this.template2 = template2;}

    @JsonProperty
    public String getDefaultHgvs() {return this.defaultHgvs;}
    public void setDefaultHgvs(String defaultHgvs ) { this.defaultHgvs = defaultHgvs;}

    @JsonProperty
    public String getDefaultResponse() {return this.defaultResponse;}
    public void setDefaultResponse(String defaultResponse ) { this.defaultHgvs = defaultResponse;}

}

