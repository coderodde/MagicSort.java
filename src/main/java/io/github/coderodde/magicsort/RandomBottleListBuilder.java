package io.github.coderodde.magicsort;

import io.github.coderodde.magicsort.Bottle.SectionColor;
import static io.github.coderodde.magicsort.Bottle.SectionColor.NONE;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class is responsible for randomizing the bottle sets.
 */
public final class RandomBottleListBuilder {
    
    /**
     * The default seed for the {@link java.util.Random}.
     */
    private static final long DEFAULT_SEED = 113L;

    /**
     * The random number generator.
     */
    private final Random random;
    
    /**
     * This list caches all the non-full bottles.
     */
    private final List<Bottle> nonFullBottleList = new ArrayList<>();
    
    /**
     * This constructor builds this bottle list randomizer.
     * 
     * @param random the random number generator to use. 
     */
    public RandomBottleListBuilder(Random random) {
        this.random = random;
    }
    
    /**
     * This constructor builds this bottle list randomizer.
     * 
     * @param seed the seed value for the random number generator.
     */
    public RandomBottleListBuilder(long seed) {
        this(new Random(seed));
    }
    
    /**
     * This constructor builds this bottle list randomizer with the default 
     * seed.
     */
    public RandomBottleListBuilder() {
        this(DEFAULT_SEED);
    }
    
    /**
     * Builds randomly a bottle list with {@code pours} liquid pours in total.
     * 
     * @param pours indicates how many random pours to perform.
     * 
     * @return a randomized bottle list.
     */
    public BottleList randomize(int fullBottles, int emptyBottles, int pours) {
        BottleList bottleList = new BottleList();
        
        // Create full bottles:
        for (int i = 0; i < fullBottles; ++i) {
            Bottle b = new Bottle();
            colorBottleRandomly(b);
            bottleList.addBottle(b);
        }
        
        // Create empty bottles:
        for (int i = 0; i < emptyBottles; ++i) {
            Bottle b = new Bottle();
            nonFullBottleList.add(b);
            bottleList.addBottle(b);
        }
        
        pourRandomize(bottleList, pours);
        return bottleList;
    }
    
    private Bottle findRandomBottle(BottleList bottleList) {
        return bottleList.get(random.nextInt(bottleList.size()));
    }
    
    private void pourRandomize(BottleList bottleList, int pours) {
        for (int i = 0; i < pours; ++i) {
            Bottle sourceBottle = findRandomBottle(bottleList);
            
            if (sourceBottle.isEmpty()) {
                // Repeat this iteration:
                --i;
                continue;
            }
            
            Bottle targetBottle = findNonFullBottle();
            
            if (targetBottle == null) {
                System.out.println("oops!");
                // Repeat this iteration:
                --i;
                continue;
            }
            
            int maximumPourSections = Math.min(sourceBottle.filledSections(),
                                               targetBottle.freeSections());
            
            if (maximumPourSections == 0) {
                // Repeat this iteration:
                --i;
                continue;
            }
            
            nonFullBottleList.remove(sourceBottle);
            nonFullBottleList.remove(targetBottle);
            
            sourceBottle.transferTo(targetBottle, random);
            
            if (!sourceBottle.isFull()) {
                nonFullBottleList.add(sourceBottle);
            }
            
            if (!targetBottle.isFull()) {
                nonFullBottleList.add(targetBottle);
            }
        }
    }
    
    private void colorBottleRandomly(Bottle bottle) {
        int colors = SectionColor.values().length;
        SectionColor color = SectionColor.values()[random.nextInt(colors)];
        
        while (!bottle.isFull()) {
            bottle.push(color);
        }
    }
    
    private Bottle findNonFullBottle() {
        return nonFullBottleList.get(random.nextInt(nonFullBottleList.size()));
    }
}
