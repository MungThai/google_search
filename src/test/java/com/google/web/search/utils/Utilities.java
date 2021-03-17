package com.google.web.search.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.slf4j.helpers.MessageFormatter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utilities {
    private static final Logger logger = LogManager.getLogger( Utilities.class );

    public String getProperties(String key) {
        logger.info("Get properties file");
        InputStream inputStream = null;
        final Properties prop = new Properties();
        String propFileName = "./config/application.properties";

        try {
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            if(inputStream != null) {
                prop.load(inputStream);
            } else {
                logger.error(MessageFormatter.format("Property {0} file is missing.", propFileName));
            }
        } catch (IOException ioException) {
            ioException.printStackTrace() ;
        } finally {
            if( inputStream != null) {
                try {
                    inputStream.close();
                }catch (IOException ignored) {
                    logger.error(MessageFormatter.format("Unable to close {0} file.", propFileName));
                }
            }
        }
        return prop.getProperty(key);
    }
}
