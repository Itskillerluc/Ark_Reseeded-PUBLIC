package com.huskytacodile.alternacraft.util;

import net.minecraft.world.item.Item;

import java.util.Arrays;
import java.util.Random;

public enum Dino {
    EMPTY("empty", DNATier.COMMON),
    ACRO("acrocanthosaurus", DNATier.VERY_RARE),
    ALBERTO("albertosaurus", DNATier.VERY_RARE),
    ATROCIRAPTOR("atrociraptor", DNATier.UNCOMMON),
    BARYG1("baryg1", DNATier.RARE),
    BARYG2("baryg2", DNATier.RARE),
    CARCHARODONTOSAURUS("carcharodontosaurus", DNATier.VERY_RARE),
    CARNORAPTOR("carnoraptor", DNATier.COMMON),
    CARNOTAURUS("carnotaurus", DNATier.RARE),
    CERATO("cerato", DNATier.RARE),
    COELOPHYSIS("coelophysis", DNATier.COMMON),
    DILOPHOSAURUS("dilophosaurus", DNATier.UNCOMMON),
    GIGANTOSAURUS("giganotosaurus", DNATier.VERY_RARE),
    GIRAFFTITAN("girafftitan", DNATier.VERY_RARE),
    HERRERASAURUS("herrerasaurus", DNATier.UNCOMMON),
    IBEROSPINUS("iberospinus", DNATier.RARE),
    ICHTYOVENATOR("ichtyovenator", DNATier.RARE),
    INDOMINUS_REX("indominus_rex", true, DNATier.RARE),
    INDORAPTOR("indoraptor", true, DNATier.RARE),
    MOROS_INTREPIDUS("moros_intrepidus", DNATier.UNCOMMON),
    MOSASAURUS("mosasaurus", DNATier.EPIC),
    OVIRAPTOR("oviraptor", DNATier.UNCOMMON),
    PYRORAPTOR("pyroraptor", DNATier.UNCOMMON),
    QIANZHOUSAURUS("qianzhousaurus", DNATier.RARE),
    SARCHOSUCHUS("sarchosuchus", DNATier.VERY_RARE),
    SCORPIOS_REX("scorpios_rex", true, DNATier.COMMON),
    SIAMOSAURUS("siamosaurus", DNATier.VERY_RARE),
    SIAMOSAURUSG2("siamosaurusg2", DNATier.VERY_RARE),
    SKORPIOVENATOR("skorpiovenator", DNATier.RARE),
    SPINOJP3("spinosaurus_jp3", DNATier.VERY_RARE),
    SPINOG2("spinosaurusg2", DNATier.VERY_RARE),
    SUCHOMIMUS("suchomimus", DNATier.RARE),
    SUSSYAMOGUSAURUS("sussyamogusaurus", DNATier.MYTHICALLY_RARE),
    TARBOSAURUS("tarbosaurus", DNATier.COMMON),
    TYRANOSAURUS("tyranosaurus", DNATier.VERY_RARE),
    VELOCIRAPTOR("velociraptor", DNATier.UNCOMMON)
    ;

    private final String name;
    private Item syringeItem;
    private final boolean isHybrid;
    private final DNATier tier;

    Dino(String name, DNATier tier) {
        this.name = name;
        this.isHybrid = false;
        this.tier = tier;
    }

    Dino(String name, boolean hybrid, DNATier tier) {
        this.name = name;
        this.isHybrid = hybrid;
        this.tier = tier;
    }

    public DNATier getTier() {
        return tier;
    }

    public String getName() {
        return name;
    }

    public void setSyringeItem(Item item) {
        this.syringeItem = item;
    }

    public Item getSyringeItem() {
        return this.syringeItem;
    }

    public static Dino getDinoByItem(Item item) {
        for (Dino dino : Arrays.stream(values()).filter(dino -> dino != Dino.EMPTY).toList()) {
            if(dino.getSyringeItem() == item) {
                return dino;
            }
        }

        return Dino.EMPTY;
    }

    public static Item getRandomSyringeByTier(DNATier tier) {
        Random random = new Random();
        var validValues = Arrays.stream(values()).filter(d -> d.getTier() == tier).toList();

        return validValues.get(random.nextInt(validValues.size())).syringeItem;
    }
}
