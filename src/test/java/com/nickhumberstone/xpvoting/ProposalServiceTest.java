package com.nickhumberstone.xpvoting;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProposalServiceTest {
    ProposalService service = new ProposalService();

    @Test
    void should_start_with_no_proposals() {
        assertThat(service.proposals()).isEmpty();
    }

    @Test
    void should_be_able_to_add_proposal() {
        service.addProposal("Proposal 1");
        assertThat(service.proposals())
                .extracting(Proposal::getTopicTitle)
                .contains("Proposal 1");
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
        assertThat(service.votesFor(proposalId)).isZero();
    }

    @Test
    void ids_should_not_repeat() {
        int idOfA = service.addProposal("Proposal A");
        int idOfB = service.addProposal("Proposal B");
        assertThat(idOfA).isNotEqualTo(idOfB);
    }

    @Test
    void should_be_able_to_add_vote() {
        int proposalIdOfA = service.addProposal("Proposal A");
        int proposalIdOfB = service.addProposal("Proposal B");
        service.castVote(proposalIdOfA);
        service.castVote(proposalIdOfA);
        service.castVote(proposalIdOfB);
        assertThat(service.votesFor(proposalIdOfA)).isEqualTo(2);
        assertThat(service.votesFor(proposalIdOfB)).isEqualTo(1);
    }

    @Test
    void should_get_details_of_a_proposal() {
        var proposalId = service.addProposal("Something cool");
        service.castVote(proposalId);

        var proposalDetails = service.detailsFor(proposalId);

        assertThat(proposalDetails)
                .isEqualTo(new ProposalDetails(proposalId, "Something cool", 1));
    }
}
