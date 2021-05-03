package com.gangchanger.survey.service.controller

import com.gangchanger.survey.service.interceptor.ApiKeyRequired
import com.gangchanger.survey.service.model.Reply
import com.gangchanger.survey.service.services.SysadminService
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@Slf4j
@RestController
@RequestMapping('/gangchanger/sysadmin')
class SysadminController{

    @Autowired
    SysadminService sysadminService;

    @ApiKeyRequired
    @GetMapping('/list')
    Reply get(@RequestParam(name = 'collectionName', required = true, defaultValue = 'bass_integration') String collectionName) {
        return new Reply(data: sysadminService.findAll(collectionName));
    }

    @ApiKeyRequired
    @PutMapping('/{collectionName}')
    Reply update(@PathVariable String collectionName, @RequestBody String json) {
        return new Reply(data: sysadminService.updateEntity(collectionName, json));
    }

    @ApiKeyRequired
    @PostMapping('/{collectionName}')
    Reply save(@PathVariable String collectionName, @RequestBody String json) {
        return new Reply(data: sysadminService.saveEntity(collectionName, json));
    }

    @ApiKeyRequired
    @DeleteMapping('/{collectionName}/{id}')
    Reply del(@PathVariable String collectionName, @PathVariable String id) {
        return new Reply(data: sysadminService.deleteEntity(collectionName, id));
    }
}