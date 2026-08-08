package io.github.coderodde.magicsort;

import static io.github.coderodde.magicsort.Bottle.SectionColor.NAVY;
import static io.github.coderodde.magicsort.Bottle.SectionColor.NONE;
import static io.github.coderodde.magicsort.Bottle.SectionColor.ORANGE;
import static io.github.coderodde.magicsort.Bottle.SectionColor.PINK;
import static io.github.coderodde.magicsort.Bottle.SectionColor.YELLOW;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public final class BottleTest {
    
    private Bottle b;
    
    @Before
    public void before() {
        b = new Bottle();
    }
    
    @Test
    public void test1() {
        assertEquals(NONE, b.getTopmostSectionColor());
        
        assertEquals(0, b.filledSections());
        assertEquals(b.totalSections(), b.freeSections());
        
        b.push(ORANGE);
        
        assertEquals(1, b.filledSections());
        assertEquals(3, b.freeSections());
        
        assertEquals(ORANGE, b.getTopmostSectionColor());
        
        assertEquals(ORANGE, b.pop());
        
        b.push(PINK);
        b.push(NAVY);
        b.push(YELLOW);
        
        assertEquals(PINK, b.getSectionColor(2));
        assertEquals(NAVY, b.getSectionColor(1));
        assertEquals(YELLOW, b.getSectionColor(0));
        
        assertEquals(1, b.freeSections());
        assertEquals(3, b.filledSections());
        
        assertEquals(YELLOW, b.pop());
        assertEquals(NAVY, b.pop());
        assertEquals(PINK, b.pop());
    }
}
