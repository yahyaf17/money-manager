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
public class RegisterRequest extends BaseRequest {
    private String userId;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String email;
    private Boolean isSwitchGuestAccount;
}
