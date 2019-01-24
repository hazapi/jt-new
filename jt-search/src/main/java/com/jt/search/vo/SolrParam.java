package com.jt.search.vo;

public class SolrParam {
	
	private String q;
	
	private Integer start;

	@Override
	public String toString() {
		return "SolrParam [q=" + q + ", start=" + start + "]";
	}

	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public Integer getStart() {
		return start;
	}

	public void setStart(Integer start) {
		this.start = start;
	}
	
	
}
