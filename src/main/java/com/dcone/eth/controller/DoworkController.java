package com.dcone.eth.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dcone.eth.domain.Dowork;
import com.dcone.eth.service.DoworkService;
import com.dcone.eth.service.TurnCountService;
import com.dcone.eth.util.Encrypt;
import com.dcone.eth.util.EncryptImpl;

/**
 * 做任务业务的控制层
 * 
 * @author xueleic
 *
 */
@RestController
public class DoworkController {

	@Autowired
	private DoworkService doworkService;
	
	@Autowired
	private TurnCountService TurnCountService;
	
	/**
	 * 查看今日签到情况
	 * 
	 * @param do_date
	 * @param accountkey
	 * @return
	 */
	@RequestMapping("/eth/getTodayWork/{do_date}/{accountkey}")
	public String getTodayWork(@PathVariable String do_date,@PathVariable String accountkey){
		List<Dowork> list = doworkService.findTodayWork(do_date, accountkey);
		if(list.size() == 0){
			return "do";
		}else{
			return "did";
		}
	}
	
	/**
	 * 保存今日签到数据
	 * 
	 * @param msg
	 */
	@RequestMapping("/eth/saveTodayWork/{msg}")
	public void save(@PathVariable String msg){
		Dowork dowork  = new Dowork();
		String[] msgArr = msg.split("=");
    	String itcode = msgArr[0];       //itcode
    	String accountkey = msgArr[1];   //以太坊账户
    	String type = msgArr[2];         //做任务类型 0-每日登陆奖励 
    	String date = msgArr[3];         //当前日期
    	
    	dowork.setAccountkey(accountkey);
    	dowork.setDodate(date);
    	dowork.setRemark("每日登陆奖励");
    	dowork.setItcode(itcode);
    	dowork.setType(type);
    	
    	String data = "0x8c735de7b8c388347b7443b492740a9c80df20a6-"+accountkey+"-每日登陆奖励-10.00-root-"+itcode;
    	String s = null;
		try {
			s = URLEncoder.encode(data,"utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Encrypt encrypt = new EncryptImpl();
		String ecUrl = null;
		try {
			ecUrl = encrypt.encrypt(s);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	TurnCountService.transition(ecUrl);
    	
    	doworkService.save(dowork);
	}
}
