package com.aleshkin.gtd.bot.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.joda.time.DateTime;
import org.springframework.jdbc.core.RowMapper;

import com.aleshkin.gtd.bot.type.TgUser;

public class TgUserMapper implements RowMapper<TgUser> {

	@Override
	public TgUser mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new TgUser(
				rs.getInt("user_id"),
				rs.getString("user_name"),
				new DateTime(rs.getTimestamp("create_dt"))
				);
	}

}
