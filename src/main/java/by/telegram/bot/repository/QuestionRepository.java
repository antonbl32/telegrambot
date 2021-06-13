package by.telegram.bot.repository;

import by.telegram.bot.en.State;
import by.telegram.bot.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Integer> {
    List<Question> findAllById(Integer id);
    Question findAllByState(State state);
}
