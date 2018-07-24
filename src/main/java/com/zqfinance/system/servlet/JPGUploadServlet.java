//package com.zqfinance.system.servlet;
//
//import java.io.BufferedInputStream;
//import java.io.BufferedOutputStream;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.PrintWriter;
//
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.commons.fileupload.FileItemIterator;
//import org.apache.commons.fileupload.FileItemStream;
//import org.apache.commons.fileupload.disk.DiskFileItemFactory;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;
//import org.apache.commons.fileupload.util.Streams;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
//
//import com.zqfinance.system.config.ConfigManager;
//
//public class JPGUploadServlet extends HttpServlet {
//	private static final long serialVersionUID = 1L;
//	private static final Log log = LogFactory.getLog(JPGUploadServlet.class);
//
//	File tmpDir = null;// 初始化上传文件的临时存放目录
//	File saveDir = null;// 初始化上传文件后的保存目录
//
//	public void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		this.doPost(request, response);
//	}
//
//	public void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		request.setCharacterEncoding("utf-8");
//		response.setContentType("text/html");
//		log.info("doPost 上传文件...");
//		PrintWriter pr = response.getWriter();
//		//返回结果
//		com.alibaba.fastjson.JSONObject obj = new com.alibaba.fastjson.JSONObject();
//		try {
//			if (ServletFileUpload.isMultipartContent(request)) {
//				DiskFileItemFactory dff = new DiskFileItemFactory();
//				dff.setRepository(tmpDir);
//				dff.setSizeThreshold(1024000);
//				ServletFileUpload sfu = new ServletFileUpload(dff);
//				sfu.setFileSizeMax(5000000);
//				sfu.setSizeMax(10000000);
//				log.info("创建DiskFileItemFactory对象并设置属性"+dff);
//				FileItemIterator fii = sfu.getItemIterator(request);
//				while (fii.hasNext()) {
//					FileItemStream fis = fii.next();
//					if (!fis.isFormField() && fis.getName().length() > 0) {
//						BufferedInputStream in = new BufferedInputStream(
//								fis.openStream());
//						String suffix = fis.getName().substring(fis.getName().indexOf('.')+1, fis.getName().length());
//						if(suffix.equalsIgnoreCase("JPG") || suffix.equalsIgnoreCase("PNG") || suffix.equalsIgnoreCase("GIF")){
//							log.info("上传文件绝对路径为："+saveDir+"\\" + fis.getName());
//							BufferedOutputStream out = new BufferedOutputStream(
//									new FileOutputStream(new File(saveDir+"\\" + fis.getName())));
//							Streams.copy(in, out, true);
//							log.info("上传文件成功！");
//							obj.put("msg", "上传文件成功！");
//							obj.put("path",fis.getName());
//							pr.print(obj);
//							pr.close();
//						}else{
//							obj.put("msg", "文件数据错误");
//							pr.print(obj);
//							pr.close();
//						}
//					}
//				}
//			}
//		} catch (Exception e) {
//			log.info("文件读取失败:"+e);
//			pr.close();
//			e.printStackTrace();
//		}
//	}
//
//	@Override
//	public void init() throws ServletException {
//		log.info("uploadServlet启动");
//		super.init();
//		/*
//		 * 对上传文件夹和临时文件夹进行初始化
//		 */
//
//		ServletContext s1=this.getServletContext();
//		String temp=s1.getRealPath("/");
//		System.out.println(temp);
//		String tmpPath = ConfigManager.LOAN_IMAGE_SAVEPATH;
//		String savePath = ConfigManager.LOAN_IMAGE_SAVEPATH;
//		log.info("文件上传文件夹路径"+savePath);
//		tmpDir = new File(tmpPath);
//		saveDir = new File(savePath);
//		if (!tmpDir.isDirectory())
//			tmpDir.mkdir();
//		if (!saveDir.isDirectory())
//			saveDir.mkdir();
//	}
//
//}
