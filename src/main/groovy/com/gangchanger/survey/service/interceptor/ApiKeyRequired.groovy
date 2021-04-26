package com.gangchanger.survey.service.interceptor

import java.lang.annotation.*

/**
 * Annotation define if the Controller endpoint protected by API key.
 */
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@interface ApiKeyRequired {
    boolean isActive() default true
}