package com.nickhumberstone.xpvoting;

import lombok.Data;

// An individual proposal. It has a topicTitle

@Data
public class Proposal {
    private final String topicTitle;
    private final int id;
    private int votes;

    public synchronized void increaseVote() {
        votes++;
    }

}
