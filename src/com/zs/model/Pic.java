package com.zs.model;
//图片实体类
public class Pic {
	private int picId;
	private int goodsId;
	private String picUrl;
	
	
	public Pic() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Pic(int picId, int goodsId, String picUrl, int picType) {
		super();
		this.picId = picId;
		this.goodsId = goodsId;
		this.picUrl = picUrl;
	}
	public int getPicId() {
		return picId;
	}
	public void setPicId(int picId) {
		this.picId = picId;
	}
	public int getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}
	public String getPicUrl() {
		return picUrl;
	}
	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}
	

	@Override
	public String toString() {
		return "Pic [picId=" + picId + ", goodsId=" + goodsId + ", picUrl=" + picUrl +  "]";
	}
	
	
}
