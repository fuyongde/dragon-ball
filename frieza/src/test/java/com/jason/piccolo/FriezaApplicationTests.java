package com.jason.piccolo;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FriezaApplicationTests {

  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @Before
  public void before() {
    this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
  }

}
