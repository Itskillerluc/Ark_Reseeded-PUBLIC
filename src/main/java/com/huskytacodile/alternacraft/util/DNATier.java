package com.huskytacodile.alternacraft.util;

import com.huskytacodile.alternacraft.Alternacraft;
import net.minecraft.ChatFormatting;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.levelgen.PositionalRandomFactory;

import java.util.Random;

public enum DNATier implements WeightedEntry {
    COMMON(600),
    UNCOMMON(200),
    RARE(100),
    VERY_RARE(50),
    EPIC(35),
    MYTHICALLY_RARE(15);

    private final int weight;
    private static final WeightedRandomList<DNATier> weightedRandomList = WeightedRandomList.create(values());

    DNATier(int weight) {
        this.weight = weight;
    }

    public static DNATier getRandomTierByWeight() {
        DNATier tier = weightedRandomList.getRandom(new RandomSource() {
            @Override
            public RandomSource fork() {
                return null;
            }

            @Override
            public PositionalRandomFactory forkPositional() {
                return null;
            }

            @Override
            public void setSeed(long p_216342_) {

            }

            @Override
            public int nextInt() {
                return 0;
            }

            @Override
            public int nextInt(int p_216331_) {
                return 0;
            }

            @Override
            public long nextLong() {
                return 0;
            }

            @Override
            public boolean nextBoolean() {
                return false;
            }

            @Override
            public float nextFloat() {
                return 0;
            }

            @Override
            public double nextDouble() {
                return 0;
            }

            @Override
            public double nextGaussian() {
                return 0;
            }
        }).orElse(COMMON);
        Alternacraft.LOGGER.debug("Chose " + tier);
        return tier;
    }

    public Rarity getRarity() {
        return switch (this) {
            case UNCOMMON -> Rarity.UNCOMMON;
            case RARE -> Rarity.RARE;
            case VERY_RARE -> Rarity.create("very_rare", ChatFormatting.GREEN);
            case EPIC -> Rarity.EPIC;
            case MYTHICALLY_RARE -> Rarity.create("mythically_rare", ChatFormatting.GOLD);
            default -> Rarity.COMMON;
        };
    }

    @Override
    public Weight getWeight() {
        return Weight.of(weight);
    }
}
