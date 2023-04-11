package com.aleshkin.gtd.bot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import lombok.Data;

@Configuration
public class GtdBotConfig {

	@Bean
	TelegramBotsApi telegramBotsApi() throws TelegramApiException {
		return new TelegramBotsApi(DefaultBotSession.class);
	}
}
