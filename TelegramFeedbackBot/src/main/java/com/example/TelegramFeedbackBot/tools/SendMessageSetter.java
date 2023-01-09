package com.example.TelegramFeedbackBot.tools;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;

public class SendMessageSetter {
    private String chatID;

    public String getChatID() { return this.chatID; }

    public void setChatID(String chatID) { this.chatID = chatID; }

    public SendMessage set(String text, String [] kbrTextArr) {
        return set(text, getChatID(), kbrTextArr);
    }

    public SendMessage set(String text, String kbrText) {
        return set(text, getChatID(), kbrText);
    }

    public SendMessage set(String textToSend, String chatID, String kbrText) {
        var markup = new ReplyKeyboardMarkup();
        var keyboardRow = new ArrayList<KeyboardRow>();
        KeyboardRow row = new KeyboardRow();
        row.add(kbrText);
        keyboardRow.add(row);
        markup.setKeyboard(keyboardRow);
        return executeSetting(textToSend, chatID, markup);
    }

    public SendMessage set(String textToSend, String chatID, String [] kbrTextArr) {
        var markup = new ReplyKeyboardMarkup();
        var keyboardRow = new ArrayList<KeyboardRow>();
        for (String el : kbrTextArr) {
            KeyboardRow row = new KeyboardRow();
            row.add(el);
            keyboardRow.add(row);
        }
        markup.setKeyboard(keyboardRow);
        return executeSetting(textToSend, chatID, markup);
    }

    private SendMessage executeSetting (String textToSend, String chatID, ReplyKeyboardMarkup markup) {
        markup.setResizeKeyboard(true);
        var message = new SendMessage();
        message.setChatId(chatID);
        message.setText(textToSend);
        message.setReplyMarkup(markup);
        return message;
    }
}