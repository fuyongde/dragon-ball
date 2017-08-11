package com.jason.bulma.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jason.bulma.protocol.MailMessage;
import com.jason.bulma.protocol.Message;
import com.jason.bulma.service.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MailMessageSender implements MessageSender {

    private static Logger logger = LoggerFactory.getLogger(MailMessageSender.class);

    @Autowired
    private MailSender javaMailSender;

    @Autowired
    private MailProperties mailProperties;

    @Override
    public void send(Message message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailProperties.getUsername());
        mailMessage.setTo(message.getTo());
        mailMessage.setSubject(message.getSubject());
        mailMessage.setText(message.getSubject());
        javaMailSender.send(mailMessage);
    }

    @JmsListener(destination = "mail.queue")
    public void receiveQueue(String text) throws IOException {

        logger.info("send mail");

        logger.info(text);

        ObjectMapper mapper = new ObjectMapper();
        MailMessage message = mapper.readValue(text, MailMessage.class);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailProperties.getUsername());
        mailMessage.setTo(message.getTo());
        mailMessage.setSubject(message.getSubject());
        mailMessage.setText(message.getSubject());
        javaMailSender.send(mailMessage);
    }

}
