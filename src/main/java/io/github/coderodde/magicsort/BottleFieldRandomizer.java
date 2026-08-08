package io.github.coderodde.magicsort;

import io.github.coderodde.magicsort.Bottle.SectionColor;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class is responsible for randomizing the bottle sets.
 */
public final class BottleFieldRandomizer {
    
    /**
     * The default seed for the {@link java.util.Random}.
     */
    private static final long DEFAULT_SEED = 113L;

    /**
     * The random number generator.
     */
    private final Random random;
    
    /**
     * This constructor builds this bottle list randomizer.
     * 
     * @param random the random number generator to use. 
     */
    public BottleFieldRandomizer(Random random) {
        this.random = random;
    }
    
    /**
     * This constructor builds this bottle list randomizer.
     * 
     * @param seed the seed value for the random number generator.
     */
    public BottleFieldRandomizer(long seed) {
        this(new Random(seed));
    }
    
    /**
     * This constructor builds this bottle list randomizer with the default 
     * seed.
     */
    public BottleFieldRandomizer() {
        this(DEFAULT_SEED);
    }
    
    /**
     * Builds randomly a bottle list with {@code pours} liquid pours in total.
     * 
     * @param pours indicates how many random pours to perform.
     * 
     * @return a randomized bottle list.
     */
    public BottleList randomize(int pours) {
        BottleList bottleList = new BottleList();
        
        int numColors = Bottle.SectionColor.values().length;
        
        // Build non-empty bottles:
        for (int i = 0; i < numColors; ++i) {
            Bottle bottle = new Bottle();
            precolorBottle(bottle, Bottle.SectionColor.values()[i]);
            bottleList.addBottle(bottle);
        }
        
        // Add one empty bottle:
        bottleList.addBottle(new Bottle());
        
        pourRandomize(bottleList, pours);
        return bottleList;
    }
    
    private void precolorBottle(Bottle bottle, SectionColor color) {
        for (int i = 0; i < bottle.totalSections(); ++i) {
            bottle.push(color);
        }
    }
    
    private void pourRandomize(BottleList bottleList, int pours) {
        int tentativePours = 0;
        
        while (tentativePours < pours) {
            Bottle sourceBottle = findNonEmptyBottle(bottleList);
            Bottle targetBottle = findNonFullBottle(bottleList);
            
            int maximumPourSections = Math.min(sourceBottle.filledSections(),
                                               targetBottle.freeSections());
            
            int actualPours = random.nextInt(maximumPourSections) + 1;
            
            sourceBottle.pourTo(targetBottle, actualPours);
            ++tentativePours;
        }
    }
    
    private Bottle findNonFullBottle(BottleList bottleList) {
        List<Bottle> candidatesBottleList = new ArrayList<>();
        
        for (Bottle bottle : bottleList) {
            if (!bottle.isFull()) {
                candidatesBottleList.add(bottle);
            }
        }
        
        return choose(candidatesBottleList, random);
    }
    
    private Bottle findNonEmptyBottle(BottleList bottleList) {
        List<Bottle> candidatesBottleList = new ArrayList<>();
        
        for (Bottle bottle : bottleList) {
            if (!bottle.isEmpty()) {
                candidatesBottleList.add(bottle);
            }
        }
        
        return choose(candidatesBottleList, random);
    }
    
    private static <T> T choose(List<T> list, Random random) {
        return list.get(random.nextInt(list.size()));
    }
}
