package com.huskytacodile.alternacraft.entities.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum TripleVariant implements IVariant {
    MALE(0),
    FEMALE(1),
    FEMALE2(2);

    private static final TripleVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(TripleVariant::getId)).toArray((i) -> new TripleVariant[i]);
    private final int id;

    private TripleVariant(int p_30984_) {
        this.id = p_30984_;
    }

    public int getId() {
        return this.id;
    }

    public static TripleVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
