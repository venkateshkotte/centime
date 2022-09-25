package com.centime.assignment.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@NoArgsConstructor
public class EntityPojo {
    @Id
    @GeneratedValue
    private int id;
    @NotBlank(message = "name should not be empty")
    private String name;
    @NotBlank(message = "color should not be empty")
    private String color;
    private int parentId;
}
