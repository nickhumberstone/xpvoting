
package com.nickhumberstone.xpvoting;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ProposalService {
    List<Proposal> proposalslisttest = new CopyOnWriteArrayList<>();

    public int addProposal(String proposal) {
        proposalslisttest.add(new Proposal(proposal, proposalslisttest.size()));
        return 0;
    }

    public void clearProposals() {
        proposalslisttest.clear();
    }

    public List<Proposal> proposals() {
        return Collections.unmodifiableList(proposalslisttest);
    }

    public int votesFor(int proposalId) {
        // return 0;
        // int votes = ProposalList[proposalId].votes
        Optional<Proposal> proposalOptional = proposalslisttest.stream()
                .filter(proposal -> proposal.getId() == proposalId)
                .findAny();
        // return either proposal.votes() or 0 if proposal not present
        return proposalOptional.orElseThrow().votes();
    }

}
