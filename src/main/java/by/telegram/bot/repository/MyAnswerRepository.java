package by.telegram.bot.repository;

import by.telegram.bot.entity.MyAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MyAnswerRepository extends JpaRepository<MyAnswer,Integer> {
    List<MyAnswer> findAllByQuestion_Id(Integer id);
    Optional<MyAnswer> getByUserBot_IdAndQuestion_Id(Integer userBotId, Integer questionId);
    List<MyAnswer> findAllByUserBot_Id(Integer userBotId);
}
