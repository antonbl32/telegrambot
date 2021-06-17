package by.telegram.bot.service;

import by.telegram.bot.entity.MyAnswer;
import by.telegram.bot.repository.MyAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MyAnswerService {
    private final MyAnswerRepository myAnswerRepository;
    @Autowired
    public MyAnswerService(MyAnswerRepository myAnswerRepository) {
        this.myAnswerRepository = myAnswerRepository;
    }
    @Transactional
    public List<MyAnswer> getAllAnswerByUserBot(Integer id){
        return myAnswerRepository.findAllByUserBot_Id(id);
    }
    @Transactional
    public List<MyAnswer> getAllByQuestion(Integer id){
        return myAnswerRepository.findAllByQuestion_Id(id);
    }
    @Transactional
    public Optional<MyAnswer> getByUserBotIdAndQuestionId(Integer userBotId, Integer questionId){
        return myAnswerRepository.getByUserBot_IdAndQuestion_Id(userBotId, questionId);
    }
    @Transactional
    public void save(MyAnswer myAnswer){
        myAnswerRepository.save(myAnswer);
    }

    public List<MyAnswer> gelAll() {
        return myAnswerRepository.findAll();
    }
}
