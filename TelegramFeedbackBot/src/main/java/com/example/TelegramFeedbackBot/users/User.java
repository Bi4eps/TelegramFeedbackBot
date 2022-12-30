package com.example.TelegramFeedbackBot.users;

import java.util.ArrayList;

public abstract class User {
    private String chatID;
    private String [] commands;

    public void setChatID(String id) { this.chatID = id; }
    void setCommands(String [] commands) { this.commands = commands; }

    public String getChatID() { return chatID; }
    String [] getCommands() { return commands; }

    public abstract void execute(String command);
}

