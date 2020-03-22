package com.hasaki.vip.vipcommunity.dto;

import com.hasaki.vip.vipcommunity.model.Question;
import com.hasaki.vip.vipcommunity.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Question question;
    private UserDTO user;
    private boolean isFollowed;
}
