package com.gangchanger.survey.service.dto

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document('survey')
class Survey {
    @Id
    String id;
    String email;
}
