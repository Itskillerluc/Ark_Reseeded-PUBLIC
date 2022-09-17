package com.huskytacodile.alternacraft.entities.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum DuodecupleVariant implements IVariant {
    JP(0),
    TLW(1),
    JP3MALE(2),
    JP3FEMALE(3),
    BLUE(4),
    CHARLIE(5),
    DELTA(6),
    ECHO(7),
    RED(8),
    CC(9),
    DINOTRACKERMALE(10),
    DINOTRACKERFEMALE(11);

    private static final DuodecupleVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(DuodecupleVariant::getId)).toArray((i) -> new DuodecupleVariant[i]);
    private final int id;

    private DuodecupleVariant(int p_30984_) {
        this.id = p_30984_;
    }

    public int getId() {
        return this.id;
    }

    public static DuodecupleVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
