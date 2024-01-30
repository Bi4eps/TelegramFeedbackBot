package com.example.TelegramFeedbackBot.users;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class Feedbacker extends User {
    private String questionersID;

    private Mode mode = Mode.CHOOSING_QUESTIONER;

    private String feedback;

    private enum Mode { CHOOSING_QUESTIONER, LEAVING_FEEDBACK, ACCEPTING_SENDING_FEEDBACK}

    public Feedbacker() {
        String [] commands = {"/change mode", "/change questioner"};
        setCommands(commands);
    }

    @Override
    public SendMessage process(String receivedText) {
        if (mode == Mode.CHOOSING_QUESTIONER || receivedText.equals("/change questioner")) {
            choosingQuestioner(receivedText);
        } else if (mode == Mode.ACCEPTING_SENDING_FEEDBACK) {
            acceptingSendingFeedback(receivedText);
        } else {
            leavingFeedback(receivedText);
        }
        return null;//
    }

    private void choosingQuestioner(String nick) {
        String textToSend = null;

        for (String el : Questioner.getQuestioners().values()) {
            if (nick.equals(el)) {
                this.questionersID = "";///
                mode = Mode.LEAVING_FEEDBACK;
                textToSend = "Well, now the bot is waiting for your feedback";
            }
        }
        if (textToSend == null) {
            textToSend = "This questioner isn't exist";
        }
        //sender.send(textToSend, getChatID(), getCommands());
    }

    private void leavingFeedback(String feedback) {
    }

    private void acceptingSendingFeedback(String command) {
        switch (command) {
            case "Yes":
                break;
            case "No":
                break;
            default:
                break;
        }
    }
}