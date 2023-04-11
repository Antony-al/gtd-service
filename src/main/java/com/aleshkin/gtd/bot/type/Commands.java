package com.aleshkin.gtd.bot.type;

public enum Commands {

	START("/start"),
	HELP("/help"),
	ADD_TASK("/addTask"),
	TASK_LIST("/taskList");
	
	private final String command;
	
	Commands(String command) {
		this.command = command;
	}
	
	public String getCommand() {
		return command;
	}
}
