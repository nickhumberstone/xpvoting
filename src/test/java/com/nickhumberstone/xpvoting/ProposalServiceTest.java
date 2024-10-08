package com.nickhumberstone.xpvoting;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ProposalServiceTest {
    ProposalService service = new ProposalService();

    @Test
    void should_start_with_no_proposals() {
        assertThat(service.proposals()).isEmpty();
    }

    @Test
    void should_be_able_to_add_proposal() {
        // first add a proposal to the Proposal Service
        service.addProposal("Proposal 1");
        // expect our proposal to be in the list
        // assertThat(service.proposals()).contains("Proposal 1");
        assertThat(service.proposals()).contains(new Proposal("Proposal 1"));
    }

    @Test
    void should_be_able_to_add_multiple_proposals() {
        service.addProposal("Proposal A");
        service.addProposal("Proposal B");
        assertThat(service.proposals()).contains(new Proposal("Proposal A"), new Proposal("Proposal B"));
    }

    @Test
    void should_be_able_to_clear_proposals() {
        service.addProposal("I'm not empty");
        service.clearProposals();
        assertThat(service.proposals()).isEmpty();
    }
}
