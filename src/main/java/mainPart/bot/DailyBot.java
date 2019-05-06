package mainPart.bot;
import info.Reminder;
import info.*;
import info.Models.ModelCurrency;
import info.Models.ModelITNews;
import info.Models.ModelWeather;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import mainPart.bot.botUtils.QuestionAnswerGenerator;
import mainPart.bot.botUtils.UserScoreHandler;
import mainPart.bot.botUtils.UserSessionHandler;
import mainPart.model.UserScore;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DailyBot extends TelegramLongPollingBot{
    private final String BOT_USER_NAME = "ourdailybot";
    private final String TOKEN = "684603681:AAHrmSdNK_t-al4vv7oa1X5E_h_l5_z9nTs";
    String a = "";
    boolean menu = true;
    boolean menu1 = false;

    @Autowired
    QuestionAnswerGenerator questionAnswerGenerator;

    @Autowired
    UserSessionHandler userSessionHandler;

    @Autowired
    UserScoreHandler userScoreHandler;


    public SendMessage sendComm(Message message, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(text);
        return sendMessage;
    }

    public void sendMsg(Message message, String text){
        SendMessage sendMessage = sendComm(message, text);

        try{
            setMainButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    private void sendNewsMess(Message message, String text){
        SendMessage sendMessage = sendComm(message, text);
        try{
            setNewsButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException ex){
            ex.printStackTrace();
        }
    }
    private void sendWeatherMess(Message message, String text){
        SendMessage sendMessage = sendComm(message, text);
        try{
            setWeatherButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException ex){
            ex.printStackTrace();
        }
    }

    private void sendEntMess(Message message, String text){
        SendMessage sendMessage = sendComm(message, text);
        try{
            setEntButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException ex){
            ex.printStackTrace();
        }
    }

    @Deprecated
    private void sendMessage(Message message, String text){
        SendMessage sendMessage = sendComm(message, text);
        try{
            execute(sendMessage);
        } catch (TelegramApiException ex){
            ex.printStackTrace();
        }
    }

    public void setWeatherButtons(SendMessage sendMessage){
        String button1 = "/unit";
        String button2 = "/back";
        setButtons(sendMessage, button1, button2);
    }

    public void setNewsButtons(SendMessage sendMessage){
        String button1 = "/topHeadlines";
        String button2 = "/businessNews";
        String button3 = "/back";
        setThreeButtons(sendMessage, button1, button2, button3);
    }

    public void setEntButtons(SendMessage sendMessage){
        String button1 = "/question";
        String button2 = "/quiz";
        String button3 = "/back";
        setThreeButtons(sendMessage, button1, button2, button3);
    }

    public ReplyKeyboardMarkup setCommButtons(SendMessage sendMessage){
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

        // set the markup for our keyboard (link our message with our keyboard)
        sendMessage.setReplyMarkup(replyKeyboardMarkup);

        // parameters that will display the keyboard to specific users or all users
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);

        // the parameter that indicates to hide the keyboard after pressing a button or not to hide
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        return replyKeyboardMarkup;
    }


    public void setThreeButtons(SendMessage sendMessage, String button1, String button2, String button3){

        ReplyKeyboardMarkup replyKeyboardMarkup = setCommButtons(sendMessage);
        // creat buttons
        List<KeyboardRow> keyboardRowList = new ArrayList();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton(button1));
        keyboardFirstRow.add(new KeyboardButton(button2));

        keyboardRowList.add(keyboardFirstRow);


        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton(button3));

        keyboardRowList.add(keyboardSecondRow);
        // set list on the keyboard
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }

    public void setButtons(SendMessage sendMessage, String button1, String button2){
        ReplyKeyboardMarkup replyKeyboardMarkup = setCommButtons(sendMessage);

        // creat buttons
        List<KeyboardRow> keyboardRowList = new ArrayList();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        keyboardFirstRow.add(new KeyboardButton(button1));

        keyboardFirstRow.add(new KeyboardButton(button2));

        keyboardRowList.add(keyboardFirstRow);

        // set list on the keyboard
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }


    public void setMainButtons(SendMessage sendMessage){
        // keyboard initialization
        ReplyKeyboardMarkup replyKeyboardMarkup = setCommButtons(sendMessage);

        // creat buttons
        List<KeyboardRow> keyboardRowList = new ArrayList();

        KeyboardRow keyboardFirstRow = new KeyboardRow();
        //keyboardFirstRow.add(new KeyboardButton("/weather"));
        keyboardFirstRow.add(new KeyboardButton("/weather"));
        keyboardFirstRow.add(new KeyboardButton("/currency"));

        keyboardRowList.add(keyboardFirstRow);


        KeyboardRow keyboardSecondRow = new KeyboardRow();
        keyboardSecondRow.add(new KeyboardButton("/news"));
        keyboardSecondRow.add(new KeyboardButton("/notes"));

        keyboardRowList.add(keyboardSecondRow);


        KeyboardRow keyboardThirdRow = new KeyboardRow();
        keyboardThirdRow.add(new KeyboardButton("/entertainment"));
        keyboardThirdRow.add(new KeyboardButton("/help"));

        keyboardRowList.add(keyboardThirdRow);


        // set list on the keyboard
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }



    private void executeSendMainMenu(Long chatId){
        menu1 = true;
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Выберите команду: ");
        menu1=true;
        sendMessage.setReplyMarkup(getMainBotMarkup());
        menu1=true;
        try {
            menu1=true;
            execute(sendMessage);
            menu1=true;
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpdateReceived(Update update){

        menu1=false;
        ModelWeather modelWeather = new ModelWeather();
        ModelCurrency modelCurrency = new ModelCurrency();
        ModelITNews modelITNews = new ModelITNews();
        Emoji emoji;
        emoji = Emoji.ELECTRIC_LIGHT_BULB;
        String str = "";
        str += emoji + "   Команды бота:   " + emoji + "\n\n";

        emoji = Emoji.SUN_BEHIND_CLOUD;
        str += emoji + "  Узнать погоду : /weather" +"\n";

        emoji = Emoji.LAPTOP;
        str += emoji+ "  Узнать топ-заголовки: /topHeadlines" + "\n";

        emoji = Emoji.BAR_CHART;
        str += emoji + "  Узнать новости: /businessNews\n";

        emoji = Emoji.MONEY;
        str += emoji+ "  Узнать курсы валют: /currency" + "\n";

        emoji = Emoji.HAPPY_PERSON_RAISING_ONE_HAND;
        str += emoji+ "  Шар судьбы: /question" + "\n";

        emoji = Emoji.SPEEDBOAT;
        str += emoji+ "  Заметки и напоминания: /notes" + "\n";

        emoji = Emoji.DISAPPOINTED_BUT_RELIEVED_FACE;
        str += emoji+ "  Написать разработчикам: /setting" + "\n";




        Message message = null;

        //Текст сообщения от пользователя
        String userMessageText = null;
        String userName = null;

        Long userId = null;
        Long chatId = null;


        if(update.hasMessage()){
            message = update.getMessage();
            userId = message.getFrom().getId().longValue();
            try{
                userName = message.getFrom().getUserName().toLowerCase();
            }
            catch (NullPointerException e){
                System.out.println(e.getMessage());
                userName = "nullName".toLowerCase();
            }

            chatId = message.getChatId();
            userMessageText = message.getText().toLowerCase();

        }

        // Get pressed button from user.
        if(update.hasCallbackQuery()){
            message = update.getCallbackQuery().getMessage();
            userMessageText = update.getCallbackQuery().getData();
            chatId = message.getChatId();
            userName = update.getCallbackQuery().getFrom().getUserName();
            userId = update.getCallbackQuery().getFrom().getId().longValue();
        }


        // Сессия с написавшем пользователем не активна (нет заданного вопроса викторины).
        if(!userSessionHandler.sessionIsActive(userId) && userMessageText != null){


            if(userMessageText.contains("/helpQuiz")){
                menu1=true;
                executeSendTextMessage(chatId, Emoji.BLACK_QUESTION_MARK_ORNAMENT +
                        " Чтобы получить новый вопрос: /quiz.\n " +
                        "Для ответа на один вопрос даётся 20 секунд, " +
                        "по истечению этого времени, ответ не засчитывается.\n " +
                        "За правильный ответ засчитывается 1 балл. \n" +
                        "Для просмотра своего счета: /score.");
            }

            if(userMessageText.contains("/score")){
                menu1=true;
                // Проверяем наличие текущего пользователя в таблице БД "score",
                if(userScoreHandler.userAlreadyInChart(userId)){
                    executeSendTextMessage(chatId, "Ваш счет: " + String.valueOf(userScoreHandler.getUserScoreById(userId)));
                   // При наличии текущего пользователя в таблице - отправляем счет игры.
                   } else{
                    executeSendTextMessage(chatId, "Запись во вашему счету отсутствует, " +
                            "вероятно вы еще не играли в викторину. " +
                            "Для начала пришлите /quiz.");
//
                }


            }

            if(userMessageText.contains("/top5")){
                menu1=true;
                List<UserScore> topUsersScoreList = userScoreHandler.getTopFiveUserScore();
                String topUsersScoreString = topUsersScoreList.stream()
                        .map(UserScore::getUserName)
                        .collect(Collectors.joining("\n"));
                executeSendTextMessage(chatId, topUsersScoreString);

            }

            // Начало новой викторины.
            if(userMessageText.contains("/quiz")){
menu1=true;
                // Получаем новый вопрос + ответ из генератора в виде одной строки.
                String questionAndAnswer = questionAnswerGenerator.getNewQuestionAndAnswerForUser();
                String [] questionAndAnswerArray = questionAndAnswer.split("\\|");
                String question = questionAndAnswerArray[0];
                // Создаем сессию с текущим пользователем
                userSessionHandler.createUserSession(userId, questionAndAnswer);

                // Проверяем наличие текущего пользователя в таблице БД "score",
                // при отсутствии - добавляем пользователя в таблицу со счетом 0.
                if(!userScoreHandler.userAlreadyInChart(userId)){
                    userScoreHandler.addNewUserInChart(userId, userName);
                }

                executeSendTextMessage(chatId, question);
                // Отвечаем пользователю, если сообщение не содержит явных указаний для бота (default bot's answer)
            }

            else {
                System.out.println("message" + message);

                String command = userMessageText.split(" ")[0];


                switch(command) {
                    case "/helpNotes":
                        menu1=true;
                        showHelp(update);
                        break;

                    case "/notes":
                        menu1=true;
                        showHelp(update);
                        break;

                    //заметки
                    case "/reminder":
                        menu1=true;
                        setNewReminder(update);
                        break;
                    case "/edit":
                        menu1=true;
                        editReminder(update);
                        break;
                    case "/list":
                        menu1=true;
                        listAllReminders(update);
                        break;
                    case "/search":
                        menu1=true;
                        searchReminder(update);
                        break;
                    case "/delete":
                        menu1=true;
                        deleteReminder(update);
                        break;

                 //развлечения
                    case "/yesorno":
                        menu1=true;
                        yesOrNo(update);

                        break;
                }

                switch (message.getText()) {
                    case "/start":
                        menu1=true;
                        sendMsg(message, str);
                        break;

                    case "/help":
                        menu1=true;
                        sendMsg(message, str);
                        break;
                    case "/setting":
                        menu1=true;
                        sendMsg(message, "Что-то не нравится? Есть вопросы к разработчику?\n" +
                                "Напиши, разберёмся. Telegram: @kastenkamasha\n@krlklim " + Emoji.SLEEPY_FACE);
                        break;
                    case "/back":
                        menu1=true;
                        sendMsg(message, "Вы вернулись в главное меню");
                        break;


                    case "/question":
                        menu1=true;
                        sendEntMess(message, "Задай свой вопрос и получили в ответ \"ДА\" либо \"НЕТ\". /yesorno <Твой вопрос>" );
                        break;

                     //погода
                    case "/weather":
                        menu1=true;
                        sendWeatherMess(message, "Введите название города, погоду которого вы хотите узнать.");
                        a = "weather";
                        break;
                    case "/unit":
                        menu1=true;
                        sendWeatherMess(message, "Если вы выбираете метрическую систему измерения: /metric\n" +
                                "Если имперскую, то отправьте /imperial");
                        modelWeather.setUnits("metric");
                        break;
                    case "/metric":
                        menu1=true;
                        sendWeatherMess(message, "Вы выбрали метрическую систему");
                        modelWeather.setUnits("metric");
                        break;
                    case "/imperial":
                        menu1=true;
                        sendMsg(message, "Вы выбрали имперскую систему");
                        modelWeather.setUnits("imperial");
                        break;



                   //новости
                    case "/topHeadlines":
                        menu1=true;
                        try {
                            sendNewsMess(message, TopHeadlinesNews.getTopHeadlinesNews(message.getText(), modelITNews));
                        } catch (IOException e ) {
                            sendNewsMess(message, "Извините, на данный момент нет актуальной информации :(");
                        }
                        break;
                    case "/news":
                        menu1=true;
                        sendNewsMess(message, "Выберите тип новостей.");
                        modelWeather.setUnits("imperial");
                        break;
                    case "/entertainment" :
                        menu1 = true;
                        sendEntMess(message, "Во что сыграем? Или может судьбу испытаем?");
                        break;
                    case "/businessNews":
                        menu1=true;
                        try {
                            sendNewsMess(message, BusinessNews.getBusinessNews(modelITNews));
                        } catch (IOException e) {
                            sendNewsMess(message, "Извините, на данный момент нет актуальной информации :(");
                        }
                        break;



                        //курсы валют
                    case "/currency":
                        menu1=true;
                        sendMsg(message, "Если хотите узнать курсы для всех валют: /currencyAll\n" +
                                "Если хотите узнать курсы для главных валют(UAH, USD, EUR, PLN): /currencyMain");
                        break;
                    case "/currencyAll":
                        menu1=true;
                        try {
                            sendMsg(message, Currency.getCurrency(modelCurrency));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case "/currencyMain":
                        menu1=true;
                        try {
                            sendMsg(message, Currency.getCurrency());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;


                        default:
                            if (a == "weather") {
                                menu1=true;
                                a = "";
                                try {
                                    sendWeatherMess(message, Weather.getWeather(message.getText(), modelWeather));
                                } catch (IOException e) {
                                    sendWeatherMess(message, "Такой город не был найден.");
                                }
                            }
                            if(a=="tryQ"){
                                menu1=true;
                                a = "";
                                tryQ(chatId, userId, userMessageText);
                            }
                }


                if(!menu1) {
                    sendMsg(message, "Я не знаю такой команды. :((");
                }
            }

        } else if(userSessionHandler.sessionIsActive(userId) && userMessageText != null){

tryQ(chatId, userId, userMessageText);

        }


    }


public void tryQ(Long chatId, Long userId, String userMessageText){
    LocalDateTime currentDate = LocalDateTime.now();
    boolean fullAnswer = false;
    boolean attempt = false;
    String rightAnswer = userSessionHandler.getAnswerFromSession(userId).toLowerCase();


    if(userSessionHandler.validateDate(currentDate, userId)){



        if(rightAnswer.contains(userMessageText)){

            executeSendTextMessage(chatId, "Поздравляю! Ответ правильный!");
//
            // Увеличиваем счет пользователя на 1.
            userScoreHandler.incrementUserScore(userId);
            // Удаляем текущую сессию пользователя.
            //исправила
            userSessionHandler.deleteUserSession(userId);
            fullAnswer = true;
            executeSendMainMenu(chatId);
        } else if(!rightAnswer.contains(userMessageText) && userSessionHandler.validateDate(currentDate, userId)){
            // Неверный ответ, удаляем сессию.
            //   executeSendTextMessage(chatId, "Ответ неверный! Можете теперь смело идти в гугл.");


            executeSendTextMessage(chatId, "Ответ неверный! Но у вас ёще есть некоторое время.");
            //     attempt = true;
            a = "tryQ";
            rightAnswer = userSessionHandler.getAnswerFromSession(userId).toLowerCase();


            //исправила

            fullAnswer = false;

            // userSessionHandler.deleteUserSession(userId);
        }
        else{
            executeSendTextMessage(chatId, "Ответ неверный, да и время закончилось...\n" +
                    " Можете теперь смело идти в гугл.");
            //исправила
            fullAnswer = true;
            userSessionHandler.deleteUserSession(userId);
            executeSendMainMenu(chatId);
        }

        // Сообщение по истчению 20 секунд, отведенных на сессию пользователя.
        // При этом удаляется запись в БД.
    } else{
        executeSendTextMessage(chatId, "Увы, время на ответ уже вышло.");
//                sendMessage(message, "Время на ответ вышло.");
        //исправила
        userSessionHandler.deleteUserSession(userId);
        executeSendMainMenu(chatId);
    }
}

    private void executeSendTextMessage(Long chatId, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method builds main bot menu buttons that contains basic bot commands.
     * @return InlineKeyboardMarkup object with build menu.
     */
    private InlineKeyboardMarkup getMainBotMarkup(){
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInLine = new ArrayList<>();
        List<InlineKeyboardButton> firstRow = new ArrayList<>();
        List<InlineKeyboardButton> secondRow = new ArrayList<>();
        List<InlineKeyboardButton> thirdRow = new ArrayList<>();
        List<InlineKeyboardButton> fourthRow = new ArrayList<>();
        firstRow.add(new InlineKeyboardButton().setText(Emoji.ALARM_CLOCK + " Получить вопрос " + Emoji.ALARM_CLOCK).setCallbackData("/quiz"));
        secondRow.add(new InlineKeyboardButton().setText("Мой счёт").setCallbackData("/score"));
        thirdRow.add(new InlineKeyboardButton().setText("Топ 5").setCallbackData("/top5"));
        fourthRow.add(new InlineKeyboardButton().setText("Правила игры").setCallbackData("/helpQuiz"));
        rowsInLine.add(firstRow);
        rowsInLine.add(secondRow);
        rowsInLine.add(thirdRow);
        rowsInLine.add(fourthRow);
        markupInline.setKeyboard(rowsInLine);
        return markupInline;
    }

    /**
     *
     * Send text constructed by Bot to user who's asking.
     *
     *
     */


    private void showHelp(Update update) {
        SendMessage answer = new SendMessage();
        answer.setChatId(update.getMessage().getChatId());

        Emoji emoji;
        emoji = Emoji.ELECTRIC_LIGHT_BULB;
        String helpQuiz = "";
        helpQuiz += emoji + "   Как работать с заметками?:   " + emoji + "\n\n";

        emoji = Emoji.HEAR_NO_EVIL_MONKEY;
        helpQuiz += emoji + "  Добавить заметку : /reminder" +"\n";

        emoji = Emoji.FACE_WITH_MEDICAL_MASK;
        helpQuiz += emoji+ "  Редактировать заметку: /edit" + "\n";

        emoji = Emoji.CRYING_FACE;
        helpQuiz += emoji + "  Удалить заметку: /delete\n";

        emoji = Emoji.CAT_FACE_WITH_TEARS_OF_JOY;
        helpQuiz += emoji+ "  Просмотреть список заметок: /list" + "\n";

        emoji = Emoji.SMILING_FACE_WITH_SMILING_EYES;
        helpQuiz += emoji+ "  Поиск заметок: /search" + "\n";

        sendAnswer(update.getMessage().getChatId(), helpQuiz , true);
    }

    /*
     * 	the reminder file only got the chat id as name option as there might be groups that organize
     * 	their reminders/deadlines/whatever as teams. chatID_senderID.json would prevent that.
     */

    private void setNewReminder(Update update) {
        updateReminder(update);

        String response = "Эу, " + update.getMessage().getFrom().getUserName() + ", Я сохранил твою заметку.";
        sendAnswer(update.getMessage().getChatId(), response, false);
    }

    private void updateReminder(Update update) {
        Path reminderFile = Paths.get(
                "reminder_data/"
                        + update.getMessage().getChatId()
                        + ".json"
        );
        String message = update.getMessage().getText().replaceFirst("/reminder ", "");

        List<Reminder> allReminder = Reminder.readData(reminderFile);
        Reminder reminder = new Reminder(LocalDateTime.now(), message);
        allReminder.add(reminder);
        Reminder.writeData(allReminder, reminderFile);
    }

    private void editReminder(Update update) {
        long chatID = update.getMessage().getChatId();
        List<Reminder> allReminder = getReminders(update);
        if (allReminder == null) {
            return;
        }

        int index;
        String message = update.getMessage().getText().replaceFirst("/edit ", "");
        try {
            index = Integer.valueOf(message.split(" ")[0]);
            message = message.replaceFirst(index + " ", "");
        } catch (NumberFormatException e) {
            String response = "*Упс! Повторите ввод!* Выберите число между 1 и "
                    + allReminder.size() + ".";
            sendAnswer(chatID, response, true);
            return;
        }

        allReminder.get(index - 1).setMessage(message);
        Reminder.writeData(allReminder, Paths.get("reminder_data/" + update.getMessage().getChatId() + ".json"));
        sendAnswer(chatID, "*Заметка отредактирована!*", true);
    }

    private void listAllReminders(Update update) {
        List<Reminder> allReminder = getReminders(update);
        if (allReminder == null) {
            return;
        }

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
        StringBuilder strBuilder = new StringBuilder("Здесь ваш список заметок:\n\n");
        for (Reminder reminder : allReminder) {
            strBuilder.append("*[").append(allReminder.indexOf(reminder) + 1).append(" - ")
                    .append(reminder.getCreation().format(dateFormat)).append("]*\n")
                    .append("_").append(reminder.getMessage()).append("_\n\n");
        }

        sendAnswer(update.getMessage().getChatId(), strBuilder.toString(), true);
    }

    private void searchReminder(Update update) {
        List<Reminder> allReminder = getReminders(update);
        if (allReminder == null) {
            return;
        }

        String searchText = update.getMessage().getText().replaceAll("/search ", "").toLowerCase();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");
        StringBuilder strBuilder = new StringBuilder("Найдены совпадающие заметки:\n\n");
        for (Reminder reminder : allReminder) {
            if (!reminder.getMessage().toLowerCase().contains(searchText)) {
                continue;
            }

            strBuilder.append("*[").append(allReminder.indexOf(reminder) + 1).append(" - ")
                    .append(reminder.getCreation().format(dateFormat)).append("]*\n")
                    .append("_").append(reminder.getMessage()).append("_\n\n");
        }

        sendAnswer(update.getMessage().getChatId(), strBuilder.toString(), true);
    }

    private void deleteReminder(Update update) {
        List<Reminder> allReminder = getReminders(update);
        if (allReminder == null) {
            return;
        }

        int index;
        long chatID = update.getMessage().getChatId();
        String message = update.getMessage().getText().replaceFirst("\\s", "");
        try {
            index = Integer.valueOf(message.replace("/delete", "")) - 1;
        } catch (NumberFormatException e) {
            String response = "*Упс! Повторите ввод!* Выберите число между 1 и "
                    + allReminder.size() + ".";
            sendAnswer(chatID, response, true);
            return;
        }

        if (index < 0 || index >= allReminder.size()) {
            String response = "*Упс! Повторите ввод!* Выберите число между 1 и "
                    + allReminder.size() + ".";
            sendAnswer(chatID, response, true);
            return;
        }

        allReminder.remove(index);
        Reminder.writeData(allReminder, Paths.get("reminder_data/" + update.getMessage().getChatId() + ".json"));
        sendAnswer(chatID, "*Заметка удалена!*", true);
    }

    private List<Reminder> getReminders(Update update) {
        Path reminderFile = Paths.get("reminder_data/" + update.getMessage().getChatId() + ".json");
        List<Reminder> allReminder = Reminder.readData(reminderFile);

        if (allReminder.isEmpty()) {
            sendAnswer(update.getMessage().getChatId(), "*У вас нет ещё ни одной заметки!*", true);
            return null;
        }

        return allReminder;
    }

    private void yesOrNo(Update update) {
        SendMessage answer = new SendMessage();
        answer.setChatId(update.getMessage().getChatId());
        double rdm = Math.random();
        long chatID = update.getMessage().getChatId();

        if (rdm <= 0.5) {
            String response = update.getMessage().getFrom().getUserName() + ", мой ответ: ДА!";
            sendAnswer(chatID, response, false);
            return;
        }

        String response = update.getMessage().getFrom().getUserName() + ", наверное, не сегодня! Мой ответ: НЕТ!";
        sendAnswer(chatID, response, false);
    }

    private void sendAnswer(long chatID, String message, boolean markdown) {
        try {
            SendMessage answer = new SendMessage();
            answer.setChatId(chatID);
            answer.setText(message);
            answer.enableMarkdown(markdown);
            execute(answer);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return this.BOT_USER_NAME;
    }

    @Override
    public String getBotToken() {
        return this.TOKEN;
    }
}
