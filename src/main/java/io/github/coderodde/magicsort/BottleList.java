package io.github.coderodde.magicsort;

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
        
        SectionColor expectedColor = bottle.getTopmostSectionColor();
        
        for (int i = 0; i < bottle.filledSections(); ++i) {
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
}
