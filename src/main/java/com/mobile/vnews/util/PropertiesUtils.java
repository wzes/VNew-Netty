package com.mobile.vnews.util;


import org.apache.logging.log4j.util.PropertiesUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
        FileInputStream in = null;
        try {
            in = new FileInputStream("src/main/resources/db.properties");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            prop.load(in);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop.getProperty(key);
    }
}
