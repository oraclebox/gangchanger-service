package com.gangchanger.survey.service

import com.fasterxml.jackson.databind.MappingIterator
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.csv.CsvMapper
import com.fasterxml.jackson.dataformat.csv.CsvSchema
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
        // Load Shopify plugin data
        if(appProperty.staticData.enableShopifyPluginLoading){
            log.info("Loading Shopify plugin from static data.");
            try {
                Map<String, Software> softwares = [:];
                CsvSchema csv = CsvSchema.emptySchema().withHeader();
                CsvMapper csvMapper = new CsvMapper();
                MappingIterator<Map<?, ?>> mappingIterator =  csvMapper.reader().forType(Map.class).with(csv)
                        .readValues(new File('static/shopify/shopify.csv'));
                List<Map<?, ?>> list = mappingIterator.readAll();
                list.each {
                    Software software = objectMapper.readValue(objectMapper.writeValueAsString(it), Software.class);
                    software.category = 'Tool';
                    software.platform = 'Shopify Plugin';
                    software.rate = 3.0;
                    softwares.put(software.name, software);
                }
                log.info("Total ${softwares.size()} softwares.")
                int count = 0;
                softwares.values().each {
                    surveyService.upsert(it, it.platform);
                    log.info("Saved #${count++} software ${it.name} - ${it.platform}");
                }
                log.info("Finished load Shopify plugin from static data.");
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        // Load Slack plugin data
        if(appProperty.staticData.enableSlackPluginLoading) {
            log.info("Loading slack plugin from static data.");
            try {
                Map<String, Software> softwares = [:];
                CsvSchema csv = CsvSchema.emptySchema().withHeader();
                CsvMapper csvMapper = new CsvMapper();
                MappingIterator<Map<?, ?>> mappingIterator =  csvMapper.reader().forType(Map.class).with(csv)
                        .readValues(new File('static/slack/slack.csv'));
                List<Map<?, ?>> list = mappingIterator.readAll();
                list.each {
                    Software software = objectMapper.readValue(objectMapper.writeValueAsString(it), Software.class);
                    software.category = 'Tool';
                    software.platform = 'Slack Plugin';
                    software.rate = 3.0;
                    softwares.put(software.name, software);
                }
                log.info("Total ${softwares.size()} softwares.")
                int count = 0;
                softwares.values().each {
                    surveyService.upsert(it, it.platform);
                    log.info("Saved #${count++} software ${it.name} - ${it.platform}");
                }
                log.info("Finished load slack plugin from static data.");
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        // Load Chrome plugin data
        if(appProperty.staticData.enableChromePluginLoading) {
            log.info("Loading chrome plugin from static data.");
            Map<String, Software> softwares = [:];
            List<ChromePlugin> chromePluginList = objectMapper.readValue(new File('static/chrome/extensions.json').text, ChromePlugin[].class);
            chromePluginList.each {
                if (!softwares.containsKey(it.name) && it.category != 'Fun') {
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
            int count = 0;
            softwares.values().each {
                surveyService.upsert(it, it.platform);
                log.info("Saved #${count++} software ${it.name} - ${it.platform}");
            }
            log.info("Finished load chrome plugin from static data.");
        }
    }
}
