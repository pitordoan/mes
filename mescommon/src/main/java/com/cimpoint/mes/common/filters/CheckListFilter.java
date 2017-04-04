package com.cimpoint.mes.common.filters;

import java.io.Serializable;

import com.cimpoint.common.filters.ObjectFilter;
import com.cimpoint.common.filters.SearchConstraint;
import com.cimpoint.common.filters.SortConstraint;

public class CheckListFilter  extends ObjectFilter implements Serializable{

	private static final long serialVersionUID = -7463554350453334802L;

	public CheckListFilter() {
	}

	public SearchConstraint whereId() {
		return super.where("id");
	}

	public SearchConstraint whereName() {
		return super.where("name");
	}

	public SortConstraint orderById() {
		return super.orderBy("id");
	}

	public SortConstraint orderByName() {
		return super.orderBy("name");
	}
	
	public CheckListFilter AND() {
		this.newAND();
		return this;
	}

	public CheckListFilter OR() {
		this.newOR();
		return this;
	}
}
