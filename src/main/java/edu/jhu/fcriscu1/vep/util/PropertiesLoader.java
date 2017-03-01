package edu.jhu.fcriscu1.vep.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Properties;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import org.apache.log4j.Logger;



/**
 * Created by fcriscuo on 10/18/15.
 */
public class PropertiesLoader {

    private static final String HOME_DIR = "PORTAL_HOME";
    private static final String PORTAL_PROPERTIES_FILENAME = "database.properties";
    private static Properties properties = new Properties();
    static final Logger logger = Logger.getLogger(PropertiesLoader.class);

    // database properties from importer.properties
    private static final String DB_USER = "db.user";
    private static final String DB_PASSWORD = "db.password";
    private static final String DB_PORTAL_DB_NAME = "db.portal_db_name";
    private static final String DB_HOST = "db.host";
    private static final String DB_DRIVER = "db.driver";



    private static final String IMPORTER_BASE_DIRECTORY = "importer.base.directory";

    private static PropertiesLoader instance = null;

    protected PropertiesLoader() {
        properties = loadProperties(getResourcesStream());
    }

    public static PropertiesLoader getInstance() {
        if(instance == null) {
            instance = new PropertiesLoader();
        }
        return instance;
    }

    private InputStream getResourcesStream() {

        String resourceFilename = null;
        InputStream resourceFIS = null;

        try {
            String home = System.getenv(HOME_DIR);
            if (home != null) {
                resourceFilename =
                        home + File.separator + PORTAL_PROPERTIES_FILENAME;
                logger.info("Attempting to read properties file: " + resourceFilename);
                resourceFIS = new FileInputStream(resourceFilename);
                logger.info("Successfully read properties file");
            }
        }
        catch (FileNotFoundException e) {

        }

        if (resourceFIS == null) {
            logger.info("Attempting to read properties file from classpath");
            resourceFIS = this.getClass().getClassLoader().
                    getResourceAsStream(PORTAL_PROPERTIES_FILENAME);
            logger.info("Successfully read properties file");
        }

        return resourceFIS;
    }

    private static Properties loadProperties(InputStream resourceInputStream) {

        Properties _properties = new Properties();
        try {
            _properties.load(resourceInputStream);
            resourceInputStream.close();
        }
        catch (IOException e) {
            logger.error("Error loading properties file: " + e.getMessage());
        }

        return _properties;
    }

    public String getDbUser() { return properties.getProperty(DB_USER);}

    public String getDbPassword() {return properties.getProperty(DB_PASSWORD);}

    public String getDbPortalDbName() {return properties.getProperty(DB_PORTAL_DB_NAME);}

    public String getDbHost() { return properties.getProperty(DB_HOST);}

    public String getDbDriver() { return properties.getProperty(DB_DRIVER); }



    public String getImporterBaseDirectory() { return properties.getProperty(IMPORTER_BASE_DIRECTORY);}

    public Path getImporterBasePath() {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(this.getImporterBaseDirectory()),
                "The base directory for staging files is undefined");
        return Paths.get(this.getImporterBaseDirectory());
    }



    public String getImporterPropertyByName(String propertyName){
        Preconditions.checkArgument(!Strings.isNullOrEmpty(propertyName),"A property name is required");
        Preconditions.checkArgument( properties.containsKey(propertyName),
                "Property " +propertyName +" is invalid");
        return properties.getProperty(propertyName);
    }


    public static void main (String...args){


        logger.info("DB driver " +PropertiesLoader.getInstance().getDbDriver());
    }
}