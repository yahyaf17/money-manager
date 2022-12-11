package io.strargazer.moneymanager.service.auth;

import io.strargazer.moneymanager.base.BaseService;
import io.strargazer.moneymanager.helper.CommonHelper;
import io.strargazer.moneymanager.helper.error.ClientErrorStatusCode;
import io.strargazer.moneymanager.helper.exception.BaseException;
import io.strargazer.moneymanager.model.entity.Users;
import io.strargazer.moneymanager.model.request.RegisterRequest;
import io.strargazer.moneymanager.model.response.RegisterResponse;
import io.strargazer.moneymanager.repository.UsersRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Log4j2
public class RegisterService implements BaseService<RegisterRequest, RegisterResponse> {

    private UsersRepository usersRepository;
    private PasswordEncoder passwordEncoder;

    public RegisterService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public RegisterResponse invoke(RegisterRequest input) {
        if (BooleanUtils.isTrue(input.getIsSwitchGuestAccount())) {
            if (StringUtils.isEmpty(input.getUserId())) {
                throw new BaseException(HttpStatus.BAD_REQUEST,
                        ClientErrorStatusCode.CANT_TAKE_GUEST_USER.getCode(),
                        ClientErrorStatusCode.CANT_TAKE_GUEST_USER.getTitle(),
                        ClientErrorStatusCode.CANT_TAKE_GUEST_USER.getMessage());
            }

            log.info("User ID {} - {} set to be registered user", input.getUserId(), input.getUsername());
            return registerGuestUser(input);
        }

        validateNewUserRegister(input);

        Users registerUser = new Users();
        registerUser.setEmail(input.getEmail());
        registerUser.setUsername(input.getUsername());
        registerUser.setPassword(passwordEncoder.encode(input.getPassword()));
        registerUser.setIsGuest(false);
        registerUser.setDateResetPreference(1);
        registerUser.setTotalAsset(BigDecimal.ZERO);
        CommonHelper.initBaseEntity(registerUser);
        Users savedUser = usersRepository.save(registerUser);

        return RegisterResponse.builder()
                .id(savedUser.getId())
                .username(input.getUsername())
                .build();
    }

    private RegisterResponse registerGuestUser(RegisterRequest input) {
        Users users = usersRepository.findByUsernameAndEmailAndId(input.getUsername(), input.getEmail(),  input.getUserId())
                .orElseThrow(() -> new BaseException(HttpStatus.BAD_REQUEST,
                        ClientErrorStatusCode.USERNAME_EMAIL_ID_NOT_MATCH.getCode(),
                        ClientErrorStatusCode.USERNAME_EMAIL_ID_NOT_MATCH.getTitle(),
                        ClientErrorStatusCode.USERNAME_EMAIL_ID_NOT_MATCH.getMessage())
                );
        users.setPassword(passwordEncoder.encode(input.getPassword()));
        users.setIsGuest(false);
        usersRepository.save(users);
        return RegisterResponse.builder()
                .id(users.getId())
                .username(users.getUsername())
                .build();
    }

    private void validateNewUserRegister(RegisterRequest input) {
        if (usersRepository.existsByUsername(input.getUsername())) {
            throw new BaseException(HttpStatus.CONFLICT,
                    ClientErrorStatusCode.USER_ALREADY_EXIST.getCode(),
                    ClientErrorStatusCode.USER_ALREADY_EXIST.getTitle(),
                    ClientErrorStatusCode.USER_ALREADY_EXIST.getMessage());
        }

        if (usersRepository.existsByEmail(input.getEmail())) {
            throw new BaseException(HttpStatus.CONFLICT,
                    ClientErrorStatusCode.EMAIL_ALREADY_EXIST.getCode(),
                    ClientErrorStatusCode.EMAIL_ALREADY_EXIST.getTitle(),
                    ClientErrorStatusCode.EMAIL_ALREADY_EXIST.getMessage());
        }
    }

}
