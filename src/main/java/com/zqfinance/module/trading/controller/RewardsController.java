package com.zqfinance.module.trading.controller;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zqfinance.module.sys.domain.Loginfo;
import com.zqfinance.system.common.DataMessage;
import com.zqfinance.system.common.DataPageMessage;
import com.zqfinance.system.config.ConfigManager;
import com.zqfinance.system.config.ManageConfig;
import com.zqfinance.system.controller.DwzAjaxController;
import com.zqfinance.system.util.DateHelper;
import com.zqfinance.system.util.DateUtil;
import com.zqfinance.system.util.DictionaryMap;
import com.zqfinance.system.util.FromatMoneyUtil;
import com.zqfinance.system.util.MessageUtil;
import com.zqfinance.system.util.RequestUtil;

@SuppressWarnings("deprecation")
@Controller
@RequestMapping("/rewards")
public class RewardsController extends DwzAjaxController {
	
	/**
	 * 获取列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/getRewardsList")
	public ModelAndView getRewardsList(HttpServletRequest request){	
		
		HttpSession session =  request.getSession(false);
		com.zqfinance.module.sys.domain.User user = null;
		if(null != session){
			user = (com.zqfinance.module.sys.domain.User) session.getAttribute(ManageConfig.LOGIN_USER);
			if(null != user){
				//保存成功登陆日志
				Loginfo loginfo = new Loginfo();
				loginfo.setUserId(user.getId());
				loginfo.setUserName(user.getUserName());
				loginfo.setModuleName("活动奖励收益管理");
				loginfo.setLoginStatus(ManageConfig.loginStatus_SUCCESS);
				loginfoService.addLoginfo(loginfo);
			}
		}
		
		ModelAndView modelAndView = new ModelAndView("trading/rewards/rewardsList");
        JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		String resultString = sendRequestService.sendRequest(ConfigManager.REWARDS_FINDBYCONDITION,jsonObject);
		DataPageMessage dataMessage = MessageUtil.parseDatePageMessage(resultString);
		DateUtil.formatDateByDataPageMessage(dataMessage);
		modelAndView.addObject("dataMessage", dataMessage);
		modelAndView.addObject("beginDate",jsonObject.get("beginDate"));
		modelAndView.addObject("endDate",jsonObject.get("endDate"));
		modelAndView.addObject("orderid",jsonObject.get("orderid"));
		modelAndView.addObject("mixMoney",jsonObject.get("mixMoney"));
		modelAndView.addObject("maxMoney",jsonObject.get("maxMoney"));
		modelAndView.addObject("type", jsonObject.get("type"));
		modelAndView.addObject("flag", jsonObject.get("flag"));
		return modelAndView;
	}
	
	/**
	 * 跳转详情页面
	 * @param investProductId
	 * @return
	 */
	@RequestMapping("/forwardInfo")
	public ModelAndView forwardInfo(String id){	
		ModelAndView modelAndView = new ModelAndView("trading/rewards/rewardsInfo");
		JSONObject jsonObject = new JSONObject();
	    jsonObject.put("id", id.trim());
	    String resultString = sendRequestService.sendRequest(ConfigManager.REWARDS_FINDBYID,jsonObject);
	    DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
	    DateUtil.formatDateByDataMessage(dataMessage);
		modelAndView.addObject("dataMessage", dataMessage);
		return modelAndView;
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delRewards")
	public @ResponseBody DwzAjaxController delRewards(String ids){		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ids", ids.trim());
		String resultString = sendRequestService.sendRequest(ConfigManager.REWARDS_DELBYIDS,jsonObject);
		DataMessage dataMessage = MessageUtil.parseDataMessage(resultString);
		if(ManageConfig.DATE_MESSAGE_SUCCESS.equalsIgnoreCase(dataMessage.getResult())){
			return this.ajaxForwardSuccess("删除活动奖励收益成功！","",null,null);
		}else{
			return this.ajaxForwardError(dataMessage.getMsg(), null, null, null);
		}
	}
	
	@RequestMapping("/dwlExport")
	public void downLoadExcport(HttpServletRequest request,
			HttpServletResponse response){
		JSONObject jsonObject = RequestUtil.fromRequestToJson(request);
		jsonObject.put("pageNum", "1");
		String resultString = sendRequestService.sendRequest(ConfigManager.REWARDS_FINDBYCONDITION, jsonObject);
		DataPageMessage dataMessage = MessageUtil.parseDatePageMessage(resultString);
		DateUtil.formatDateByDataPageMessage(dataMessage);
		
		for (int i = 2; i < dataMessage.getPage().getTotalCount()/20+2; i++) {
			JSONObject jsonObjects = RequestUtil.fromRequestToJson(request);
			jsonObjects.put("pageNum", i);
			String resultStrings = sendRequestService.sendRequest(ConfigManager.REWARDS_FINDBYCONDITION, jsonObjects);
			DataPageMessage dataMessages = MessageUtil.parseDatePageMessage(resultStrings);
			DateUtil.formatDateByDataPageMessage(dataMessages);
			dataMessage.getContentList().addAll(dataMessages.getContentList());
		}
		
		HSSFWorkbook workbook = new HSSFWorkbook();// 创建一个Excel文件
		HSSFSheet sheet = workbook.createSheet();// 创建一个Excel的Sheet
		// Sheet样式
		HSSFCellStyle sheetStyle = workbook.createCellStyle();
		sheetStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		sheetStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 左右居中
		sheetStyle.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		// 设置列的样式
		for (int i = 0; i <= 14; i++) {
			sheet.setDefaultColumnStyle((short) i, sheetStyle);
		}
		// 设置字体
		HSSFFont headfont = workbook.createFont();
		headfont.setFontName("黑体");
		headfont.setFontHeightInPoints((short) 22);// 字体大小
		headfont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 加粗

		try {
			// 创建第一行
			HSSFRow row0 = sheet.createRow(0);
			row0.setHeight((short) 450);
			// 创建第一列
			HSSFCell cell0 = row0.createCell(0);
			cell0.setCellValue(new HSSFRichTextString("活动订单对照表"));
			cell0.setCellStyle(sheetStyle);
			CellRangeAddress range = new CellRangeAddress(0, 0, 0, 4);
			sheet.addMergedRegion(range);
			// 创建第二行
			HSSFRow row2 = sheet.createRow(2);
			String[] strList = new String[] { "订单号", "活动类型", "金额（RMB）","处理状态", "交易时间" };
			for (int i = 0; i < strList.length; i++) {
				HSSFCell cell2 = row2.createCell(i);
				cell2.setCellValue(strList[i]);
				cell2.setCellStyle(sheetStyle);
				sheet.setColumnWidth(i, 5800);
			}
			// 创建n行
			BigDecimal money = BigDecimal.ZERO;
			for (int i = 0; i < dataMessage.getContentList().size(); i++) {
				Map<String, String> resutltMap = dataMessage.getContentList().get(i);
				HSSFRow row = sheet.createRow(i + 3);
				sheet.setColumnWidth(i, 5800);
				row.createCell(0).setCellType(HSSFCell.CELL_TYPE_NUMERIC);
				row.createCell(0).setCellValue(resutltMap.get("orderid"));
				row.createCell(1).setCellType(HSSFCell.CELL_TYPE_STRING);
				String type = DictionaryMap.REWARDS_TYPE.get(resutltMap.get("type"));
				row.createCell(1).setCellValue(type);
				row.createCell(2).setCellType(HSSFCell.CELL_TYPE_STRING);
				money = money.add(BigDecimal.valueOf(Double.parseDouble(resutltMap.get("money") != null ? resutltMap.get("money"): new String("0.0"))));
				row.createCell(2).setCellValue(FromatMoneyUtil.fromatMoney(Double.parseDouble(resutltMap.get("money") != null ? resutltMap.get("money") : new String("0.0"))));
				row.createCell(3).setCellType(HSSFCell.CELL_TYPE_STRING);
				String status = DictionaryMap.REWARDS_STATUS.get(resutltMap.get("status"));
				row.createCell(3).setCellValue(status);
				row.createCell(4).setCellType(HSSFCell.CELL_TYPE_STRING);
				row.createCell(4).setCellValue(resutltMap.get("timeTrading"));
			}
			// 第三行
			int footRownumber = sheet.getFirstRowNum();
			HSSFRow footRow = sheet.createRow(footRownumber + 1);
			footRow.setHeight((short) 450);
			HSSFCell footRowcell = footRow.createCell(0);
			footRowcell.setCellValue(new HSSFRichTextString("导出总条数"+ dataMessage.getPage().getTotalCount() + "条    总交易金额："+ FromatMoneyUtil.fromatMoney(money.doubleValue()) + "元     导出时间为："+ DateHelper.timeToString(new Date())));
			footRowcell.setCellStyle(sheetStyle);
			range = new CellRangeAddress(footRownumber + 1, footRownumber + 1,0, 4);
			sheet.addMergedRegion(range);

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition",
					"attachment;filename="+ new String("活动订单记录".getBytes("gb2312"), "iso8859-1")+".xls");
			OutputStream ouputStream = response.getOutputStream();
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
			workbook.close();
		} catch (Exception e) {

		}
	}
}
