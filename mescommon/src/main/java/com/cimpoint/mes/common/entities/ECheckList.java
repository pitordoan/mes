package com.cimpoint.mes.common.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="CheckList")
public class ECheckList implements Serializable{
	
	private static final long serialVersionUID = -3490711463984456739L;

	@Id
	@GeneratedValue
	@Column(name="Id")
	private Long id;

	@Column(name="Name")
	private String name;
	
	@Column(name="Description")
	private String description;
	
	@Column(name="Result")
	private int result; //1 = Pass, 0 = Fail
	
	@Column(name="Comment")
	private String comment;
	
	@ManyToOne
	@JoinColumn(name="ParentCheckListId")
	private ECheckList parentCheckList;

	@OneToMany(mappedBy = "parentCheckList", targetEntity = ECheckList.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)   
	private Set<ECheckList> childCheckLists;

	@ManyToOne
	@JoinColumn(name="DependentCheckListId")
	private ECheckList dependentCheckList;
	
	@OneToMany(mappedBy = "dependentCheckList", targetEntity = ECheckList.class, fetch = FetchType.EAGER, cascade = CascadeType.ALL)   
	private Set<ECheckList> dependentCheckLists;
	
	public ECheckList() {}

	public ECheckList(String name, String description, int result, String comment, ECheckList parentChecklist, Set<ECheckList> childCheckLists) {
		this.name = name;
		this.description = description;
		this.result = result;
		this.comment = comment;
		this.parentCheckList = parentChecklist;
		this.childCheckLists = childCheckLists;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Set<ECheckList> getChildCheckLists() {
		return childCheckLists;
	}

	public void setChildCheckLists(Set<ECheckList> childCheckLists) {
		this.childCheckLists = childCheckLists;
	}

	public ECheckList getParentCheckList() {
		return parentCheckList;
	}

	public void setParentCheckList(ECheckList parentCheckList) {
		this.parentCheckList = parentCheckList;
	}

	public ECheckList getDependentCheckList() {
		return dependentCheckList;
	}

	public void setDependentCheckList(ECheckList dependentCheckList) {
		this.dependentCheckList = dependentCheckList;
	}

	public Set<ECheckList> getDependentCheckLists() {
		return dependentCheckLists;
	}

	public void setDependentCheckLists(Set<ECheckList> dependentCheckLists) {
		this.dependentCheckLists = dependentCheckLists;
	}
}
