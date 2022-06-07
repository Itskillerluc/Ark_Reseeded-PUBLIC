package com.huskytacodile.alternacraft.entities.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum GenderVariant implements IVariant {
    MALE(0),
    FEMALE(1);

    private static final GenderVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(GenderVariant::getId)).toArray((i) -> new GenderVariant[i]);
    private final int id;

    private GenderVariant(int p_30984_) {
        this.id = p_30984_;
    }

    public int getId() {
        return this.id;
    }

    public static GenderVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
