package com.jt.manage.vo;

public class EasyUITree {
	private Long id;		//商品分类id
	private String text;	//商品分类名称
	private String state;	//节点状态 open/closed
	//如果是子节点则不能写closed,会造成数据错乱.
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
	public EasyUITree(){}
	
	public EasyUITree(Long id, String text, String state) {
		super();
		this.id = id;
		this.text = text;
		this.state = state;
	}
	
	
	
}
