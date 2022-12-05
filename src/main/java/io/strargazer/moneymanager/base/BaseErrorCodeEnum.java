package io.strargazer.moneymanager.base;

public class BaseErrorCodeEnum  {

    private String code;
    private String title;
    private String message;

    BaseErrorCodeEnum(String code, String title, String message) {
        this.code = code;
        this.title = title;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

}
