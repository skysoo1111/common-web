package com.common.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/healthcheck"})
public class HealthCheckController {
    public HealthCheckController() {
    }

    @GetMapping({"/_check"})
    public String check() {
        return "_check";
    }
}
