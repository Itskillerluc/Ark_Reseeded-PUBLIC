package com.huskytacodile.alternacraft.entities.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum MultiVariant implements IVariant {
    MALE(0),
    FEMALE(1),
    FEMALE2(2);

    private static final MultiVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(MultiVariant::getId)).toArray((i) -> new MultiVariant[i]);
    private final int id;

    private MultiVariant(int p_30984_) {
        this.id = p_30984_;
    }

    public int getId() {
        return this.id;
    }

    public static MultiVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
