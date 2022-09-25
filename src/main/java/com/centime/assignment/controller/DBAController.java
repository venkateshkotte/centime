package com.centime.assignment.controller;

import com.centime.assignment.entity.EntityPojo;
import com.centime.assignment.entity.ResponsePojo;
import com.centime.assignment.exception.NoResultsFound;
import com.centime.assignment.service.DBAService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Validated
@RestController
@RequestMapping("/database-application")
public class DBAController {

    @Autowired
    private DBAService dbaService;

    @PostMapping(value = "/bulkAdd")
    public ResponseEntity<String> bulkInsert( @RequestBody List<@Valid EntityPojo> entityPojoList) {
        return dbaService.bulkInsert(entityPojoList);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ResponsePojo>> getAll() throws NoResultsFound {
        return dbaService.getAll();
    }

    @GetMapping("/getBy/{id}")
    public ResponseEntity<EntityPojo> getById(@PathVariable Integer id) throws NoResultsFound {
        return dbaService.getById(id);
    }
}
