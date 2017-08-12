package com.jason.piccolo.inner;

import com.jason.bulma.api.request.MailRequest;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient("bulma")
public interface BulmaClient {

  @PostMapping("/bulma/api/mails")
  String mail(MailRequest mailRequest);
}
