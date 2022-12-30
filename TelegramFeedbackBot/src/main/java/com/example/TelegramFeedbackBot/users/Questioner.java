package com.example.TelegramFeedbackBot.users;

import com.example.TelegramFeedbackBot.tools.Sender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Questioner extends User {
    private String nick = null;

    public Questioner() {
        String [] commands = {"/change mode", "/change nickname"};
        setCommands(commands);
    }

    @Override
    public void execute(String command) {
        Sender sender = new Sender();
        String textToSend;

        if (nick == null) {
            this.nick = command;
            questioners.put(getChatID(), nick);
//            nicksOfQuestioners.add(getChatID());
            textToSend = "Well, your nickname (" + nick + ") saved \n" +
                    "If you want to change your nickname print - /change nickname";
        }  else {
            if (command.equals("/change nickname")) {
                this.nick = null;
                questioners.remove(getChatID());
                //nicksOfQuestioners.remove(getChatID());
                textToSend = "Please come up with your nickname, as a questioner";
            } else
                textToSend = "unknown command";
        }
        sender.send(textToSend, getChatID(), getCommands());
    }

    //private static ArrayList<String> nicksOfQuestioners = new ArrayList<>();
    private static Map<String, String> questioners = new HashMap<>(); // [chatID : nick]

    public static Map<String, String> getQuestioners() { return questioners; }

}