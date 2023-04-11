package com.aleshkin.gtd.bot.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import com.aleshkin.gtd.bot.type.Task;

public class TaskMapper implements RowMapper<Task> {

	@Override
	public Task mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Task(
				rs.getLong("task_id"), 
				rs.getString("description"), 
				rs.getInt("task_type_id"), 
				new DateTime(rs.getTimestamp("create_dt"))
				);
	}

}
