package com.gangchanger.survey.service.services
import com.fasterxml.jackson.databind.ObjectMapper
import com.gangchanger.survey.service.common.Utils
import com.gangchanger.survey.service.model.Change
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.stereotype.Service
import org.springframework.util.Assert

@Service
class SysadminService {
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    MongoTemplate mongoTemplate;

    List findAll(String collectionName) {
        Class entityClass = getEntityClass(collectionName);
        if (entityClass != null)
            return mongoTemplate.findAll(entityClass, collectionName);
        return [];
    }

    public <T> T findOne(String id, Class<T> valueType) {
        return (T) mongoTemplate.findById(id, valueType);
    }

    /**
     * Delete entity from DB by id
     */
    Change deleteEntity(String collectionName, String id) {
        Assert.notNull(id, "Delete entity must provide collectionName.");
        Assert.notNull(id, "Delete entity must provide id.");
        Class entityType = getEntityClass(collectionName);
        Change change = new Change();
        change.before = findOne(id, entityType);
        mongoTemplate.remove(change.before)
        return change;
    }

    /**
     * Save entity to any collection in DB.
     */
    Change saveEntity(String collectionName, String json) {
        Class entityType = getEntityClass(collectionName);
        if (entityType == null) return null;
        Object next = objectMapper.readValue(json, entityType);
        Change change = new Change(after: mongoTemplate.save(next));
        return change;
    }

    /**
     * Ensure find the entity by id, then copy the fields from json to that object.
     */
    Change updateEntity(String collectionName, String json) {
        Class entityType = getEntityClass(collectionName);
        if (entityType == null) return null;
        Object next = objectMapper.readValue(json, entityType);
        Change change = new Change();
        // Ensure id is not null
        Assert.isTrue(next.properties.containsKey('id'), "Missing id in json.");
        change.before = findOne(next.properties.get('id').toString(), entityType);
        Object original = findOne(next.properties.get('id').toString(), entityType);
        // Copy value from new entity to original and save it.
        Utils.copyNonNullProperties(next, original);
        change.after = mongoTemplate.save(original);
        return change;
    }

    Class getEntityClass(String collectionName) {
        if (collectionName.equalsIgnoreCase('bass_integration')) {
            return Integration.class;
        } else if (collectionName.equalsIgnoreCase('bass_route_command')) {
            return RouteCommand.class;
        } else if (collectionName.equalsIgnoreCase('bass_route_job')) {
            return RouteJob.class;
        } else if (collectionName.equalsIgnoreCase('bass_archived')) {
            return Archived.class;
        } else if (collectionName.equalsIgnoreCase('bass_oauth_configuration')) {
            return OAuthConfiguration.class;
        } else if (collectionName.equalsIgnoreCase('bass_access_log')) {
            return AccessLog.class;
        } else if (collectionName.equalsIgnoreCase('bass_schema')) {
            return Schema.class;
        }
    }
}
