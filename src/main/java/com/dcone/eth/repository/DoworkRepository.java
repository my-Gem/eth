package com.dcone.eth.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dcone.eth.domain.Dowork;
import java.lang.String;


/**
 * 做任务业务的持久层
 * 
 * @author xueleic
 *
 */
public interface DoworkRepository extends JpaRepository<Dowork, Integer>{

	List<Dowork> findByDodateAndAccountkey(String dodate,String accountkey);

	
}
