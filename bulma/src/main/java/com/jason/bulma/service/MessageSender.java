package com.jason.bulma.service;

import com.jason.bulma.protocol.Message;

public interface MessageSender {

  void send(Message message);
}
