package com.aleshkin.gtd.bot;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScope;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.aleshkin.gtd.bot.components.Buttons;
import com.aleshkin.gtd.bot.components.GtdBotCommands;
import com.aleshkin.gtd.bot.config.GtdBotConfig;
import com.aleshkin.gtd.bot.service.TaskService;
import com.aleshkin.gtd.bot.service.UserService;
import com.aleshkin.gtd.bot.type.Task;
import com.google.common.collect.Maps;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Component
public class GtdTelegramBot extends TelegramLongPollingBot implements GtdBotCommands {

	private final UserService userService;
	private final TaskService taskService;

	private final String botUsername;
	private final String botToken;
	
	private static Map<Long, Boolean> addTaskActivity = Maps.newHashMap();

	@Autowired
	public GtdTelegramBot(TelegramBotsApi telegramBotsApi, UserService userService, TaskService taskService,
			@Value("${bot.name}") String botUsername, @Value("${bot.token}") String botToken) {
		super();
		this.userService = userService;
		this.taskService = taskService;
		this.botUsername = botUsername;
		this.botToken = botToken;

		try {
			this.execute(new SetMyCommands(COMMANDS, new BotCommandScopeDefault(), null));
		} catch (TelegramApiException e) {
			log.error("exception", e);
		}

		try {
			telegramBotsApi.registerBot(this);
		} catch (TelegramApiException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

//		Buttons.inlineMarkup();

	}

//	@Override
//	public String getBotUsername() {
//		return config.getBotName();
//	}
//
//	@Override
//	public String getBotToken() {
//		return config.getToken();
//	}

	@Override
	public void onUpdateReceived(Update update) {
		long chatId = 0;
		long userId = 0;

		String userName = null;
		String receivedMessage;

		if (update.hasMessage()) {
			chatId = update.getMessage().getChatId();
			userId = update.getMessage().getFrom().getId();
			userName = update.getMessage().getFrom().getFirstName();

			if (update.getMessage().hasText()) {
				receivedMessage = update.getMessage().getText();
				try {
					botAnswer(receivedMessage, chatId, userId, userName);
				} catch (TelegramApiException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// если нажата одна из кнопок бота
		} else if (update.hasCallbackQuery()) {
			chatId = update.getCallbackQuery().getMessage().getChatId();
			userId = update.getCallbackQuery().getFrom().getId();
			userName = update.getCallbackQuery().getFrom().getFirstName();
			receivedMessage = update.getCallbackQuery().getData();

			try {
				botAnswer(receivedMessage, chatId, userId, userName);
			} catch (TelegramApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

	private void botAnswer(String receivedMessage, long chatId, long userId, String userName) throws TelegramApiException {
		switch (receivedMessage) {
		case "/start":
			startBot(chatId, userId, userName);
			addTaskStatus(userId, false);
			break;
		case "/help":
//			sendHelpText(chatId, HELP_TEXT);
			addTaskStatus(userId, false);
			sendMessage(chatId, HELP_TEXT);
			break;
		case "/add_task":
			log.debug("addTask");
			addTaskStatus(userId, true);
			sendMessage(chatId, "Напиши задачу.");
			break;
		case "/task_list":
			addTaskStatus(userId, false);
			sendTaskList(userId, chatId);
			log.debug("taskList");
			break;
		default:
			log.debug("default, chatId = {}", chatId);
			
			if (addTaskActivity.get(userId) == true) {
				taskService.addTask(userId, receivedMessage);
				log.debug("task was added succuesfully");
				String message = "Задача :\n" + receivedMessage + "\n" + "была успешно добавлена";
				sendMessage(chatId, message);
				break;
			}
			String message = "Твое сообщение ни в какие ворота: \n" + receivedMessage;
			sendMessage(chatId, message);
			break;
		}
	}
	
	private void sendTaskList(long userId, long chatId) {
		List<Task> taskList = taskService.getTaskList(userId);
		int cnt = 1;
		for (Task task : taskList) {
			String message = cnt++ + ". " + task.getDescription();
			sendMessage(chatId, message);
		}
	}
	
	private void sendMessage(long chatId, String message) {
		SendMessage m = new SendMessage();
		m.setChatId(chatId);
		m.setText(message);
		try {
			execute(m);
		} catch (TelegramApiException e) {
			log.error("exception while sending message", e);
		}
	}

	private void startBot(long chatId, long userId, String userName) {
		userService.registerUser(chatId, userName);

		SendMessage message = new SendMessage();
		message.setChatId(chatId);
		message.setReplyMarkup(Buttons.inlineMarkup());
		message.setText("Ас-саля́му але́йкум, " + userName + "! Это телеграм бот для планирования своих бесполезных дел /n"
				+ "Что бы узнать как пользоваться нажми /help");

		try {
			execute(message);
			log.info("Reply sent");
		} catch (TelegramApiException e) {
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
	
	private void addTaskStatus(long userId, boolean status) {
		addTaskActivity.put(userId, status);
	}

//	private void addTask()

}
