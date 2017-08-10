package com.jason.bulma.rest;

import com.jason.bulma.api.request.MailRequest;
import com.jason.bulma.protocol.MailMessage;
import com.jason.bulma.service.MessageSender;
import com.jason.dragon.mapper.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/mails")
public class MailRestController {

    @Autowired
    private MessageSender mailMessageSender;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String send(@RequestBody MailRequest mailRequest) {
        MailMessage mailMessage = BeanMapper.map(mailRequest, MailMessage.class);
        mailMessageSender.send(mailMessage);
        return "success";
    }
}
