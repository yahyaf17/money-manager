package io.strargazer.moneymanager.helper.exception;


import io.strargazer.moneymanager.base.ErrorResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Configuration
@Log4j2
public class ErrorHelper {

    public ResponseEntity<ErrorResponse> throwErrorException(HttpStatus httpStatus, String errorCode,
                                                             String title, String message) {

        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(errorCode);
        errorResponse.setTitle(title);
        errorResponse.setMessage(message);

        return new ResponseEntity<>(errorResponse, new HttpHeaders(), httpStatus);
    }

}
