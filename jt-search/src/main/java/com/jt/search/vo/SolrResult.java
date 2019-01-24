package com.jt.search.vo;

public class SolrResult {
	private String id;
	private String title;
	private String price;
	private String img;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String image) {
		this.img = image;
	}

	@Override
	public String toString() {
		return "SolrResult [id=" + id + ", title=" + title + ",  price=" + price + ", image=" + img + "]";
	}

}
