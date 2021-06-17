package by.telegram.bot.repository;

import by.telegram.bot.entity.UserBot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserBotRepository extends JpaRepository<UserBot,Integer> {
    Optional<UserBot> findAllByChatId(long id);
    List<UserBot> findAll();
}
