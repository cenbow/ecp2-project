package com.ecp.bean;

/**
 * @ClassName FavouriteStatisticBean
 * @Description 用户收藏统计 Bean
 * @author Administrator
 * @Date 2017年5月27日 下午8:11:16
 * @version 1.0.0
 */
public class FavouriteStatisticBean {
	private int count;  //所属类目 数量
	private String c_name; //类目名称
	private long cid; //类目id
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}
	public long getCid() {
		return cid;
	}
	public void setCid(long cid) {
		this.cid = cid;
	}
}
