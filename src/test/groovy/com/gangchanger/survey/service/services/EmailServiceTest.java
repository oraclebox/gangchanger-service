package com.gangchanger.survey.service.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmailServiceTest extends Assertions {

    @Autowired
    EmailService emailService;

    @Test
    void email() {
        emailService.sendEmail("oraclebox@gmail.com", 23474743);
    }
}