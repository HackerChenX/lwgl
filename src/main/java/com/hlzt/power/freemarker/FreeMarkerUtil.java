package com.hlzt.power.freemarker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreeMarkerUtil {
	private static Configuration configuration = null;
	private static Map<String, Template> allTemplates = null;
	
	static {
		configuration = new Configuration();
		configuration.setDefaultEncoding("utf-8");
		configuration.setClassForTemplateLoading(FreeMarkerUtil.class, "/com/hlzt/power/ftl");
		allTemplates = new HashMap<>();	// Java 7 钻石语法
		try {
			allTemplates.put("taskBook", configuration.getTemplate("许昌学院本科毕业论文（设计）任务书.ftl"));
			allTemplates.put("openingReport", configuration.getTemplate("许昌学院本科毕业论文（设计）开题报告.ftl"));
			allTemplates.put("midCheck", configuration.getTemplate("许昌学院本科生毕业论文中期检查表.ftl"));
			allTemplates.put("firstPaper", configuration.getTemplate("许昌学院本科毕业论文（设计）教师指导记录表（初稿）.ftl"));
			allTemplates.put("finalPaper", configuration.getTemplate("许昌学院本科毕业论文(设计)指导教师审阅意见表.ftl"));	
			allTemplates.put("pyTable", configuration.getTemplate("许昌学院本科毕业论文(设计)评阅教师评阅意见表.ftl"));
		} catch (IOException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	private FreeMarkerUtil() {
		throw new AssertionError();
	}

	public static void createDoc(Map<?, ?> dataMap, String type,String filePath) {
//		String name = "temp" + (int) (Math.random() * 100000) + ".docx";
		File f = new File(filePath);	
		Template t = allTemplates.get(type);
		try {
			// 这个地方不能使用FileWriter因为需要指定编码类型否则生成的Word文档会因为有无法识别的编码而无法打开
			Writer w = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
			t.process(dataMap, w);
			w.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

}
