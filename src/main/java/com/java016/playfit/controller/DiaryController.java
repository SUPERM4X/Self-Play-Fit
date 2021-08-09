package com.java016.playfit.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jca.work.WorkManagerTaskExecutor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.java016.playfit.model.DailyRecord;
import com.java016.playfit.model.Food;
import com.java016.playfit.model.Meal;
import com.java016.playfit.model.TimePeriod;
import com.java016.playfit.model.User;
import com.java016.playfit.service.DailyRecordService;
import com.java016.playfit.service.FitAchieveService;
import com.java016.playfit.service.FoodService;
import com.java016.playfit.service.MealService;
import com.java016.playfit.service.TimePeriodService;
import com.java016.playfit.service.UserService;


@Controller
public class DiaryController {
	
	
	@Autowired
	UserService userService;
	@Autowired
	DailyRecordService dailyRecordService;
	@Autowired
	FitAchieveService fitAchieveService;
	@Autowired
	TimePeriodService timePeriodService;
	@Autowired
	FoodService foodService;
	@Autowired
	MealService mealService;
	
	
	@RequestMapping("/diary_add_update")
	public ModelAndView diary_add_update(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		
		java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		String email = currentLogInUsername();
		User user = userService.getUserByEmail(email);
				
		
		DailyRecord todayDailyRecord = dailyRecordService.getDailyRecordByUserAndDate(user, sqlDate);
		if(todayDailyRecord != null && todayDailyRecord.getStatus() == 0) {
			
		}
		if(todayDailyRecord == null) {
			todayDailyRecord = new DailyRecord();
			todayDailyRecord.setUser(user);
			todayDailyRecord.setCreatedDate(sqlDate);
		}
		todayDailyRecord.setStatus(0);
		session.setAttribute("todayDailyRecord", todayDailyRecord);
		List<TimePeriod> timePeriods = timePeriodService.getAllTimePeriod();
		List<Food> foods = foodService.getAllFood();
		List<Meal> meals = todayDailyRecord.getMeals();
		
		mv.addObject("meals", meals);
		mv.addObject("foods",foods);
		mv.addObject("todayDailyRecord",todayDailyRecord);
		mv.addObject("timePeriods",timePeriods);
		mv.setViewName("addDiary_form");
		return mv;
	}
	
	@PostMapping("/processDiaryUpdate")
	public String processDiaryUpdate(@RequestParam(required=false,name="mealHidden") String[] timePeriodIdFoodIdArray
									,@RequestParam(required=false,name="deleteMealHidden") String[]	deleteMealHiddenId
									,DailyRecord todayDailyRecord,HttpSession session
									,Principal principal) {
		
		DailyRecord tempTodayDailyRecord = (DailyRecord) session.getAttribute("todayDailyRecord");
		todayDailyRecord.setId(tempTodayDailyRecord.getId());
		todayDailyRecord.setUser(tempTodayDailyRecord.getUser());
		todayDailyRecord.setStatus(1);
		todayDailyRecord.setCreatedDate(tempTodayDailyRecord.getCreatedDate());
		
		session.removeAttribute("todayDailyRecord");
		dailyRecordService.saveDailyRecord(todayDailyRecord);
		
		
		
		//LinkedList才支援remove方法,就算Arrays.asList轉成List也不能用remove方法
		List<String> timePeriodIdFoodIdList = new LinkedList<String>(Arrays.asList(timePeriodIdFoodIdArray));
		
	
		//如果沒有新增任何食物
		if(timePeriodIdFoodIdList.size() == 1) {
			System.out.println("timePeriodIdFoodIdArray == null");
		}else {
			//如果有新增任何食物
			System.out.println("timePeriodIdFoodIdArray = " + Arrays.toString(timePeriodIdFoodIdArray));
			timePeriodIdFoodIdList.remove(timePeriodIdFoodIdList.size()-1);
			timePeriodIdFoodIdList.forEach(timePeriodIdFoodId -> {
				String[] s = timePeriodIdFoodId.split(",");
				TimePeriod timePeriod = timePeriodService.getTimePeriodById(Integer.parseInt(s[0]));
				Food food = foodService.getFoodById(Integer.parseInt(s[1]));
				Meal meal = new Meal();
				meal.setDailyRecord(todayDailyRecord);
				meal.setFood(food);
				meal.setTimePeriod(timePeriod);
				meal.setQuantity(1);
				int totalKcal = (int) (meal.getQuantity()*food.getKcal());
				meal.setTotalKcal(totalKcal);
				mealService.saveMeal(meal);
			});
			
		}
		
		//如果有要刪除飲食紀錄
		if(deleteMealHiddenId != null) {
			for (String id: deleteMealHiddenId) {  
			    mealService.deleteMeal(Integer.parseInt(id), principal.getName());
			}
		}
		
		return "redirect:/index";
	}
	
	@RequestMapping("/diary_check")
	public ModelAndView diary_check() {

		ModelAndView mv = new ModelAndView();
		
		java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		String email = currentLogInUsername();
		User user = userService.getUserByEmail(email);

		
		
		DailyRecord todayDailyRecord = dailyRecordService.getDailyRecordByUserAndDate(user, sqlDate);
		List<DailyRecord> dailyRecords = dailyRecordService.getAllDailyRecordByUser(user);

		boolean isDiary = true;
		List<Meal> todayMeals = null;
		if(todayDailyRecord != null && todayDailyRecord.getStatus() == 0) {
			isDiary = false;
			todayMeals = todayDailyRecord.getMeals();
		}
		if(todayDailyRecord == null) {
			isDiary = false;
		}
		
		System.out.println("todayMeals" + todayMeals);
		
		mv.addObject("todayMeals", todayMeals);
		mv.addObject("isDiary",isDiary);
		mv.addObject("dailyRecords",dailyRecords);
		mv.addObject("todayDailyRecord",todayDailyRecord);
		mv.setViewName("diary");
		return mv;
	}
	@GetMapping("/showDailyRecord/{id}")
	public ModelAndView showDailyRecord(@PathVariable(value = "id") int id, Principal principal, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		DailyRecord dailyRecord = dailyRecordService.getDailyRecordByIdWithUserCheck(id, principal.getName());
		List<Meal> todayMeals = dailyRecord.getMeals();
		mv.addObject("dailyRecord", dailyRecord);
		mv.addObject("todayMeals", todayMeals);
		mv.setViewName("showDailyRecord");
		return mv;
	}
	
	//取得目前登入的用戶名稱
	public String currentLogInUsername() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String username;
		if (principal instanceof UserDetails) {
		  username = ((UserDetails)principal).getUsername();
		} else {
		  username = principal.toString();
		}
		return username;
	}
	
	public void printAllSessionAttribute(HttpSession session) {
		 // Get all key values ​​in session
		Enumeration<?> enumeration = session.getAttributeNames();
		 // traverse enumeration
		while (enumeration.hasMoreElements()) {
		     // Get the attribute name of the session
		  String name = enumeration.nextElement().toString();
		     // Take the value in the session based on the key value
		  Object value = session.getAttribute(name);
		     // print the result
		  System.out.println("name:" + name + ",value:" + value );
		}
	}
}
