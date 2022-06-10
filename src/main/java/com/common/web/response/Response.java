package com.common.web.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Builder
@ToString
@AllArgsConstructor
public class Response implements Serializable {
    private static final long serialVersionUID = 2044753748756796256L;
    String status;
    String message;
}
