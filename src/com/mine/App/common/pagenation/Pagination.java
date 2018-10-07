
package com.mine.App.common.pagenation;

import java.util.Collection;

@SuppressWarnings("rawtypes")
public interface Pagination extends Collection {

	public abstract Integer getRecordTotal();

	public abstract void setRecordTotal(Integer integer);

	public abstract Integer getCurrentPage();

	public abstract Integer getCurrentRecord();

	public abstract void setCurrentPage(Integer integer);

	public abstract Integer getPageTotal();

	public abstract Integer getPageSize();

	public abstract boolean hasNextPage();

	public abstract boolean hasUpPage();

	public abstract Integer getFirstRecord();

	public abstract Integer getLastRecord();
}