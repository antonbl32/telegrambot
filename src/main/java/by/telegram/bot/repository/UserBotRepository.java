package by.telegram.bot.repository;

import by.telegram.bot.entity.UserBot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBotRepository extends JpaRepository<UserBot,Integer> {
}
