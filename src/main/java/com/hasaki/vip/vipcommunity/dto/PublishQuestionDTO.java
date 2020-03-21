package com.hasaki.vip.vipcommunity.dto;

import lombok.Data;

/**
 * Create by hanzp on 2020-03-21
 */
@Data
public class PublishQuestionDTO {
    private Long id;
    private String title;
    private String Content;
    private String[] tags;
}
