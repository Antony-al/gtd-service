package com.aleshkin.gtd.bot.db;

import java.sql.Timestamp;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.aleshkin.gtd.bot.type.Task;
import com.aleshkin.gtd.bot.util.TaskMapper;

@Component
public class TaskDao {
	
	private static final TaskMapper TASK_MAPPER = new TaskMapper();
	
	@Autowired
	private JdbcTemplate template;
	
	public void addTask(long userId, int taskType, String taskText, DateTime createDt) {
		StringBuilder sb = new StringBuilder();
		sb.append("insert into public.task (task_id, user_id, task_type_id, description, create_dt) "
				+ "values (nextval('task_seq'),?,?,?,?)");
		
		template.update(sb.toString(), userId, taskType, taskText, new Timestamp(createDt.getMillis()));
	}
	
	public List<Task> getTasksByUser(long userId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from public.task where user_id = ?");
		
		return template.query(sb.toString(), TASK_MAPPER, userId); 
	}
}
