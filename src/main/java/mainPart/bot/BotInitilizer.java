package mainPart.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@Component
public class BotInitilizer {

    private DailyBot dailyBot;
    private TelegramBotsApi telegramBotsApi;

    @Autowired
    public BotInitilizer(DailyBot dailyBot, TelegramBotsApi telegramBotsApi){
        try{
            telegramBotsApi.registerBot(dailyBot);
        } catch (TelegramApiException ex){
            ex.printStackTrace();
        }

    }
}
