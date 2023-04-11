package com.aleshkin.gtd.bot.components;

import java.util.List;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import com.aleshkin.gtd.bot.type.Commands;
import com.google.common.collect.Lists;

public interface GtdBotCommands {

	List<BotCommand> COMMANDS = Lists.newArrayList(
			new BotCommand("/start", "start bot"),
			new BotCommand("/help", "bot info"),
			new BotCommand("/add_task", "add task"),
			new BotCommand("/task_list", "task list")
			);

	String HELP_TEXT = "Этот бот поможет составить список запланированных дел. Команды:\n\n" 
	+ "/addTask - добавить задачу\n"
	+ "/taskList - список задач\n"
	+ "/help - help menu";
}
