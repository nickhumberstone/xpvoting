package com.nickhumberstone.xpvoting;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Disabled;
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
        assertThat(service.proposals()).extracting(Proposal::getTopicTitle).contains("Proposal 1");

    }

    @Test
    void should_be_able_to_add_multiple_proposals() {
        service.addProposal("Proposal A");
        service.addProposal("Proposal B");
        assertThat(service.proposals())
                .extracting(Proposal::getTopicTitle)
                .contains("Proposal A", "Proposal B");
    }

    @Test
    void should_be_able_to_clear_proposals() {
        service.addProposal("I'm not empty");
        service.clearProposals();
        assertThat(service.proposals()).isEmpty();
    }

    @Test
    void can_see_votes_on_proposal() {
        int proposalId = service.addProposal("Proposal Test");
        // assert that proposal called X has 1 vote
        assertThat(service.votesFor(proposalId)).isZero();
    }

    @Test
    @Disabled
    void ids_should_not_repeat() {
        int idOfA = service.addProposal("Proposal A");
        int idOfB = service.addProposal("Proposal B");
        assertThat(idOfA).isNotEqualTo(idOfB);
    }

}
