package com.hlzt.commons.helper;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringHelper {
	
	static Logger  logger=LoggerFactory.getLogger(StringHelper.class);
	public static String listToString(List<String>list)
	{
		String str=list.toString();
		str=str.substring(1);
		str=str.substring(0, str.length()-1);
		str="("+str+")";
		return str;
		
	}
	
	public static String getMD5(String str) {
	    try {
	        // 生成一个MD5加密计算摘要
	        MessageDigest md = MessageDigest.getInstance("MD5");
	        // 计算md5函数
	        md.update(str.getBytes());
	        // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
	        // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
	        return new BigInteger(1, md.digest()).toString(16);
	    } catch (Exception e) {
	    	logger.error("#############MD5加密出错！！！");
	    
	    }
		return null;
	}
	
	/**
	 * 替换特殊字符
	 * @param str
	 * @return
	 */
	 public static String replaceSpicalStr(String str) {
	        String dest = "";
	        str=str.trim();
	        if (str!=null) {
	           // Pattern p = Pattern.compile("\\s*|\t|\r|\n");
	        	Pattern p = Pattern.compile("\t|\r|\n");
	            Matcher m = p.matcher(str);
	            dest = m.replaceAll("");
	        }
	        return dest;
	    }
	public static void main(String[] args) {
		
		System.out.println(getMD5("123456"));
	}
	/**
	 * 判断是否不是空
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str){
		if((str!=null)&&!"".equals(str.trim())){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 格式化模糊查询
	 * @param str
	 * @return
	 */
	public static String formatLike(String str){
		if(isNotEmpty(str)){
			return "%"+str+"%";
		}else{
			return null;
		}
	}

}
