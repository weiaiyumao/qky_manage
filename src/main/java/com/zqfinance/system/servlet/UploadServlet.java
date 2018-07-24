package com.zqfinance.system.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.zqfinance.system.common.DataMessage;
import com.zqfinance.system.common.DataPageMessage;
import com.zqfinance.system.config.ConfigManager;
import com.zqfinance.system.config.ManageConfig;
import com.zqfinance.system.service.SendRequestService;
import com.zqfinance.system.service.impl.SendRequestServiceImpl;
import com.zqfinance.system.util.ExcelReader;

public class UploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(UploadServlet.class);
	
	File tmpDir = null;// 初始化上传文件的临时存放目录
	File saveDir = null;// 初始化上传文件后的保存目录

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		log.info("doPost 上传文件...");
		PrintWriter pr = response.getWriter();
		//返回结果  
		com.alibaba.fastjson.JSONObject obj = new com.alibaba.fastjson.JSONObject();  
		try {
			if (ServletFileUpload.isMultipartContent(request)) {
				DiskFileItemFactory dff = new DiskFileItemFactory();// 创建该对象
				dff.setRepository(tmpDir);
				dff.setSizeThreshold(1024000);
				ServletFileUpload sfu = new ServletFileUpload(dff);// 创建该对象
				sfu.setFileSizeMax(5000000);
				sfu.setSizeMax(10000000);
				log.info("创建DiskFileItemFactory对象并设置属性"+dff);
				FileItemIterator fii = sfu.getItemIterator(request);
				while (fii.hasNext()) {
					FileItemStream fis = fii.next();
					if (!fis.isFormField() && fis.getName().length() > 0) {
						BufferedInputStream in = new BufferedInputStream(
								fis.openStream());
						log.info("导入文件绝对路径为："+saveDir+ConfigManager.FILE_SEPARATOR
								+ fis.getName());
						BufferedOutputStream out = new BufferedOutputStream(
								new FileOutputStream(new File(saveDir+ConfigManager.FILE_SEPARATOR
										+ fis.getName())));
						Streams.copy(in, out, true);
						log.info("文件拷贝成功！");
						DataPageMessage dataMessage = uploadImportDate(saveDir+ConfigManager.FILE_SEPARATOR
								+ fis.getName());
						log.info("从文件解析出数据："+dataMessage);
						List<Map<String, String>> list = dataMessage.getContentList();
						if(list!=null&&list.size()>0){
							for (int i = 0; i < list.size(); i++) {
								SendRequestService sendRequestService=new SendRequestServiceImpl();
								Map<String, String> map = list.get(i);
								JSONObject json = JSONObject.fromObject(map);
								String resultString = sendRequestService.sendRequest(
												ConfigManager.OUTMONEYFLOW_SAVEOUTMONEYRECONCILIATION,
												json);
								log.info("save操作结果:"+resultString);
								DataMessage resultMessage = JSON.parseObject(resultString,
										DataMessage.class);
								if (ManageConfig.DATE_MESSAGE_FAIL
										.equals(resultMessage.getResult())) {
									log.info("文件读入失败");
									 obj.put("result", "1");  
									 obj.put("msg", "文件读入失败");
									 pr.print(obj.toJSONString());
									 pr.close();
								}
							}
							log.info("文件读入成功");
							 obj.put("result", "0");  
							 obj.put("msg", "文件读入成功");
							 pr.print(obj);
							 pr.close();
						}else{
							log.info("文件数据错误");
							 obj.put("result", "1"); 
							 obj.put("msg", "文件数据错误");
							 pr.print(obj);
							 pr.close();
						}
					}
				}
			}
		} catch (Exception e) {
			log.info("文件读取异常:"+e);
			obj.put("result", "1");  
			obj.put("msg", "文件读取异常");
			pr.print(obj.toJSONString());
			pr.close();
			e.printStackTrace();
		}
	}

	@Override
	public void init() throws ServletException {
		log.info("uploadServlet启动");
		super.init();
		/*
		 * 对上传文件夹和临时文件夹进行初始化
		 */
		String tmpPath = "/mnt/var/www/manage/tmp";
		String savePath = "/mnt/var/www/manage/upload";
//		String tmpPath = "C:/Users/Administrator/Desktop/tmpPath";
//		String savePath = "C:/Users/Administrator/Desktop/savePath";
		log.info("文件上传文件夹路径"+savePath);
		tmpDir = new File(tmpPath);
		saveDir = new File(savePath);
		if (!tmpDir.isDirectory())
			tmpDir.mkdir();
		if (!saveDir.isDirectory())
			saveDir.mkdir();
	}

	// 读取文件保存对账信息
		public DataPageMessage uploadImportDate(String file) throws Exception{
			DataPageMessage resultMessage = new DataPageMessage();
			List<Map<String,String>> contentList=new  ArrayList<Map<String,String>>();
			List<String[]> list = new ArrayList<String[]>();
			try {
				list = ExcelReader.getExcle(file);
				for (int i = 1; i < list.size(); i++) {
					String[] str = list.get(i);  
					Map<String, String> map = new HashMap<String, String>();
					map.put("orderId", str[0]);
					map.put("jetcoOrderId", str[1]);
					map.put("comeFrom", str[2]);
					map.put("bank", str[3]);
					map.put("cardNo", str[4]);
					map.put("cardName", str[5]);
					map.put("r_timeCreate", str[6]);
					map.put("r_timeTrading", str[7]);
					map.put("r_money", str[8]);
					map.put("tradingStatus", str[9].equals("支付关闭")?"1":"0");
					contentList.add(map);  
			    }
				resultMessage.setResult("0");
				resultMessage.setContentList(contentList);
			} catch (Exception e) {
				e.printStackTrace();
			} 
			return resultMessage;
		}
}
