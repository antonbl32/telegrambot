package by.telegram.bot;

import by.telegram.bot.entity.AnswerValue;
import by.telegram.bot.entity.Question;
import by.telegram.bot.entity.UserBot;
import by.telegram.bot.service.QuestionService;
import by.telegram.bot.service.UserBotService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DBConnectionTest {
    @Autowired
    private UserBotService userBotService;
    @Autowired
    private QuestionService questionService;

    @Test
    public void getUser(){
        UserBot userBot=userBotService.getUserByIdChat(1071087542);
        Assert.assertEquals(userBot.getStatus(),"NONE");
    }
    @Test
    public void getQuestion(){
        Question question=questionService.getQuestionOnly("NONE");
        List<AnswerValue> list=questionService.getAllAnswers("NONE");
        list.stream().forEach(System.out::println);
        Assert.assertEquals(question.getState(),"NONE");
    }
//    @Test
//    public void keyboardTest(){
//        Message message=new Message();
//        SendMessage sendMessage= message.createMessageStart(1071087542L,user);
//    }
}
