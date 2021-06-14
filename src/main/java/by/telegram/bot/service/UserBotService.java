package by.telegram.bot.service;

import by.telegram.bot.entity.UserBot;
import by.telegram.bot.repository.QuestionRepository;
import by.telegram.bot.repository.UserBotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.User;

@Service
public class UserBotService {
    private UserBotRepository userBotRepository;
    @Autowired
    public UserBotService(UserBotRepository userBotRepository) {
        this.userBotRepository = userBotRepository;
    }

    @Transactional
    public boolean checkUser(long id, User user){
        if(userBotRepository.findAllByChatId(id).isPresent()){
            return true;
        }else{
            UserBot userBot=new UserBot();
            userBot.setChatId(id);
            userBot.setStatus("NONE");
            if(user.getUserName()!=null){
                userBot.setUsername(user.getUserName());
            }else{
                userBot.setUsername(user.getFirstName()+" "+user.getLastName());
            }
            userBotRepository.save(userBot);
            return false;
        }
    }

    public UserBot getUserByIdChat(long id){
        return userBotRepository.findAllByChatId(id).get();
    }

    public void save(UserBot userBot) {
        userBotRepository.save(userBot);
    }
}
