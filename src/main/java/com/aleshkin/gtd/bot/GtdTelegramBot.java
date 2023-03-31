package com.aleshkin.gtd.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScope;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.aleshkin.gtd.bot.components.GtdBotCommands;
import com.aleshkin.gtd.bot.config.GtdBotConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GtdTelegramBot extends TelegramLongPollingBot implements GtdBotCommands{
	
	private GtdBotConfig config;
	
	@Autowired
	public GtdTelegramBot(GtdBotConfig config) {
		super();
		this.config = config;
		
		try {
			this.execute(new SetMyCommands(COMMANDS, new BotCommandScopeDefault(), null));
		} catch (TelegramApiException e) {
			log.error(e.getMessage());
		}
			
	}

	@Override
	public String getBotUsername() {
		return config.getBotName();
	}

	@Override
	public String getBotToken() {
		return config.getToken();
	}
	
	@Override
	public void onUpdateReceived(Update update) {
		long chatId = 0;
		long userId = 0;
		
		String userName = null;
        String receivedMessage;
        
        if(update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            userId = update.getMessage().getFrom().getId();
            userName = update.getMessage().getFrom().getFirstName();

            if (update.getMessage().hasText()) {
                receivedMessage = update.getMessage().getText();
                botAnswer(receivedMessage, chatId, userName);
            }

        //если нажата одна из кнопок бота
        } else if (update.hasCallbackQuery()) {
            chatId = update.getCallbackQuery().getMessage().getChatId();
            userId = update.getCallbackQuery().getFrom().getId();
            userName = update.getCallbackQuery().getFrom().getFirstName();
            receivedMessage = update.getCallbackQuery().getData();

            botAnswer(receivedMessage, chatId, userName);
        }
		
//		if (update.hasMessage() && update.getMessage().hasText()) {
//			String messageText = update.getMessage().getText();
//			long chatId = update.getMessage().getChatId();
//			String memberName = update.getMessage().getFrom().getFirstName();
//			
//			switch (messageText) {
//			case "/start":
//				startBot(chatId, memberName);
//				break;
//				default:
//					log.info("Unexpected message");
//			}
//		}
		
	}
	
	private void botAnswer(String receivedMessage, long chatId, String userName) {
		switch (receivedMessage){
        case "/start":
            startBot(chatId, userName);
            break;
        case "/help":
            sendHelpText(chatId, HELP_TEXT);
            break;
        default: break;
    }
	}
	
	private void startBot(long chatId, String userName) {
	    SendMessage message = new SendMessage();
	    message.setChatId(chatId);
	    message.setText("Hello, " + userName + "! I'm a Telegram bot.");

	    try {
	        execute(message);
	        log.info("Reply sent");
	    } catch (TelegramApiException e){
	        log.error(e.getMessage());
	    }
	}
	
	private void sendHelpText(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textToSend);

        try {
            execute(message);
            log.info("Reply sent");
        } catch (TelegramApiException e){
            log.error(e.getMessage());
        }
    }

}
