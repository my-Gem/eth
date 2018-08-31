package com.dcone.eth.job;

import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.http.HttpService;

import com.dcone.eth.domain.TurnCountBean;
import com.dcone.eth.repository.TurnCountRepository;
import com.dcone.eth.service.TurnCountService;

/**
 * 更新转账的状态
 * 
 * @author xueleic
 *
 */
@Component
public class UpdateTurnResultStatusJob {
	@Autowired
	private TurnCountRepository turnCountRepository;
	@Autowired
	private TurnCountService turnCountService;
	private static String ip = "";
	private static String[] ipArr = {"http://10.7.10.124:8545","http://10.7.10.125:8545"};
	
	@Scheduled(fixedRate=20000)
	public void updateTurnResultStatusJob(){
		Integer index = (int)(Math.random()*2);
    	ip = ipArr[index];
		System.err.println("更新交易状态时以太坊链接的ip为"+ip);
		
		Admin admin = Admin.build(new HttpService(ip));
		
		List<TurnCountBean> list = turnCountRepository.findAll();
		EthTransaction ethTransaction = null;
		String hash = "";  // 交易的hash值
		for (TurnCountBean turnCountBean : list) {
			if(turnCountBean == null){
				continue;
			}
			hash = turnCountBean.getTurnhash();
			if(hash == null){
				continue;
			}
			try {
				ethTransaction = admin.ethGetTransactionByHash(hash).sendAsync().get();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			org.web3j.protocol.core.methods.response.Transaction result = ethTransaction.getResult();
			if(result == null){
				System.err.println(hash);
				continue;
			}
	    	String blockHash = result.getBlockHash();
	    	System.err.println("blockHash为"+blockHash);
	    	if(!blockHash.contains("0000000000000000000")){
	    		turnCountService.updateStatue(hash, "2", 2);
	    	}
		}
	}
}
