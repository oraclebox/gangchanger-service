package com.gangchanger.survey.service.controller

import com.gangchanger.survey.service.dto.Software
import com.gangchanger.survey.service.dto.Survey
import com.gangchanger.survey.service.interceptor.ApiKeyRequired
import com.gangchanger.survey.service.model.Reply
import com.gangchanger.survey.service.model.Search
import com.gangchanger.survey.service.services.SurveyService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.Assert
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@Slf4j
@RestController
@RequestMapping('/gangchanger')
class SurveyController {

    @Autowired
    SurveyService surveyService;

    @ApiKeyRequired
    @GetMapping('/greeting')
    Reply greeting() {
        return new Reply(systemMessage: "Hello World from Gangchanger.");
    }

    @ApiKeyRequired
    @PostMapping('/software')
    Reply postSoftware(@RequestBody List<Software> softwares) {
        Assert.notNull(softwares, "Missing softwares.")
        return new Reply(data: surveyService.upsert(softwares));
    }

    @ApiKeyRequired
    @GetMapping('/softwares')
    Reply searchSoftware(@RequestParam(required = false, name = "name") String name,
                         @RequestParam(required = false, name = "platform") String platform,
                         @RequestParam(required = false, name = "limit", defaultValue = "50") Integer limit) {
        Search search = new Search();
        search.params.put("name", name);
        search.params.put("platform", platform);
        return new Reply(data: surveyService.searchSoftware(search, limit));
    }

    @ApiKeyRequired
    @GetMapping('/popular/softwares')
    Reply getMostPopularSoftware(@RequestParam(required = false, name = "limit", defaultValue = "100") Integer limit) {
        return new Reply(data: surveyService.findMostPopularSoftware(limit));
    }

    @ApiKeyRequired
    @PostMapping('/survey')
    Reply submitSurvey(@RequestBody Survey survey) {
        Assert.notNull(survey, "Missing survey.");
        Assert.notNull(survey.email, "Email must need to provide.")
        return new Reply(data: surveyService.updateSurvey(survey));
    }

    @GetMapping('/survey/{hash}')
    Reply getSurvey(@PathVariable String hash) {
        Assert.notNull(hash, "Required id is missing.");
        return new Reply(data: surveyService.findByHash(hash));
    }
}
