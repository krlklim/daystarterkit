package mainPart.springConfiguration;

import mainPart.bot.botUtils.QuestionAnswerGenerator;
import mainPart.bot.botUtils.UserScoreHandler;
import mainPart.bot.botUtils.UserSessionHandler;
import mainPart.dataBaseUtil.QuestionAndAnswerDao;
import mainPart.dataBaseUtil.QuestionAndAnswerDaoImpl;
import mainPart.dataBaseUtil.UserSessionDao;
import mainPart.dataBaseUtil.UserSessionDaoImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.TelegramBotsApi;

@Configuration
public class ApplicationConfiguration {

    @Bean
    public UserSessionDao userSessionDao(){
        return new UserSessionDaoImpl();
    }

    @Bean
    public QuestionAndAnswerDao questionAndAnswerDao(){
        return new QuestionAndAnswerDaoImpl();
    }

    @Bean
    public UserSessionHandler userSessionHandler(){
        return new UserSessionHandler();
    }

    @Bean
    public QuestionAnswerGenerator questionAnswerGenerator(){
        return new QuestionAnswerGenerator();
    }

    @Bean
    public UserScoreHandler userScoreHandler(){
        return new UserScoreHandler();
    }



    @Bean
    public TelegramBotsApi telegramBotsApi(){
        return new TelegramBotsApi();
    }
}
