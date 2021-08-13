package com.java016.playfit.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.IntToDoubleFunction;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.jca.work.WorkManagerTaskExecutor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.java016.playfit.dao.DailyRecordRepository;
import com.java016.playfit.model.DailyRecord;
import com.java016.playfit.model.FitAchieve;
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
	@Autowired
	DailyRecordRepository dailyRecordRepo;
	
	//修改或新增日記的表單頁面
	@RequestMapping("/diary_add_update")
	public ModelAndView diary_add_update(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		
		//登入的使用者帳號(電子信箱)
		String email = userService.getCurrentLogInUsername();
		//用帳號抓出此用戶的Entity
		User user = userService.getUserByEmail(email);

		//抓出今天的日期
		java.util.Date utilDate = new java.util.Date();
		//把日期轉成SQL型態的Date
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		//取出目前用戶今天的日常紀錄
		DailyRecord todayDailyRecord = dailyRecordService.getDailyRecordByUserAndDate(user, sqlDate);
		List<FitAchieve> fitAchieves = null;
		//如果目前用戶沒有今天的日常紀錄
		if(todayDailyRecord == null) {
			//new一個日常紀錄的物件
			todayDailyRecord = new DailyRecord();
			//擁有者設為目前用戶
			todayDailyRecord.setUser(user);
			//日期設今天
			todayDailyRecord.setCreatedDate(sqlDate);
			//狀態設為0
			todayDailyRecord.setStatus(0);
		}
		else {
			fitAchieves = fitAchieveService.getAllFitAchieveByDailyRecordAndStatus(todayDailyRecord, "按計畫執行");
		}
		System.out.println(fitAchieves);
		//此日常紀錄存在session裡面
		session.setAttribute("todayDailyRecord", todayDailyRecord);
		//取出全部的飲食時段
		List<TimePeriod> timePeriods = timePeriodService.getAllTimePeriod();
		//取出全部的食物品項
		List<Food> foods = foodService.getAllFood();
		//取出今天的日常紀錄裡的所有飲食紀錄
		List<Meal> meals = todayDailyRecord.getMeals();
		
		mv.addObject("fitAchieves",fitAchieves);
		mv.addObject("meals", meals);
		mv.addObject("foods",foods);
		mv.addObject("todayDailyRecord",todayDailyRecord);
		mv.addObject("timePeriods",timePeriods);
		mv.setViewName("addDiary_form");
		return mv;
	}
	
	@PostMapping("/processDiaryUpdate")
	public String processDiaryUpdate(@RequestParam(required=false,name="mealHidden") String[] timePeriodIdsFoodIdsForUpdate
									,@RequestParam(required=false,name="deleteMealHidden") String[]	mealIdsForDelete
									,DailyRecord todayDailyRecord,HttpSession session
									,Principal principal) {
		
		DailyRecord tempTodayDailyRecord = (DailyRecord) session.getAttribute("todayDailyRecord");
		tempTodayDailyRecord.setTitle(todayDailyRecord.getTitle());
		tempTodayDailyRecord.setContent(todayDailyRecord.getContent());
		tempTodayDailyRecord.setStatus(1);

		
		session.removeAttribute("todayDailyRecord");
		
		//存成日記
		dailyRecordService.saveDailyRecord(tempTodayDailyRecord);
		//新增或刪除用餐紀錄
		dailyRecordService.updateDailyRecordAndMeal(tempTodayDailyRecord, timePeriodIdsFoodIdsForUpdate, mealIdsForDelete, principal.getName());
		
//		//LinkedList才支援remove方法,就算Arrays.asList轉成List也不能用remove方法
//		List<String> timePeriodIdFoodIdList = new LinkedList<String>(Arrays.asList(timePeriodIdFoodIdArray));
//		
//	
//		//如果沒有新增任何用餐紀錄 List裡面會是 ["last"]
//		if(timePeriodIdFoodIdList.size() == 1) {
//			System.out.println("timePeriodIdFoodIdArray == null");
//		}
//		//如果有新增任何用餐紀錄 假設新增兩組用餐紀錄 List裡面會是 ["時段id,食物id","時段id,食物id","last"]
//		else {
//			System.out.println("timePeriodIdFoodIdArray = " + Arrays.toString(timePeriodIdFoodIdArray));
//			//把List的最後面的元素"last"移除 List裡面會變成 ["時段id,食物id","時段id,食物id"]
//			timePeriodIdFoodIdList.remove(timePeriodIdFoodIdList.size()-1);
//			//List裡面會是 ["時段id,食物id","時段id,食物id"]
//			//循環List 每次抓出一組字串 "時段id,食物id"
//			timePeriodIdFoodIdList.forEach(timePeriodIdFoodId -> {
//				// 用split "時段id,食物id" 把時段id與食物id分開
//				String[] s = timePeriodIdFoodId.split(",");
//				//用時段id去資料庫取出時段的Entity
//				TimePeriod timePeriod = timePeriodService.getTimePeriodById(Integer.parseInt(s[0]));
//				//用食物id去資料庫取出食物的Entity
//				Food food = foodService.getFoodById(Integer.parseInt(s[1]));
//				
//				//new 用餐紀錄的物件
//				//設定時段,設定食物,設定數量
//				Meal meal = new Meal();
//				meal.setDailyRecord(tempTodayDailyRecord);
//				meal.setFood(food);
//				meal.setTimePeriod(timePeriod);
//				meal.setQuantity(1);
//				// 食物kacal * 數量 = 此次用餐的攝取量
//				int totalKcal = (int) (meal.getQuantity()*food.getKcal());
//				meal.setTotalKcal(totalKcal);
//				//把以上設定存入資料庫
//				mealService.saveMeal(meal);
//			});
//			
//		}
//		
//		System.out.println("------------Start------------");
//		//如果有要刪除飲食紀錄
//		if(deleteMealHiddenId != null) {
//			for (String id: deleteMealHiddenId) {  
//			    mealService.deleteMeal(Integer.parseInt(id), principal.getName());
//			}
//		}
//		System.out.println("------------End------------");
		
		//因為可能會新增或刪除用餐紀錄 所以要更新日常紀錄的卡路里
		dailyRecordService.updateDailyRecordKcalIntake(tempTodayDailyRecord);
		return "redirect:/index";
	}
	
	//日記的首頁
	@RequestMapping("/diary_homepage/{pageNumber}")
	public ModelAndView diary_homepage(@PathVariable("pageNumber") int currentPage) {

		ModelAndView mv = new ModelAndView();
		
		//登入的使用者帳號(電子信箱)
		String email = userService.getCurrentLogInUsername();
		//用帳號抓出此用戶的Entity
		User user = userService.getUserByEmail(email);

		//抓出今天的日期
		java.util.Date utilDate = new java.util.Date();
		//把日期轉成SQL型態的Date
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        //取出目前用戶今天的日常紀錄
		DailyRecord todayDailyRecord = dailyRecordService.getDailyRecordByUserAndDate(user, sqlDate);
		
		//用戶今天的日常紀錄是否已經成為日記
		boolean isDiary = true;

		//如果今天的日常紀錄Status欄位為0的話，代表此日常紀錄不為日記
		if(todayDailyRecord != null && todayDailyRecord.getStatus() == 0) {
			//設成否
			isDiary = false;

		}//或是根本沒有抓到今天的日常紀錄
		if(todayDailyRecord == null) {
			//設成否
			isDiary = false;
		}
		
		//取出目前用戶全部的日常紀錄
		Page<DailyRecord> page = dailyRecordService.getAllDailyRecordByUserAndPage(user,currentPage);
		List<DailyRecord> dailyRecords = page.getContent();
		long totalItems = page.getTotalElements();
		int totalPages = page.getTotalPages();
		
		mv.addObject("currentPage", currentPage);
		mv.addObject("totalItems", totalItems);
		mv.addObject("totalPages", totalPages);
		mv.addObject("isDiary",isDiary);
		mv.addObject("dailyRecords",dailyRecords);
		mv.addObject("todayDailyRecord",todayDailyRecord);
		mv.setViewName("diary");
		return mv;
	}
	//某一天的日記頁面
	@GetMapping("/showDailyRecord/{id}")
	public ModelAndView showDailyRecord(@PathVariable(value = "id") int id, Principal principal, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		DailyRecord dailyRecord = dailyRecordService.getDailyRecordByIdWithUserCheck(id, principal.getName());
		List<Meal> todayMeals = dailyRecord.getMeals();
		List<FitAchieve> fitAchieves = fitAchieveService.getAllFitAchieveByDailyRecordAndStatus(dailyRecord, "按計畫執行");
		mv.addObject("fitAchieves",fitAchieves);
		mv.addObject("dailyRecord", dailyRecord);
		mv.addObject("todayMeals", todayMeals);
		mv.setViewName("showDailyRecord");
		return mv;
	}
	 
	@GetMapping(value = "/test")
	  public ModelAndView index(ModelAndView mv) {
	    mv.setViewName("test");
	    return mv;
	  }
	
	@RequestMapping("/jsTest")
    public @ResponseBody Map<String, Object> jsTest(@RequestBody  Map<String, Object> json) {
		System.out.println("Controller jsonTest Started");
		System.out.println(json.get("timePeriod"));
		System.out.println(json.get("food"));
        return json;
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
