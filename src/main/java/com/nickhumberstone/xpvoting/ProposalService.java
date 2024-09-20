
package com.nickhumberstone.xpvoting;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.Collections;

@Service
public class ProposalService {
    List<String> proposals = new CopyOnWriteArrayList<>();

    public List<String> proposals() {
        // return proposals;
        return Collections.unmodifiableList(proposals);
    }

    public void addProposal(String proposal) {
        proposals.add(proposal);
    }

    public void clearProposals() {
        proposals.clear();
    }

}
