package com.aleshkin.gtd.bot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import com.aleshkin.gtd.bot.GtdTelegramBot;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class Initializer {
	
	@Autowired
	private GtdTelegramBot bot;
	
	@EventListener({ContextRefreshedEvent.class})
	public void init() {
		try {
			TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
			api.registerBot(bot);
		} catch (TelegramApiException e) {
			log.error(e.getMessage());
		}
	}

}
