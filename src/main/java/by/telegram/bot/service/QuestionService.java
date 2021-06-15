package by.telegram.bot.service;

import by.telegram.bot.entity.AnswerValue;
import by.telegram.bot.entity.Question;
import by.telegram.bot.repository.QuestionRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Data
@NoArgsConstructor
@Service
public class QuestionService {
    private QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Transactional
    public String getQuestion(String state) {
        return questionRepository.findQuestionByStateOrderByAsk(state).getAsk();
    }

    @Transactional
    public Question getQuestionOnly(String state) {
        return questionRepository.findQuestionByStateOrderByAsk(state);
    }

    @Transactional
    public List<AnswerValue> getAllAnswers(String state) {
        return questionRepository.findQuestionByStateOrderByAsk(state).getAnswer();
    }
}
