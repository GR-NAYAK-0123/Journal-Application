package net.engineeringdigest.journalApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {

    //This method is checks the health
    @GetMapping("/health-check")
    public String healthCheck(){
        return "Okay";
    }
}
