

package com.zqfinance.system.common;

public class Page {
	/**当前页面**/
	private int pageNum = 1;
	/**每页展示记录数**/
	private int numPerPage = 20;
	/**总记录数**/
	private int totalCount;
	/**总页数**/
	private int totalPage;
	
	/**记录开始**/
	@SuppressWarnings("unused")
	private int beginRow;
	
	/**记录结束**/
	@SuppressWarnings("unused")
	private int endRow = 20;
	
	
	
	public int getBeginRow() {		
		return (pageNum-1) * numPerPage ;
	}
	public void setBeginRow(int beginRow) {
		this.beginRow = beginRow;
	}
	public int getEndRow() {
		return pageNum * numPerPage;
	}
	public void setEndRow(int endRow) {
		this.endRow =  endRow;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPage() {
		if(totalCount%numPerPage==0){
			this.totalPage = totalCount/numPerPage;
		}else{
			this.totalPage = totalCount/numPerPage + 1;
		}
		return totalPage;
	}
	public int getNumPerPage() {
		return numPerPage;
	}
	public void setNumPerPage(int numPerPage) {
		this.numPerPage = numPerPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	
	
}

