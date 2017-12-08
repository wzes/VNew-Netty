package com.mobine.vnews.util;

import org.apache.logging.log4j.util.PropertiesUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Create by xuantang
 * @date on 12/8/17
 */
public class PropertiesUtils {
    public static String getValue(String key){
        Properties prop = new Properties();
        InputStream in = new PropertiesUtils().getClass().getResourceAsStream("src/main/resource/db.properties");
        try {
            prop.load(in);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop.getProperty(key);
    }
}
