package com.zqfinance.system.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CsvUtil {

	private BufferedReader bufferedreader = null;
	private List<String> list = new ArrayList<String>();

	public CsvUtil() {
	}

	public CsvUtil(String filename) throws IOException {
		InputStreamReader isr = new InputStreamReader(new FileInputStream(filename), "gb2312");
		bufferedreader = new BufferedReader(isr);
		String stemp;
		while ((stemp = bufferedreader.readLine()) != null) {
			list.add(stemp);
		}
		
	}

	public List<String> getList() throws IOException {
		return list;
	}

	// 得到csv文件的行数
	public int getRowNum() {
		return list.size();
	}

	// 得到csv文件的列数
	public int getColNum() {
		if (!list.toString().equals("[]")) {
			if (list.get(0).toString().contains(",")) { // csv文件中，每列之间的是用','来分隔的
				return list.get(0).toString().split(",").length;
			} else if (list.get(0).toString().trim().length() != 0) {
				return 1;
			} else {
				return 0;
			}
		} else {
			return 0;
		}
	}

	// 取得指定行的值

	public String getRow(int index) {
		if (this.list.size() != 0)
			return (String) list.get(index);
		else
			return null;
	}

	// 取得指定列的值
	public String getCol(int index) {
		if (this.getColNum() == 0) {
			return null;
		}
		StringBuffer scol = new StringBuffer();
		String temp = null;
		int colnum = this.getColNum();
		if (colnum > 1) {
			for (Iterator<String> it = list.iterator(); it.hasNext();) {
				temp = it.next().toString();
				scol = scol.append(temp.split(",")[index] + ",");
			}
		} else {
			for (Iterator<String> it = list.iterator(); it.hasNext();) {
				temp = it.next().toString();
				scol = scol.append(temp + ",");
			}
		}
		String str = new String(scol.toString());
		str = str.substring(0, str.length() - 1);
		return str;
	}

	// 取得指定行，指定列的值
	public String getString(int row, int col) {
		String temp = null;
		int colnum = this.getColNum();
		if (colnum > 1) {
			//由于excel最后一列可能为空值也有可能有值，防止取值超过数组的异常所以此处做无奈选择
			if(col==13){
				try {
					temp = list.get(row).toString().split(",")[col];
				} catch (Exception e) {
					temp="NULL";
				}
			}else{
				temp = list.get(row).toString().split(",")[col];
			}
		} else if (colnum == 1) {
			temp = list.get(row).toString();
		} else {
			temp = null;
		}
		return temp;
	}

	public void CsvClose() throws IOException {
		this.bufferedreader.close();
	}

	public List<String[]> read(String filename) throws IOException {
		CsvUtil cu = new CsvUtil(filename);
		List<String[]> list = new ArrayList<String[]>();
		for (int i = 1; i < cu.getRowNum(); i++) {
			String[]  str = new String[14];
			str[0] = cu.getString(i, 0).substring(2, cu.getString(i, 0).length()-1);
			str[1] = cu.getString(i, 1).substring(2, cu.getString(i, 1).length()-1);
			str[2] = cu.getString(i, 2);
			str[3] = cu.getString(i, 3);
			str[4] = cu.getString(i, 4).substring(2, cu.getString(i, 4).length()-1);
			str[5] = cu.getString(i, 5);
			str[6] = cu.getString(i, 6);
			str[7] = cu.getString(i, 7);
			str[8] = cu.getString(i, 8);
			str[9] = cu.getString(i, 9);
			str[10] = cu.getString(i, 10);
			str[11] = cu.getString(i, 11);
			str[12] = cu.getString(i, 12);
			str[13] = cu.getString(i, 13);
			list.add(str);
			
		}
		cu.CsvClose();
		return list;
	}

	public static void main(String[] args) throws IOException {
		CsvUtil test = new CsvUtil();
		test.read("C:\\Users\\Administrator\\Documents\\JYMX_201411121000096508_20150319.csv");
	}
}
