package com.security.uunnm.entity;

import lombok.Data;

@Data
public class Students {
    private Long id;
    private String name;
    private Float marks;
    public Students(Long id, String name, Float marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }
}
