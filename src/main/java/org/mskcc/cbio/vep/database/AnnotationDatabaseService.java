package org.mskcc.cbio.vep.database;

import autovalue.shaded.com.google.common.common.base.Preconditions;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import org.apache.log4j.Logger;
import org.mskcc.cbio.vep.database.dao.VepJsonCacheMapper;
import org.mskcc.cbio.vep.database.model.VepJsonCache;
import org.mskcc.cbio.vep.database.model.VepJsonCacheExample;
import org.mskcc.cbio.vep.util.VEPSessionManager;

import java.util.List;

/**
 * Created by fcriscuo on 8/30/15.
 */
public enum AnnotationDatabaseService {
    INSTANCE;
    private final Logger logger = Logger.getLogger(AnnotationDatabaseService.class);
    private final VepJsonCacheMapper cacheMapper = VEPSessionManager.INSTANCE.getVepSession().getMapper(VepJsonCacheMapper.class);
    private final VepJsonCacheExample cacheExample = new VepJsonCacheExample();

    /*
    Public method to find a cached annotation
     */
    public Optional<String> findAnnotationInDatabase(String variation){
        Preconditions.checkArgument(!Strings.isNullOrEmpty(variation),
                "A varition is required");
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
        return Optional.absent();
    }

    /*
    public method to insert annotation into database using the variation as a key
     */
    public void insertAnnotationIntoDatabase(String variation, String json){
        Preconditions.checkArgument(!Strings.isNullOrEmpty(variation),
                "A variation is required");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(json),
                "An annotation in JSON format is required");

        VepJsonCache jsonCache = new VepJsonCache();
        jsonCache.setCACHE_KEY(variation);
        jsonCache.setRAW_JSON(json);
        this.cacheMapper.insert(jsonCache);
        VEPSessionManager.INSTANCE.getVepSession().commit();
        logger.info("====> variation " +variation +" inserted into database");
    }

}
