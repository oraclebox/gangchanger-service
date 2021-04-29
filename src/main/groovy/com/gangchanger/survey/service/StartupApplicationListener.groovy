package com.gangchanger.survey.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.gangchanger.survey.service.dto.Software
import com.gangchanger.survey.service.model.ChromePlugin
import com.gangchanger.survey.service.services.SurveyService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

@Slf4j
@Component
class StartupApplicationListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    AppProperty appProperty;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    SurveyService surveyService;

    @Override
    void onApplicationEvent(ContextRefreshedEvent event) {
        loadStaticData();
    }

    void loadStaticData(){
        // Load Chrome plugin data
        if(appProperty.staticData.enableChromePluginLoading) {
            log.info("Loading chrome plugin from static data.");
            Map<String, Software> softwares = [:];
            List<ChromePlugin> chromePluginList = objectMapper.readValue(new File('static/chrome/extensions.json').text, ChromePlugin[].class);
            chromePluginList.each {
                if (!softwares.containsKey(it.name)) {
                    Software software = new Software(rate: it.rating,
                            platform: "Chrome Plugin",
                            priceTag: it.price,
                            name: it.name,
                            category: it.category,
                            url: it.url);
                    softwares.put(it.name, software);
                }
            }
            log.info("Total ${softwares.size()} softwares.")
            softwares.values().each {
                surveyService.upsert(it, 'Chrome Plugin');
            }
            log.info("Finished load chrome plugin from static data.");
        }
    }
}
