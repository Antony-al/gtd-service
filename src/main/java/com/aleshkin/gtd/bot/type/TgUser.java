package com.aleshkin.gtd.bot.type;

import org.joda.time.DateTime;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Data
@RequiredArgsConstructor
@ToString
public class TgUser {

	private final int userId;
	private final String userName;
	private final DateTime createDt;
	
}
