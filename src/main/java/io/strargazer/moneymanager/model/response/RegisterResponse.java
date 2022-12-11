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
public class RegisterResponse extends BaseResponse {
    private String id;
    private String username;
}
