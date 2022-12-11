package io.strargazer.moneymanager.model.request;

import io.strargazer.moneymanager.base.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest extends BaseRequest {
    @NotNull
    private String email;
    @NotNull
    private String username;
    private String password;
    private Boolean isGuestMode;
}
