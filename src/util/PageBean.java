package util;

import java.util.List;

public class PageBean<T> {
	
	private int pageSize = 2;
//	private int pageCount;
	private int currentPage;
	private int totalRecord;
	
	private volatile int start;
	private volatile int end;
	
	/**
	 * the every page result
	 */
	private List<T> page;
	
//	private Map<String, String> attrMap;
	
	/**
	 * the more information to
	 * search result
	 */
	private String attrUrl;
	

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageCount() {
		return (totalRecord + pageSize - 1) / pageSize;
	}

//	public void setPageCount(int pageCount) {
//		this.pageCount = pageCount;
//	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
	}

	public List<T> getPage() {
		return page;
	}

	public void setPage(List<T> page) {
		this.page = page;
	}

	public String getAttrUrl() {
		return attrUrl;
	}

	public void setAttrUrl(String attrUrl) {
		this.attrUrl = attrUrl;
	}
	
	public int getStart(){
		if(this.getPageCount() <= 10){
			this.start = 1;
			this.end = this.getPageCount();
		}else{
			this.start = this.currentPage - 5;
			this.end = this.currentPage + 4;
			
			if(this.start < 1){
				this.start = 1;
				this.end = 10;
			}
			if(this.end > this.getPageCount()){
				this.start = this.getPageCount() - 9;
				this.end = this.getPageCount();
			}
		}
		
		
		return this.start;
	}
	public int getEnd(){
		
		
		return this.end;
	}
	
	

}
