package com.hasaki.vip.vipcommunity.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    //用户模块
    USER_PASSWORD_INCORRECT(1001, "用户名密码不正确"),
    USER_ACCOUNT_ISNULL(1002, "用户名不能为空"),
    USER_PASSWORD_ISNULL(1003, "密码不能为空"),
    USER_TOKEN_EXPIRY(1004, "UserToken已失效"),

    //问题模块
    QUESTION_TITLE_EMPTY(2001, "问题标题不能为空"),
    QUESTION_CONTENT_EMPTY(2002, "问题描述不能为空"),
    QUESTION_TAG_EMPTY(2003, "问题标签不能为空")

    ;

    private Integer code;
    private String message;

    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }
}
