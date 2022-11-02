package com.huskytacodile.alternacraft.entities.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum SextupleVariant implements IVariant {
    MALE(0),
    FEMALE(1),
    MALE2(2),
    FEMALE2(3),
    MALE3(4),
    FEMALE3(5);

    private static final SextupleVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(SextupleVariant::getId)).toArray((i) -> new SextupleVariant[i]);
    private final int id;

    private SextupleVariant(int p_30984_) {
        this.id = p_30984_;
    }

    public int getId() {
        return this.id;
    }

    public static SextupleVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
