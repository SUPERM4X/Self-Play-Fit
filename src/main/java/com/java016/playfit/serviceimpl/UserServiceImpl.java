package com.java016.playfit.serviceimpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.java016.playfit.dao.UserRepository;
import com.java016.playfit.model.User;
import com.java016.playfit.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	UserRepository userRepo;

	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

	@Override
	public void saveUser(User user) {
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		user.setBirthday(sqlDate);
		user.setGender("Male");     
		userRepo.save(user);
		
	}

	@Override
	public User getUserById(int id) {
		Optional<User> optional = userRepo.findById(id);
		User user = null;
		if(optional.isPresent()) {
			user = optional.get();
		}else {
			throw new RuntimeException("User not found for id :: " + id);
		}
		return user;
	}

	@Override
	public void updateUserName(int id, String fullName) {
		userRepo.updateUserName(id, fullName);
	}

	@Override
	public User getUserByEmail(String email) {
		User user = userRepo.findByEmail(email);
		if(user != null) {
			return user;
		}else {
			throw new RuntimeException("User not found for email :: " + email);
		}
	}

	@Override
	//取得目前登入的用戶名稱
	public String getCurrentLogInUsername() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		String username;
		if (principal instanceof UserDetails) {
		  username = ((UserDetails)principal).getUsername();
		} else {
		  username = principal.toString();
		}
		return username;
	}
	
	
}
