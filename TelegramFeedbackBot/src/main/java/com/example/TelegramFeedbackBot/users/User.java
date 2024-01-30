package com.example.TelegramFeedbackBot.users;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.ArrayList;

public abstract class User {
    private final String [] defCommands = {"/start as an admin", "/start as a questioner", "/start as a feedbacker"};

    private String chatID;

    private String [] commands;

    public String[] getDefCommands() { return defCommands; }

    public String getChatID() { return chatID; }

    String [] getCommands() { return commands; }


    public abstract SendMessage process(String command);

    public void setChatID(String id) { this.chatID = id; }

    void setCommands(String [] commands) { this.commands = commands; }
}