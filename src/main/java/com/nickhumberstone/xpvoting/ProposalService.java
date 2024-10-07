
package com.nickhumberstone.xpvoting;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class ProposalService {
    List<Proposal> proposalslisttest = new CopyOnWriteArrayList<>();

    public void addProposal(String proposal) {
        proposalslisttest.add(new Proposal(proposal));
    }

    public void clearProposals() {
        proposalslisttest.clear();
    }

    public List<Proposal> proposals() {
        return Collections.unmodifiableList(proposalslisttest);
    }

}
