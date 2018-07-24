package com.zqfinance.system.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelReader {
	 /** 
     *获得excle的数据 
     *  
     * @param pathname 
     * @return 
     */  
    public static List<String[]> getExcle(String pathname) {  
        InputStream fs = null;  
        Workbook wb = null;  
       List<String[]> list = new ArrayList<String[]>();  
        try {  
            // excle的类型  
            String readType = pathname.substring(pathname.lastIndexOf("."));  
            File file = new File(pathname);  
            if (file.exists()) {  
                fs = new FileInputStream(file);  
            } else {  
                System.out.println("文件不存在！");  
            }  
            if (readType.equals(".xls")) {  
                wb = new HSSFWorkbook(fs);  
            } 
            list = getExcleData_xls(wb);  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return list;  
    }  
    
    /** 
     * 获得excle xls格式的数据 
     *  
     * @param wb 
     * @return 
     */  
    public static List<String[]> getExcleData_xls(Workbook wb) {  
        List<String[]> list = new ArrayList<String[]>();  
        try {  
            if (wb != null) {  
                Sheet sheet = wb.getSheetAt(0);  
                list = getSheetData(sheet); // 读取sheet里的数据  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }
		return list;  
    }  
    
    /** 
     * 获得sheet 里的数据 
     *  
     * @param sheet 
     * @return 
     */  
    public static List<String[]> getSheetData(Sheet sheet) {  
        List<String[]> listData = new ArrayList<String[]>();  
        try {  
            if (sheet != null) {  
                for (int i = 0; i <= sheet.getLastRowNum(); i++) {  
                    Row row = sheet.getRow(i);  
                    String[] rowData = getRowData(row);  
                    listData.add(rowData);  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return listData;  
    }  
    
    /** 
     * 获得row 的数据 
     *  
     * @param row 
     * @return 
     */  
    public static String[] getRowData(Row row) {  
        String[] rowData = null;  
        try {  
            if (row != null) {  
                int numcell = row.getLastCellNum();  
                rowData = new String[numcell];  
                for (int i = 0; i < numcell; i++) {  
                    Cell cell = row.getCell(i);  
                    rowData[i] = getCellData(cell);  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return rowData;  
    }  
    
    /** 
     * 获得单元格的值  
     * @param cell 
     * @return 
     */  
    public static String getCellData(Cell cell) {  
        String value = null;  
        try {  
            if (cell != null) {  
                switch (cell.getCellType()) {  
                case Cell.CELL_TYPE_NUMERIC: // 数值型  
                	 value = String.valueOf(cell.getNumericCellValue());  
                    break;  
                case Cell.CELL_TYPE_STRING: // 字符串型  
                    value = cell.getRichStringCellValue().toString();  
                    break;  
                case Cell.CELL_TYPE_FORMULA:// 公式型  
                    // 读公式计算值  
                    value = String.valueOf(cell.getNumericCellValue());  
                    break;  
                case Cell.CELL_TYPE_BOOLEAN:// 布尔  
                    value = " " + cell.getBooleanCellValue();  
                    break;  
                /* 此行表示该单元格值为空 */  
                case Cell.CELL_TYPE_BLANK: // 空值  
                    value = " ";  
                    break;  
                case Cell.CELL_TYPE_ERROR: // 故障  
                    value = " ";  
                    break;  
                default:  
                    value = cell.getRichStringCellValue().toString();  
                }  
            }  
        } catch (Exception e) {  
             e.printStackTrace();  
        }  
        return value;  
    }  
    
    
    /** 
     * 处理单元格值相等的单元格 
     *  
     * @param sheet 
     */  
    @SuppressWarnings("unused")  
    public static List<String[]> setMergedRegion(Sheet sheet,  
            List<String[]> list) {  
        int num = sheet.getNumMergedRegions();  
        List<String[]> listDate = new ArrayList<String[]>();  
        try {  
  
            for (int i = 0; i < num; i++) {  
                CellRangeAddress rangeAddress = sheet.getMergedRegion(i);  
                int firstcell = rangeAddress.getFirstColumn();  
                int firstrow = rangeAddress.getFirstRow();  
                int lastcell = rangeAddress.getLastColumn();  
                int lastrow = rangeAddress.getLastRow();  
                // 处理合并行的值  
                if (firstcell == lastcell) {  
                    for (int j = firstrow; j <= lastrow; j++) {  
                        list.get(j)[firstcell] = list.get(firstrow)[firstcell];  
                    }  
                }  
                // 处理合并列的值  
                if (firstrow == lastrow) {  
                    for (int j = firstcell; j <= lastcell; j++) {  
                        list.get(firstrow)[j] = list.get(firstrow)[j];  
                    }  
                }  
                // 处理合并行列  
                if (firstcell != lastcell && firstrow != lastrow) {  
                    for (int j = firstrow; j <= lastrow; j++) {  
                        for (int k = firstcell; k <= lastcell; k++) {  
                            list.get(j)[k] = list.get(firstrow)[firstcell];  
                        }  
                    }  
                }  
            }  
            listDate = list;  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return list;  
    }  
}
