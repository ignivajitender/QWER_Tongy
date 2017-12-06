package com.igniva.qwer.ui.activities.twilio_chat.channels;

import com.twilio.chat.Channel;

import java.util.Comparator;

public class CustomChannelComparator implements Comparator<Channel> {
  private String defaultChannelName;

  CustomChannelComparator() {
    defaultChannelName =
        "Default Channel";
  }

  @Override
  public int compare(Channel lhs, Channel rhs) {
    if (lhs.getFriendlyName().contentEquals(defaultChannelName)) {
      return -100;
    } else if (rhs.getFriendlyName().contentEquals(defaultChannelName)) {
      return 100;
    }
    return lhs.getFriendlyName().toLowerCase().compareTo(rhs.getFriendlyName().toLowerCase());
  }
}
