package com.gangchanger.survey.service.services

import com.gangchanger.survey.service.dto.Software
import com.gangchanger.survey.service.model.Search
import org.apache.commons.lang3.StringUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service

@Service
class SurveyService {

    @Autowired
    MongoTemplate mongoTemplate;

    List<Software> upsert(List<Software> softwares){
        List<Software> rs = [];
        softwares.each {
            it.updateTs = new Date();
            rs.add(mongoTemplate.save(it));
        }
        return rs;
    }

    List<Software> searchSoftware(Search search, Integer limit) {
        Query query = new Query();
        query.limit(limit);
        search.params.keySet().each {
            if (StringUtils.isNotBlank(search.params.get(it))) {
                query.addCriteria(Criteria.where(it).regex(search.params.get(it)));
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
}
