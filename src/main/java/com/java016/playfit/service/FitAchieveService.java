package com.java016.playfit.service;

import java.util.List;

import com.java016.playfit.model.DailyRecord;
import com.java016.playfit.model.FitAchieve;

public interface FitAchieveService {
	List<FitAchieve> getAllFitAchieveByDailyRecord(DailyRecord dailyRecord);
}
