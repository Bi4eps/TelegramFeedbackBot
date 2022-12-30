package com.example.TelegramFeedbackBot.tools;

import com.example.TelegramFeedbackBot.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.ArrayList;

public class Sender {
    public void send(String textToSend, String chatID, String keyboardrowText) {
        var markup = new ReplyKeyboardMarkup();
        var keyboardrow = new ArrayList<KeyboardRow>();
        KeyboardRow row = new KeyboardRow();
        row.add(keyboardrowText);
        keyboardrow.add(row);
        markup.setKeyboard(keyboardrow);
        executeSending(textToSend, chatID, markup);
    }

    public void send(String textToSend, String chatID, String [] kbrTextArr) {
        var markup = new ReplyKeyboardMarkup();
        var keyboardrow = new ArrayList<KeyboardRow>();
        for (String el : kbrTextArr) {
            KeyboardRow row = new KeyboardRow();
            row.add(el);
            keyboardrow.add(row);
        }
        markup.setKeyboard(keyboardrow);
        executeSending(textToSend, chatID, markup);
    }

    private void executeSending (String textToSend, String chatID, ReplyKeyboardMarkup markup) {
        markup.setResizeKeyboard(true);
        var message = new SendMessage();
        message.setChatId(chatID);
        message.setText(textToSend);
        message.setReplyMarkup(markup);
        Bot bot = new Bot();
        //bot.setBot("botName", "botToken");
        bot.setBot("The_FeedbackBot", "5458388234:AAFeGKzbVkAje11Nce5J2wfzXsgWuz70H0s");
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}