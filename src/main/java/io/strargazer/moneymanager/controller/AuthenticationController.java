package io.strargazer.moneymanager.controller;

import io.strargazer.moneymanager.helper.CommonHelper;
import io.strargazer.moneymanager.helper.error.ClientErrorStatusCode;
import io.strargazer.moneymanager.helper.exception.BaseException;
import io.strargazer.moneymanager.model.request.LoginRequest;
import io.strargazer.moneymanager.model.request.RegisterRequest;
import io.strargazer.moneymanager.model.response.LoginResponse;
import io.strargazer.moneymanager.model.response.RegisterResponse;
import io.strargazer.moneymanager.service.auth.LoginService;
import io.strargazer.moneymanager.service.auth.RegisterService;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;

@RestController
@RequestMapping("/api/auth/v1")
public class AuthenticationController {

    private LoginService loginService;
    private RegisterService registerService;

    public AuthenticationController(LoginService loginService, RegisterService registerService) {
        this.loginService = loginService;
        this.registerService = registerService;
    }

    @PostMapping("login")
    public LoginResponse login(HttpServletRequest servletRequest,
                               @Valid @RequestBody(required = false) LoginRequest loginRequest) {
        String authHeader = servletRequest.getHeader("Authorization");
        if (Objects.nonNull(authHeader) && StringUtils.startsWithIgnoreCase(authHeader, "Basic")) {
            String[] auth = CommonHelper.decodeBasicAuth(authHeader);
            return loginService.invoke(LoginRequest.builder()
                    .username(auth[0])
                    .password(auth[1])
                    .isGuestMode(false)
                    .build());
        } else if (Objects.nonNull(loginRequest)) {
            return loginService.invoke(LoginRequest.builder()
                    .username(loginRequest.getUsername())
                    .password(null)
                    .isGuestMode(true)
                    .email(loginRequest.getEmail())
                    .build());
        } else {
            throw new BaseException(HttpStatus.UNAUTHORIZED,
                    ClientErrorStatusCode.AUTH_LOGIN_NOT_VALID.getCode(),
                    ClientErrorStatusCode.AUTH_LOGIN_NOT_VALID.getTitle(),
                    ClientErrorStatusCode.AUTH_LOGIN_NOT_VALID.getMessage());
        }
    }

    @PostMapping("register")
    public RegisterResponse register(@Valid @RequestBody RegisterRequest request) {
        return registerService.invoke(request);
    }

}
