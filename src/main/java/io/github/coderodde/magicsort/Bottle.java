package io.github.coderodde.magicsort;

import static io.github.coderodde.magicsort.Bottle.SectionColor.NONE;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * This class implements the bottle in the Magic Sort game.
 */
public final class Bottle {
    
    private static final int[] HASH_CODE_MULTIPLIERS = { 47, -32, 15, 77 };
    
    /**
     * The number of sections per bottle.
     */
    private static final int SECTIONS = 4;
    
    /**
     * This enumeration specifies all the valid section colours.
     */
    public enum SectionColor {
        NONE           ("x", 2),
        RED            ("R", 3),
        ORANGE         ("O", 5),
        YELLOW         ("Y", 7),
        GREEN          ("G", 11),
        BLUE           ("B", 13),
        LIGHT_BLUE     ("L", 17),
        VIOLET         ("V", 19),
        BROWN          ("W", 23),
        NAVY           ("N", 29),
        PINK           ("P", 31);
        
        private final String name;
        private final int hashc;
        
        private SectionColor(String name, int hashc) {
            this.name = name;
            this.hashc = hashc;
        }
        
        public int getHashCode() {
            return hashc;
        }
        
        @Override
        public String toString() {
            return name;
        }
    }
    
    // The sections[3] is the topmost section, the sections[0] is at the bottom:
    private final SectionColor[] sections = new SectionColor[SECTIONS];
    private int filledSections = 0;
    
    /**
     * Constructs an empty bottle.
     */
    public Bottle() {
        Arrays.fill(sections, NONE);
    }
    
    /**
     * The copy-constructor.
     * 
     * @param bottle the bottle to copy.
     */
    public Bottle(Bottle bottle) {
        this.filledSections = bottle.filledSections;
        
        for (int i = 0; i < totalSections(); ++i) {
            sections[i] = bottle.sections[i];
        }
    }
    
    /**
     * Returns the section color of the {@code index}th section. The {@code 0}th
     * section is the topmost non-empty section.
     * 
     * @param index the index of an non-empty section.
     * @return the section color of the {@code index}th section.
     */
    public SectionColor getSectionColor(int index) {
        return sections[filledSections() - 1 - index];
    }
    
    /**
     * Returns the total number of empty/non-empty sections in this bottle.
     * 
     * @return the total number of sections.
     */
    public static int totalSections() {
        return SECTIONS;
    }
    
    /**
     * Returns the number of free sections in this bottle.
     * 
     * @return the number of free sections. 
     */
    public int freeSections() {
        return SECTIONS - filledSections;
    }
    
    /**
     * Returns the number of filled sections in this bottle.
     * 
     * @return the number of filled sections.
     */
    public int filledSections() {
        return filledSections;
    }
    
    /**
     * Returns {@code true} only if this bottle is empty.
     * 
     * @return a {@code boolean} flag indicating whether this bottle is empty.
     */
    public boolean isEmpty() {
        return filledSections == 0;
    }
    
    /**
     * Returns {@code true} only if this bottle is full.
     * 
     * @return a {@code boolean} flag indicating whether this bottle is full.
     */
    public boolean isFull() {
        return filledSections == SECTIONS;
    }
    
    public SectionColor getTopmostSectionColor() {
        if (isEmpty()) {
            return NONE;
        }
        
        return getSectionColor(0);
    }
    
    public SectionColor pop() {
        SectionColor color = getSectionColor(0);
        sections[--filledSections] = NONE;
        return color;
    }
    
    public void push(SectionColor color) {
        sections[filledSections++] = color;
    }
    
    public void transferTo(Bottle target, Random random) {
        if (isEmpty()) {
            return;
        }
        
        SectionColor color = getTopmostSectionColor();
        int count = 1;
        
        for (int i = 1; i < filledSections(); ++i) {
            SectionColor tentativeColor = getSectionColor(i);
            
            if (tentativeColor != color) {
                break;
            }
            
            ++count;
        }
        
        count = Math.min(count, target.freeSections());
        count = randomizeCount(count, random);
        
        for (int i = 0; i < count; ++i) {
            target.push(pop());
        }
    }
    
