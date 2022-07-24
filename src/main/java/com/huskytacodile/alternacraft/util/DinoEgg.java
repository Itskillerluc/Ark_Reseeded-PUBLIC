package com.huskytacodile.alternacraft.util;

import net.minecraft.world.item.Item;

import java.util.Arrays;
import java.util.Random;

public enum DinoEgg {
    EMPTY("empty", EggTier.COMMON),
    ACRO("acrocanthosaurus", EggTier.VERY_RARE),
    ALBERTO("albertosaurus", EggTier.VERY_RARE),
    ATROCIRAPTOR("atrociraptor", EggTier.UNCOMMON),
    BARYG1("baryg1", EggTier.RARE),
    BARYG2("baryg2", EggTier.RARE),
    CARCHARODONTOSAURUS("carcharodontosaurus", EggTier.VERY_RARE),
    CARNORAPTOR("carnoraptor",true, EggTier.MYTHICALLY_RARE),
    CARNOTAURUS("carnotaurus", EggTier.RARE),
    CERATO("cerato", EggTier.RARE),
    COELOPHYSIS("coelophysis", EggTier.COMMON),
    DILOPHOSAURUS("dilophosaurus", EggTier.UNCOMMON),
    GIGANTOSAURUS("giganotosaurus", EggTier.VERY_RARE),
    GIRAFFTITAN("girafftitan", EggTier.VERY_RARE),
    HERRERASAURUS("herrerasaurus", EggTier.UNCOMMON),
    IBEROSPINUS("iberospinus", EggTier.RARE),
    ICHTYOVENATOR("ichtyovenator", EggTier.RARE),
    INDOMINUS_REX("indominus_rex", true, EggTier.MYTHICALLY_RARE),
    INDORAPTOR("indoraptor", true, EggTier.MYTHICALLY_RARE),
    MOROS_INTREPIDUS("moros_intrepidus", EggTier.UNCOMMON),
    MOSASAURUS("mosasaurus", EggTier.EPIC),
    OVIRAPTOR("oviraptor", EggTier.UNCOMMON),
    PYRORAPTOR("pyroraptor", EggTier.UNCOMMON),
    QIANZHOUSAURUS("qianzhousaurus", EggTier.RARE),
    SARCHOSUCHUS("sarchosuchus", EggTier.VERY_RARE),
    SCORPIOS_REX("scorpios_rex", true, EggTier.MYTHICALLY_RARE),
    SIAMOSAURUS("siamosaurus", EggTier.VERY_RARE),
    SIAMOSAURUSG2("siamosaurusg2", EggTier.VERY_RARE),
    SKORPIOVENATOR("skorpiovenator", EggTier.RARE),
    SPINOJP3("spinosaurus_jp3", EggTier.VERY_RARE),
    SPINOG2("spinosaurusg2", EggTier.VERY_RARE),
    SUCHOMIMUS("suchomimus", EggTier.RARE),
    SUSSYAMOGUSAURUS("sussyamogusaurus", EggTier.MYTHICALLY_RARE),
    TARBOSAURUS("tarbosaurus", EggTier.VERY_RARE),
    TYRANOSAURUS("tyranosaurus", EggTier.VERY_RARE),
    VELOCIRAPTOR("velociraptor", EggTier.UNCOMMON)
    ;

    private final String name;
    private Item dinoEggItem;
    private final boolean isHybrid;
    private final EggTier tier;

    DinoEgg(String name, EggTier tier) {
        this.name = name;
        this.isHybrid = false;
        this.tier = tier;
    }

    DinoEgg(String name, boolean hybrid, EggTier tier) {
        this.name = name;
        this.isHybrid = hybrid;
        this.tier = tier;
    }

    public EggTier getTier() {
        return tier;
    }

    public String getName() {
        return name;
    }

    public void setDinoEggItem(Item item) {
        this.dinoEggItem = item;
    }

    public Item getEggItem() {
        return this.dinoEggItem;
    }

    public static DinoEgg getDinoByItem(Item item) {
        for (DinoEgg dinoEgg : Arrays.stream(values()).filter(dinoEgg -> dinoEgg != DinoEgg.EMPTY).toList()) {
            if(dinoEgg.getEggItem() == item) {
                return dinoEgg;
            }
        }
        return DinoEgg.EMPTY;
    }
}
