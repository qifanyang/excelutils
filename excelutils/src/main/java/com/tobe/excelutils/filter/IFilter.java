package com.tobe.excelutils.filter;

/**
 * 过滤器
 *@author TOBE
 *
 */
public interface IFilter {

	/**
	 * 通过过滤返回true, true表示可以传递到写一个过滤器, 否则返回false,终止过滤
	 * 
	 * 
	 * @return
	 */
	boolean filter();
}
