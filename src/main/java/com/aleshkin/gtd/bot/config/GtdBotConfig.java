package com.aleshkin.gtd.bot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import lombok.Data;

@Configuration
@Data
@PropertySource("config.properties")
public class GtdBotConfig {

	@Value("${bot.name}")
	private String botName;
	@Value("${bot.token}")
	private String token;
}
