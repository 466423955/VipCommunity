package com.hasaki.vip.vipcommunity.dto;

import lombok.Data;

/**
 * Create by hanzp on 2020-03-15
 */
@Data
public class UserDTO {
    private Long id;
    private String account;
    private String email;
    private String phoneno;
    private String name;
    private String token;
    private Long gmtCreate;
    private Long gmtModify;
    private String thirdparty;
    private String thirdpartyid;
    private String bio;
    private String avartarurl;
    private Integer flowcount;
}
