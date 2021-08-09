package com.java016.playfit.controller;

import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.java016.playfit.dao.DailyRecordRepository;
import com.java016.playfit.model.Avatar;
import com.java016.playfit.model.DailyRecord;
import com.java016.playfit.model.Food;
import com.java016.playfit.model.TimePeriod;
import com.java016.playfit.model.User;
import com.java016.playfit.service.DailyRecordService;
import com.java016.playfit.service.FitAchieveService;
import com.java016.playfit.service.FoodService;
import com.java016.playfit.service.TimePeriodService;
import com.java016.playfit.service.UserService;

@Controller
public class HomeController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/users")
	public ModelAndView Users(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		
		
		List<User> listUser = userService.getAllUsers();
		mv.addObject("objs",listUser);
		mv.setViewName("all_user");
		return mv;
	}
	
	@RequestMapping("index")
	public ModelAndView index(HttpSession session) {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("index");
		printAllSessionAttribute(session);
		System.out.println(currentLogInUsername() + " 你好!");
		
		
		return mv;
	}
	
	@RequestMapping("/register")
	public ModelAndView ShowRegistrationForm() {
		ModelAndView mv = new ModelAndView();
		mv.addObject("user",new User());
		mv.setViewName("signup_form");
		return mv;
	}
	
	@PostMapping("/process_register")
	public ModelAndView processRegister(User user) {
		ModelAndView mv = new ModelAndView();
		userService.saveUser(user);
		mv.setViewName("register_success");
		return mv;
	}
	
	
	@GetMapping("/login")
	public String login() {
		return "/login";
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable(value = "id") int id, Model model, HttpSession session) {
		
		User user = userService.getUserById(id);
		session.setAttribute("currentUserId", id);
		model.addAttribute("user",user);
		return "update_user";
	}
	
	@PostMapping("/processUserUpdate")
	public ModelAndView processUserUpdate(User user, HttpSession session) {
		ModelAndView mv = new ModelAndView();
		int userId = (int) session.getAttribute("currentUserId");
		userService.updateUserName(userId, user.getFullName());
		
		List<User> listUser = userService.getAllUsers();

		mv.addObject("objs",listUser);
		mv.setViewName("all_user");
		return mv;
	}
	
	@RequestMapping("login_success")
	@ResponseBody
	public String login_success() {
		
		return "LogIn Successfully";
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
