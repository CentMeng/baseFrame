package com.core.api.entity.enums;

/**
 * Created by suetming on 15-9-21.
 */
public enum Gender implements BaseEnum {

    MALE("男"),
    FEMALE("女"),
    SECRET("保密");

    private final String key;

    Gender(String key){
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }

}

