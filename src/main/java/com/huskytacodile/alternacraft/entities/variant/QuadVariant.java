package com.huskytacodile.alternacraft.entities.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum QuadVariant implements IVariant {
    MALE(0),
    FEMALE(1),
    FEMALE2(2),
    MALE2(3);

    private static final QuadVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(QuadVariant::getId)).toArray((i) -> new QuadVariant[i]);
    private final int id;

    private QuadVariant(int p_30984_) {
        this.id = p_30984_;
    }

    public int getId() {
        return this.id;
    }

    public static QuadVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
