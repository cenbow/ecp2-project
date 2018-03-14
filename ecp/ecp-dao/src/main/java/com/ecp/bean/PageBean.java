package com.ecp.bean;

/**
 * 分页实体类
 * @author srd
 *
 */
public class PageBean {

	private int pageNum;
	
	private int pageSize;
	
	private int navigatePages;

	public int getPageNum() {
		return pageNum!=0?pageNum:1;//默认值1
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize!=0?pageSize:5;//默认值5
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getNavigatePages() {
		return navigatePages!=0?navigatePages:4;//默认值4
	}

	public void setNavigatePages(int navigatePages) {
		this.navigatePages = navigatePages;
	}

	@Override
	public String toString() {
		return "PageBean [pageNum=" + pageNum + ", pageSize=" + pageSize + ", navigatePages=" + navigatePages + "]";
	}

}
