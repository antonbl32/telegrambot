package by.telegram.bot.handler;

import by.telegram.bot.entity.AnswerValue;
import by.telegram.bot.service.QuestionService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Component
public class KeyBoard {
    private QuestionService questionService;
    @Autowired
    public KeyBoard(QuestionService questionService) {
        this.questionService = questionService;
    }
    private InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();
    public void setState(String state) {
        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        List<AnswerValue> answerValue= questionService.getAllAnswers(state);
            for (int i = 0; i < answerValue.size(); i+=2) {
                List<InlineKeyboardButton> row=new ArrayList<>();
                row.add(new InlineKeyboardButton().setCallbackData(String.valueOf(answerValue.get(i).getId()))
                        .setText(answerValue.get(i).getValue()));
                if(i!= answerValue.size()-1){
                    row.add(new InlineKeyboardButton().setCallbackData(String.valueOf(answerValue.get(i+1).getId()))
                            .setText(answerValue.get(i+1).getValue()));
                }
                list.add(row);
            }

        replyKeyboardMarkup.setKeyboard(list);
    }

    public InlineKeyboardMarkup getReplyKeyboardMarkup(String state) {
        setState(state);
        return replyKeyboardMarkup;
    }
}
