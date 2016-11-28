package com.hlzt.power.web;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ansj.domain.Result;
import org.ansj.domain.Term;
import org.ansj.library.UserDefineLibrary;
import org.ansj.recognition.impl.FilterRecognition;
import org.ansj.recognition.impl.SynonymsRecgnition;
import org.ansj.splitWord.analysis.DicAnalysis;
import org.ansj.splitWord.analysis.NlpAnalysis;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.ansj.util.MyStaticValue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hlzt.commons.helper.SysConfig;
import com.hlzt.power.model.Paper;
import com.hlzt.power.service.PublicSer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
public class AnsjController
{
	@Autowired
	private PublicSer publiSer;
	/**
	 * @Title: AnsjSplitWord
	 * @Description:中文分词,过滤停用词,提取关键字进行往年课题查重
	 * @param title
	 * @param url
	 * @param request
	 * @param response
	 * @return String 
	 * @throws
	 */
	@RequestMapping("AnsjSplitWord.shtm")
	@ResponseBody
	public String AnsjSplitWord(String title,String url,HttpServletRequest request, HttpServletResponse response){		
		
		//去除课题名中的空白字符
		String formatTitle = title.replaceAll("\\s+", "");
		
		//根目录
		String baseUrl = request.getServletContext().getRealPath("/");
		
		//读取用户自定义词典
		FileInputStream fr = null;
		try {			
			fr = new FileInputStream(new File(baseUrl+"library/default.dic"));
		} catch (FileNotFoundException e2) {		
			e2.printStackTrace();
		}
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(fr,"UTF-8"));
		} catch (UnsupportedEncodingException e3) {
			e3.printStackTrace();
		}
		String line;
		//定义i是为了读取文件出错时，显示错误在哪一行
		int i =0;
		try {
			//将用户自定义词典写入内存
			while((line=br.readLine())!=null){
			 String[] words=line.split("\\s+");
			 i++;
			 UserDefineLibrary.insertWord(words[0],words[1],Integer.parseInt(words[2]));			 
			}
		} catch (Exception e2) {
			System.out.println(i);
			e2.printStackTrace();
		} 
		//关闭文件流
		 try {
			br.close();
		} catch (IOException e2) {			
			e2.printStackTrace();
		}
		 
		//停用词表地址
		String stopWordTable = baseUrl+"library/StopWord.txt"; 
		//创建List接收StopWord
		List<String> filterWords = new ArrayList<String>();
		//读入停用词文件 
		File f = new File(stopWordTable);  
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}  		
		BufferedReader StopWordFileBr = null;
		try {
			StopWordFileBr = new BufferedReader(new InputStreamReader(fileInputStream,"UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}  		
		//读取到的停用词字符串写入内存中
		String stopWord = null;  
		try {
			for(; (stopWord = StopWordFileBr.readLine()) != null;){  
				filterWords.add(stopWord);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}  
		//关闭文件流
		try {
			StopWordFileBr.close();
		} catch (IOException e){
			e.printStackTrace();
		}  
		
		//创建停用词过滤器
		FilterRecognition fitler = new FilterRecognition();
		//过滤停用词表
		fitler.insertStopWords(filterWords);
		//过滤词性(en--英文)
		fitler.insertStopNatures("en");
		//过滤词性(m--数词)
		fitler.insertStopNatures("m");
		Result result = ToAnalysis.parse(formatTitle);;
		System.out.println(result);
		//过滤停用词和发现同义词，得到关键词
		Result term = ToAnalysis.parse(formatTitle).recognition(fitler); 
		System.out.println(term);
		
		//将关键词从Result term转存到List<String> spiltWords
		List<String> keyWords = new ArrayList<String>();
		//向前台传递关键词结果
		List<String> keyWordJson = new ArrayList<String>();
		for(int k = 0;k<term.size();k++){
			if(StringUtils.isNotBlank(term.get(k).getRealName()))
			{
				keyWords.add(term.get(k).getRealName());
				keyWordJson.add(term.get(k).getRealName());
			}
		}
		//查询近三年课题,获取当前学年
		String nowTerm = SysConfig.getValue("term");
		//获取三年前学年
		String threeTerm = String.valueOf((Integer.parseInt(nowTerm)-3));
        //将关键词传至后台，查询重复后返回的结果
        List<Paper> papers = null;
        try {
        	if(!(keyWords.isEmpty())){
        		papers = publiSer.findRepeatByAnsj(keyWords,nowTerm,threeTerm);
        	}        	
		} catch (Exception e) {
			e.printStackTrace();
		}    
        //将查重得出的课题传入JSON
        JSONArray jsonArray = JSONArray.fromObject(papers);
        //将前面得到的关键词List<String> spiltJSON传入JSON,放在jsonArray最后一位
        jsonArray.add(jsonArray.size(),keyWordJson);
        return jsonArray.toString();        
	}
}

