package by.telegram.bot.repository;

import by.telegram.bot.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    Question findQuestionByStateOrderByAsk(String state);
    List<Question> findAll();
}
