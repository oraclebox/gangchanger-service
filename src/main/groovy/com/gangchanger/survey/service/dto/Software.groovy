package com.gangchanger.survey.service.dto

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document('softwares')
class Software {
    @Id
    String id;
    String name;
    String category;
    String description;
    Date updateTs = new Date();
    boolean active = true;
}
