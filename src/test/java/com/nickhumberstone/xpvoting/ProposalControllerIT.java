package com.nickhumberstone.xpvoting;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProposalController.class)
class ProposalControllerIT {

    @Autowired
    MockMvc mvc;

    @Test
    void should_return_200_for_home_page() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk());
    }
}