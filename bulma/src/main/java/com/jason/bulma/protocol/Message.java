package com.jason.bulma.protocol;

import java.util.Date;

public interface Message {

    String getFrom();

    String getReplyTo();

    String[] getTo();

    String[] getCc();

    String[] getBcc();

    Date getSentDate();

    String getSubject();

    String getText();
}
