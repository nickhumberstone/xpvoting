package com.nickhumberstone.xpvoting;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProposalController.class)
class ProposalControllerIT {

    @Autowired
    MockMvc mvc;

    @MockBean
    ProposalService service;

    @Test
    void should_return_200_for_home_page() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk());
    }
}