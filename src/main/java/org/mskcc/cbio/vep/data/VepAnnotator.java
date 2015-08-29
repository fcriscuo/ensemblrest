package org.mskcc.cbio.vep.data;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.base.Optional;
import org.apache.log4j.Logger;
import org.mskcc.cbio.ensemblrest.EnsemblRestService;
import org.mskcc.cbio.vep.database.dao.VepJsonCacheMapper;
import org.mskcc.cbio.vep.database.model.VepJsonCache;
import org.mskcc.cbio.vep.database.model.VepJsonCacheExample;
import org.mskcc.cbio.vep.util.VEPSessionManager;

import java.util.List;

/**
 * Copyright (c) 2015 Memorial Sloan-Kettering Cancer Center.
 * <p/>
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY, WITHOUT EVEN THE IMPLIED WARRANTY OF
 * MERCHANTABILITY OR FITNESS FOR A PARTICULAR PURPOSE.  The software and
 * documentation provided hereunder is on an "as is" basis, and
 * Memorial Sloan-Kettering Cancer Center
 * has no obligations to provide maintenance, support,
 * updates, enhancements or modifications.  In no event shall
 * Memorial Sloan-Kettering Cancer Center
 * be liable to any party for direct, indirect, special,
 * incidental or consequential damages, including lost profits, arising
 * out of the use of this software and its documentation, even if
 * Memorial Sloan-Kettering Cancer Center
 * has been advised of the possibility of such damage.
 * <p/>
 * Created by Fred Criscuolo on 4/12/15.
 * criscuof@mskcc.org
 */
/*
Java class that supports VEP annotation of a somatic mutation in HGVS format. Upon receiving
a mutation, an underlying database is queried to determine if a VEP annotation already exists
for that mutation. If so, the persisted data is returned. If not, the remote ensembl RESTful
annotation service is invoked, the novel mutation and annotation are inserted into the database,
and the annotation is returned in JSON format
TODO: convert to Singleton service (i.e. enum)
 */
public class VepAnnotator {
    private static final Logger logger = Logger.getLogger(VepAnnotator.class);
    private final EnsemblRestService service = new EnsemblRestService();
    private final VepJsonCacheMapper cacheMapper;
    private final VepJsonCacheExample cacheExample;


    public VepAnnotator() {
        // instantiate myBatis components for mySQL access
        this.cacheMapper = VEPSessionManager.INSTANCE.getVepSession().getMapper(VepJsonCacheMapper.class);
        this.cacheExample = new VepJsonCacheExample();
    }

    public String annotateVariation(String variation) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(variation),
                "A variation in HGVS notation is required");
        // check database
        Optional<String> annotationOpt = this.findAnnotationInDatabase(variation);
        if (annotationOpt.isPresent()) {
            logger.info("====> variation " + variation + " found in database");
            return annotationOpt.get();
        }
        // get from ensembl REST service
        String annotation = this.service.resolveVEPByGenomicLocation(variation);
        if (!Strings.isNullOrEmpty(annotation)) {
            this.insertAnnotationIntoDatabase(variation, annotation);
        }
        return annotation;
    }

    /*
    Private method to persist HGVS variation string (PK) and VEP annotation into
    a mySQL database
     */
    private void insertAnnotationIntoDatabase(String variation, String json){
        if(Strings.isNullOrEmpty(json)){
            logger.info("No annotation for " +variation);
        }
        VepJsonCache jsonCache = new VepJsonCache();
        jsonCache.setCACHE_KEY(variation);
        jsonCache.setRAW_JSON(json);
        this.cacheMapper.insert(jsonCache);
        VEPSessionManager.INSTANCE.getVepSession().commit();
        logger.info("====> variation " +variation +" inserted into database");
    }
    /*
    Private method to query a mySQL database for a HGVS variation.
    Uses myBatis as an ORM
     */
        private Optional<String> findAnnotationInDatabase(String variation){
        this.cacheExample.clear();
        this.cacheExample.createCriteria().andCACHE_KEYEqualTo(variation);
        List<VepJsonCache> vepList =this.cacheMapper.selectByExampleWithBLOBs(this.cacheExample);
        if (vepList.isEmpty()) { return Optional.absent();}
        if(vepList.size() > 1) {
            logger.error("variation: " +variation +" is not unique");
        }
            if ( null != vepList.get(0).getRAW_JSON() ) {
                return Optional.of(vepList.get(0).getRAW_JSON());
            }
            this.deleteRow(variation);
            return Optional.absent();
    }

    /*
    Private method to delete a variation from the mySQL database using myBatis
     */
    private void deleteRow(String variation){
        this.cacheExample.clear();
        this.cacheExample.createCriteria().andCACHE_KEYEqualTo(variation);
        this.cacheMapper.deleteByExample(cacheExample);
        VEPSessionManager.INSTANCE.getVepSession().commit();
        logger.info("===>Deleted row with variation " +variation);
    }


}
