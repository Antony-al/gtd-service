package com.aleshkin.gtd.bot.type;

import org.joda.time.DateTime;

import lombok.Data;

@Data
public class Task {

	private final long taskId;
	private final String description;
	private final int taskTypeId;
	private final DateTime createDt;
}
