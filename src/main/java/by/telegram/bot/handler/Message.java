package by.telegram.bot.handler;

import by.telegram.bot.entity.MyAnswer;
import by.telegram.bot.entity.UserBot;
import by.telegram.bot.service.QuestionService;
import by.telegram.bot.service.UserBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;

@Component
public class Message {
    private KeyBoard keyBoard;
    private QuestionService questionService;
    private UserBotService userBotService;
    @Autowired
    public Message(KeyBoard keyBoard, QuestionService questionService, UserBotService userBotService) {
        this.keyBoard = keyBoard;
        this.questionService = questionService;
        this.userBotService = userBotService;
    }
    public SendMessage createMessageStart(Long userId, User user) {
        String text;
        if (userBotService.checkUser(userId, user)) {
            text = "Рады снова вас видеть " + userBotService.getUserByIdChat(userId).getUsername() + " попробуем еще раз найти" +
                    " с кем поговорить?";
        } else {
            text = "Добро пожаловать " + user.getUserName();
        }
        keyBoard.setState(userBotService.getUserByIdChat(userId).getStatus());
        SendMessage sendMessage=new SendMessage();
        sendMessage.setChatId(userId).setText(text).setReplyMarkup(keyBoard.getReplyKeyboardMarkup("NONE"));
        return sendMessage;

    }
//    public String getMessageStart(Long userId, User user){
//        String text;
//        if (userBotService.checkUser(userId, user)) {
//            text = "Рады снова вас видеть " + userBotService.getUserByIdChat(userId).getUsername() + " попробуем еще раз найти" +
//                    " с кем поговорить?";
//        } else {
//            text = "Добро пожаловать " + user.getUserName();
//        }
//        return text;
//    }

    public SendMessage createMessage(Long userId, CallbackQuery callbackQuery, User user) {
        if (!ObjectUtils.isEmpty(callbackQuery.getData())) {
            int callBackId = Integer.valueOf(callbackQuery.getData());
            UserBot userBot = userBotService.getUserByIdChat(userId);
            MyAnswer myAnswer;            String state = userBot.getStatus();
            switch (state) {
                case "NONE":
                        userBot.setStatus("ONE");
                        myAnswer = new MyAnswer();
                        myAnswer.setQuestion(questionService.getQuestionOnly("NONE"));
//                        myAnswer.getMyAnswers().add(1);
                        List<Integer> ans=myAnswer.getMyAnswers();
                        ans.add(callBackId);
                        myAnswer.setMyAnswers(ans);
                        userBot.getMyAnswer().add(myAnswer);
                        userBotService.save(userBot);
                        return new SendMessage()
                                .setChatId(userId)
                                .setText(questionService.getQuestion("ONE"))
                                .setReplyMarkup(keyBoard.getReplyKeyboardMarkup("ONE"));
                case "ONE":
                    if (callBackId == 1) {
                        userBot.setStatus("TWO");
                        myAnswer = new MyAnswer();
                        myAnswer.setQuestion(questionService.getQuestionOnly("ONE"));
                        myAnswer.getMyAnswers().add(callBackId);
                        userBot.getMyAnswer().add(myAnswer);
                        userBotService.save(userBot);
                        return new SendMessage()
                                .setChatId(userId)
                                .setText(questionService.getQuestion("TWO"))
                                .setReplyMarkup(keyBoard.getReplyKeyboardMarkup("TWO"));
                    }else {

                    }
                case "TWO":
                    userBot.setStatus("THREE");
                    myAnswer = new MyAnswer();
                    myAnswer.setQuestion(questionService.getQuestionOnly("TWO"));
                    myAnswer.getMyAnswers().add(callBackId);
                    userBot.getMyAnswer().add(myAnswer);
                    userBotService.save(userBot);
                    return new SendMessage()
                            .setChatId(userId)
                            .setText(questionService.getQuestion("THREE"))
                            .setReplyMarkup(keyBoard.getReplyKeyboardMarkup("THREE"));
            }
        } else {
            return createMessageStart(userId, user);
        }
        return createMessageStart(userId, user);
    }
}
