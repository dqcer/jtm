package com.dqcer.jtm.core.util;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: dongQin
 * @Date: 2018/12/3 17:57
 * @Description:
 */
@Data
public class PageUtil<V> implements Serializable {

	/**
	 * 每页条数
	 */
	private Integer pageSize = 10000;

	/**
	 * 当前页数
	 */
	private Integer currentPage = 1;

	/**
	 * 总页数
	 */
	private Integer totalPage = 0;

	/**
	 * 是否有上一页
	 */
	private Boolean hasPrePage = false;

	/**
	 * 是否有下一页
	 */
	private Boolean hasNextPage = false;

	/**
	 * 返回List集合
	 */
	private List<V> result = new ArrayList<>();

	/**
	 * 总的数量
	 */
	private Integer totalCount = 0;

	/**
	 * 查询的参数
	 */
	@JSONField(serialize = false)
	private Object conditions = new Object();

	public Integer getTotalPage() {
		if (this.totalCount == 0) {
			this.totalPage = 1;
		} else if (this.totalCount % this.pageSize == 0) {
			this.totalPage = this.totalCount / this.pageSize;
		} else {
			this.totalPage = (this.totalCount / this.pageSize) + 1;
		}
		return this.totalPage;
	}

	public Boolean getHasPrePage() {
		this.hasPrePage = currentPage > 1 ? true : false;
		return this.hasPrePage;
	}

	public Boolean getHasNextPage() {
		this.hasNextPage = currentPage < getTotalPage() ? true : false;
		return this.hasNextPage;
	}



}
