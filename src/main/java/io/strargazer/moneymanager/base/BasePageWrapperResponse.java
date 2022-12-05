package io.strargazer.moneymanager.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasePageWrapperResponse<T> extends BaseResponse {
    private Boolean isLastPage;
    private Boolean isFirstPage;
    private transient List<T> content;
    private Long totalContent;
    private Integer totalPage;
    private Integer page;
    private Integer size;
}
