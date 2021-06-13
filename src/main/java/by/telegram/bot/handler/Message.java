package by.telegram.bot.handler;

import by.telegram.bot.en.State;
import by.telegram.bot.entity.MyAnswer;
import by.telegram.bot.entity.UserBot;
import by.telegram.bot.repository.UserBotRepository;
import by.telegram.bot.service.QuestionService;
import by.telegram.bot.service.UserBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.ArrayList;

@Component
public class Message {
    private KeyBoard keyBoard;
    private QuestionService questionService;
    private UserBotService userBotService;
    @Autowired
    public void setQuestionService(QuestionService questionService) {
        this.questionService = questionService;
    }
    @Autowired
    public void setUserBotService(UserBotService userBotService) {
        this.userBotService = userBotService;
    }

    public SendMessage createMessageStart(Long userId, User user){
        String text;
        keyBoard=new KeyBoard();
        if (userBotService.checkUser(userId, user)) {
            text = "Рады снова вас видеть " + userBotService.getUserByIdChat(userId).getUsername()+" попробуем еще раз найти" +
                    " с кем поговорить?";
        } else {
            text = "Добро пожаловать " + user.getUserName();
        }
        keyBoard.setState(userBotService.getUserByIdChat(userId).getStatus());
        return new SendMessage()
                .setChatId(userId)
                .setText(text).setReplyMarkup(keyBoard.getReplyKeyboardMarkup(State.NONE));
    }

    public SendMessage createMessage(Long userId, CallbackQuery callbackQuery, User user){
        if(ObjectUtils.isEmpty(callbackQuery.getData())) {
            int callBackId = Integer.valueOf(callbackQuery.getData());
            UserBot userBot=userBotService.getUserByIdChat(userId);
            MyAnswer myAnswer;
            State state=userBot.getStatus();
            switch (state){
                case NONE:
                    if(callBackId==1){
                        userBot.setStatus(State.ONE);
                        myAnswer=new MyAnswer();
                        myAnswer.setQuestion(questionService.getQuestionOnly(State.NONE));
                        myAnswer.getMyAnswers().add(1);
                        userBot.getMyAnswer().add(myAnswer);
                        userBotService.save(userBot);
                        return new SendMessage()
                                .setChatId(userId)
                                .setText(questionService.getQuestion(State.ONE))
                                .setReplyMarkup(keyBoard.getReplyKeyboardMarkup(State.ONE));
                    }else{

                    }
                    break;
                case ONE:
                    userBot.setStatus(State.TWO);
                    myAnswer=new MyAnswer();
                    myAnswer.setQuestion(questionService.getQuestionOnly(State.ONE));
                    myAnswer.getMyAnswers().add(callBackId);
                    userBot.getMyAnswer().add(myAnswer);
                    userBotService.save(userBot);
                    return new SendMessage()
                            .setChatId(userId)
                            .setText(questionService.getQuestion(State.TWO))
                            .setReplyMarkup(keyBoard.getReplyKeyboardMarkup(State.TWO));
                case TWO:
                    userBot.setStatus(State.THREE);
                    myAnswer=new MyAnswer();
                    myAnswer.setQuestion(questionService.getQuestionOnly(State.TWO));
                    myAnswer.getMyAnswers().add(callBackId);
                    userBot.getMyAnswer().add(myAnswer);
                    userBotService.save(userBot);
                    return new SendMessage()
                            .setChatId(userId)
                            .setText(questionService.getQuestion(State.THREE))
                            .setReplyMarkup(keyBoard.getReplyKeyboardMarkup(State.THREE));
            }
        }else{
           return createMessageStart(userId,user);
        }
        return createMessageStart(userId,user);
    }
}
