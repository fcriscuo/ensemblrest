package edu.jhu.fcriscu1.vep.util;

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import java.io.InputStream;
import java.sql.SQLException;

/**
 * Created by criscuof on 11/20/14.
 */
public enum VEPSessionManager {
    /*
    A Singleton implemented as an enum to provide access to a Darwin SQL
    session object
     */
            INSTANCE;
    private static final Logger logger = Logger.getLogger(VEPSessionManager.class);
    private SqlSession session = Suppliers.memoize(new VepSessionSupplier()).get();


    public SqlSession getVepSession() {
        return this.session;
    }

    public void closeSession() {
        this.session.close();
        logger.info("The SQL session has been closed.");
    }

    private class VepSessionSupplier implements Supplier<SqlSession> {
        //TODO: make this a property
        private final String vepConfigFile = "/mybatis-config.xml";


        public SqlSession get() {
            //boolean autocommit = true;
            InputStream inputStream = VepSessionSupplier.class.getResourceAsStream(vepConfigFile);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            return sqlSessionFactory.openSession();
        }
    }

    // main method for testing
    public static void main(String... args) {
        SqlSession session = VEPSessionManager.INSTANCE.getVepSession();
        try {
            logger.info("The session is open? " + !session.getConnection().isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        VEPSessionManager.INSTANCE.closeSession();
    }


}
