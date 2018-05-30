package nz.co.yukich.brett.project;

import nz.co.yukich.brett.project.api.DummyController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(DummyController.class)
public class DummyControllerTest {

  @Autowired
  private MockMvc mvc;

  @Test
  @WithMockUser(value = "dummy")
  public void testGreet() throws Exception {
    mvc.perform(get("/api/dummy").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
        .andExpect(jsonPath("status", is(200)))
        .andExpect(jsonPath("message", is("OK")))
        .andExpect(jsonPath("data.greeting", is("Hello dummy")));
  }
}
