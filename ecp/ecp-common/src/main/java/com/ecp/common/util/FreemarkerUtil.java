package com.ecp.common.util;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

/**
 * @author yangj_000
 */
public class FreemarkerUtil {

	/**
	 * 方法功能：返回模板替换变量后的内容字符串
	 * @param directory
	 * @param templateFileName
	 * @param root
	 * @return
	 * <hr>
	 * <b>描述：</b>
	 * <p>Description:方法功能详细说明</p> 
	 * <p>Version: 1.0</p>
	 * <p>Author: srd </p>
	 * <p>Date: 2017年1月13日 下午2:27:11</p>
	 */
	public static String getTemplateContentString(String directory, String templateFileName, Map<String, Object> root) {
		String retString = null;
		StringWriter out = new StringWriter();
		try {
			// 通过一个文件输出流，就可以写到相应的文件中
			Template temp = getTemplate(directory, templateFileName);
			temp.process(root, out);
			retString = out.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return retString;
	}

	/**
	 * 获取模板
	 * 
	 * @param name
	 * @return
	 */
	public static Template getTemplate(String directory, String name) {
		try {
			// 通过Freemaker的Configuration读取相应的ftl
			Configuration cfg = new Configuration();
			// 设定去哪里读取相应的ftl模板文件
			cfg.setDirectoryForTemplateLoading(new File(directory));
			cfg.setDefaultEncoding("UTF-8");
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
			// 在模板文件目录中找到名称为name的文件
			Template temp = cfg.getTemplate(name);
			return temp;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 输出到控制台
	 * 		未用
	 * @param name
	 *            模板文件名
	 * @param root
	 */
	public static void print(String dirctory, String name, Map<String, Object> root) {
		try {
			// 通过Template可以将模板文件输出到相应的流
			Template temp = getTemplate(dirctory, name);
			temp.process(root, new PrintWriter(System.out));
		} catch (TemplateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 输出到文件
	 * 		未用
	 * @param name
	 * @param root
	 * @param outFile
	 */
	/*public void fprint(String name, Map<String, Object> root, String outFile) {
		FileWriter out = null;
		try {
			// 通过一个文件输出流，就可以写到相应的文件中
			out = new FileWriter(new File("D:\\Reference\\FreeMarker\\ftl\\" + outFile));
			Template temp = this.getTemplate(name);
			temp.process(root, out);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}*/

}
