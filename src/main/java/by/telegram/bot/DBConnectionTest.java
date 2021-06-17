package by.telegram.bot;

import by.telegram.bot.entity.AnswerValue;
import by.telegram.bot.entity.MyAnswer;
import by.telegram.bot.entity.Question;
import by.telegram.bot.entity.UserBot;
import by.telegram.bot.service.MyAnswerService;
import by.telegram.bot.service.QuestionService;
import by.telegram.bot.service.UserBotService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DBConnectionTest {
    @Autowired
    private UserBotService userBotService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private MyAnswerService myAnswerService;

    @Test
    public void getUser(){
        UserBot userBot=userBotService.getUserByIdChat(1071087542);
        UserBot tagretUser=null;
        List<MyAnswer> list=myAnswerService.getAllAnswerByUserBot(userBot.getId());
        List<MyAnswer> allAnswers=myAnswerService.gelAll();
        List<String> allStates=questionService.getAllStates();
        Iterator<String> iterator= allStates.listIterator();
        List<UserBot> justList=new ArrayList<>();
        while (iterator.hasNext()) {
            String state=iterator.next();
            if(state!="NINE" && state!="NONE" && state!="EIGHT"){
                justList.addAll(allAnswers.stream().filter(a->a.getQuestion().getState().equals(state))
                        .filter(a->a.getAnswers().containsAll(userBot.getMyAnswer().stream().filter(b->b.getQuestion().getState().equals(state)).collect(Collectors.toList())))
                        .map(a->userBotService.getUserByIdChat(a.getUserBot().getChatId()))
                        .collect(Collectors.toList()));
            }
        }

        Map<UserBot,Long> userScore=justList.stream().collect(Collectors.groupingBy(e->e, Collectors.counting()));
        System.out.println(userScore);
//        if(userScore!=null) {
//            tagretUser = Collections.max(userScore.entrySet(), (entry1, entry2) -> (int) (entry1.getValue() - entry2.getValue())).getKey();
//        }
        System.out.println(tagretUser);
        list.stream().flatMap(c->c.getAnswers().stream()).forEach(System.out::println);
        Assert.assertNotNull(userBot.getStatus());
    }
    @Test
    public void getQuestion(){
        Question question=questionService.getQuestionOnly("NONE");
//        List<MyAnswer> list=userBotService.getUserByIdChat(1071087542);
//        list.stream().flatMap(c->c.getAnswers())

        Assert.assertEquals(question.getState(),"NONE");
    }
//    @Test
//    public void keyboardTest(){
//        Message message=new Message();
//        SendMessage sendMessage= message.createMessageStart(1071087542L,user);
//    }
}
