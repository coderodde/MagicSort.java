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
    public BottleList randomize(int pours) {
        BottleList bottleList = new BottleList();
        
        int numColors = Bottle.SectionColor.values().length;
        
        // Build non-empty bottles:
        for (int i = 0; i < numColors; ++i) {
            SectionColor color = Bottle.SectionColor.values()[i];
            
            if (color != NONE) {
                Bottle bottle = new Bottle();
                precolorBottle(bottle, Bottle.SectionColor.values()[i]);
                bottleList.addBottle(bottle);
            }
        }
        
        // Add two empty bottle:
        Bottle emptyBottle1 = new Bottle();
        Bottle emptyBottle2 = new Bottle();
        
        nonFullBottleList.add(emptyBottle1);
        nonFullBottleList.add(emptyBottle2);
        
        bottleList.addBottle(emptyBottle1);
        bottleList.addBottle(emptyBottle2);
        System.out.println("Before: " + bottleList);
        pourRandomize(bottleList, pours);
        return bottleList;
    }
    
    /**
     * Fills the input bottle entirely with the input section color.
     * 
     * @param bottle the target bottle.
     * @param color  the target color.
     */
    private void precolorBottle(Bottle bottle, SectionColor color) {
        for (int i = 0; i < bottle.totalSections(); ++i) {
            bottle.push(color);
        }
    }
    
    private Bottle findRandomBottle(BottleList bottleList) {
        return bottleList.get(random.nextInt(bottleList.size()));
    }
    
    private void pourRandomize(BottleList bottleList, int pours) {
        for (int i = 0; i < pours; ++i) {
            Bottle sourceBottle = findRandomBottle(bottleList);
            Bottle targetBottle = findNonFullBottle(bottleList);
            
            if (targetBottle == null) {
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
            
            sourceBottle.transferTo(targetBottle, random);
            
            nonFullBottleList.remove(sourceBottle);
            nonFullBottleList.remove(targetBottle);
            
            if (!sourceBottle.isFull()) {
                nonFullBottleList.add(sourceBottle);
            }
            
            if (!targetBottle.isFull()) {
                nonFullBottleList.add(targetBottle);
            }
        }
    }
    
    private int getActualPours(int maximumPourSection) {
        return 1 + random.nextInt(maximumPourSection);
    }
    
    private Bottle findNonFullBottle(BottleList bottleList) {
        return nonFullBottleList.get(random.nextInt(nonFullBottleList.size()));
    }
    
    private static <T> T choose(List<T> list, Random random) {
        return list.get(random.nextInt(list.size()));
    }
}
