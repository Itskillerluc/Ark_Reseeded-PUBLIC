package com.huskytacodile.alternacraft.entities.variant;

import java.util.Arrays;
import java.util.Comparator;

public enum RarityVariant implements IVariant {
    MALE(0),
    FEMALE(1),
    MALE2(2),
    FEMALE2(3),
    MALE3(4),
    FEMALE3(5),
    MALE4(6),
    FEMALE4(7),
    MALE5(8),
    FEMALE5(9),
    MALE6(10),
    FEMALE6(11),
    AETHER(12);

    private static final RarityVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(RarityVariant::getId)).toArray((i) -> new RarityVariant[i]);
    private final int id;

    private RarityVariant(int p_30984_) {
        this.id = p_30984_;
    }

    public int getId() {
        return this.id;
    }

    public static RarityVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
