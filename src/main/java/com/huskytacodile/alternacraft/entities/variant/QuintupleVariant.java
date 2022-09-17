package com.huskytacodile.alternacraft.entities.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum QuintupleVariant implements IVariant {
    MALE(0),
    FEMALE(1),
    FEMALE2(2),
    MALE2(3),
    FEMALE3(4);

    private static final QuintupleVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(QuintupleVariant::getId)).toArray((i) -> new QuintupleVariant[i]);
    private final int id;

    private QuintupleVariant(int p_30984_) {
        this.id = p_30984_;
    }

    public int getId() {
        return this.id;
    }

    public static QuintupleVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
