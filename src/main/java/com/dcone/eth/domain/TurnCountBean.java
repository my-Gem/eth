package com.dcone.eth.domain;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 转账数据的javabean
 * 
 * @author xueleic
 */
@Entity
@Table(name="uf_turncount")
public class TurnCountBean {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	private Integer id;
	private String fromcount;
	private String tocount;
	private Double value;
	private Double gas;
	private String turndate;
	private Integer flag;
	private Integer requestid;
	private String remark;
	private String fromitcode;
	private String toitcode;
	private String turnhash;
	private String timer;
	public TurnCountBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TurnCountBean(Integer id, String fromcount, String tocount, Double value, Double gas, String turndate,
			Integer flag, Integer requestid, String remark, String fromitcode, String toitcode, String turnhash,
			String timer) {
		super();
		this.id = id;
		this.fromcount = fromcount;
		this.tocount = tocount;
		this.value = value;
		this.gas = gas;
		this.turndate = turndate;
		this.flag = flag;
		this.requestid = requestid;
		this.remark = remark;
		this.fromitcode = fromitcode;
		this.toitcode = toitcode;
		this.turnhash = turnhash;
		this.timer = timer;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFromcount() {
		return fromcount;
	}
	public void setFromcount(String fromcount) {
		this.fromcount = fromcount;
	}
	public String getTocount() {
		return tocount;
	}
	public void setTocount(String tocount) {
		this.tocount = tocount;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public Double getGas() {
		return gas;
	}
	public void setGas(Double gas) {
		this.gas = gas;
	}
	public String getTurndate() {
		return turndate;
	}
	public void setTurndate(String turndate) {
		this.turndate = turndate;
	}
	public Integer getFlag() {
		return flag;
	}
	public void setFlag(Integer flag) {
		this.flag = flag;
	}
	public Integer getRequestid() {
		return requestid;
	}
	public void setRequestid(Integer requestid) {
		this.requestid = requestid;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getFromitcode() {
		return fromitcode;
	}
	public void setFromitcode(String fromitcode) {
		this.fromitcode = fromitcode;
	}
	public String getToitcode() {
		return toitcode;
	}
	public void setToitcode(String toitcode) {
		this.toitcode = toitcode;
	}
	public String getTurnhash() {
		return turnhash;
	}
	public void setTurnhash(String turnhash) {
		this.turnhash = turnhash;
	}
	public String getTimer() {
		return timer;
	}
	public void setTimer(String timer) {
		this.timer = timer;
	}
	
}
