package com.hasaki.vip.vipcommunity.dto;

import com.hasaki.vip.vipcommunity.enums.FollowType;
import com.hasaki.vip.vipcommunity.model.Follow;
import lombok.Data;

@Data
public class FollowObjectDTO {
    private String objectType;
    private Long objectId;

    public FollowType getFollowType(){
        if("Question".equalsIgnoreCase(this.objectType)){
            return FollowType.QUESTION;
        }
        return FollowType.DEFAULT;
    }
}
