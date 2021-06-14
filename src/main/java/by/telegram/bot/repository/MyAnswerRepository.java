package by.telegram.bot.repository;

import by.telegram.bot.entity.MyAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MyAnswerRepository extends JpaRepository<MyAnswer, Integer> {
    List<MyAnswer> findAllByUserBot(Integer id);

    List<MyAnswer> findAllByQuestion(Integer id);
}
