package com.minsu.structure.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.annotation.Scheduled;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtils implements InitializingBean {

    private Properties prop = new Properties();

    private PropertiesUtils() {
    }

    public static PropertiesUtils getInstance() {
        return SingletonContainer.instance;
    }

    private static class SingletonContainer {
        private static PropertiesUtils instance = new PropertiesUtils();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        loadStatic();
        loadDynamic();
    }

    private void loadStatic() throws IOException {
        InputStream ins = null;
        try {
            ins = getClass().getResourceAsStream("/context-static.properties");
            prop.load(ins);
        } finally {
            if (ins != null) {
                ins.close();
            }
        }
    }

    @Scheduled(cron = "0 0/1 * * * ?")
    public void loadDynamic() throws IOException {
        InputStream ins = null;
        try {
            ins = getClass().getResourceAsStream("/context-dynamic.properties");
            prop.load(ins);
        } finally {
            if (ins != null) {
                ins.close();
            }
        }
    }

    public String getProperty(String key, String defaultValue) {
        return prop.getProperty(key, defaultValue);
    }

    public int getProperty(String key, int defaultValue) {
        return NumberUtils.toInt(prop.getProperty(key), defaultValue);
    }

    public String getProperty(String key) {
        return prop.getProperty(key);
    }

    public boolean getSwitch(String key) {
        return StringUtils.equalsIgnoreCase("on", getProperty(key, "off"));
    }

}
