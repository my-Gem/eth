package com.dcone.eth.service;

import java.util.List;

import com.dcone.eth.domain.TurnCountBean;



public interface TurnCountService {

	/**
	 * 保存转账基本数据
	 * 
	 * @param turnCountBean
	 */
	public abstract void save(TurnCountBean turnCountBean);
	
	/**
	 * 查询所有转账数据
	 * 
	 * @return
	 */
	public abstract List<TurnCountBean> findAll(String fromcount,String tocount);
	
	/**
	 * 根据转账人账户查询明细
	 * 
	 * @param fromcount
	 * @return
	 */
	public abstract List<TurnCountBean> findByFromcount(String fromcount);
	
	/**
	 * 根据收账人账户查询明细
	 * 
	 * @param tocount
	 * @return
	 */
	public abstract List<TurnCountBean> findByTocount(String tocount);
	
	/**
	 * 更新转账hash
	 * 
	 * @param fromcount
	 * @param turnhash
	 * @param turndate
	 * @return
	 */
	public abstract void updateTurnhash(String fromcount,String turnhash,String turndate);
	
	
	/**
	 * 查询账户余额
	 * 
	 * @param account
	 * @return 账户余额
	 */
	public abstract String getBalance(String account);
	
	/**
	 * 转账
	 * 
	 * @param msg 转账信息:转账人以太坊账户-收账人以太坊账户-备注-金额-转账人itcode-收账人itcode
	 * @return 此笔交易的hash值
	 */
	public abstract String transition(String msg);
	
	/**
	 * 更新交易状态
	 * 
	 * @param turnhash
	 * @param timer
	 * @param flag
	 */
	public abstract void updateStatue(String turnhash,String timer,Integer flag);
	
	
	
}
