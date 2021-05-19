package com.gangchanger.survey.service.dto

import jdk.nashorn.internal.ir.annotations.Ignore
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document('survey')
class Survey {
    @Id
    String id;
    @Ignore
    @Indexed
    String email;
    @Indexed
    String hash;

    List<Choice> choices = [];
    List<Suggestion> suggestions = [];
    Date updateTs = new Date();
    @Ignore
    boolean sentWelcomeMail;

    static class Suggestion{
        String name;
        String platform;
    }

    static class Choice{
        Software software;
        Software alternative;
        String reason;
    }
}
