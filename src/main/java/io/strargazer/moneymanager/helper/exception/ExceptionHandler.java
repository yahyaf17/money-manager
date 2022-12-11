package io.strargazer.moneymanager.helper.exception;

import io.strargazer.moneymanager.base.ErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@Log4j2
@ControllerAdvice
public class ExceptionHandler {

    private ErrorHelper errorHelper;

    private static final String DEFAULT_ERROR_CODE = "99";

    public ExceptionHandler(ErrorHelper errorHelper) {
        this.errorHelper = errorHelper;
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> renderDefaultResponse(Exception ex) {
        log.error("Exception occurred: ", ex);

        return errorHelper.throwErrorException(HttpStatus.INTERNAL_SERVER_ERROR,
                DEFAULT_ERROR_CODE,
                "Something Went Wrong",
                "Something Bad Happened"
        );
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> renderBusinessErrorResponse(BaseException exception) {
        log.error("BusinessException occurred: ", exception);

        return errorHelper.throwErrorException(exception.getHttpStatus(),
                exception.getErrorCode(),
                exception.getErrorTitle(),
                exception.getErrorMessage()
        );
    }


}
