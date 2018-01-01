package net.asifhossain.devopsbuddy.web.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

/**
 * @aurhor asifhossain
 * @since 11/23/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTestIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHomeController() throws Exception {
        mockMvc.perform(get("/")).andExpect(view().name("home"));
    }
}
