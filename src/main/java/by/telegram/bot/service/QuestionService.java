package by.telegram.bot.service;

import by.telegram.bot.en.State;
import by.telegram.bot.entity.AnswerValue;
import by.telegram.bot.entity.Question;
import by.telegram.bot.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class QuestionService {
    private QuestionRepository questionRepository;
    @Autowired
    public void setQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }
    @Transactional
    public String getQuestion(State state){
        return questionRepository.findAllByState(state).getAsk();
    }
    @Transactional
    public Question getQuestionOnly(State state){
        return questionRepository.findAllByState(state);
    }
    @Transactional
    public List<AnswerValue> getAllAnswers(State state){
        return questionRepository.findAllByState(state).getAnswer();
    }
}
