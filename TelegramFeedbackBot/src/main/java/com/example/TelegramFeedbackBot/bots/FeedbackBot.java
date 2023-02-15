package com.example.TelegramFeedbackBot.bots;

import com.example.TelegramFeedbackBot.tools.SendMessageSetter;
import com.example.TelegramFeedbackBot.users.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

@Component
public class FeedbackBot extends TelegramLongPollingBot {
    private ArrayList<User> usersArr = new ArrayList<>();

    private String chatID;
    private int numInUsersArr = -1; //if -1 -> user isn't in usersArr

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String receivedText = update.getMessage().getText();
            this.chatID = update.getMessage().getChatId().toString();
            boolean userIsSet = false;
            User user = null;

            if (usersArr.size() > 0) {
                for (User tempUser : usersArr) {
                    if (tempUser.getChatID().equals(this.chatID)) {
                        user = tempUser;
                        userIsSet = true;
                        break;
                    }
                }
            }


            if (!userIsSet) {
                setUser(UserType.DEFAULTUSER);
                user = usersArr.get(usersArr.size() - 1);
            }

            user.process(receivedText);

            if (user.userTypeChanged()) {
                setUser(user.getNewUserType());

            }


            boolean defCommand = this.defCommand(receivedText);
            if (!defCommand) {
                if (numInUsersArr == -1) {
                    //sender.send("unknown command", this.chatID, "/start");
                }

            } else if (defCommand && !(numInUsersArr == -1) )
                usersArr.remove(numInUsersArr);
        }
    }

    private boolean defCommand(String command) {
        String textToSend = null;
        boolean defCommand = true;
        if (command.equals("/start") || command.equals("/change mode")) {
            textToSend = "Please choose the mode";
        } else {
            switch (command) {
                case "/start as an admin" -> {
                    setUser(UserType.ADMIN);
                    textToSend = "Please enter the admin password \n " +
                            "If you don't know the password, you can start as another user";
                }
                case "/start as a questioner" -> {
                    setUser(UserType.QUESTIONER);
                    textToSend = "Please come up with your nickname, as a questioner";
                }
                case "/start as a feedbacker" -> {
                    setUser(UserType.FEEDBACKER);
                    textToSend = "well";
                }
                default -> {
                    return false;
                }
            }
        }
        //sender.send(textToSend, this.chatID, this.defCommands);
        return defCommand;
    }

    private void setUser(UserType userType) {
        User user;
        switch (userType){
            case ADMIN -> user = new Admin();
            case FEEDBACKER -> user = new Feedbacker();
            case QUESTIONER -> user = new Questioner();
            default -> user = new DefaultUser();
        }
        user.setChatID(this.chatID);
        usersArr.add(user);
    }

    public void setBot(String botName, String botToken) {
        this.botName = botName;
        this.botToken = botToken;
    }

    private void sendMessage(SendMessage messageToSend) {
        try {
            execute(messageToSend);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Value("${FeedbackBot.name}")
    private String botName;

    @Value("${FeedbackBot.token}")
    private String botToken;

    @Override
    public String getBotUsername(){ return botName; }

    @Override
    public String getBotToken() { return botToken; }
}