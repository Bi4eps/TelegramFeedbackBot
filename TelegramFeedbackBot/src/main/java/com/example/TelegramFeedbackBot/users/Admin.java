package com.example.TelegramFeedbackBot.users;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

public class Admin extends User {
    private boolean adminAbilitiesActive = false;

    public Admin() {
        String [] commands = {"/change mode", ""};
        setCommands(commands);
    }

    @Override
    public void execute(String command) {
        if (adminAbilitiesActive) {

        } else
            adminPasswordVer(command);
    }

    private void adminPasswordVer(String receivedText) {

    }
}