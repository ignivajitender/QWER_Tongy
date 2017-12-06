package com.igniva.qwer.ui.activities.twilio_chat.listeners;

public interface TaskCompletionListener<T, U> {

  void onSuccess(T t);

  void onError(U u);
}
