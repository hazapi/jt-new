package com.jt.common.po;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Table(name="tb_item")			//对象与表一一映射
//如果对象转化时,忽略未知属性!!!!
@JsonIgnoreProperties(ignoreUnknown=true)
public class Item extends BasePojo{
	
	@Id		//表示主键
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long 	id;			//商品的ID
	private String  title;		//商品的标题
	private String  sellPoint;	//商品卖点信息
	private Long    price;		//6190 / 100  int > long > dubble 精度问题 
	private Integer num;		//商品的数量
	private String barcode;		//条码信息
	private String image;		//保存图片信息 多张图片采用","号分割  1.jpg,2.jpg
	private Long cid;			//商品分类id号
	private Integer status;		//状态信息 1正常，2下架
	
	//为了提交购物车时,展现第一张图片 编辑
	public String[] getImages(){
		
		return image.split(",");
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSellPoint() {
		return sellPoint;
	}
	public void setSellPoint(String sellPoint) {
		this.sellPoint = sellPoint;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Long getCid() {
		return cid;
	}
	public void setCid(Long cid) {
		this.cid = cid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
