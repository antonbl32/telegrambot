package by.telegram.bot.service;

import by.telegram.bot.entity.UserBot;
import by.telegram.bot.repository.QuestionRepository;
import by.telegram.bot.repository.UserBotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.User;

@Service
public class UserBotService {
    private UserBotRepository userBotRepository;
    private QuestionRepository questionRepository;
    @Autowired
    public void setQuestionRepository(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }
    @Autowired
    public void setUserBotRepository(UserBotRepository userBotRepository) {
        this.userBotRepository = userBotRepository;
    }
    @Transactional
    public boolean checkUser(long id, User user){
        if(userBotRepository.findAllByChatId(id).isPresent()){
            return true;
        }else{
            UserBot userBot=new UserBot();
            userBot.setChatId(id);
            if(user.getUserName()!=null){
                userBot.setUsername(user.getUserName());
            }else{
                userBot.setUsername(user.getFirstName()+" "+user.getLastName());
            }
            userBotRepository.save(userBot);
            return false;
        }
    }
    @Transactional
    public UserBot getUserByIdChat(long id){
        return userBotRepository.findAllByChatId(id).get();
    }
    @Transactional
    public void save(UserBot userBot) {
        userBotRepository.save(userBot);
    }
}
