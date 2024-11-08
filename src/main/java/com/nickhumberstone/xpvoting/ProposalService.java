package com.nickhumberstone.xpvoting;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class ProposalService {
    List<Proposal> proposalslist = new CopyOnWriteArrayList<>();

    public int addProposal(String proposal) {
        int id = proposalslist.size();
        proposalslist.add(new Proposal(proposal, id));
        return id;
    }

    public void clearProposals() {
        proposalslist.clear();
    }

    public List<Proposal> proposals() {
        return Collections.unmodifiableList(proposalslist);
    }

    public int votesFor(int proposalId) {
        // return 0;
        // int votes = ProposalList[proposalId].votes
        Optional<Proposal> proposalOptional = proposalslist.stream()
                .filter(proposal -> proposal.getId() == proposalId)
                .findAny();
        // return either proposal.votes() or 0 if proposal not present
        return proposalOptional.orElseThrow().getVotes();
    }

    public void castVote(int proposalId) {
        // find item in list matching index, then increase vote value by 1
        // call the increaseVote function on that item
        Optional<Proposal> proposalOptional = proposalslist.stream()
                .filter(proposal -> proposal.getId() == proposalId)
                .findAny();
        proposalOptional.orElseThrow().increaseVote();
    }

    public ProposalDetails detailsFor(int proposalId) {
        return proposalslist.stream()
                .filter(proposal -> proposal.getId() == proposalId)
                .findAny()
                .map(ProposalDetails::proposalDetailsFrom)
                .orElseThrow();
    }
}
