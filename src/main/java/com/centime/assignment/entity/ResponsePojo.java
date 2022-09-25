package com.centime.assignment.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponsePojo {
    public ResponsePojo(String name) {
        this.name = name;
    }
    @JsonProperty("Name")
    private String name;
    @JsonProperty("Sub Classes")
    List<ResponsePojo> responsePojos;
}
