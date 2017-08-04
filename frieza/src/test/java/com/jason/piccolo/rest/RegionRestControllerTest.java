package com.jason.piccolo.rest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * RegionRestController Tester.
 *
 * @author fuyongde
 * @version 1.0
 * @since <pre>08/03/2017</pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RegionRestControllerTest {

  private MockMvc mvc;

  @Autowired
  private WebApplicationContext context;

  @Before
  public void before() {
    this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
  }

  @After
  public void after() throws Exception {
  }

  /**
   * Method: getRegionById(@PathVariable("id") Integer id)
   */
  @Test
  public void testGetRegionById() throws Exception {
    this.mvc.perform(get("/api/regions/110000").accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$.name").value("北京市"))
        .andDo(MockMvcResultHandlers.print());
  }

  /**
   * Method: getRegionByParentId(@RequestParam(name = "parentId") Integer parentId)
   */
  @Test
  public void testGetRegionByParentId() throws Exception {
    this.mvc.perform(get("/api/regions").param("parentId", "110000")
        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$.[0].name").value("市辖区"))
        .andDo(MockMvcResultHandlers.print());
  }

  /**
   * Method: getAllLeaf(@PathVariable("parentId")Integer parentId)
   */
  @Test
  public void testGetAllLeaf() throws Exception {
    this.mvc.perform(get("/api/regions/allLeaf/110000")
        .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
        .andExpect(jsonPath("$.[0].name").value("北京市"))
        .andDo(MockMvcResultHandlers.print());
  }

} 
