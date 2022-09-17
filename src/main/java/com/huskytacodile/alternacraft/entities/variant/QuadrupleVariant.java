package com.huskytacodile.alternacraft.entities.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum QuadrupleVariant implements IVariant {
    MALE(0),
    FEMALE(1),
    FEMALE2(2),
    MALE2(3);

    private static final QuadrupleVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(QuadrupleVariant::getId)).toArray((i) -> new QuadrupleVariant[i]);
    private final int id;

    private QuadrupleVariant(int p_30984_) {
        this.id = p_30984_;
    }

    public int getId() {
        return this.id;
    }

    public static QuadrupleVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
