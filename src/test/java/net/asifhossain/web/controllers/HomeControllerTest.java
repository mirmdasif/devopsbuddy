package net.asifhossain.web.controllers;

import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * @aurhor asifhossain
 * @since 11/23/17.
 */
public class HomeControllerTest {
    @Test
    public void onGetRequestReturnHomePage() throws Exception {

        MockMvc mockMvc =
                standaloneSetup(new HomeController()).build();
        mockMvc.perform(get("/")).andExpect(view().name("home"));
    }
}
