package com.aleshkin.gtd.bot.service;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.aleshkin.gtd.bot.db.GtdDao;
import com.aleshkin.gtd.bot.type.TgUser;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

	@Autowired
	private GtdDao dao;
	
	public void registerUser(long userId, String userName) {
		
		try {
			TgUser user = getUserById(userId);
			log.debug("user = {}", user.toString());
		} catch (EmptyResultDataAccessException e) {
			log.debug("user not found, need to save");
			insertUser(userId, userName);
		}
		
//		if (getUserById(userId) == null) {
//			log.debug("user not found, need to save");
//			insertUser(userId, userName);
//		} else {
//			log.debug("user has been found");
//		}
	}
	
	public TgUser getUserById(long userId) {
		return dao.getUserById(userId);
	}
	
	public void insertUser(long userId, String userName) {
		DateTime createDt = DateTime.now();
		dao.insertUser(userId, userName, createDt);
		log.debug("user has been saved in db");
	}
	
}
