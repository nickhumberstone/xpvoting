package com.nickhumberstone.xpvoting;

public record ProposalDetails(int id, String title, int votes) {
    static ProposalDetails proposalDetailsFrom(Proposal proposal) {
        return new ProposalDetails(proposal.getId(), proposal.getTopicTitle(), proposal.getVotes());
    }
}
