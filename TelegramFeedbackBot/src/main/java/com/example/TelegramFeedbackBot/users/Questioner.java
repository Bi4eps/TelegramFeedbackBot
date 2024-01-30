package com.example.TelegramFeedbackBot.users;

import com.example.TelegramFeedbackBot.tools.SendMessageSetter;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import java.util.HashMap;
import java.util.Map;

public class Questioner extends User {
    private String nick = null;

    public Questioner() {
        String [] commands = {"/change mode", "/change nickname"};
        setCommands(commands);
    }

    @Override
    public SendMessage process(String command) {
        var mesSetter = new SendMessageSetter();
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
            } else textToSend = "unknown command";
        }
        return mesSetter.set(textToSend, getChatID(), getCommands());
    }

    private static Map<String, String> questioners = new HashMap<>(); // [chatID : nick]

    public static Map<String, String> getQuestioners() { return questioners; }
}