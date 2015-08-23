package org.mskcc.cbio.vep.service;

import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * Created by fcriscuo on 8/22/15.
 */
public class VepServiceApplication extends Application<VepRestServiceConfiguration> {
   // private static final Logger logger = Logger.getLogger(VepRestService.class);
    public static void main(String... args) {
        try {
            new VepServiceApplication().run(args);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    @Override
    public String getName() {
        return "VepRestService";
    }

    @Override
    public void initialize(Bootstrap<VepRestServiceConfiguration> bootstrap) {
        // to be implemented
    }

    @Override
    public void run(VepRestServiceConfiguration vepRestServiceConfiguration, Environment environment) throws Exception {
        final VepRestServiceResource resource = new VepRestServiceResource(vepRestServiceConfiguration.getTemplate1(), vepRestServiceConfiguration.getTemplate2(),
                vepRestServiceConfiguration.getDefaultHgvs(), vepRestServiceConfiguration.getDefaultResponse());
        environment.jersey().register(resource);

    }
}
