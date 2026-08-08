package io.github.coderodde.magicsort;

import static io.github.coderodde.magicsort.Bottle.SectionColor.NONE;
import java.util.Arrays;

/**
 * This class implements the bottle in the Magic Sort game.
 */
public final class Bottle {
    
    /**
     * The number of sections per bottle.
     */
    private static final int SECTIONS = 4;
    
    /**
     * This enumeration specifies all the valid section colours.
     */
    public enum SectionColor {
        NONE           ("x"),
        RED            ("R"),
        ORANGE         ("O"),
        YELLOW         ("Y"),
        GREEN          ("G"),
        BLUE           ("B"),
        LIGHT_BLUE     ("L"),
        VIOLET         ("V"),
        BROWN          ("B"),
        NAVY           ("N"),
        PINK           ("P");
        
        private final String name;
        
        private SectionColor(String name) {
            this.name = name;
        }
        
        @Override
        public String toString() {
            return name;
        }
    }
    
    // The sections[0] is the topmost section, the sections[3] is at the bottom:
    private final SectionColor[] sections = new SectionColor[SECTIONS];
    private int filledSections = 0;
    
    /**
     * Constructs an empty bottle.
     */
    public Bottle() {
        Arrays.fill(sections, NONE);
    }
    
    /**
     * Returns the section color of the {@code index}th section. The {@code 0}th
     * section is the topmost non-empty section.
     * 
     * @param index the index of an non-empty section.
     * @return the section color of the {@code index}th section.
     */
    public SectionColor getSectionColor(int index) {
        return sections[index + freeSections()];
    }
    
    /**
     * Returns the total number of empty/non-empty sections in this bottle.
     * 
     * @return the total number of sections.
     */
    public int totalSections() {
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
        
        return sections[SECTIONS - filledSections()];
    }
    
    public SectionColor pop() {
        int targetSectionIndex          = freeSections();
        SectionColor color              = sections[targetSectionIndex];
        sections[targetSectionIndex]    = SectionColor.NONE;
        filledSections--;
        return color;
    }
    
    public void push(SectionColor color) {
        int targetSectionIndex = freeSections() - 1;
        sections[targetSectionIndex] = color;
        filledSections++;
    }
    
    public void pourTo(Bottle target, int numSections) {
        numSections = Math.min(numSections, target.freeSections());
        numSections = Math.min(numSections, filledSections());
        
        if (numSections == 0) {
            // Cannot pour.
            return;
        }
        
        SectionColor color = getTopmostSectionColor();
        
        if (color != target.getTopmostSectionColor()) {
            // Topmost section colors mismatch:
            return;
        }
        
        for (int i = 0; i < numSections; ++i) {
            SectionColor clr = pop();
            
            if (clr != color) {
                return;
            }
            
            target.push(clr);
        }
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder().append("{");
        
        for (SectionColor sectionColor : sections) {
            sb.append(sectionColor);
        }
        
        return sb.append("]").toString();
    }
}
