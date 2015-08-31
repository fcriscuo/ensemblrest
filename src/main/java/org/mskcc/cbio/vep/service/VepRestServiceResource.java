package org.mskcc.cbio.vep.service;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import org.apache.log4j.Logger;
import org.mskcc.cbio.ensemblrest.EnsemblRestService;
import org.mskcc.cbio.vep.data.VepAnnotator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Created by fcriscuo on 8/22/15.
 */
@Path("/vep")
@Produces(MediaType.APPLICATION_JSON)
public class VepRestServiceResource {
    private static final Logger logger = Logger.getLogger(VepRestServiceResource.class);
    private final String template1;
    private final String template2;
    private final String defaultHgvs;
    private final String defaultResponse;
    private EnsemblRestService service;
    private final VepAnnotator annotator;

    public VepRestServiceResource( String template1, String template2, String defaultHgvs,String defaultResponse) {
        this.service = new EnsemblRestService();
        this.template1 = template1;
        this.template2 = template2;
        this.defaultHgvs = defaultHgvs;
        this.defaultResponse = defaultResponse;
        this.annotator = new VepAnnotator();
    }
    @GET
    @Timed
    public VepAnnotationString annotate (@QueryParam("hgvs")Optional<String> hgvsOpt){
        if (hgvsOpt.isPresent()){
            logger.info(service.resolveVEPByGenomicLocation(hgvsOpt.get()));
            return new VepAnnotationString(hgvsOpt.get(), annotator.annotateVariation(hgvsOpt.get()));
        }

        final String value1 = String.format(this.template1,defaultHgvs);
        final String value2 = String.format(this.template2,defaultResponse);
        return new VepAnnotationString(value1, value2);
    }


}
