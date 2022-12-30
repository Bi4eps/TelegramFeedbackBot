package com.example.TelegramFeedbackBot;

import com.example.TelegramFeedbackBot.tools.Sender;
import com.example.TelegramFeedbackBot.users.Admin;
import com.example.TelegramFeedbackBot.users.Feedbacker;
import com.example.TelegramFeedbackBot.users.Questioner;
import com.example.TelegramFeedbackBot.users.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;

@Component
public class Bot extends TelegramLongPollingBot {
    private ArrayList<User> usersArr = new ArrayList<>();
    private final String [] defCommands = {"/start as an admin", "/start as a questioner", "/start as a feedbacker"};
    private String chatID;
    private int numInUsersArr = -1; //if -1 -> user isn't in usersArr
    private enum UserType { ADMIN, QUESTIONER, FEEDBACKER; }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String receivedText = update.getMessage().getText();
            this.chatID = update.getMessage().getChatId().toString();
            Sender sender = new Sender();

            if (usersArr.size() > 0) {
                for (int i = 0; i < usersArr.size(); i++) {
                    if (usersArr.get(i).getChatID().equals(chatID)) {
                        numInUsersArr = i;
                        break;
                    }
                }
            }
            boolean defCommand = this.defCommand(receivedText);
            if (!defCommand) {
                if (numInUsersArr == -1) {
                    sender.send("unknown command", this.chatID, "/start");
                } else
                    usersArr.get(numInUsersArr).execute(receivedText);
            } else if (defCommand && !(numInUsersArr == -1) )
                usersArr.remove(numInUsersArr);
        }
    }

    private boolean defCommand(String command) {
        Sender sender = new Sender();
        String textToSend = null;
        boolean defCommand = true;
        if (command.equals("/start") || command.equals("/change mode")) {
            textToSend = "Please choose the mode";
        } else {
            switch (command) {
                case "/start as an admin" :
                    setUser(this.chatID, UserType.ADMIN);
                    textToSend = "Please enter the admin password \n " +
                            "If you don't know the password, you can start as another user";
                    break;
                case "/start as a questioner" :
                    setUser(this.chatID, UserType.QUESTIONER);
                    textToSend = "Please come up with your nickname, as a questioner";
                    break;
                case "/start as a feedbacker" :
                    setUser(this.chatID, UserType.FEEDBACKER);
                    textToSend = "well";
                    break;
                default:
                    return false;
            }
        }
        sender.send(textToSend, this.chatID, this.defCommands);
        return defCommand;
    }

    private void setUser(String chatID, UserType userType) {
        User user;
        switch (userType){
            case ADMIN -> user = new Admin();
            case FEEDBACKER -> user = new Feedbacker();
            default -> user = new Questioner(); //if mode = QUESTIONER
        }
        user.setChatID(chatID);
        usersArr.add(user);
    }

    public void setBot(String botName, String botToken) {
        this.botName = botName;
        this.botToken = botToken;
    }

    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;

    @Override
    public String getBotUsername(){ return botName; }

    @Override
    public String getBotToken() { return botToken; }
}