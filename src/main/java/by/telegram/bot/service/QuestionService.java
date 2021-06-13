package by.telegram.bot.service;

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
    public String getQuestion(String state){
        return questionRepository.findQuestionByState(state).getAsk();
    }
    @Transactional
    public Question getQuestionOnly(String state){
        return questionRepository.findQuestionByState(state);
    }
    @Transactional
    public List<AnswerValue> getAllAnswers(String state){
        System.out.println(questionRepository.findQuestionByState(state).getAnswer());
        return questionRepository.findQuestionByState(state).getAnswer();
    }
}
