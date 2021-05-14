package com.gangchanger.survey.service.services

import com.gangchanger.survey.service.dto.Software
import com.gangchanger.survey.service.dto.Survey
import com.gangchanger.survey.service.model.Search
import groovy.util.logging.Slf4j
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.annotation.Order
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

@Slf4j
@Service
class SurveyService {

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    EmailService emailService;

    List<Software> upsert(List<Software> softwares){
        List<Software> rs = [];
        softwares.each {
            it.updateTs = new Date();
            rs.add(mongoTemplate.save(it));
        }
        return rs;
    }

    Survey updateSurvey(Survey survey){
        survey.updateTs = new Date();
        Survey rs = findByEmail(survey.email);
        if(rs != null){
            survey.id = rs.id;
            survey.sentWelcomeMail = rs.sentWelcomeMail;
        }
        if(!survey.sentWelcomeMail){
            emailService.sendEmail(survey.email, 23474743);
            survey.sentWelcomeMail = true;
        }
        return mongoTemplate.save(survey);
    }


    Survey findByEmail(String email) {
        Query query = new Query();
        query.addCriteria(Criteria.where('email').is(email));
        return mongoTemplate.findOne(query, Survey.class);
    }

    List<Software> searchSoftware(Search search, Integer limit) {
        Query query = new Query();
        query.limit(limit);
        query.with(Sort.by(new Sort.Order(Sort.Direction.ASC, "name")));
        search.params.keySet().each {
            if (StringUtils.isNotBlank(search.params.get(it))) {
                query.addCriteria(Criteria.where(it).regex("^${search.params.get(it)}.*", "i"));
            }
        }
        return mongoTemplate.find(query, Software.class);
    }


    List<Software> findMostPopularSoftware(Integer limit) {
        Query query = new Query();
        query.limit(limit);
        query.with(Sort.by(Sort.Direction.DESC,"rate", "name"));
        return mongoTemplate.find(query, Software.class);
    }

    void upsert(Software software, String platformName){
        Query query = new Query();
        query.addCriteria(Criteria.where("platform").is(platformName));
        query.addCriteria(Criteria.where("name").is(software.name));
        Software rec = mongoTemplate.findOne(query, Software.class);
        if(rec == null){
            mongoTemplate.save(software);
        }
    }
}
