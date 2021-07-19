package com.report.util;

public class PageInfo {

	private Integer page;
	private Integer rows;

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page-1;
	}


	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "PageInfo [page=" + page + ", rows=" + rows + "]";
	}

	
	
}
