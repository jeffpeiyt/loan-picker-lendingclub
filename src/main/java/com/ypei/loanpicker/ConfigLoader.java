package com.ypei.loanpicker;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.log4j.Logger;

import static org.junit.Assert.assertEquals;

public class ConfigLoader {


	private static Logger logger = Logger.getLogger(ConfigLoader.class);

	
	public static void loadConfig() {

		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream(Cfg.CONFIG_FILE_PATH);
			prop.load(input);

			Enumeration<?> e = prop.propertyNames();
			while (e.hasMoreElements()) {
				String key = (String) e.nextElement();
				String value = prop.getProperty(key);
				Cfg.m.put(key, value);
				
				if(key.equalsIgnoreCase("AUTHORIZATION_TOKEN")){
					logger.info("Config loaded: Key : " + key + ", Value : " + "****");
				}else{
					
					logger.info("Config loaded: Key : " + key + ", Value : " + value);
				}
			}
			
			//validate
			
			for(Cfg.T type: Cfg.T.values()){
				
				assertEquals(Cfg.m.containsKey(type.name()), true);
			}

		} catch (AssertionError e){
			logger.error("Missing required config in application.conf. Fatal Error.", e);

		} catch (Throwable e) {
			logger.error("Fails", e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					logger.error(e);
				}
			}
		}

	}

}
