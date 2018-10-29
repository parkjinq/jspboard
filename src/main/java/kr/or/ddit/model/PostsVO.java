package kr.or.ddit.model;

import java.util.Date;

public class PostsVO {
	
	private int rnum;
	private String ps_id;
	private String bd_id;
	private Date ps_date;
	private String ps_title;
	private String ps_cnt;
	private String ps_id2;
	private String userid;
	private String ps_del;
	private String groupid;
	
	public int getRnum() {
		return rnum;
	}
	public void setRnum(int rnum) {
		this.rnum = rnum;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getPs_del() {
		return ps_del;
	}
	public void setPs_del(String ps_del) {
		this.ps_del = ps_del;
	}
	public String getPs_id() {
		return ps_id;
	}
	public void setPs_id(String ps_id) {
		this.ps_id = ps_id;
	}
	public String getBd_id() {
		return bd_id;
	}
	public void setBd_id(String bd_id) {
		this.bd_id = bd_id;
	}
	public Date getPs_date() {
		return ps_date;
	}
	public void setPs_date(Date ps_date) {
		this.ps_date = ps_date;
	}
	public String getPs_title() {
		return ps_title;
	}
	public void setPs_title(String ps_title) {
		this.ps_title = ps_title;
	}
	public String getPs_cnt() {
		return ps_cnt;
	}
	public void setPs_cnt(String ps_cnt) {
		this.ps_cnt = ps_cnt;
	}
	public String getPs_id2() {
		return ps_id2;
	}
	public void setPs_id2(String ps_id2) {
		this.ps_id2 = ps_id2;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@Override
	public String toString() {
		return "PostsVO [ps_id=" + ps_id + ", bd_id=" + bd_id + ", ps_date="
				+ ps_date + ", ps_title=" + ps_title + ", ps_cnt=" + ps_cnt
				+ ", ps_id2=" + ps_id2 + ", userid=" + userid + ", ps_del="
				+ ps_del + ", groupid=" + groupid + "]";
	}
	
}
