package com.example.TelegramFeedbackBot.users;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class Admin extends User {
    private boolean adminAbilitiesActive = false;

    public Admin() {
        String [] commands = {"/change mode", ""};
        setCommands(commands);
    }

    @Override
    public SendMessage process (String command) {
        if (adminAbilitiesActive) {

        } else
            adminPasswordVer(command);
        return null;//
    }

    private void adminPasswordVer(String receivedText) {

    }

}