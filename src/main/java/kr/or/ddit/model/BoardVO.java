package kr.or.ddit.model;

import java.util.Date;

public class BoardVO {
	private String bd_id;
	private String bd_title;
	private String bd_use;
	private Date bd_date;
	private String userid;
	
	public String getBd_id() {
		return bd_id;
	}
	public void setBd_id(String bd_id) {
		this.bd_id = bd_id;
	}
	public String getBd_title() {
		return bd_title;
	}
	public void setBd_title(String bd_title) {
		this.bd_title = bd_title;
	}
	public String getBd_use() {
		return bd_use;
	}
	public void setBd_use(String bd_use) {
		this.bd_use = bd_use;
	}
	public Date getBd_date() {
		return bd_date;
	}
	public void setBd_date(Date bd_date) {
		this.bd_date = bd_date;
	}
	public String getUser_id() {
		return userid;
	}
	public void setUser_id(String user_id) {
		this.userid = user_id;
	}
}
