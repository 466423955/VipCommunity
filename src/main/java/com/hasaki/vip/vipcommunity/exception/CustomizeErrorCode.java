package com.hasaki.vip.vipcommunity.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    USER_PASSWORD_INCORRECT(1001, "用户名密码不正确"),
    USER_ACCOUNT_ISNULL(1002, "用户名不能为空"),
    USER_PASSWORD_ISNULL(1003, "密码不能为空");

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
