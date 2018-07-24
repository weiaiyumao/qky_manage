package com.zqfinance.system.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
import com.zqfinance.system.util.CsvUtil;

public class OrderFileUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(OrderFileUploadServlet.class);
	
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
						log.info("导入文件绝对路径为："+saveDir+"\\"
								+ fis.getName());
						BufferedOutputStream out = new BufferedOutputStream(
								new FileOutputStream(new File(saveDir+"\\"
										+ fis.getName())));
						Streams.copy(in, out, true);
						log.info("文件拷贝成功！");
						DataPageMessage dataMessage = uploadImportDate(saveDir+"\\"
								+ fis.getName());
						log.info("从文件解析出数据："+dataMessage);
						List<Map<String, String>> list = dataMessage.getContentList();
						StringBuffer sb = new StringBuffer();
						if(list!=null&&list.size()>0){
							for (int i = 0; i < list.size(); i++) {
								SendRequestService sendRequestService=new SendRequestServiceImpl();
								Map<String, String> map = list.get(i);
								JSONObject json = JSONObject.fromObject(map);
								//未完待续
								String resultString = sendRequestService.sendRequest(ConfigManager.BUY_SAVEANDRECONCILIATION,json);
								log.info("save操作结果:"+resultString);
								DataMessage resultMessage = JSON.parseObject(resultString,DataMessage.class);
								if (ManageConfig.DATE_MESSAGE_FAIL.equals(resultMessage.getResult())) {
									log.error(resultMessage.getMsg());
									 obj.put("result", "1");  
									 log.error(resultMessage.getMsg());
									 sb.append(map.get("orderId")+",");
								}
							}
							 log.info("文件读入成功，对账成功！");
							 obj.put("result", "0");  
							 if(!sb.toString().trim().equals("")){
								 obj.put("msg", "对账成功，录入对账数据正常！失败订单号：["+sb+"]");
							 }else{
								 obj.put("msg", "对账成功，录入对账数据正常！");
							 }
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
//		String tmpPath = "/mnt/var/www/manage/tmp";
//		String savePath = "/mnt/var/www/manage/upload";
		String tmpPath = "D:\\eclipse\\reconciliation\\tmp";
		String savePath = "D:\\eclipse\\reconciliation\\upload";
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
			InputStreamReader read = new InputStreamReader(new FileInputStream(file), "UTF-8");
			List<String[]> list = new ArrayList<String[]>();
			try {
				if(file.substring(file.length()-3, file.length()).equalsIgnoreCase("csv")){
					CsvUtil test = new CsvUtil();
					list = test.read(file);
					for (int i = 0; i < list.size(); i++) {
						String[] str = list.get(i);  
						Map<String, String> map = new HashMap<String, String>();
						map.put("orderId", str[0]);
						map.put("merchantNo", str[1]);
						map.put("orderDate", str[2]);
						map.put("merchantBusinessNo", str[3]);
						map.put("jetcoOrderid", str[4]);
						map.put("jetcoDate", str[5]);
						map.put("orderMoney", str[6]);
						map.put("receiptStatus", str[7]);
						map.put("tradeStatus", str[8]);
						map.put("updateDate", str[9]);
						map.put("poundage", str[10]);
						map.put("productName", str[11]);
						map.put("payStatus", str[12]);
						if(str.length > 13){
							map.put("orderInfo", str[13]);
						}else{
							map.put("orderInfo", "NULL");
						}
						contentList.add(map);  
				    }
				}else if(file.substring(file.length()-3, file.length()).equals("txt")){
					File fileTxt = new File(file);
					if (fileTxt.isFile() && fileTxt.exists()) {
						@SuppressWarnings("resource")
						BufferedReader bufferedReader = new BufferedReader(read);
						String lineTXT = null;
						while ((lineTXT = bufferedReader.readLine()) != null) {
							String[] str = lineTXT.toString().split(",");
							if (str[0].equals("商户订单号")) {
								continue;
							}
							Map<String, String> map = new HashMap<String, String>();
							map.put("orderId", str[0]);
							map.put("merchantNo", str[1]);
							map.put("orderDate", str[2]);
							map.put("merchantBusinessNo", str[3]);
							map.put("jetcoOrderid", str[4]);
							map.put("jetcoDate", str[5]);
							map.put("orderMoney", str[6]);
							map.put("receiptStatus", str[7]);
							map.put("tradeStatus", str[8]);
							map.put("updateDate", str[9]);
							map.put("poundage", str[10]);
							map.put("productName", str[11]);
							map.put("payStatus", str[12]);
							if(str.length > 13){
								map.put("orderInfo", str[13]);
							}else{
								map.put("orderInfo", "NULL");
							}
							contentList.add(map); 
						}
						
					} else {
						log.error("文件解析异常！");
					}
				}
				
				resultMessage.setResult("0");
				resultMessage.setContentList(contentList);
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				read.close();
			}
			return resultMessage;
		}
		
}
