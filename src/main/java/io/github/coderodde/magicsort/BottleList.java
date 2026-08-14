package io.github.coderodde.magicsort;

import io.github.coderodde.magicsort.Bottle.BottlePair;
import io.github.coderodde.magicsort.Bottle.SectionColor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * This class is responsible for representing a list of bottles.
 */
public final class BottleList implements Iterable<Bottle> {
    
    /**
     * The actual internal bottle list.
     */
    private final List<Bottle> bottleList = new ArrayList<>();
    
    /**
     * Constructs an empty bottle list.
     */
    public BottleList() {
        
    }
    
    /**
     * The copy-constructor.
     * 
     * @param bottleList the bottle list to copy.
     */
    public BottleList(BottleList bottleList) {
        for (Bottle bottle : bottleList) {
            this.bottleList.add(new Bottle(bottle));
        }
    }
    
    /**
     * Adds a bottle into this bottle list.
     * 
     * @param bottle the bottle to add. 
     */
    public void addBottle(Bottle bottle) {
        bottleList.add(
            Objects.requireNonNull(bottle, "The input bottle is null."));
    }
    
    /**
     * Returns the total number of bottles in this bottle list.
     * 
     * @return the total number of bottles in this list. 
     */
    public int size() {
        return bottleList.size();
    }
    
    /**
     * Returns the {@code index}th bottle.
     * 
     * @param index the target bottle index.
     * 
     * @return the {@code index}th bottle.
     */
    public Bottle get(int index) {
        return bottleList.get(index);
    }
    
    /**
     * Resets a particular bottle in the list.
     * 
     * @param index  the location index.
     * @param bottle the bottle to set.
     */
    public void set(int index, Bottle bottle) {
        bottleList.set(index, bottle);
    }
    
    /**
     * Computes and returns the list of neighbouring bottle lists.
     * 
     * @return the list of neighbouring bottle lists.
     */
    public List<BottleListNeighbourhood> generateNeighbors() {
        List<BottleListNeighbourhood> neighbors = new ArrayList();
        
        for (int i = 0; i < size(); ++i) {
            for (int j = 0; j < size(); ++j) {
                if (i == j) {
                    continue;
                }
                
                Bottle source = bottleList.get(i);
                Bottle target = bottleList.get(j);
                
                int maxPours = Bottle.maxPours(source, target);
                
                for (int p = 1; p <= maxPours; ++p) {
                    BottleList neighbourBottleList = new BottleList(this);
                    BottlePair pair = Bottle.pour(source, target, p);
                    neighbourBottleList.set(i, pair.bottleA());
                    neighbourBottleList.set(j, pair.bottleB());
                    neighbors.add(
                        new BottleListNeighbourhood(
//                            this,       // source bottle list
                            neighbourBottleList, // target bottle list
                            i,          // source bottle index
                            j,          // target bottle index
                            p));        // number of pours
                }
            }
        }
        
        return neighbors;
    }
    
    /**
     * Return {@code true} only if this bottle list represents a solved list.
     * A bottle list is <b>solved</b> if and only if each bottle is either empty
     * or is full of liquid sections of the <b>same</b> color.
     * 
     * @return {@code true} only if this bottle list is solved.
     */
    public boolean isSolved() {
        for (Bottle bottle : bottleList) {
            if (!isSolved(bottle)) {
                return false;
            }
        }
        
        return true;
    }
    
    /**
     * This method implements the actual solution check.
     * 
     * @param bottle the target bottle to check.
     * 
     * @return {@code true} if and only if the input bottle is solved.
     */
    private static boolean isSolved(Bottle bottle) {
        if (bottle.isEmpty()) {
            return true;
        }
        
        if (!bottle.isFull()) {
            return false;
        }
        
        SectionColor expectedColor = bottle.getTopmostSectionColor();
        
        for (int i = 0; i < Bottle.totalSections(); ++i) {
            SectionColor currentColor = bottle.getSectionColor(i);
            
            if (currentColor != expectedColor) {
                return false;
            }
        }
        
        return true;
    }

    @Override
    public Iterator<Bottle> iterator() {
        return bottleList.iterator();
    }
    
    @Override
    public String toString() {
        return bottleList.toString();
    }
    
    @Override
    public int hashCode() {
        return bottleList.hashCode();
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof BottleList bl) {
            return bottleList.equals(bl.bottleList);
        }
        
        return false;
    }
    
    public static final class BottleListNeighbourhood {
//        public final BottleList sourceBottleList;
        public final BottleList targetBottleList;
        public final int sourceBottleIndex;
        public final int targetBottleIndex;
        public final int pours;
        
        public BottleListNeighbourhood(//BottleList sourceBottleList,
                                       BottleList targetBottleList,
                                       int sourceBottleIndex,
                                       int targetBottleIndex,
                                       int pours) {
//            this.sourceBottleList  = sourceBottleList;
            this.targetBottleList  = targetBottleList;
            this.sourceBottleIndex = sourceBottleIndex;
            this.targetBottleIndex = targetBottleIndex;
            this.pours             = pours;
        }
        
        @Override
        public boolean equals(Object o) {
            if (o instanceof BottleListNeighbourhood other) {
                return targetBottleList.equals(other.targetBottleList)
                    && sourceBottleIndex == other.sourceBottleIndex 
                    && targetBottleIndex == other.targetBottleIndex
                    && pours == other.pours;
            }
            
            return false;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 71 * hash + Objects.hashCode(targetBottleList);
            hash = 71 * hash + sourceBottleIndex;
            hash = 71 * hash + targetBottleIndex;
            hash = 71 * hash + pours;
            return hash;
        }
    }
}
