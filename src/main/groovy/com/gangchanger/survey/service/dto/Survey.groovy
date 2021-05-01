package com.gangchanger.survey.service.dto

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document('survey')
class Survey {
    @Id
    String id;
    @Indexed
    String email;
    List<Choice> choices = [];
    Date updateTs = new Date();

    static class Choice{
        Software software;
        Software alternative;
        String reason;
    }
}
