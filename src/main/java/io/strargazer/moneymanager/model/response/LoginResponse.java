package io.strargazer.moneymanager.model.response;

import io.strargazer.moneymanager.base.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse extends BaseResponse {
    private String token;
    private int expiration;
    private String userId;
}
