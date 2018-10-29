package kr.or.ddit.model;

import java.sql.Clob;
import java.util.Date;

public class CommentsVO {

	private String cm_id;
	private String ps_id;
	private String cm_cnt;
	private Date cm_date;
	private String userId;
	private String cm_del;
	
	public String getCm_del() {
		return cm_del;
	}
	public void setCm_del(String cm_del) {
		this.cm_del = cm_del;
	}
	public String getCm_id() {
		return cm_id;
	}
	public void setCm_id(String cm_id) {
		this.cm_id = cm_id;
	}
	public String getPs_id() {
		return ps_id;
	}
	public void setPs_id(String ps_id) {
		this.ps_id = ps_id;
	}
	public String getCm_cnt() {
		return cm_cnt;
	}
	public void setCm_cnt(String cm_cnt) {
		this.cm_cnt = cm_cnt;
	}
	public Date getCm_date() {
		return cm_date;
	}
	public void setCm_date(Date cm_date) {
		this.cm_date = cm_date;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
}
