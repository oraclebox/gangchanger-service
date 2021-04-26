package com.gangchanger.survey.service.controller

import com.gangchanger.survey.service.dto.Software
import com.gangchanger.survey.service.interceptor.ApiKeyRequired
import com.gangchanger.survey.service.model.Reply
import com.gangchanger.survey.service.model.Search
import com.gangchanger.survey.service.services.SurveyService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
@RequestMapping('/gangchanager/survey')
class SurveyController {

    @Autowired
    SurveyService surveyService;

    @ApiKeyRequired
    @PostMapping()
    Reply postSoftware(@RequestBody List<Software> softwares) {
        Assert.notNull(softwares, "Missing softwares.")
        return new Reply(data: surveyService.upsert(softwares));
    }

    @ApiKeyRequired
    @GetMapping('/software')
    Reply searchSoftware(@RequestParam(required = false, name = "name") String name) {
        Search search = new Search();
        search.params.put("name", name);
        return new Reply(data: surveyService.searchSoftware(search));
    }
}
