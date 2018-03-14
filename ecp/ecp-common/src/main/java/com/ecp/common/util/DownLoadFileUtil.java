package com.ecp.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DownLoadFileUtil {
	
	private static final Log log = LogFactory.getLog(DownLoadFileUtil.class);
	
	/**
	 * 方法功能：获取文件的真实路径
	 * @param request
	 * @param filePath	文件相对路径（/upload/gen/attached/20170116/2017011614005634889063.pdf）
	 * @return
	 * <hr>
	 * <b>描述：</b>
	 * <p>Description:方法功能详细说明</p> 
	 * <p>Version: 1.0</p>
	 * <p>Author: srd </p>
	 * <p>Date: 2017年1月16日 下午5:13:33</p>
	 */
	public static String getFileRealPath(HttpServletRequest request, String filePath){
		try {
			String realPath = request.getSession().getServletContext().getRealPath("/");
			String temp = realPath.substring(0, realPath.lastIndexOf(File.separator));
			temp = temp.substring(0, temp.lastIndexOf(File.separator));
			return temp + filePath;
		} catch (Exception e) {
			log.error("获取文件的真实路径异常", e);
		}
		return null;
	}
	
	/**
	 * 方法功能：下载文件
	 * @param fileName	文件名（可以自定义文件名，下载后的文件名与此文件名相同，注：要加扩展名，如：下载文件PDF.pdf）
	 * @param fileType	文件类型（扩展名）
	 * @param realPath	文件真实路径（D:\Tomcat\apache-tomcat-8.5.8\webapps\gwkj-back\/upload/gen/attached/20170116/2017011614005634889063.pdf）
	 * @param response
	 * @return
	 * @throws Exception
	 * <hr>
	 * <b>描述：</b>
	 * <p>Description:方法功能详细说明</p> 
	 * <p>Version: 1.0</p>
	 * <p>Author: srd </p>
	 * <p>Date: 2017年1月16日 下午5:09:31</p>
	 */
	public static boolean downLoad(String fileName, String fileType, String realPath, HttpServletResponse response)
			throws Exception {
		File file = new File(realPath); // 根据文件路径获得File文件
		// 设置文件类型
		if ("pdf".equals(fileType)) {
			response.setContentType("application/pdf;charset=UTF-8");
		} else if ("csv".equals(fileType)) {
			response.setContentType("application/msexcel;charset=UTF-8");
		} else if ("doc".equals(fileType)) {
			response.setContentType("application/msword;charset=UTF-8");
		} else if ("xls".equals(fileType)) {
			response.setContentType("application/msexcel;charset=UTF-8");
		}
		// 文件名
		response.setHeader("Content-Disposition",
				"attachment;filename=\"" + new String(fileName.getBytes(), "ISO8859-1") + "\"");
		// + new String(fileName.getBytes("gb2312"), "ISO8859-1" ) + "\"");
		response.setContentLength((int) file.length());
		byte[] buffer = new byte[4096];// 缓冲区
		BufferedOutputStream output = null;
		BufferedInputStream input = null;
		try {
			output = new BufferedOutputStream(response.getOutputStream());
			input = new BufferedInputStream(new FileInputStream(file));
			int n = -1;
			// 遍历，开始下载
			while ((n = input.read(buffer, 0, 4096)) > -1) {
				output.write(buffer, 0, n);
			}
			output.flush(); // 不可少
			response.flushBuffer();// 不可少
		} catch (Exception e) {
			e.printStackTrace();
			// 异常自己捕捉
		} finally {
			// 关闭流，不可少
			if (input != null)
				input.close();
			if (output != null)
				output.close();
		}
		return false;
	}
}
