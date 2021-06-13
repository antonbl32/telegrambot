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

@TelegramBot
public class MainController {
    private UserBotService userBotService;
    private KeyBoard keyBoard = new KeyBoard();
    private Message message;

    @Autowired
    public void setMessage(Message message) {
        this.message = message;
    }

    @Autowired
    public void setUserBotService(UserBotService userBotService) {
        this.userBotService = userBotService;
    }

    @TelegramMessage
    public SendMessage defaultAction(long userId, User user, String message) {
        return new SendMessage()
                .setChatId(userId)
                .setText(String.format("Hello %s, you've send me %s, i don't know this", user.getFirstName(), message));
    }

    @TelegramCommand(commands = "/some_command", description = "Some command")
    public SendMessage someCommand(long userId, TelegramMessageCommand telegramMessageCommand) {
        return new SendMessage()
                .setChatId(userId)
                .setText(String.format("You've send me %s command with arguments: %s",
                        telegramMessageCommand.getCommand(), telegramMessageCommand.getArgument()
                ));
    }

    @TelegramCommand(commands = "/start", description = "FirstCommand")
    public SendMessage startCommand(long userId, User user) {
        return message.createMessageStart(userId, user);
    }

    @TelegramCallbackQuery
    public SendMessage CallBack(CallbackQuery callbackQuery, long userid, User user) {
        return message.createMessage(userid, callbackQuery, user);
    }
}
