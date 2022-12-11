package io.strargazer.moneymanager.helper.exception;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@ToString
public class BaseException extends RuntimeException {

    protected HttpStatus httpStatus;
    protected String errorCode;
    protected String errorTitle;
    protected String errorMessage;

    public BaseException(HttpStatus httpStatus, String errorCode, String errorTitle, String errorMessage) {
        super("");
        this.httpStatus = httpStatus;
        this.errorTitle = errorTitle;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
