package by.telegram.bot.controller;

import by.telegram.bot.handler.KeyBoard;
import by.telegram.bot.handler.Message;
import by.telegram.bot.service.UserBotService;
import name.maratik.spring.telegram.annotation.TelegramBot;
import name.maratik.spring.telegram.annotation.TelegramCallbackQuery;
import name.maratik.spring.telegram.annotation.TelegramCommand;
import name.maratik.spring.telegram.annotation.TelegramMessage;
import name.maratik.spring.telegram.model.TelegramMessageCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

@TelegramBot
public class MainController {

    private Message message;
    @Autowired
    public MainController( Message message) {
        this.message = message;
    }

    @TelegramMessage
    public SendMessage defaultAction(long userId, User user, String message) {
        return new SendMessage()
                .setChatId(userId)
                .setText(String.format("Hello %s, you've send me %s, i don't know this, use /start!", user.getFirstName(), message));
    }

    @TelegramCommand(commands = "/start", description = "FirstCommand")
    public SendMessage startCommand(long userId,User user) {
        return message.createMessageStart(userId,user);
    }

    @TelegramCallbackQuery
    public SendMessage CallBack(CallbackQuery callbackQuery, long userid, User user) {
        return message.createMessage(userid, callbackQuery, user);
    }
}
