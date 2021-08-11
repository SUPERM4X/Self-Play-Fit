package com.java016.playfit.service;


import java.sql.Date;
import java.util.List;

import com.java016.playfit.model.DailyRecord;
import com.java016.playfit.model.User;

public interface DailyRecordService {
	List<DailyRecord> getAllDailyRecordByUser(User user);
	DailyRecord getDailyRecordByUserAndDate(User user,Date date);
	void saveDailyRecord(DailyRecord dailyRecord);
	DailyRecord getDailyRecordByIdWithUserCheck(int id, String username);
	void updateDailyRecordKcalIntakeById(DailyRecord dailyRecord);
}
