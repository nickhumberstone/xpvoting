
package com.nickhumberstone.xpvoting;

import java.util.List;
import java.util.ArrayList;

import java.util.Collections;

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
