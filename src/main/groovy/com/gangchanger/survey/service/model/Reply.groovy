package com.gangchanger.survey.service.model

import com.fasterxml.jackson.annotation.JsonInclude
import groovy.transform.ToString
import groovy.transform.builder.Builder

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
@ToString
class Reply {
    String jwtToken;
    String systemMessage;
    Object data;
}
