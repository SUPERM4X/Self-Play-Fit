package com.java016.playfit.serviceimpl;

import java.sql.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.java016.playfit.dao.DailyRecordRepository;
import com.java016.playfit.dao.UserRepository;
import com.java016.playfit.model.DailyRecord;
import com.java016.playfit.model.Meal;
import com.java016.playfit.model.User;
import com.java016.playfit.service.DailyRecordService;

@Service
public class DailyRecordServiceImpl implements DailyRecordService {
	
	@PersistenceContext
    private EntityManager entityManager;
	@Autowired
	DailyRecordRepository dailyRecordRepo;
	@Autowired
	UserRepository userRepo;

	@Override
	public List<DailyRecord> getAllDailyRecordByUser(User user) {
		List<DailyRecord> dailyRecord = dailyRecordRepo.findAllByUserOrderByCreatedDateDesc(user);
		
		return dailyRecord;
	}

	@Override
	public DailyRecord getDailyRecordByUserAndDate(User user, Date date) {
		DailyRecord dailyRecord = dailyRecordRepo.findByUserIdAndDate(user.getId(), date);
		return dailyRecord;
	}

	@Override
	public void saveDailyRecord(DailyRecord dailyRecord) {
		dailyRecordRepo.save(dailyRecord);
		
	}

	@Override
	public DailyRecord getDailyRecordByIdWithUserCheck(int id, String username) {
		DailyRecord dailyRecord = dailyRecordRepo.getById(id);
		
		if(dailyRecord == null) {
			throw new AccessDeniedException("user attempted to access unexisting daily record.");
		}
		
		User user = dailyRecord.getUser();
		if(!user.getEmail().equals(username)) {
			System.out.println(user.getEmail());
			System.out.println(username);
			throw new AccessDeniedException("user attempted to access another user's daily record.");
		}
		
		return dailyRecord;
	}

	@Override
	public void updateDailyRecordKcalIntakeById(DailyRecord dailyRecord) {
		int id = dailyRecord.getId();
		entityManager.detach(dailyRecord);
		dailyRecord = dailyRecordRepo.getById(id);
		
		List<Meal> meals = dailyRecord.getMeals();
		int kcal = 0;
		System.out.println("---------------"+dailyRecord.getUser().getEmail());
		System.out.println("---------------"+dailyRecord.getMeals());
		
		if(meals != null) {
			for (Meal meal : meals) {
				System.out.println(meal.getId());
				kcal += meal.getTotalKcal();
			}
		}
		dailyRecord.setKcalIntake(kcal);
		dailyRecordRepo.save(dailyRecord);
	}

}
