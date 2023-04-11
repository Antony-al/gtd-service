package com.aleshkin.gtd.bot.components;

import java.util.List;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

public class Buttons {
	
	private static final InlineKeyboardButton START_BUTTON = new InlineKeyboardButton("Start");
    private static final InlineKeyboardButton HELP_BUTTON = new InlineKeyboardButton("Help");
    private static final InlineKeyboardButton ADD_TASK = new InlineKeyboardButton("Add Task");
//    private static final InlineKeyboardButton TASK_LIST = new 

    public static InlineKeyboardMarkup inlineMarkup() {
        START_BUTTON.setCallbackData("/start");
        HELP_BUTTON.setCallbackData("/help");
        ADD_TASK.setCallbackData("/addTask");

        List<InlineKeyboardButton> rowInline = List.of(START_BUTTON, HELP_BUTTON, ADD_TASK);
        List<List<InlineKeyboardButton>> rowsInLine = List.of(rowInline);

        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        markupInline.setKeyboard(rowsInLine);

        return markupInline;
    }
}
