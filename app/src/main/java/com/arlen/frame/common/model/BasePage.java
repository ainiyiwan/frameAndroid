package com.arlen.frame.common.model;

import java.io.Serializable;
import java.util.List;

/**
 * 分类列表中获取的后台数据的bean封装
 * @author Administrator
 *
 * @param <T>
 */
public class BasePage<T> implements Serializable {

	public int totalPage;
	public int currPage;
	public int total;
	public int count;
	public boolean hasNext;

	public List<T> data;
}