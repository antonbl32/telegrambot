package by.telegram.bot.handler;

import by.telegram.bot.en.State;
import by.telegram.bot.entity.AnswerValue;
import by.telegram.bot.service.QuestionService;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
public class KeyBoard {
    private QuestionService questionService;
    private InlineKeyboardMarkup replyKeyboardMarkup = new InlineKeyboardMarkup();
    public void setState(State state) {
        int callBackId=1;
        List<List<InlineKeyboardButton>> list = new ArrayList<>();
        List<AnswerValue> answerValue= questionService.getAllAnswers(state);
        if((answerValue.size()%2)==0){
            for (int i = 0; i < answerValue.size(); i+=2) {
                List<InlineKeyboardButton> row=new ArrayList<>();
                row.add(new InlineKeyboardButton().setCallbackData(String.valueOf(callBackId++))
                        .setText(answerValue.get(i).getValue()));
                if(i!= answerValue.size()-1){
                    row.add(new InlineKeyboardButton().setCallbackData(String.valueOf(callBackId++))
                            .setText(answerValue.get(i+1).getValue()));
                }
            }
        }
        replyKeyboardMarkup.setKeyboard(list);
    }

    public InlineKeyboardMarkup getReplyKeyboardMarkup(State state) {
        setState(state);
        return replyKeyboardMarkup;
    }
}
