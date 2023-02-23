package com.example.animalchipization.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Getter
@AllArgsConstructor
public class SearchCriteria<T> {
    private String key;
    private T value;
    private CriteriaOperation operation;

    public SearchCriteria(String key, T value) {
        this.key = key;
        this.value = value;
    }

    public enum CriteriaOperation {
        EQUALS,
        GREATER_OR_EQUAL,
        LESS_OR_EQUAL;
    }
}
