package com.java016.playfit.service;


import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Page;

import com.java016.playfit.model.DailyRecord;
import com.java016.playfit.model.FitAchieve;
import com.java016.playfit.model.User;

public interface DailyRecordService {
	Page<DailyRecord> getAllDailyRecordByUserAndPage(User user,int pageNumber);
	DailyRecord getDailyRecordByUserAndDate(User user,Date date);
	void saveDailyRecord(DailyRecord dailyRecord);
	DailyRecord getDailyRecordByIdWithUserCheck(int id, String username);
	void updateDailyRecordKcalIntake(DailyRecord dailyRecord);
	void updateDailyRecordAndMeal(DailyRecord dailyRecord,String[] timePeriodIdsFoodIdsForUpdate,
									String[] mealIdsForDelete,String username);
	void updateDailyRecordKcalBurned(DailyRecord dailyRecord,FitAchieve fitAchieve);
}
