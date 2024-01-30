package com.example.TelegramFeedbackBot.users;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class DefaultUser extends User {
    public DefaultUser() {
        String [] commands = getDefCommands();
        setCommands(commands);
    }

    @Override
    public SendMessage process(String command) {
        return null;//
    }
}
