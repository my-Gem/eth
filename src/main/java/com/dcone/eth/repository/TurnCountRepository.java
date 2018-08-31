package com.dcone.eth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.dcone.eth.domain.TurnCountBean;

import java.lang.Integer;
import java.lang.String;
import java.util.List;

public interface TurnCountRepository extends JpaRepository<TurnCountBean, Integer> {
	
	List<TurnCountBean> findByFromcountOrderByTurndateDesc(String fromcount);
	List<TurnCountBean> findByTocountOrderByTurndateDesc(String tocount);
	
	@Query("UPDATE TurnCountBean SET turnhash=?2 WHERE fromcount=?1 AND turndate=?3")
	@Modifying
	void updateTurnhash(String fromcount, String turnhash,String turndate);
	
	List<TurnCountBean> findByTurnhashNotNullAndFlagIsNull();
	
	@Query("UPDATE TurnCountBean SET flag=?3,timer=?2 WHERE turnhash=?1")
	@Modifying
	void updateStatue(String turnhash,String timer,Integer flag);
	
	List<TurnCountBean> findByFromcountOrTocountOrderByTurndateDesc(String fromcount,String tocount);
}
