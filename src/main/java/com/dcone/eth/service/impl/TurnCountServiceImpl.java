package com.dcone.eth.service.impl;


import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetBalance;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.http.HttpService;

import com.dcone.eth.domain.TurnCountBean;
import com.dcone.eth.repository.TurnCountRepository;
import com.dcone.eth.service.TurnCountService;
import com.dcone.eth.util.Encrypt;
import com.dcone.eth.util.EncryptImpl;


@Service("turnCountService")
@Transactional
public class TurnCountServiceImpl implements TurnCountService {

	@Autowired
	private TurnCountRepository turnCountRepository;
	

    private volatile static Web3j web3j;
    private static String ip = "";
    private static String[] ipArr = {"http://10.7.10.124:8545","http://10.7.10.125:8545"};
    
	@Override
	public void save(TurnCountBean turnCountBean) {
		turnCountRepository.save(turnCountBean);
		
	}

	@Override
	public List<TurnCountBean> findAll(String fromcount,String tocount) {
		List<TurnCountBean> list = turnCountRepository.findByFromcountOrTocountOrderByTurndateDesc(fromcount, tocount);
		return list;
	}

	@Override
	public List<TurnCountBean> findByFromcount(String fromcount) {
		List<TurnCountBean> findByFromcount = turnCountRepository.findByFromcountOrderByTurndateDesc(fromcount);
		return findByFromcount;
	}

	@Override
	public List<TurnCountBean> findByTocount(String tocount) {
		List<TurnCountBean> findByTocount = turnCountRepository.findByTocountOrderByTurndateDesc(tocount);
		return findByTocount;
	}

	@Override
	public void updateTurnhash(String fromcount, String turnhash, String turndate) {
		turnCountRepository.updateTurnhash(fromcount, turnhash, turndate);
	}

	@Override
	public String getBalance(String account) {
		Integer index = (int)(Math.random()*2);
    	ip = ipArr[index];
		System.err.println("得到余额时以太坊链接的ip为"+ip);
		if(web3j==null){
            synchronized (TurnCountService.class){
                if(web3j==null){
                    web3j =Web3j.build(new HttpService(ip));
                }
            }
        }
        EthGetBalance send = null;
		try {
			send = web3j.ethGetBalance(account,DefaultBlockParameter.valueOf("latest")).send();
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("获取账户余额时得到send对象时发生的异常为:"+e.toString());
		}
        BigDecimal balance = new BigDecimal(send.getBalance().divide(new BigInteger("100000000000")).toString());
        BigDecimal nbalance = balance.divide(new BigDecimal("100000"),8,BigDecimal.ROUND_DOWN);
        
        return nbalance.toString();
	}

	@Override
	public String transition(String msg) {
		Encrypt encrypt = new EncryptImpl();
    	String decrypt = null;
		try {
			decrypt = encrypt.decrypt(msg);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.err.println("解密转账信息时发生的异常为:"+e1.toString());
		}
    	String data = null;
		try {
			data = URLDecoder.decode(decrypt, "utf-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("url解码转账信息时发生的异常为:"+e.toString());
		}
    	System.err.println(data);
    	// 获取转账参数
    	String[] arr = data.split("-");
    	String fromcount = arr[0];// 转账人
    	String tocount = arr[1];  //收账人
    	String remark = arr[2];   // 备注
    	String fromitcode = arr[4];
    	String toitcode = arr[5];
    	Double money = (Double.parseDouble(arr[3]))*10000000000000000L;
    	BigDecimal bigDecimal = new BigDecimal(money);// 转账金额
    	
    	 // 获取当前时间
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = format.format(date);
		
		TurnCountBean turnCountBean = new TurnCountBean(null, fromcount, tocount, Double.parseDouble(arr[3]), null, dateStr, null, null, remark, fromitcode, toitcode, null,null);
		turnCountRepository.save(turnCountBean);  // 保存转账基本信息
		
		
		/* 转账操作 */
		Integer index = (int)(Math.random()*2);
    	ip = ipArr[index];
		System.err.println("转账时以太坊链接的ip为"+ip);
		if(web3j==null){
            synchronized (TurnCountService.class){
                if(web3j==null){
                    web3j =Web3j.build(new HttpService(ip));
                }
            }
        }
		
		// 解锁转账人账户
    	Admin admin = Admin.build(new HttpService(ip));
    	try{
    		@SuppressWarnings("unused")
			PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(fromcount, "mini0823").send();
    	}catch(Exception e){
    		System.err.println(e.toString());
    		System.err.println(ip);
    	}
    	// 获取转账随机数,对于每个账户转账随机数从0开始依次执行,前面有随机数未执行的,则改随机数对应的转账会先在队列里面不执行
    	EthGetTransactionCount ethGetTransactionCount = null;
		try {
			ethGetTransactionCount = web3j.ethGetTransactionCount(
			        fromcount, DefaultBlockParameterName.LATEST).sendAsync().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        BigInteger nonce = ethGetTransactionCount.getTransactionCount();
        
        // 转账
    	Transaction transaction = Transaction.createEtherTransaction(fromcount, nonce, BigInteger.valueOf(22000000000L), BigInteger.valueOf(22000), tocount, bigDecimal.toBigInteger());
    	EthSendTransaction ethSendTransaction = null;
		try {
			ethSendTransaction = admin.ethSendTransaction(transaction).sendAsync().get();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	// 获取转账的hash值
    	String hash = ethSendTransaction.getTransactionHash();
    	
    	turnCountRepository.updateTurnhash(fromcount, hash, dateStr);
    	
		return hash;
	}

	@Override
	public void updateStatue(String turnhash, String timer, Integer flag) {
		turnCountRepository.updateStatue(turnhash, timer, flag);
	}


}
