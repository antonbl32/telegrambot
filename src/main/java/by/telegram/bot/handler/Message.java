package by.telegram.bot.handler;

import by.telegram.bot.entity.MyAnswer;
import by.telegram.bot.entity.UserBot;
import by.telegram.bot.service.MyAnswerService;
import by.telegram.bot.service.QuestionService;
import by.telegram.bot.service.UserBotService;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.*;

@Component
public class Message {
    private final KeyBoard keyBoard;
    private final QuestionService questionService;
    private final UserBotService userBotService;
    private final MyAnswerService myAnswerService;

    @Autowired
    public Message(KeyBoard keyBoard, QuestionService questionService, UserBotService userBotService, MyAnswerService myAnswerService) {
        this.keyBoard = keyBoard;
        this.questionService = questionService;
        this.userBotService = userBotService;
        this.myAnswerService=myAnswerService;
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

    public SendMessage createMessage(Long userId, CallbackQuery callbackQuery, User user) {
        if (!ObjectUtils.isEmpty(callbackQuery.getData())) {
            int callBackId = Integer.valueOf(callbackQuery.getData());
            UserBot userBot = userBotService.getUserByIdChat(userId);
            String state = userBot.getStatus();
            switch (state) {
                case "NONE":
                    userBot.setStatus("ONE");
                    this.addAnswer(userBot,callBackId,state);
                    return new SendMessage()
                            .setChatId(userId)
                            .setText(questionService.getQuestion("ONE"))
                            .setReplyMarkup(keyBoard.getReplyKeyboardMarkup("ONE"));
                case "ONE":
                    if (callBackId == 1) {
                        userBot.setStatus("TWO");
                        this.addAnswer(userBot,callBackId,state);
                        return new SendMessage()
                                .setChatId(userId)
                                .setText(questionService.getQuestion("ONE"))
                                .setReplyMarkup(keyBoard.getReplyKeyboardMarkup("ONE"));
                    } else {
                        return new SendMessage()
                                .setChatId(userId)
                                .setText(" in future...")
                                .setReplyMarkup(keyBoard.getReplyKeyboardMarkup("NONE"));
                    }
                case "TWO":
                    userBot.setStatus("THREE");
                    this.addAnswer(userBot,callBackId,state);
                    return new SendMessage()
                            .setChatId(userId)
                            .setText(questionService.getQuestion("THREE"))
                            .setReplyMarkup(keyBoard.getReplyKeyboardMarkup("THREE"));
                case "THREE":
                    userBot.setStatus("FOUR");
                    this.addAnswer(userBot,callBackId,state);
                    return new SendMessage()
                            .setChatId(userId)
                            .setText(questionService.getQuestion("FOUR"))
                            .setReplyMarkup(keyBoard.getReplyKeyboardMarkup("FOUR"));
                case "FOUR":
                    userBot.setStatus("FIVE");
                    this.addAnswer(userBot,callBackId,state);
                    return new SendMessage()
                            .setChatId(userId)
                            .setText(questionService.getQuestion("FIVE"))
                            .setReplyMarkup(keyBoard.getReplyKeyboardMarkup("FIVE"));
                case "FIVE":
                    userBot.setStatus("SIX");
                    this.addAnswer(userBot,callBackId,state);
                    return new SendMessage()
                            .setChatId(userId)
                            .setText(questionService.getQuestion("SIX"))
                            .setReplyMarkup(keyBoard.getReplyKeyboardMarkup("SIX"));
                case "SIX":
                    userBot.setStatus("SEVEN");
                    this.addAnswer(userBot,callBackId,state);
                    return new SendMessage()
                            .setChatId(userId)
                            .setText(questionService.getQuestion("SEVEN"))
                            .setReplyMarkup(keyBoard.getReplyKeyboardMarkup("SEVEN"));
                case "SEVEN":
                    userBot.setStatus("EIGHT");
                    this.addAnswer(userBot,callBackId,state);
                    return new SendMessage()
                            .setChatId(userId)
                            .setText(questionService.getQuestion("EIGHT"))
                            .setReplyMarkup(keyBoard.getReplyKeyboardMarkup("EIGHT"));
                case "EIGHT":
                    userBot.setStatus("NINE");
                    this.addAnswer(userBot,callBackId,state);
                    return new SendMessage()
                            .setChatId(userId)
                            .setText(matchUser(userBot))
                            .setReplyMarkup(keyBoard.getReplyKeyboardMarkup("NINE"));
                case "NINE":
                    userBot.setStatus("NONE");
                    return new SendMessage()
                            .setChatId(userId)
                            .setText(matchUser(userBot))
                            .setReplyMarkup(keyBoard.getReplyKeyboardMarkup("ONE"));
            }
        } else {
            return createMessageStart(userId, user);
        }
        return createMessageStart(userId, user);
    }
    public void addAnswer(UserBot userBot,Integer callBackId,String state){
        MyAnswer myAnswer;
        Optional<MyAnswer> listOfMyAnswers=myAnswerService.getByUserBotIdAndQuestionId(userBot.getId(), questionService.getQuestionOnly(state).getId());
        if(!listOfMyAnswers.isPresent()){
            myAnswer=new MyAnswer();
            myAnswer.setQuestion(questionService.getQuestionOnly(state));
            myAnswer.setUserBot(userBot);
            Set<Integer> listAnswers=new HashSet<>();
            listAnswers.add(callBackId);
            myAnswer.setAnswers(listAnswers);
            myAnswerService.save(myAnswer);
        }else{
            myAnswer= listOfMyAnswers.get();
            myAnswer.getAnswers().add(callBackId);
            myAnswerService.save(myAnswer);
        }
        userBotService.save(userBot);
    }

    public String matchUser(UserBot userBot){
        UserBot tagretUser;
        List<List<MyAnswer>> allAnswers=new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            allAnswers.add(myAnswerService.getAllByQuestion(i));
        }
        List<MyAnswer> userBotAnswers=myAnswerService.getAllAnswerByUserBot(userBot.getId());
        Optional<List<MyAnswer>> targetUserAnswers= allAnswers.stream().filter(a->a.containsAll(userBotAnswers)).findAny();
        if(targetUserAnswers.isPresent()){
            tagretUser=targetUserAnswers.get().get(0).getUserBot();
        }else{
            tagretUser=null;
        }
        if(tagretUser!=null){
            return "Нет совпадений, попробуйте изменить выбор";
        }else{
            return "Ваш собеседник @" +tagretUser.getUsername();
        }

    }
}
