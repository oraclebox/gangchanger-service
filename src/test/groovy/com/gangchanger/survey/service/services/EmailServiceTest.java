package com.gangchanger.survey.service.services;

import jdk.nashorn.internal.ir.annotations.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class EmailServiceTest extends Assertions {

    @Autowired
    EmailService emailService;

    @Ignore
    @Test
    void email() {
        emailService.sendEmail("info@gangchanger.com","raymondsze27@gmail.com", "hash02031231", 23474743);
    }
}