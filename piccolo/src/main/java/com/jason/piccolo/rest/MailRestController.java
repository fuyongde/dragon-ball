package com.jason.piccolo.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jason.bulma.api.request.MailRequest;
import com.jason.piccolo.inner.BulmaClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;
import javax.jms.TextMessage;

@RestController
@RequestMapping(value = "/api/mails")
public class MailRestController {

  @Autowired
  Destination destination;
  @Autowired
  private BulmaClient bulmaClient;
  @Autowired
  private JmsTemplate jmsTemplate;


  @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public String mail(@RequestBody MailRequest mailRequest) {
    return bulmaClient.mail(mailRequest);
  }

  @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public String mailAdd(@RequestBody MailRequest mailRequest) throws JsonProcessingException {

    ObjectMapper mapper = new ObjectMapper();

    jmsTemplate.send(destination, (session) -> {
      TextMessage message = session.createTextMessage();
      try {
        message.setText(mapper.writeValueAsString(mailRequest));
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
      return message;
    });

    return "success";
  }
}
