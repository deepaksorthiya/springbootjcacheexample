package com.example.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.example.common.SampleConstant;
import com.example.model.UserModel;
import com.example.repository.UserRepository;
import com.example.service.IUser;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@Service
public class UserService implements IUser {
	@Autowired
	private CacheManager cacheManager;

	@Autowired
	UserRepository userRepo;

	public static final String LIST_CACHE = "listCache";

	@Override
	@CachePut(value = SampleConstant.SAMPLE_DETAIL_CACHE, key = "#user.id")
	public UserModel saveUser(UserModel user) {
		new Thread(() -> {
			insertCache(user);
		}).start();
		return userRepo.save(user);
	}

	@Override
	@Cacheable(value = SampleConstant.SAMPLE_DETAIL_CACHE, key = "#id")
	public UserModel getOne(Long id) {
		System.err.println("NOT CACHING !!!");
		return userRepo.findOne(id);
	}

	@Override
	@Cacheable(value = SampleConstant.SAMPLE_CACHE, key = "#root.target.LIST_CACHE")
	public List<UserModel> getList() {
		System.err.println("NOT CACHING !!!");
		return (List<UserModel>) userRepo.findAll();
	}

	@Override
	@CacheEvict(value = SampleConstant.SAMPLE_DETAIL_CACHE, key = "#id", condition = "#result == true")
	public boolean deleteUser(Long id) {
		if (userRepo.findOne(id) != null) {
			userRepo.delete(id);
			new Thread(() -> {
				deleteCache(id);
			}).start();
			return true;
		}
		return false;
	}

	@Override
	@CachePut(value = SampleConstant.SAMPLE_DETAIL_CACHE, key = "#user.id", condition = "#result != null")
	public UserModel updateUser(Long id, UserModel updateUser) {
		if (userRepo.findOne(id) != null) {
			updateUser.setId(id);
			new Thread(() -> {
				updateCache(updateUser, id);
			}).start();
			return userRepo.save(updateUser);
		}
		return null;
	}

	private void deleteCache(Long id) {
		Cache cache = cacheManager.getCache(SampleConstant.SAMPLE_CACHE);
		Element ele = cache.get(LIST_CACHE);
		if (ele != null) {
			if (ele.getObjectValue() != null) {
				List<UserModel> listCache = (List<UserModel>) ele.getObjectValue();
				List<UserModel> newList = new ArrayList<>();
				newList.addAll(listCache);
				UserModel contact = listCache.stream().filter(x -> x.getId().equals(id)).findFirst().get();
				newList.remove(listCache.indexOf(contact));
				cache.replace(new Element(LIST_CACHE, newList));
			}
		}
	}

	private void insertCache(UserModel savedUser) {
		Cache cache = cacheManager.getCache(SampleConstant.SAMPLE_CACHE);
		Element ele = cache.get(LIST_CACHE);
		if (ele != null) {
			                 if (ele.getObjectValue() != null) {
				List<UserModel> listCache = (List<UserModel>) ele.getObjectValue();
				List<UserModel> newList = new ArrayList<>();
				newList.addAll(listCache);
				newList.add(savedUser);
				cache.put(new Element(LIST_CACHE, newList));
			}
		}
	}

	private void updateCache(UserModel updatedUser, Long id) {
		Cache cache = cacheManager.getCache(SampleConstant.SAMPLE_CACHE);
		Element ele = cache.get(LIST_CACHE);
		if (ele != null) {
			if (ele.getObjectValue() != null) {
				List<UserModel> listCache = (List<UserModel>) ele.getObjectValue();
				List<UserModel> newList = new ArrayList<>();
				newList.addAll(listCache);
				UserModel user = listCache.stream().filter(x -> x.getId().equals(id)).findFirst().get();
				newList.set(newList.indexOf(user), updatedUser);
				cache.replace(new Element(LIST_CACHE, newList));
			}
		}
	}
}
