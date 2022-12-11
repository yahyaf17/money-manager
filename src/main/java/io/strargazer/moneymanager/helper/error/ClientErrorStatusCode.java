package io.strargazer.moneymanager.helper.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ClientErrorStatusCode {

    AUTH_LOGIN_NOT_VALID("40101", "Request Can't be Processed", "Please send username and password"),
    CANT_GUEST_USER_ALREADY_EXIST("40102", "User Not Allowed Guest Mode", "User already exist, please enter your password"),
    USER_ALREADY_EXIST("40103", "Username already exist", "Username already taken, please choose other username"),
    EMAIL_ALREADY_EXIST("40104", "Email already exist", "Email already exist, please login"),
    CANT_TAKE_GUEST_USER("40401", "Guest User Detected", "Please enter guest user ID to continue process"),
    USERNAME_EMAIL_ID_NOT_MATCH("40402", "Request Can't be Processed", "Please enter valid email, username, and ID");

    private String code;
    private String title;
    private String message;

}
