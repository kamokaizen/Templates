package com.example.springwebtemplate.controller.response;

import java.util.ArrayList;
import java.util.List;

public class PageModel<E> implements BaseRestResponse {
	private int page;
	private int totalPage;
	private List<E> pageResult;

	public PageModel() {
		pageResult = new ArrayList<E>();
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<E> getPageResult() {
		return pageResult;
	}

	public void setPageResult(List<E> pageResult) {
		this.pageResult = pageResult;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
}
