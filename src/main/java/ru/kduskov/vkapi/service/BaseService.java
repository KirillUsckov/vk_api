package ru.kduskov.vkapi.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.kduskov.vkapi.constants.ExternalApiConstants;
import ru.kduskov.vkapi.model.audit.AuditRecord;
import ru.kduskov.vkapi.model.external.api.Album;
import ru.kduskov.vkapi.model.external.api.Post;
import ru.kduskov.vkapi.repository.AuditRecordRepository;
import ru.kduskov.vkapi.utils.GeneralInfo;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static ru.kduskov.vkapi.constants.ExternalApiConstants.URL;
import static ru.kduskov.vkapi.constants.ExternalApiConstants.USERS;
import static ru.kduskov.vkapi.utils.StringUtils.cutWithEllipsis;

@Service
public class BaseService {
    private final Logger logger = LoggerFactory.getLogger(BaseService.class);
    @Autowired
    protected RestTemplate restTemplate;
    @Autowired
    protected AuditRecordRepository auditRep;

    protected boolean delete(int id, String endpPart) {
        String action = String.format("Delete %s with id %d", endpPart, id);
        logger.info(action);
        auditRep.save(new AuditRecord(LocalDateTime.now(), endpPart, GeneralInfo.user(), true, action));
        try {
            String endpoint = String.format("%s/%d", endpPart, id);
            logger.info("Send DELETE request to :" + URL + endpoint);
            restTemplate.delete(endpoint);
        } catch (Exception e) {
            logger.error(String.format("Error while deleting %s with id %d: %s", endpPart, id, e.getMessage()));
            return false;
        }
        return true;
    }

    protected  <T> T create(T input, String endpoint) {
        String action = String.format("Create %s with params %s", endpoint, cutWithEllipsis(input.toString(), 50));
        logger.info(action);
        auditRep.save(new AuditRecord(LocalDateTime.now(), endpoint, GeneralInfo.user(), true, action));

        HttpEntity<T> request = new HttpEntity<>(input);
        logger.info("Send POST request to :" + URL + endpoint);

        return (T) restTemplate.postForObject(endpoint, request, input.getClass());
    }

    protected  <T> T update(T input, int id, String endpPart) {
        String action = String.format("Update %s with id %d: %s", endpPart, id, cutWithEllipsis(input.toString(), 50));
        logger.info(action);
        auditRep.save(new AuditRecord(LocalDateTime.now(), endpPart, GeneralInfo.user(), true, action));

        String endpoint = String.join("/", endpPart, String.valueOf(id));
        logger.info("Send PUT request to :" + URL + endpoint);
        ResponseEntity<T> response = (ResponseEntity<T>) restTemplate.exchange(
                endpoint,
                HttpMethod.PUT,
                new HttpEntity<T>(input), input.getClass());
        if (response.getStatusCode() == HttpStatus.OK) {
            return response.getBody();
        } else {
            logger.warn(String.format("%s with id %d wasn't updated", endpPart, id));
            return null;
        }
    }

    protected  <T> Optional<T> get(int id, String endpPart, Class<T> cl) {
        String action = String.format("Get %s with id : %d", endpPart, id);
        logger.info(action);
        auditRep.save(new AuditRecord(LocalDateTime.now(), endpPart, GeneralInfo.user(), true, action));

        Optional result = Optional.empty();
        try {
            String endpoint = format("%s/%d", endpPart, id);
            logger.info("Send POST request to :" + URL + endpoint);

            result =  Optional.of(restTemplate.getForObject(endpoint, cl));
        } catch (Exception e) {
            logger.error(format("%s with id %d wasn't found:%s", endpPart, id, e.getMessage()));
        }
        return result;
    }

    protected <T> Optional<List<T>> getList(int id, String endpoint) {
        String action =String.format("Get %s by id %d", endpoint,  id);
        logger.info(action);
        auditRep.save(new AuditRecord(LocalDateTime.now(), endpoint, GeneralInfo.user(), true, action));

        Optional resList = Optional.empty();
        try {
            logger.info("Send request to " + URL + endpoint);
            resList = Optional.of((List<T>) restTemplate.getForObject(endpoint, List.class));
        } catch (Exception e) {
            logger.error(format("Error while searching for %s with id %d: %s", endpoint, id, e.getMessage()));
        }
        logger.info(format("Send result for request %s", endpoint));
        return resList;
    }
}
