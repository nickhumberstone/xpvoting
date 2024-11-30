package com.nickhumberstone.xpvoting;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.and;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@SuppressWarnings("AccessStaticViaInstance")
@WebMvcTest(ProposalController.class)
class ProposalControllerIT {

    @Autowired
    MockMvcTester mvc;

    @MockitoBean
    ProposalService service;

    @Test
    void should_return_200_for_home_page() {
        assertThat(mvc.get().uri("/"))
                .hasStatusOk();
    }

    @Test
    void should_show_message_when_there_are_no_topics() {
        assertThat(mvc.get().uri("/"))
                .hasStatusOk()
                .bodyText().contains("No topics yet");
    }

    @Test
    void should_vote_on_proposal_matching_id() {
        var proposal1Details = new ProposalDetails(1, "Some Topic", 1);
        given(service.detailsFor(1))
                .willReturn(proposal1Details);

        var result = mvc.post().uri("/castvote/1").exchange();

        then(service).should()
                .castVote(1);
        and.then(result)
                .hasStatusOk();
    }
}