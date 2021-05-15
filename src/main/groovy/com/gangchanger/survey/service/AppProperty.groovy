package com.gangchanger.survey.service


import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "app")
class AppProperty {

    Mail mail = new Mail();
    StaticData staticData = new StaticData();

    static class Proxy {
        boolean enable;
        String username;
        String password;
        String host;
        int port;
        List<String> nonProxyHosts = [];
    }

    static class Mail{
        String from = 'info@gangchanger.com'
    }

    static class StaticData{
        boolean enableChromePluginLoading=true;
        boolean enableSlackPluginLoading=true;
        boolean enableShopifyPluginLoading=true;
        boolean enableCapterraPluginLoading=true;
    }
}
