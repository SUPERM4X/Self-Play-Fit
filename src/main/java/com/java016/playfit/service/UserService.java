package com.java016.playfit.service;

import java.util.List;

import com.java016.playfit.model.User;

public interface UserService {
	List<User> getAllUsers();
	void saveUser(User user);
	User getUserById(int id);
	User getUserByEmail(String email);
	void updateUserName(int id,String fullName);
	String getCurrentLogInUsername();
}