    private static int randomizeCount(int maxCount, Random random) {
        switch (maxCount) {
            case 0:
                return 0;
            case 1:
                return 1;
                
            case 2:
                return 1 + random.nextInt(maxCount);
                
            case 3:
                double coin = random.nextDouble();
                
                if (coin < 0.2) {
                    return 1;
                }
                
                if (coin < 0.5) {
                    return 2;
                }
                
                return 3;
                
            case 4:
                coin = random.nextDouble();
                
                if (coin < 0.1) {
                    return 1;
                }
                
                if (coin < 0.25) {
                    return 2;
                }
                
                if (coin < 0.5) {
                    return 4;
                }
                
                return 3;
                
            default:
                throw new IllegalStateException("Should not get here.");
        }
    }

    public void pourTo(Bottle target, int numSections) {
        numSections = Math.min(numSections, target.freeSections());
        numSections = Math.min(numSections, filledSections());
        
        if (numSections == 0) {
            // Cannot pour.
            return;
        }
        
        SectionColor color = getTopmostSectionColor();
        SectionColor targetColor = target.getTopmostSectionColor();
        
        if (color != targetColor && targetColor != NONE) {
            // Topmost section colors mismatch:
            return;
        }
        
        for (int i = 0; i < numSections; ++i) {
            SectionColor clr = pop();
            
            if (clr != color) {
                push(clr);
                return;
            }
            
            target.push(clr);
        }
    }
    
    @Override
    public int hashCode() {
        int hash = 13;
        
        for (int i = 0; i < SECTIONS; ++i) {
            hash += HASH_CODE_MULTIPLIERS[i] * sections[i].getHashCode();
        }
        
        return hash;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder().append("={");
        
        for (int i = Bottle.SECTIONS - 1; i >= 0; --i) {
            sb.append(sections[i]);
        }
        
        return sb.append("]").toString();
    }
    
    @Override
    public boolean equals(Object o) {
        if (o instanceof Bottle b) {
            if (freeSections() != b.freeSections()) {
                return false;
            }
            
            for (int i = 0; i < filledSections(); ++i) {
                if (getSectionColor(i) != b.getSectionColor(i)) {
                    return false;
                }
            }
            
            return true;
        }
        
        return false;
    }
    
    public static int maxPours(Bottle source, Bottle target) {
        int maxPours = Math.min(source.filledSections(), target.freeSections());
        
        if (maxPours == 0) {
            return 0;
        }
        
        if (target.isEmpty()) {
            return source.filledSections();
        }
        
        if (target.isFull()) {
            return 0;
        }
        
        SectionColor sourceColor = source.getTopmostSectionColor();
        SectionColor targetColor = target.getTopmostSectionColor();
        
        if (sourceColor != targetColor) {
            return 0;
        }
        
        maxPours = Math.min(maxPours, target.filledSections());
        
        for (int i = 1; i < maxPours; ++i) {
            // TODO: Bug is here.
            SectionColor tentativeSourceColor = source.getSectionColor(i);
            SectionColor tentativeTargetColor = target.getSectionColor(i);
            
            // Here, sourceColor and targetColor are equal:
            if (tentativeSourceColor != sourceColor ||
                tentativeTargetColor != sourceColor) {
                return i;
            }
        }
        
        return maxPours;
    }
    
    static BottlePair pour(Bottle source, Bottle target, int pours) {
        Bottle a = new Bottle(source);
        Bottle b = new Bottle(target);
        
        for (int i = 0; i <  pours; ++i) {
            b.push(a.pop());
        }
        
        return new BottlePair(a, b);
    }
    
    static final class BottlePair {
        private final Bottle bottleA;
        private final Bottle bottleB;
        
        BottlePair(Bottle bottleA, Bottle bottleB) {
            this.bottleA = bottleA;
            this.bottleB = bottleB;
        }
        
        Bottle bottleA() {
            return bottleA;
        }
        
        Bottle bottleB() {
            return bottleB;
        }
    }
}
