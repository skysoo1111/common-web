package com.common.web.prop;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@EqualsAndHashCode
@Component
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "errors")
public class ErrorProperty {
    private Map<String, String> errors = new HashMap();

    @Override
    public String toString() {
        return "ErrorProperty{" +
                "errors=" + errors +
                '}';
    }
}
