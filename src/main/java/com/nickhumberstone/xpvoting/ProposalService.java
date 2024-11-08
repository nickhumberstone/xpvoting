package com.nickhumberstone.xpvoting;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class ProposalService {
    List<Proposal> proposalslisttest = new CopyOnWriteArrayList<>();

    public int addProposal(String proposal) {
        int id = proposalslisttest.size();
        proposalslisttest.add(new Proposal(proposal, id));
        return id;
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
        return proposalOptional.orElseThrow().getVotes();
    }

    public void castVote(int proposalId) {
        // find item in list matching index, then increase vote value by 1
        // call the increaseVote function on that item
        Optional<Proposal> proposalOptional = proposalslisttest.stream()
                .filter(proposal -> proposal.getId() == proposalId)
                .findAny();
        proposalOptional.orElseThrow().increaseVote();
    }

    public ProposalDetails detailsFor(int proposalId) {
        return proposalslisttest.stream()
                .filter(proposal -> proposal.getId() == proposalId)
                .findAny()
                .map(proposal -> new ProposalDetails(proposal.getId(), proposal.getTopicTitle(), proposal.getVotes()))
                .orElseThrow();
    }
}
