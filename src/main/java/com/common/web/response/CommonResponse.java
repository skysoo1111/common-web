package com.common.web.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommonResponse<T> implements Serializable {
    private static final long serialVersionUID = 6561096404796959925L;
    private String code;
    private String message;
    private T data;
    private String detailMessage;
}
