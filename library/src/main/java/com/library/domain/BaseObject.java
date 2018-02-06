package com.library.domain;

import java.util.Map;

public abstract class BaseObject {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public abstract Map<String, Object> getParameterMapping();
}
