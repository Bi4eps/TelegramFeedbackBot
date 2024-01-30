package com.example.TelegramFeedbackBot.bots;

import com.example.TelegramFeedbackBot.users.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.security.auth.callback.CallbackHandler;
import java.util.ArrayList;

@Component
public class FeedbackBot extends TelegramLongPollingBot {

    private ArrayList<User> arrOfUsers = new ArrayList<>();

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText())
            processMessage(update.getMessage());
    }

    private void processMessage(Message message) {
        String chatID = message.getChatId().toString();
        if (findUser(chatID) == null) {

        } else {
            findUser(chatID).process(message.getText());
        }
    }

    private User findUser(String chatID) {
        for (User tempUser : arrOfUsers) {
            if (tempUser.getChatID().equals(chatID)) {
                return tempUser;
            }
        }
        return null;
    }

    private void setUser(String chatID) {
        //to implement
    }

    @Value("${FeedbackBot.name}")
    private String botName;

    @Value("${FeedbackBot.token}")
    private String botToken;

    @Override
    public String getBotUsername() { return this.botName; }

    @Override
    public String getBotToken() { return this.botToken; }
}