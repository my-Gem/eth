package com.dcone.eth.service;

import java.util.List;

import com.dcone.eth.domain.Dowork;

/**
 * 做任务业务的业务层
 * 
 * @author xueleic
 *
 */
public interface DoworkService {

	/**
	 * 查看今日签到情况
	 * 
	 * @param dodate
	 * @param accountkey
	 * @return
	 */
	public abstract List<Dowork> findTodayWork(String dodate,String accountkey);
	
	/**
	 * 保存今日签到数据
	 * 
	 * @param dowork
	 */
	public abstract void save(Dowork dowork);
}
