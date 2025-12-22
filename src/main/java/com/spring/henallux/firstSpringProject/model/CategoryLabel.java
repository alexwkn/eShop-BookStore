package com.spring.henallux.firstSpringProject.model;

public class CategoryLabel {
    private final Integer id; // category_id
    private final String name;

    public CategoryLabel(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
}
