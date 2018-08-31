package com.dcone.eth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dcone.eth.domain.Dowork;
import com.dcone.eth.repository.DoworkRepository;
import com.dcone.eth.service.DoworkService;

@Service("doworkService")
@Transactional
public class DoworkServiceImpl implements DoworkService {

	@Autowired
	private DoworkRepository doworkRepository;

	@Override
	public List<Dowork> findTodayWork(String dodate, String accountkey) {
		List<Dowork> list = doworkRepository.findByDodateAndAccountkey(dodate, accountkey);
		return list;
	}

	@Override
	public void save(Dowork dowork) {
		doworkRepository.save(dowork);
	}
	


}
