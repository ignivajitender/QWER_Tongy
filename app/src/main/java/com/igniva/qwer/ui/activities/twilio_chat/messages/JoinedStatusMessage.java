package com.igniva.qwer.ui.activities.twilio_chat.messages;

public class JoinedStatusMessage extends StatusMessage {


  public JoinedStatusMessage(String author) {
    super(author);
  }

  @Override
  public String getMessageBody() {
    return this.getAuthor() + " joined the channel";
  }
}
