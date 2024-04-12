package com.campus.filter.repositories.entities;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PropertyType {
    NEW((long) 0), USED((long) 1), VILLAGE((long) 2), LOCAL((long) 3);

    private Long value;

    public Long getValue() {
        return value;
    }

    public static PropertyType fromValue(Long value) {
        for (PropertyType type : PropertyType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid PropertyType value: " + value);
    }
}