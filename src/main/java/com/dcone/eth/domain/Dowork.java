package com.dcone.eth.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 做任务实体类
 * 
 * @author xueleic
 *
 */
@Entity
@Table(name="dowork")
public class Dowork {
	
	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	private String itcode;
	private String accountkey;
	@Column(name="do_date")
	private String dodate;
	private String type;
	private String remark;
	public Dowork() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Dowork(Integer id, String itcode, String accountkey, String dodate, String type, String remark) {
		super();
		this.id = id;
		this.itcode = itcode;
		this.accountkey = accountkey;
		this.dodate = dodate;
		this.type = type;
		this.remark = remark;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getItcode() {
		return itcode;
	}
	public void setItcode(String itcode) {
		this.itcode = itcode;
	}
	public String getAccountkey() {
		return accountkey;
	}
	public void setAccountkey(String accountkey) {
		this.accountkey = accountkey;
	}
	public String getDodate() {
		return dodate;
	}
	public void setDodate(String dodate) {
		this.dodate = dodate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
