package com.centime.assignment.service;

import com.centime.assignment.aop.LogMethodParams;
import com.centime.assignment.entity.EntityPojo;
import com.centime.assignment.entity.ResponsePojo;
import com.centime.assignment.exception.NoResultsFound;
import com.centime.assignment.repository.EntityPojoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DBAService {

    @Autowired
    private EntityPojoRepository entityPojoRepository;

    @LogMethodParams
    public ResponseEntity<String> bulkInsert(List<EntityPojo> entityPojoList) {
        entityPojoRepository.saveAll(entityPojoList);
        return new ResponseEntity<>("Bulk insertion successful!!", HttpStatus.OK);
    }
    public ResponseEntity<List<ResponsePojo>> getAll() throws NoResultsFound {
        Iterable<EntityPojo> entityPojoListStream = entityPojoRepository.findAll();
        List<EntityPojo> entityPojoList = new ArrayList<>();
        entityPojoListStream.forEach(entityPojo -> entityPojoList.add(entityPojo));
        if (entityPojoList.isEmpty()) {
            throw new NoResultsFound("No results found.", HttpStatus.NOT_FOUND);
        }

        List<ResponsePojo> response = processData(entityPojoList);
        if (response.isEmpty()) {
            throw new NoResultsFound("No parent child relation found.", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @LogMethodParams
    private List<ResponsePojo>  processData(List<EntityPojo> entityPojoList) {
        Map<Integer, List<EntityPojo>> parentEntityMap = new HashMap<>();
        //preparing parent -> entity object map for entities having parentId.
        entityPojoList.stream().forEach(entityPojo ->
                {
                    if (entityPojo.getParentId() != 0) {
                        List<EntityPojo> mappedPojos = parentEntityMap.get(entityPojo.getParentId());
                        if (mappedPojos == null) {
                            mappedPojos = new ArrayList<>();
                        }
                        mappedPojos.add(entityPojo);
                        parentEntityMap.put(entityPojo.getParentId(), mappedPojos);
                    }
                }
        );
        List<ResponsePojo> responseList = new ArrayList<>();
        for (EntityPojo entityPojo : entityPojoList) {
            if (parentEntityMap.containsKey(entityPojo.getId())) {
                ResponsePojo parentResponse = new ResponsePojo(entityPojo.getName());
                //calling recursively for all nested entities with parent entity object.
                addChildInfo(entityPojo, parentEntityMap, parentResponse);
                responseList.add(parentResponse);
            }
        }
        return responseList;
    }

    @LogMethodParams
    private void addChildInfo(EntityPojo entityPojo,
                              Map<Integer, List<EntityPojo>> parentEntityMap,
                              ResponsePojo parentResponse) {
        if (!parentEntityMap.containsKey(entityPojo.getId())) {
            return;
        }
        //1. getting child info
        //2. building child response pojo
        //3. adding child response pojo to parent response pojo.
        //4. calling addChildInfo recursively for all child entity pojos.
        for (EntityPojo entity : parentEntityMap.remove(entityPojo.getId())) {
            ResponsePojo responsePojo = new ResponsePojo(entity.getName());
            List<ResponsePojo> existingList = parentResponse.getResponsePojos() == null ? new ArrayList<>() : parentResponse.getResponsePojos();
            existingList.add(responsePojo);
            parentResponse.setResponsePojos(existingList);
            addChildInfo(entity, parentEntityMap, responsePojo);
        }
    }

    @LogMethodParams
    public ResponseEntity<EntityPojo> getById(Integer id) throws NoResultsFound {
        Optional<EntityPojo> entityPojo = entityPojoRepository.findById(id);
        if (entityPojo.isPresent()) {
            return new ResponseEntity<>(entityPojo.get(), HttpStatus.OK);
        }
        throw new NoResultsFound("No results found for id: " + id, HttpStatus.NOT_FOUND);
    }
}
