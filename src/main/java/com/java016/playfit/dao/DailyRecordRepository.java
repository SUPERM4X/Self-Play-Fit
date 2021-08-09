package com.java016.playfit.dao;


import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.java016.playfit.model.DailyRecord;
import com.java016.playfit.model.User;


public interface DailyRecordRepository extends JpaRepository<DailyRecord, Integer>{
	List<DailyRecord> findAllByUser(User user);
	
	List<DailyRecord> findAllByUserOrderByCreatedDateDesc(User user);
	
	@Query(value = "select * from daily_record d where d.created_date = :date AND d.user_id = :userId", nativeQuery=true)
	DailyRecord findByUserIdAndDate(@Param("userId")Integer userId,@Param("date")Date date);
	
	

}
