package com.nickhumberstone.xpvoting;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
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

    @Test
    void should_show_message_when_there_are_no_topics() throws Exception {
        given(service.proposals())
                .willReturn(emptyList());

        mvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("No topics yet")));
    }

    @Test
    @Disabled
    void should_vote_on_proposal_matching_id() throws Exception {
        given(service.proposals())
                .willReturn(emptyList());

        mvc.perform(post("/castvote/1"))
                .andExpect(status().isOk());

        then(service).should().castVote(1);
    }
}