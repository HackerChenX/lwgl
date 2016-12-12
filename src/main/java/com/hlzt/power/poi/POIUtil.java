package com.hlzt.power.poi;
import java.io.*;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.*;
import org.apache.poi.hwpf.usermodel.Range;
//xwpf专门加强处理Word2007 .docx 格式
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

import java.io.File;  
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
  
import org.apache.poi.hwpf.HWPFDocument;  
import org.apache.poi.hwpf.usermodel.Paragraph;  
import org.apache.poi.hwpf.usermodel.Range;  
import org.apache.poi.hwpf.usermodel.Table;  
import org.apache.poi.hwpf.usermodel.TableCell;  
import org.apache.poi.hwpf.usermodel.TableIterator;  
import org.apache.poi.hwpf.usermodel.TableRow;  
       
import java.io.InputStream;
import java.util.List;

import org.apache.poi.POIXMLDocument;     
import org.apache.poi.POIXMLTextExtractor;     
import org.apache.poi.hwpf.extractor.WordExtractor;     
import org.apache.poi.openxml4j.opc.OPCPackage;     
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;    
 
import org.apache.poi.poifs.filesystem.POIFSFileSystem;  
public class POIUtil {

	WordExtractor wordExtractor;

	public void POI(String filePath) {
		
		System.out.println("\n获取整个word文本内容:");
		System.out.println(new POIUtil().getTextFromWord(filePath));
		
		System.out.println("按段获取文本内容:");
		System.out.println(new POIUtil().getTextByParagraph(filePath));
	}

	// 统计word文件总页数(仅docx格式的有效！) doc格式也有相应的方法，但是由于doc本身的问题，导致获取的页数总是错误的！
	public void getPageCount(String filePath) {
		XWPFDocument docx;
		try {
			docx = new XWPFDocument(POIXMLDocument.openPackage(filePath));
			int pages = docx.getProperties().getExtendedProperties()
					.getUnderlyingProperties().getPages();// 总页数
			int wordCount = docx.getProperties().getExtendedProperties()
					.getUnderlyingProperties().getCharacters();// 忽略空格的总字符数
			// 另外还有getCharactersWithSpaces()方法获取带空格的总字数。
			System.out.println("Total pages=" + pages +"页; "+ " Total wordCount=" + wordCount);
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	// 获取word文档中所有文本的方法(仅对doc文件有效)
	public String getTextFromWord(String filePath){
		String res = null;
		File file = new File(filePath);
		try {
			FileInputStream fis = new FileInputStream(file);
			wordExtractor = new WordExtractor(fis);
			// 获取所有文本
			res = wordExtractor.getText();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

	// 按段获取文本(仅对doc文件有效)
	public String getTextByParagraph(String filePath){
		String res = null;
		FileInputStream fis;
		try {
			fis = new FileInputStream(filePath);
			wordExtractor = new WordExtractor(fis);
			// 获取段文本
			String[] strArray = wordExtractor.getParagraphText();
			for (int i = 0; i < strArray.length; i++) {
				System.out.println("第 " + (i+1)+" 段\n"+strArray[i]);
			}

			// 这个构造函数从InputStream中加载Word文档
			HWPFDocument doc = new HWPFDocument(
					(InputStream) new FileInputStream(filePath));
			// 这个类为HWPF对象模型,对文档范围段操作
			Range range = doc.getRange();
			int num = range.numParagraphs();
			System.out.println("该文档共" + num + "段");//空行也算一段
			System.out.println("获取第"+num+"段内容如下：\n"+range.getParagraph(num-1).text());
			fis.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	 public void testWord(String filePath){  
	        try{  
	            FileInputStream in = new FileInputStream(filePath);//载入文档  
	           POIFSFileSystem pfs = new POIFSFileSystem(in);     
	            HWPFDocument hwpf = new HWPFDocument(pfs);     
	            Range range = hwpf.getRange();//得到文档的读取范围  
	            TableIterator it = new TableIterator(range);  
	           //迭代文档中的表格  
	            while (it.hasNext()) {     
	                Table tb = (Table) it.next();     
	                //迭代行，默认从0开始  
	                for (int i = 0; i < tb.numRows(); i++) {     
	                    TableRow tr = tb.getRow(i);     
	                    //迭代列，默认从0开始  
	                    for (int j = 0; j < tr.numCells(); j++) {     
	                        TableCell td = tr.getCell(j);//取得单元格  
	                        //取得单元格的内容  
	                        for(int k=0;k<td.numParagraphs();k++){     
	                            Paragraph para =td.getParagraph(k);     
	                            String s = para.text();     
	                              
	                        } //end for      
	                    }   //end for  
	                }   //end for  
	            } //end while  
	        }catch(Exception e){  
	            e.printStackTrace();  
	        }  
	    }//end method  
	      
	      
	           public void testWord1(){  
	           try {     
	            //word 2003： 图片不会被读取     
	            InputStream is = new FileInputStream(new File("D:\\sinye.doc"));     
	                  WordExtractor ex = new WordExtractor(is);     
	                  String text2003 = ex.getText();     
	                  System.out.println(text2003);     
	            //word 2007 图片不会被读取， 表格中的数据会被放在字符串的最后     
	            OPCPackage opcPackage = POIXMLDocument.openPackage("D:\\sinye.doc");     
	                  POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);     
	                  String text2007 = extractor.getText();     
	                  System.out.println(text2007);     
	                 
	        } catch (Exception e) {     
	                  e.printStackTrace();     
	        }   
	    } 
	           
	           public void testReadByDoc(String filePath) throws Exception {  
	        	      InputStream is = new FileInputStream(filePath);  
	        	      XWPFDocument doc = new XWPFDocument(is);  
	        	      List<XWPFParagraph> paras = doc.getParagraphs();  
	        	      for (XWPFParagraph para : paras) {  
	        	         //当前段落的属性  
//	        	       CTPPr pr = para.getCTP().getPPr();  
	        	         System.out.println(para.getText());  
	        	      }  
	        	      //获取文档中所有的表格  
	        	      List<XWPFTable> tables = doc.getTables();  
	        	      List<XWPFTableRow> rows;  
	        	      List<XWPFTableCell> cells;  
	        	      for (XWPFTable table : tables) {  
	        	         //表格属性  
//	        	       CTTblPr pr = table.getCTTbl().getTblPr();  
	        	         //获取表格对应的行  
	        	         rows = table.getRows();  
	        	         for (XWPFTableRow row : rows) {  
	        	            //获取行对应的单元格  
	        	            cells = row.getTableCells();  
	        	            for (XWPFTableCell cell : cells) {  
	        	                System.out.println(cell.getText());;  
	        	            }  
	        	         }  
	        	      }  
	        	   }
}
