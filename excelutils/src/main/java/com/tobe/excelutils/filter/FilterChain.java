package com.tobe.excelutils.filter;

import java.util.ArrayList;
import java.util.List;

/**
 * 过滤链,只要没有通过其中一个过滤器,则停止
 *@author TOBE
 *
 */
public class FilterChain {

	private List<IFilter> fl = new ArrayList<IFilter>();
	
	public boolean add(IFilter f){
		return fl.add(f);
	}
	
	public boolean filter(){
		for(IFilter f : fl){
			boolean goOn = f.filter();
			if(!goOn){
				return false;
			}
		}
		return true;
	}
}
