package com.jason.piccolo.rest;

import com.jason.bulma.api.request.MailRequest;
import com.jason.piccolo.inner.BulmaClient;
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
    private BulmaClient bulmaClient;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String mail(@RequestBody MailRequest mailRequest) {
        return bulmaClient.mail(mailRequest);
    }
}
