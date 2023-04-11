package com.aleshkin.gtd.bot.service;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aleshkin.gtd.bot.db.TaskDao;
import com.aleshkin.gtd.bot.type.Task;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TaskService {

	@Autowired
	private TaskDao taskDao;
	
	public void addTask(long userId, String taskText) {
		log.debug("adding task to userId = {}", userId);
		DateTime createDt = DateTime.now();
		taskDao.addTask(userId, 1, taskText, createDt);
	}
	
	public List<Task> getTaskList(long userId) {
		log.debug("getting task list to userId = {}", userId);
		
		List<Task> taskList = taskDao.getTasksByUser(userId);
		log.debug("task list size = {}", taskList.size());
		
		return taskList;
	}
}
