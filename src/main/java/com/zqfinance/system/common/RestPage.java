/**
 * 与其他项目交互的page
 */

package com.zqfinance.system.common;

public class RestPage {
	/**当前页码**/
	private int size;
	/**每页记录数**/
	private int number;
	/**共有多少条记录**/
	private int totalElements;
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public int getTotalElements() {
		return totalElements;
	}
	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}
	
	
}

