package com.igniva.qwer.ui.activities.twilio_chat.listeners;

import com.twilio.chat.Channel;

public interface ChannelFindOrCreateListener {

  void channelCreated(Channel channel);
  void channelFind(Channel channel);

}
