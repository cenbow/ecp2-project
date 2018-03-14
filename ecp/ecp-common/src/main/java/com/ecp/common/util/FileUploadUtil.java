package com.ecp.common.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * Class: FileUploadUtil
 * 文件上传工具类
 * @author srd 
 * @version 1.0 $Date: 2016年12月27日 下午3:40:43
 */
public class FileUploadUtil {

	private static final Log log = LogFactory.getLog(FileUploadUtil.class);
	
    private static String saveRootPath = null;
    
	/**
	 * 方法功能：保存文件(默认路径)
	 * @param request
	 * @param file
	 * @return
	 * @throws IOException
	 * <hr>
	 * <b>描述：</b>
	 * <p>Description:方法功能详细说明</p> 
	 * <p>Version: 1.0</p>
	 * <p>Author: srd </p>
	 * <p>Date: 2017年1月12日 下午6:03:25</p>
	 */
	public static String uploadFile(HttpServletRequest request, MultipartFile file)
			throws IOException {

		String savePath = "";
		try {
			savePath = getSavePath();
			String realPath = getRealPath(request, savePath);
			if(StringUtils.isBlank(realPath)){
				return null;
			}
			saveRootPath = realPath;
		} catch (Exception e) {
			log.error("获取保存路径异常", e);
		}
			
		File filePath =new File(saveRootPath);    
		//如果文件夹不存在则创建    
		if (!filePath.exists() && !filePath.isDirectory()) {
			log.debug("目录不存在   创建目录: "+filePath);
			filePath.mkdir();
		} else {
			log.debug("目录存在 : "+filePath);
		}
		
		
		String fileName = uploadFile(file, null, saveRootPath);
		String reqPath = StringUtils.replace(savePath + File.separator + fileName, File.separator, "/");
		return reqPath;
	}
	
	/**
	 * 方法功能：自定义文件类型并加到保存路径中
	 * @param request
	 * @param typeDir自定义文件类型
	 * @param file
	 * @return
	 * @throws IOException
	 * <hr>
	 * <b>描述：</b>
	 * <p>Description:方法功能详细说明</p> 
	 * <p>Version: 1.0</p>
	 * <p>Author: srd </p>
	 * <p>Date: 2017年1月12日 下午6:03:41</p>
	 */
	public static String uploadFile(HttpServletRequest request, String typeDir, MultipartFile file)
			throws IOException {

		String savePath = "";
		try {
			savePath = getSavePath(typeDir);
			String realPath = getRealPath(request, savePath);
			if(StringUtils.isBlank(realPath)){
				return null;
			}
			saveRootPath = realPath;
		} catch (Exception e) {
			log.error("获取保存路径异常", e);
		}
			
		File filePath =new File(saveRootPath);    
		//如果文件夹不存在则创建    
		if (!filePath.exists() && !filePath.isDirectory()) {
			boolean flag = filePath.mkdirs();
			log.debug("目录不存在   创建目录: "+filePath+" 结果："+flag);
		} else {
			log.debug("目录存在 : "+filePath);
		}
		
		String fileName = uploadFile(file, null, saveRootPath);
		if(StringUtils.isBlank(fileName)){
			return null;
		}
		
		String reqPath = StringUtils.replace(savePath + File.separator + fileName, File.separator, "/");
		return reqPath;
	}

