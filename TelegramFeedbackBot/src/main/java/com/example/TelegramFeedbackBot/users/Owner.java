package com.example.TelegramFeedbackBot.users;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class Owner extends User{
    @Override
    public boolean userTypeChanged() {
        return false;
    }

    @Override
    public SendMessage process(String command) {
        return null;
    }
}
