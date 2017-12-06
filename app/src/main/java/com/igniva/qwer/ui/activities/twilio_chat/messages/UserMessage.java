package com.igniva.qwer.ui.activities.twilio_chat.messages;

import com.twilio.chat.Message;

public class UserMessage implements ChatMessage {

  private String author = "";
  private String timeStamp = "";
  private String messageBody = "";

  public UserMessage(Message message) {
    this.author = message.getAuthor();
    this.timeStamp = message.getTimeStamp();
    this.messageBody = message.getMessageBody();
  }

  @Override
  public String getMessageBody() {
    return messageBody;
  }

  @Override
  public String getAuthor() {
    return author;
  }

  @Override
  public String getTimeStamp() {
    return timeStamp;
  }
}
