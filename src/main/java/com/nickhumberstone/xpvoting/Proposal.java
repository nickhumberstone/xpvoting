package com.nickhumberstone.xpvoting;

import lombok.Data;

@Data
public class Proposal {
    private final String topicTitle;

    public Proposal(String topicTitle) {
        this.topicTitle = topicTitle;
    }

}
