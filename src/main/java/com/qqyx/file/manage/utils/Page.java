package com.qqyx.file.manage.utils;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable{
	
	 public static final int DEFAULT_PAGE_SIZE = 15;
	 private int total;
	 private List<T> items;
	 
	 public List<T> getItems(){
	    return this.items;
	 }

	 public void setItems(List<T> items) {
	    this.items = items;
	 }

	 public int getTotal(){
	    return this.total;
	 }

	 public void setTotal(int total){
	    this.total = total;
	 }
}
