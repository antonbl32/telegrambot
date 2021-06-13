package by.telegram.bot.repository;

import by.telegram.bot.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Integer> {
    Question findQuestionByState(String state);
}
