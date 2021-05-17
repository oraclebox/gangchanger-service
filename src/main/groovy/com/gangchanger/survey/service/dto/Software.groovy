package com.gangchanger.survey.service.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.Index
import org.springframework.data.mongodb.core.mapping.Document

@CompoundIndex(def = "{'platform':1, 'name':1}", name = "SoftwareInEachPlatform")
@Document('softwares')
class Software {
    @Id
    String id;
    String platform;
    String name;
    String category;
    @JsonIgnore
    String imgSrc;
    String description;
    @JsonIgnore
    String url;
    Double price;
    String priceTag;
    Double rate;
    String rateTag;
    boolean popular;
    Date updateTs = new Date();
    boolean active = true;
}
