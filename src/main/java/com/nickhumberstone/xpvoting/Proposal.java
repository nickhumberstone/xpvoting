package com.nickhumberstone.xpvoting;

import lombok.Data;

// An individual proposal. It has a topicTitle

@Data
public class Proposal {
    private final String topicTitle;
    private final int id;

    public int votes() {
        return 0;
    }

}
