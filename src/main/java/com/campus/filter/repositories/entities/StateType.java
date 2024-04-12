package com.campus.filter.repositories.entities;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum StateType {
    SELL((long) 0), RENT((long) 1), BOTH((long) 2);

    private final Long value;

    public Long getValue() {
        return value;
    }

    public static StateType fromValue(Long value) {
        for (StateType type : StateType.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid StateType value: " + value);
    }
}
