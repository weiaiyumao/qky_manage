package com.zqfinance.system.util;

import java.io.OutputStream;
import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;

import com.zqfinance.module.report.vo.RptVO;

@SuppressWarnings("deprecation")
public class ExcelUtil {
	/**
	 * 模板1
	 * 理财产品每月订单资金对照表	
		月份	金额（RMB）
		1	10889.19
		2	2931608.2
		3	861688
		总交易金额：3804185.39元     导出时间为：2015-03-09 16:39:47	
	 */
	public static void modelOne(RptVO rptVO,HttpServletResponse response,String hssTitle,String cellOneTitle){
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
			cell0.setCellValue(new HSSFRichTextString(hssTitle));
			cell0.setCellStyle(sheetStyle);
			CellRangeAddress range = new CellRangeAddress(0, 0, 0, 1);
			sheet.addMergedRegion(range);
			// 创建第二行
			HSSFRow row2 = sheet.createRow(1);
			String[] strList = new String[] { cellOneTitle,  "金额（RMB）" };
			for (int i = 0; i < strList.length; i++) {
				HSSFCell cell2 = row2.createCell(i);
				cell2.setCellValue(strList[i]);
				cell2.setCellStyle(sheetStyle);
				sheet.setColumnWidth(i, 5800);
			}
			// 创建n行
			for (int i = 0; i < rptVO.getXaixsList().size(); i++) {
				HSSFRow row = sheet.createRow(i + 2);
				sheet.setColumnWidth(i, 5800);
				row.createCell(0).setCellType(HSSFCell.CELL_TYPE_STRING);
				row.createCell(0).setCellValue(String.valueOf(rptVO.getXaixsList().get(i)));
			}
			for (int i = 0; i < rptVO.getDataList().size(); i++) {
				HSSFRow row = sheet.getRow(i+2);
				row.createCell(1).setCellType(HSSFCell.CELL_TYPE_STRING);
				row.createCell(1).setCellValue(FromatMoneyUtil.fromatMoney(Double.parseDouble(String.valueOf(rptVO.getDataList().get(i)) != null ? String.valueOf(rptVO.getDataList().get(i)) : new String("0.0"))));
			}
			// 列尾
			int footRownumber = sheet.getLastRowNum();
			HSSFRow footRow = sheet.createRow(footRownumber + 1);
			footRow.setHeight((short) 450);
			HSSFCell footRowcell = footRow.createCell(0);
			footRowcell.setCellValue(new HSSFRichTextString("总交易金额："+ FromatMoneyUtil.fromatMoney(rptVO.getCountMoney().doubleValue()) + "元     导出时间为："+ DateHelper.timeToString(new Date())));
			footRowcell.setCellStyle(sheetStyle);
			range = new CellRangeAddress(footRownumber + 1, footRownumber + 1,0, 1);
			sheet.addMergedRegion(range);

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition",
					"attachment;filename="+ new String(hssTitle.getBytes("gb2312"), "iso8859-1")+".xls");
			OutputStream ouputStream = response.getOutputStream();
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
			workbook.close();
		} catch (Exception e) {

		}
	}
}
