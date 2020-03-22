package com.hasaki.vip.vipcommunity.enums;

public enum FollowType {
    USER(10),
    QUESTION(20),
    KNOWLEDGE(30),
    COURSE(40),
    COMMENT(50),
    DEFAULT(0);


    private Integer type;

    public Integer getType() {
        return type;
    }

    FollowType(Integer type) {
        this.type = type;
    }


}
