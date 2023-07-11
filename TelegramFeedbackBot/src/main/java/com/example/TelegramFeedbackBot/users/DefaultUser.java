package com.example.TelegramFeedbackBot.users;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class DefaultUser extends User {
    public DefaultUser() {
        setNewUserType(UserType.DEFAULTUSER);
        String [] commands = getDefCommands();
        setCommands(commands);
    }

    @Override
    public boolean userTypeChanged() {
        return getNewUserType() != UserType.DEFAULTUSER;
    }

    @Override
    public SendMessage process(String command) {
        return null;//
    }
}
