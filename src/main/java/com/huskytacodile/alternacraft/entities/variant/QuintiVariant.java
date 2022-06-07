package com.huskytacodile.alternacraft.entities.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum QuintiVariant implements IVariant {
    MALE(0),
    FEMALE(1),
    FEMALE2(2),
    MALE2(3),
    FEMALE3(4);

    private static final QuintiVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(QuintiVariant::getId)).toArray((i) -> new QuintiVariant[i]);
    private final int id;

    private QuintiVariant(int p_30984_) {
        this.id = p_30984_;
    }

    public int getId() {
        return this.id;
    }

    public static QuintiVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
