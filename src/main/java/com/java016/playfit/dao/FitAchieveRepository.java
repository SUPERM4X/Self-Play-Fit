package com.java016.playfit.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java016.playfit.model.DailyRecord;
import com.java016.playfit.model.FitAchieve;

public interface FitAchieveRepository extends JpaRepository<FitAchieve, Integer> {
	List<FitAchieve> findAllByDailyRecordAndStatus(DailyRecord dailyRecord,String status);
}
