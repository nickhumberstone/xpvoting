
package com.nickhumberstone.xpvoting;

import java.util.List;

import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.Collections;

@Service
public class ProposalService {
    List<String> proposals = new ArrayList<>();

    public List<String> proposals() {
        // return proposals;
        return Collections.unmodifiableList(proposals);
    }

    public void addProposal(String proposal) {
        proposals.add(proposal);
    }

}
