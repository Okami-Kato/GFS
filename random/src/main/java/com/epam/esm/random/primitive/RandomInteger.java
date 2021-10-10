package com.epam.esm.random.primitive;

import java.util.Random;

public class RandomInteger {
    private final Integer value;

    public RandomInteger(int min, int max) {
        if (min > max)
            throw new IllegalArgumentException("min must be less or equal to max");
        this.value = extractValue(min, max);
    }

    private Integer extractValue(int min, int max) {
        return (min + new Random().nextInt(max + 1 - min));
    }

    public Integer getValue() {
        return value;
    }
}
