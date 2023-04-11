package com.aleshkin.gtd.bot.db;

import java.sql.Timestamp;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.aleshkin.gtd.bot.type.Task;
import com.aleshkin.gtd.bot.type.TgUser;
import com.aleshkin.gtd.bot.util.TaskMapper;
import com.aleshkin.gtd.bot.util.TgUserMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GtdDao {
	
	private static final TgUserMapper USER_MAPPER = new TgUserMapper();

	@Autowired
	private JdbcTemplate template;
	
	
	public TgUser getUserById(long userId) {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from public.tg_user where user_id = ?");
		
		return (TgUser) template.queryForObject(sb.toString(), USER_MAPPER, userId);
	}
	
	public void insertUser(long userId, String userName, DateTime createDt) {
		StringBuilder sb = new StringBuilder();
		sb.append("insert into public.tg_user (user_id, user_name, create_dt) values (?,?,?)");
		
		int result = template.update(sb.toString(), userId, userName, new Timestamp(createDt.getMillis()));
		log.debug("insert user result = {}", result);
	}
	
}
