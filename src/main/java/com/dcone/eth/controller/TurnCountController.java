package com.dcone.eth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dcone.eth.domain.TurnCountBean;
import com.dcone.eth.service.TurnCountService;


/**
 * 转账业务的控制层
 * 
 * @author xueleic
 *
 */
@RestController
@Scope("prototype")
public class TurnCountController {

	@Autowired
	private TurnCountService turnCountService;
	
	/**
	 * 查询所有明细
	 * 
	 * @return
	 */
	@RequestMapping("/eth/findAll/{account}")
	public List<TurnCountBean> findAll(@PathVariable String account){
		List<TurnCountBean> list = turnCountService.findAll(account,account);
		return list;
	}
	
	/**
	 * 查询支出明细
	 * 
	 * @param fromcount
	 * @return
	 */
	@RequestMapping("/eth/findByFromcount/{fromcount}")
	public List<TurnCountBean> findByFromcount(@PathVariable String fromcount){
		List<TurnCountBean> list = turnCountService.findByFromcount(fromcount);
		return list;
	}
	
	/**
	 * 查询收入明细
	 * 
	 * @param tocount
	 * @return
	 */
	@RequestMapping("/eth/findByTocount/{tocount}")
	public List<TurnCountBean> findByTocount(@PathVariable String tocount){
		List<TurnCountBean> list = turnCountService.findByTocount(tocount);
		return list;
	}
	
	/**
	 * 更新转账hash
	 * 
	 * @param fromcount
	 * @param turnhash
	 * @param turndate
	 */
	@RequestMapping("/eth/updateTurnhash/{fromcount}/{turnhash}/{turndate}")
	public void updateTurnhash(@PathVariable String fromcount, @PathVariable String turnhash, @PathVariable String turndate){
		turnCountService.updateTurnhash(fromcount, turnhash, turndate);
	}
	
	/**
	 * 获取账户余额
	 * 
	 * @param account
	 * @return
	 */
	 @RequestMapping("/eth/getBalance/{account}")
	 public  String getBalance(@PathVariable String account) {
		 String balance = turnCountService.getBalance(account);
		 return balance;
	 }
	 
	 /**
	  * 转账
	  * 
	  * @param msg
	  * @return
	  */
	 @RequestMapping("/eth/transition/{msg}")
	 public  String transition(@PathVariable String msg){
		 String hash = turnCountService.transition(msg);
		 return hash;
	 }
	 
}
