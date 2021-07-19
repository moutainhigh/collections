package com.zx.util;

import com.zx.microserver.register.util.ReadPropertiesUtil;

import java.io.*;
import java.util.Properties;

public class ZxParamUtil {

    public static String getConfigParam(String type) {
        try {
            InputStream inputStream = new BufferedInputStream(new FileInputStream(ReadPropertiesUtil.ProFilePath));
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
            Properties properties = new Properties();
            properties.load(reader);
            return properties.getProperty(type);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
