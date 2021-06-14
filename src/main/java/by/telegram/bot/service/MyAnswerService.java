package by.telegram.bot.service;

import by.telegram.bot.entity.MyAnswer;
import by.telegram.bot.repository.MyAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;

import java.util.List;

public class MyAnswerService {
    private MyAnswerRepository myAnswerRepository;
    @Autowired
    public MyAnswerService(MyAnswerRepository myAnswerRepository) {
        this.myAnswerRepository = myAnswerRepository;
    }
    public List<MyAnswer> getByUserId(Integer id){
        return myAnswerRepository.findAllByUserBot(id);
    }
    public List<MyAnswer> getByQuestionId(Integer id){
        return myAnswerRepository.findAllByQuestion(id);
    }
}
