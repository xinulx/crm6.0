package com.mine.App.common.pagenation;

import java.util.ArrayList;

@SuppressWarnings("rawtypes")
public class PaginationList extends ArrayList implements Pagination {

	private static final long serialVersionUID = 1L;
	public PaginationList(Integer pageSize, Integer currentPage) {
		recordTotal = Integer.valueOf(0);
		pageTotal = Integer.valueOf(0);
		this.currentPage = Integer.valueOf(1);
		this.pageSize = Integer.valueOf(0);
		setPageSize(pageSize);
		setCurrentPage(currentPage);
	}

	public Integer getRecordTotal() {
		return recordTotal;
	}

	public void setRecordTotal(Integer i) {
		recordTotal = i;
		total = Integer.valueOf(i.intValue());
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

	public Integer getPageTotal() {
		if (getRecordTotal().intValue() <= 0) {
			return Integer.valueOf(0);
		} else {
			int tmp = recordTotal.intValue() / getPageSize().intValue();
			pageTotal = Integer.valueOf(getRecordTotal().intValue()
					% getPageSize().intValue() != 0 ? tmp + 1 : tmp);
			return pageTotal;
		}
	}

	public void setCurrentPage(Integer i) {
		currentPage = i;
	}

	public Integer getCurrentRecord() {
		if (getPageTotal().intValue() == 0)
			return Integer.valueOf(0);
		int i = getCurrentPage().intValue() - 1;
		if (i < 0)
			i = 0;
		return Integer.valueOf(i * getPageSize().intValue());
	}

	public void setPageTotal(Integer i) {
		pageTotal = i;
	}

	public void setPageSize(Integer i) {
		pageSize = i;
	}

	public Integer getFirstRecord() {
		if (getPageTotal().intValue() == 0)
			return Integer.valueOf(0);
		else
			return Integer.valueOf((getCurrentPage().intValue() - 1)
					* getPageSize().intValue());
	}

	public Integer getLastRecord() {
		if (getPageTotal().intValue() == 0)
			return Integer.valueOf(0);
		if (getPageTotal().intValue() == getCurrentPage().intValue())
			return getRecordTotal();
		else
			return Integer.valueOf(getCurrentPage().intValue()
					* getPageSize().intValue());
	}

	public boolean hasNextPage() {
		return getCurrentPage().intValue() < getPageTotal().intValue();
	}

	public boolean hasUpPage() {
		return getCurrentPage().intValue() > 1;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	private Integer recordTotal;
	private Integer pageTotal;
	private Integer currentPage;
	private Integer pageSize;
	private Integer total;
}