package com.aleshkin.gtd.bot.components;

import java.util.List;

import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import com.google.common.collect.Lists;

public interface GtdBotCommands {

	List<BotCommand> COMMANDS = Lists.newArrayList(new BotCommand("/start", "start bot"),
			new BotCommand("/help", "bot info"));

	String HELP_TEXT = "This bot is helper of plan your life. Commands:\n\n" 
	+ "/start - start the bot\n"
	+ "/help - help menu";
}
