package io.strargazer.moneymanager.service.auth;

import io.strargazer.moneymanager.base.BaseService;
import io.strargazer.moneymanager.helper.CommonHelper;
import io.strargazer.moneymanager.helper.error.ClientErrorStatusCode;
import io.strargazer.moneymanager.helper.exception.BaseException;
import io.strargazer.moneymanager.model.entity.Users;
import io.strargazer.moneymanager.model.request.LoginRequest;
import io.strargazer.moneymanager.model.response.LoginResponse;
import io.strargazer.moneymanager.repository.UsersRepository;
import io.strargazer.moneymanager.security.UserDetailsImpl;
import io.strargazer.moneymanager.security.utils.JwtUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Log4j2
public class LoginService implements BaseService<LoginRequest, LoginResponse> {

    @Value("${money-manager.app.jwtExpirationMs}")
    private Integer jwtExpirationMs;

    private AuthenticationManager authenticationManager;
    private JwtUtils jwtUtils;
    private UsersRepository usersRepository;

    public LoginService(AuthenticationManager authenticationManager, JwtUtils jwtUtils,
                        UsersRepository usersRepository) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.usersRepository = usersRepository;
    }

    @Override
    public LoginResponse invoke(LoginRequest input) {
        if (BooleanUtils.isTrue(input.getIsGuestMode())) {
            log.info("username {} invoke guest mode", input.getUsername());
            return invokeGuestMode(input);
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(input.getUsername(), input.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String token = jwtUtils.generateJwtToken(authentication);
        return LoginResponse.builder()
                .expiration(expirationInSecond())
                .token(token)
                .userId(userDetails.getId())
                .build();
    }

    private LoginResponse invokeGuestMode(LoginRequest input) {
        Optional<Users> usersOptional = findUserGuest(input);
        String id;
        Users user;

        if (usersOptional.isEmpty())  {
            Users newUser = Users.builder()
                    .username(input.getUsername())
                    .isGuest(true)
                    .dateResetPreference(1)
                    .password(null)
                    .email(input.getEmail())
                    .totalAsset(BigDecimal.ZERO)
                    .build();
            CommonHelper.initBaseEntity(newUser);

            user = usersRepository.save(newUser);
            id = user.getId();
        } else  {
            id = usersOptional.get().getId();
            user = usersOptional.get();
        }

        String token = jwtUtils.generateJwtToken(UserDetailsImpl.build(user));
        return LoginResponse.builder()
                .expiration(expirationInSecond())
                .token(token)
                .userId(id)
                .build();
    }

    private Optional<Users> findUserGuest(LoginRequest input) {
        Optional<Users> usersOptional = usersRepository.findByUsername(input.getUsername());
        if (usersOptional.isPresent()
                && isNotGuestOrEmailNotEquals(usersOptional.get(), input.getEmail()) ) {
            throw new BaseException(HttpStatus.UNAUTHORIZED,
                    ClientErrorStatusCode.CANT_GUEST_USER_ALREADY_EXIST.getCode(),
                    ClientErrorStatusCode.CANT_GUEST_USER_ALREADY_EXIST.getTitle(),
                    ClientErrorStatusCode.CANT_GUEST_USER_ALREADY_EXIST.getMessage()
            );
        }

        return usersOptional;
    }

    private boolean isNotGuestOrEmailNotEquals(Users users, String email) {
        return BooleanUtils.isFalse(users.getIsGuest())
                || !StringUtils.equalsIgnoreCase(email, users.getEmail());
    }

    private int expirationInSecond() {
        return jwtExpirationMs / 100;
    }

}