	/**
	 * 保存文件
	 * 
	 * @param request
	 * @param file
	 * @return 文件名 "2015120132365.jpg"
	 * @throws IOException
	 */
	public static String uploadFile(MultipartFile file, String savePath, String realPath)
			throws IOException {
		try {
			String fileName = getRandomFileName(file);
			if(StringUtils.isBlank(fileName)){
				return null;
			}
			//FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath, fileName));
			file.transferTo(new File(realPath, fileName));

			return fileName;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 方法功能：上传文件
	 * @param file
	 * @param fileName
	 * @param savePath
	 * @param realPath
	 * @return
	 * @throws IOException
	 * <hr>
	 * <b>描述：</b>
	 * <p>Description:方法功能详细说明</p> 
	 * <p>Version: 1.0</p>
	 * <p>Author: srd </p>
	 * <p>Date: 2017年2月10日 下午3:32:23</p>
	 */
	public static String uploadFile(MultipartFile file, String fileName, String savePath,
			String realPath) throws IOException {

		FileUtils.copyInputStreamToFile(file.getInputStream(), new File(realPath, fileName));
		return fileName;
	}

	/**
	 * 获取文件存储的真实文件夹路径
	 * D:/Java/apache-tomcat-8.0.27/webapps/upload/(typeDir)/20151125
	 * 
	 * @param request
	 * @param file
	 * @return
	 */
	public static String getRealPath(HttpServletRequest request, String savePath) {
		try {
			String realPath = request.getSession().getServletContext().getRealPath("/");
			String temp = realPath.substring(0, realPath.lastIndexOf(File.separator));
			//TODO DEBUG 屏蔽以下代码后，为当前项目所在目录，否则为webapp目录
			temp = temp.substring(0, temp.lastIndexOf(File.separator));
			return temp + savePath;
		} catch (Exception e) {
			log.error("获取文件存储的真实文件夹路径异常", e);
		}
		return null;
	}
	/**
	 * 获取文件存储的真实文件夹路径(不包括项目名)
	 * D:/Java/apache-tomcat-8.0.27/webapps/
	 * @param request
	 * @return
	 */
	public static String getRealPath(HttpServletRequest request) {
		try {
			String realPath = request.getSession().getServletContext().getRealPath("/");
			String temp = realPath.substring(0, realPath.lastIndexOf(File.separator));
			temp = temp.substring(0, temp.lastIndexOf(File.separator));
			return temp;
		} catch (Exception e) {
			log.error("获取文件存储的真实文件夹路径异常", e);
		}
		return null;
	}
	/**
	 * 获取文件存储的真实文件夹路径
	 * D:/Java/apache-tomcat-8.0.27/webapps/upload/(typeDir)/20170227
	 * @param realPath
	 * @param savePath
	 * @return
	 */
	public static String getRealPath(String realPath, String savePath) {
		try {
			String temp = realPath.substring(0, realPath.lastIndexOf(File.separator));
			temp = temp.substring(0, temp.lastIndexOf(File.separator));
			return temp + savePath;
		} catch (Exception e) {
			log.error("获取文件存储的真实文件夹路径异常", e);
		}
		return null;
	}

	/**
	 * 获取由时间和5位随机数字组成的文件名 2015112512345.jpg
	 * 
	 * @param file
	 * @param date
	 * @return
	 */
	public static String getRandomFileName(MultipartFile file) {

		try {
			String fileName = "";

			String oldFilename = file.getOriginalFilename();
			if(StringUtils.isBlank(oldFilename)){
				return null;
			}
			String bufix = oldFilename.substring(oldFilename.indexOf("."));
			String random = Integer.toString(new Random().nextInt(89999) + 10000);
			fileName = DateUtils.getDateRandom() + random + bufix;
			return fileName;// 当前时间
		} catch (Exception e) {
			log.error("获取由时间和5位随机数字组成的文件名异常", e);
		}
		return null;
	}

	/**
	 * 获取业务中某个字段存储的文件所属文件夹路径，便于前台显示/upload/(typeDir)/20151125
	 * 
	 * @return
	 * @throws Exception 
	 */
	public static String getSavePath() throws Exception {
		return getSavePath(null);
	}

	/**
	 * 获取业务中某个字段存储的文件所属文件夹路径，便于前台显示 /upload/(typeDir)/20151125
	 * 
	 * @param typeDir
	 *            "文件夹名称"
	 * @return
	 */
	public static String getSavePath(String typeDir) throws Exception{

		String savePath = "";
		String subFolderName = DateUtils.getDate("yyyyMMdd");

		if (StringUtils.isNotBlank(typeDir)) {
			savePath = File.separator + "upload" + File.separator 
					+ typeDir + File.separator + subFolderName;
		} else {
			savePath = File.separator + "upload" + File.separator 
					+ subFolderName;
		}

		return savePath;
	}

	/**
	 * 删除单个文件
	 * 
	 * @param request
	 * @param filePath
	 *            业务中某个字段存储的logo文件路径 /upload/(typeDir)/20151125/logo.jpg
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(HttpServletRequest request, String filePath) {
		boolean flag = false;
		try {
			String reqPath = StringUtils.replace(filePath, "/", File.separator);
			String path = getRealPath(request, reqPath);
			File file = new File(path);
			// 路径为文件且不为空则进行删除
			if (file.isFile() && file.exists()) {
				boolean isDel = file.delete();
				if(!isDel){
					log.error("删除上传文件异常，文件已删除或文件不存在  文件路径："+filePath);
				}
				flag = true;
			}
		} catch (Exception e) {
			log.error("删除上传文件异常"+filePath, e);
		}
		return flag;
	}
	
	/**
	 * 删除单个文件
	 * 
	 * @param realPath
	 * @param filePath
	 *            业务中某个字段存储的logo文件路径 /upload/(typeDir)/20151125/logo.jpg
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public static boolean deleteFile(String realPath, String filePath){
		boolean flag = false;
		try {
			String reqPath = StringUtils.replace(filePath, "/", File.separator);
			String path = getRealPath(realPath, reqPath);
			File file = new File(path);
			// 路径为文件且不为空则进行删除
			if (file.isFile() && file.exists()) {
				boolean isDel = file.delete();
				if(!isDel){
					log.error("删除上传文件异常，文件已删除或文件不存在  文件路径："+filePath);
				}
				flag = true;
			}
		} catch (Exception e) {
			log.error("删除上传文件异常"+filePath, e);
		}
		return flag;
	}
	
	/**
	 * 判断文件是否存在
	 * 
	 * @param request
	 * @param filePath
	 *            业务中某个字段存储的logo文件路径 /upload/(typeDir)/20151125/logo.jpg
	 * @return 存在返回true，否则返回false
	 */
	public static boolean fileExists(HttpServletRequest request, String filePath) {
		boolean flag = false;
		try {
			String reqPath = StringUtils.replace(filePath, "/", File.separator);
			String path = getRealPath(request, reqPath);
			File file = new File(path);
			// 路径为文件且存在
			if (file.isFile() && file.exists()) {
				flag = true;
				return flag;
			}
		} catch (Exception e) {
			log.error("上传文件不存在  文件路径："+filePath, e);
		}
		return flag;
	}

	/**
	 * 获取访问域名
	 * 
	 * @param request
	 * @return "http://主机名：端口/(contextPath)"
	 */
	public static String getReqServerURL(HttpServletRequest request) throws Exception {

		StringBuffer url = new StringBuffer();
		String scheme = request.getScheme();
		int port = request.getServerPort();
		if (port < 0) {
			port = 80;
		}

		url.append(scheme);
		url.append("://");
		//url.append(request.getServerName());
		url.append("zl.cnwell.com");
		if ((scheme.equals("http") && (port != 80)) || (scheme.equals("https") && (port != 443))) {
			url.append(':');
			url.append(port);
		}
		url.append(request.getContextPath());
		return url.toString();
	}
	
	/**
	 * 方法功能：根据fileName获取上传文件并上传
	 * @param request
	 * @param typeDir	类型目录
	 * @param fileName	上传文件input标签的name值
	 * @return	文件上传目录
	 * @throws IOException
	 * <hr>
	 * <b>描述：</b>
	 * <p>Description:方法功能详细说明</p> 
	 * <p>Version: 1.0</p>
	 * <p>Author: srd </p>
	 * <p>Date: 2017年1月17日 下午1:59:01</p>
	 */
	public static String getFile2Upload(HttpServletRequest request, String typeDir, String fileName) throws IOException{
		// 创建一个通用的多部分解析器.  
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
        // 设置编码  
        commonsMultipartResolver.setDefaultEncoding("utf-8");  
        // 判断是否有文件上传  
        if (commonsMultipartResolver.isMultipart(request)) {//有文件上传  
        	// 转型为MultipartHttpRequest：
    		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
    		// 获得文件：
    		MultipartFile file = multipartRequest.getFile(fileName);
    		if (file!=null) {
				String uploadPath = uploadFile(request, typeDir, file);
				log.debug("上传文件成功   文件保存路径 : "+uploadPath);
    			return uploadPath;
    		}
        }
		return null;
    }
	
	/**
	 * 方法功能：根据fileName获取多个上传文件并上传
	 * @param request
	 * @param typeDir	类型目录
	 * @param fileName	上传文件input标签的name值
	 * @return	
	 * 		List<String>:文件上传目录集合
	 * @throws IOException
	 * <hr>
	 * <b>描述：</b>
	 * <p>Description:方法功能详细说明</p> 
	 * <p>Version: 1.0</p>
	 * <p>Author: srd </p>
	 * <p>Date: 2017年7月15日 下午16:11:01</p>
	 */
	public static List<String> getFiles2UploadPath(HttpServletRequest request, String typeDir, String fileName){
		try {
			// 创建一个通用的多部分解析器.  
	        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
	        // 设置编码  
	        commonsMultipartResolver.setDefaultEncoding("utf-8");  
	        // 判断是否有文件上传  
	        if (commonsMultipartResolver.isMultipart(request)) {//有文件上传  
	        	// 转型为MultipartHttpRequest：
	    		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
	    		/*Iterator<String> it = multipartRequest.getFileNames();
	    		while (it.hasNext()) {
					String string = (String) it.next();
					System.out.println(string);
				}*/
	    		// 获得文件：
	    		List<MultipartFile> fileList = multipartRequest.getFiles(fileName);
	    		List<String> filePathList = new ArrayList<String>();
	    		for(MultipartFile file : fileList){
	    			if (file!=null) {
	    				String uploadPath = uploadFile(request, typeDir, file);
	    				if(StringUtils.isNotBlank(uploadPath)){
	    					filePathList.add(uploadPath);
	    					log.debug("上传文件成功   文件保存路径 : "+uploadPath);
	    				}
	        		}
	    		}
	    		if(!filePathList.isEmpty() && filePathList.size()>0){
	    			return filePathList;
	    		}
	        }
		} catch (IOException e) {
			e.printStackTrace();
			log.info("上传文件IO异常，返回null");
		} catch (NullPointerException e) {
			e.printStackTrace();
			log.info("上传文件为空，返回null");
		} catch (Exception e) {
			e.printStackTrace();
			log.info("上传文件异常，返回null");
		}
		return null;
    }
	
}
