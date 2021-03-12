package com.google.web.search.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utilities {
    private static final Logger log = LogManager.getLogger( Utilities.class );

    public String getProperties(String key) {
        InputStream inputStream = null;
        final Properties prop = new Properties();
        String propFileName = "./config/application.properties";

        try {
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            if(inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }
        } catch (IOException ioException) {
            ioException.printStackTrace() ;
        } finally {
            if( inputStream != null) {
                try {
                    inputStream.close();
                }catch (IOException ignored) {}
            }
        }
        return prop.getProperty(key);
    }
}
