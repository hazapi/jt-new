package com.jt.common.po;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name="tb_content_category")
public class ContentCategory extends BasePojo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;			//类目ID
	private Long parentId;		//父类id
	private String name;		//分类名称
	private Integer status;		//状态   1正常     2删除
	private Integer sortOrder;	//排列序号，表示同级类目的展现次序，如数值相等则按名称次序排列。取值范围:大于零的整数
	private Boolean isParent;	//该类目是否为父类目，1为true，0为false
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	public Boolean getIsParent() {
		return isParent;
	}
	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}
	
	
}
