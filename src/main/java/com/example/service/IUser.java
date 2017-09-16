package com.example.service;

import java.util.List;

import com.example.model.UserModel;

public interface IUser {
	UserModel saveUser(UserModel user);

	UserModel getOne(Long id);

	List<UserModel> getList();

	boolean deleteUser(Long id);

	UserModel updateUser(Long id, UserModel updateUser);
}
