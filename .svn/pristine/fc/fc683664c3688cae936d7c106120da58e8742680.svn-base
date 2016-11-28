package com.hlzt.commons.helper;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;


/**
 * 配置文件读写帮助类
 * 
 * @author lhy
 * 
 */
public class SysConfig {
	public final static String PROPERTY_CONFIG = "classpath:config/config.properties";
	/**
	 * 日志变量
	 */
	protected static Logger logger = LoggerFactory.getLogger(SysConfig.class);
	private static Properties configs ;
	private static String configFilePath="";

	static {
		configs = new Properties();
		InputStream is = null;
		try {
			ResourceLoader resourceLoader = new DefaultResourceLoader();
			ResourcePatternResolver resolverConfig = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
			Resource resourceConfig=resolverConfig.getResource(PROPERTY_CONFIG);
			configFilePath=resourceConfig.getFile().getPath();
			logger.info("################################配置文件路径:"+configFilePath);
			
			ResourcePatternResolver resolver = ResourcePatternUtils.getResourcePatternResolver(resourceLoader);
			Resource[] resources = resolver.getResources(PROPERTY_CONFIG);
			for(Resource r:resources) {
				//logger.info("################################路径1:"+r.getFile().getCanonicalPath());
				//logger.info("################################路径2:"+r.getFile().getPath());
				is = r.getInputStream();
				configs.load(new InputStreamReader(is, "utf-8"));
			}
		} catch (IOException ex) {
			logger.info("Could not load properties from path:{}, {} ", PROPERTY_CONFIG, ex.getMessage());
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 得到键值对
	 * 
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {
		String value = configs.getProperty(key);
		System.out.println("*****************"+value+"***********************");
		return value;
	}

	/**
	 * 更新内存
	 * @param key
	 * @param value
	 */
	 public static void updateProperties(String key,String value) {    
		 configs.setProperty(key, value); 
     } 
	 
	 /**
	  * 存储
	  * @return
	  */
	 public static boolean save() {
	 
	 	String comments="system config file";
	
	 	//FileWriter out;
			try {
				OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(configFilePath),"utf-8"); 
				configs.store(out, comments);
				out.close();
			} catch (Exception e) {
				return false;
			} 
			return true;
			
		}

	public static void main(String[] args) throws IOException {

	

	}

}
