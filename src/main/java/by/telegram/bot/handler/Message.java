package by.telegram.bot.handler;

import by.telegram.bot.entity.MyAnswer;
import by.telegram.bot.entity.UserBot;
import by.telegram.bot.service.MyAnswerService;
import by.telegram.bot.service.QuestionService;
import by.telegram.bot.service.UserBotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.*;
import java.util.stream.Collectors;

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
        this.myAnswerService = myAnswerService;
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
        SendMessage sendMessage = new SendMessage();
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
                    userBotService.save(userBot);
                    this.addAnswer(userBot, callBackId, state);
                    return new SendMessage()
                            .setChatId(userId)
                            .setText(questionService.getQuestion("ONE"))
                            .setReplyMarkup(keyBoard.getReplyKeyboardMarkup("ONE"));
                case "ONE":
                    if (callBackId == 1) {
                        userBot.setStatus("TWO");
                        userBotService.save(userBot);
                        this.addAnswer(userBot, callBackId, state);
                        return new SendMessage()
                                .setChatId(userId)
                                .setText(questionService.getQuestion("TWO"))
                                .setReplyMarkup(keyBoard.getReplyKeyboardMarkup("TWO"));
                    } else {
                        // for add location in future
                        this.addAnswer(userBot, callBackId, state);
                        userBot.setStatus("TWO");
                        userBotService.save(userBot);
                        return new SendMessage()
                                .setChatId(userId)
                                .setText(questionService.getQuestion("TWO"))
                                .setReplyMarkup(keyBoard.getReplyKeyboardMarkup("TWO"));
                    }
                case "TWO":
                    userBot.setStatus("THREE");
                    userBotService.save(userBot);
                    this.addAnswer(userBot, callBackId, state);
                    return new SendMessage()
                            .setChatId(userId)
                            .setText(questionService.getQuestion("THREE"))
                            .setReplyMarkup(keyBoard.getReplyKeyboardMarkup("THREE"));
                case "THREE":
                    userBot.setStatus("FOUR");
                    userBotService.save(userBot);
                    this.addAnswer(userBot, callBackId, state);
                    return new SendMessage()
                            .setChatId(userId)
                            .setText(questionService.getQuestion("FOUR"))
                            .setReplyMarkup(keyBoard.getReplyKeyboardMarkup("FOUR"));
                case "FOUR":
                    userBot.setStatus("FIVE");
                    userBotService.save(userBot);
                    this.addAnswer(userBot, callBackId, state);
                    return new SendMessage()
                            .setChatId(userId)
                            .setText(questionService.getQuestion("FIVE"))
                            .setReplyMarkup(keyBoard.getReplyKeyboardMarkup("FIVE"));
                case "FIVE":
                    userBot.setStatus("SIX");
                    userBotService.save(userBot);
                    this.addAnswer(userBot, callBackId, state);
                    return new SendMessage()
                            .setChatId(userId)
                            .setText(questionService.getQuestion("SIX"))
                            .setReplyMarkup(keyBoard.getReplyKeyboardMarkup("SIX"));
                case "SIX":
                    userBot.setStatus("SEVEN");
                    userBotService.save(userBot);
                    this.addAnswer(userBot, callBackId, state);
                    return new SendMessage()
                            .setChatId(userId)
                            .setText(questionService.getQuestion("SEVEN"))
                            .setReplyMarkup(keyBoard.getReplyKeyboardMarkup("SEVEN"));
                case "SEVEN":
                    userBot.setStatus("EIGHT");
                    userBotService.save(userBot);
                    this.addAnswer(userBot, callBackId, state);
                    return new SendMessage()
                            .setChatId(userId)
                            .setText(matchUser(userBot))
                            .setReplyMarkup(keyBoard.getReplyKeyboardMarkup("EIGHT"));
                case "EIGHT":
                    userBot.setStatus("NINE");
                    userBotService.save(userBot);
                    this.addAnswer(userBot, callBackId, state);
                    return new SendMessage()
                            .setChatId(userId)
                            .setText(matchUser(userBot))
                            .setReplyMarkup(keyBoard.getReplyKeyboardMarkup("NINE"));
                case "NINE":
                    userBot.setStatus("NONE");
                    userBotService.save(userBot);
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
    public void addAnswer(UserBot userBot, Integer callBackId, String state) {
        MyAnswer myAnswer;
        if (userBot.getMyAnswer() == null) {
            userBot.setMyAnswer(new ArrayList<>());
            myAnswer = new MyAnswer();
            myAnswer.setAnswers(new HashSet<>());
            myAnswer.getAnswers().add(callBackId);
            myAnswer.setQuestion(questionService.getQuestionOnly(state));
            myAnswer.setUserBot(userBot);
            userBot.getMyAnswer().add(myAnswer);
            userBotService.save(userBot);
        }
        Optional<MyAnswer> listOfMyAnswers = myAnswerService.getByUserBotIdAndQuestionId(userBot.getId()
                , questionService.getQuestionOnly(state).getId());
        if (!listOfMyAnswers.isPresent()) {
            myAnswer = new MyAnswer();
            myAnswer.setQuestion(questionService.getQuestionOnly(state));
            myAnswer.setUserBot(userBot);
            Set<Integer> listAnswers = new HashSet<>();
            listAnswers.add(callBackId);
            myAnswer.setAnswers(listAnswers);
            userBot.getMyAnswer().add(myAnswer);
            myAnswerService.save(myAnswer);
            userBotService.save(userBot);
        } else if (!userBot.getMyAnswer().stream().filter(a -> a.getQuestion() == questionService.getQuestionOnly(state))
                .flatMap(b -> b.getAnswers().stream()).anyMatch(c -> c == callBackId)) {
            myAnswer = listOfMyAnswers.get();
            myAnswer.getAnswers().add(callBackId);
            myAnswerService.save(myAnswer);
            userBotService.save(userBot);
        }
    }

    public String matchUser(UserBot userBot) {
        UserBot tagretUser = null;
        List<MyAnswer> allAnswers=myAnswerService.gelAll();
        List<String> allStates=questionService.getAllStates();
        Iterator<String> iterator= allStates.listIterator();
        Map<UserBot,Integer> allUsers=new HashMap<>();
        while (iterator.hasNext()) {
            String state=iterator.next();
            if(!state.equals("NINE") && state.equals("NONE") && state.equals("EIGHT")){
                for(MyAnswer myAn: userBot.getMyAnswer()){
                    List<MyAnswer> getAnswer=allAnswers.stream().filter(a->a.getAnswers().containsAll(myAn.getAnswers())).collect(Collectors.toList());
                    if(getAnswer.size()>0){
                        for(MyAnswer ans:getAnswer){
                            if(allUsers.containsKey(ans.getUserBot())){
                                allUsers.put(ans.getUserBot(),allUsers.get(ans.getUserBot())+1);
                            }else{
                                allUsers.put(ans.getUserBot(),1);
                            }
                        }
                    }
                }
            }
        }
        if(allUsers!=null) {
            tagretUser = allUsers.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
        }

        if (tagretUser==null) {
            return "Нет совпадений, попробуйте изменить выбор";
        } else {
            return "Ваш собеседник @" + tagretUser.getUsername();
        }
    }
}
