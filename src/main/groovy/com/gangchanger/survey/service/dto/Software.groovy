package com.gangchanger.survey.service.dto

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document('softwares')
class Software {
    @Id
    String id;
    String platform;
    String name;
    String category;
    String imgSrc;
    String description;
    String url;
    Double price;
    String priceTag;
    Double rate;
    Date updateTs = new Date();
    boolean active = true;
}
