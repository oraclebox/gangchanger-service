package com.gangchanger.survey.service.services

import com.wildbit.java.postmark.Postmark
import com.wildbit.java.postmark.client.ApiClient
import com.wildbit.java.postmark.client.data.model.message.Message
import com.wildbit.java.postmark.client.data.model.message.MessageResponse
import com.wildbit.java.postmark.client.data.model.templates.TemplatedMessage
import groovy.util.logging.Slf4j
import org.apache.commons.lang3.StringUtils
import org.springframework.stereotype.Service

@Slf4j
@Service
class EmailService {

    void sendEmail(String email, int templateId){
        ApiClient client = Postmark.getApiClient("0f1bba5d-bc4c-4a4e-8c9b-3e731aa56cba");
        TemplatedMessage templatedMessage = new TemplatedMessage("info@gangchanger.com", email, templateId);
        templatedMessage.setTemplateModel(["name": StringUtils.substringBefore(email, "@")]);
        //client.deliverMessage(templatedMessage)
        //Message message = new Message("info@gangchanger.com", "info@gangchanger.com", "Hello from Postmark!", "Hello message body");
        //message.setMessageStream("outbound");

        //MessageResponse response = client.deliverMessage(message);
        MessageResponse response = client.deliverMessageWithTemplate(templatedMessage);
        log.info(response.getMessage());
    }

}
