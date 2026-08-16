package io.github.coderodde.magicsort;

import io.github.coderodde.magicsort.MagicSortSolver.SearchResult;
import java.util.List;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

public final class SearchTest {
    
    private static final int POURS = 30;
    private static final int FULL_BOTTLES = 4;
    private static final int EMPTY_BOTTLES = 2;
    
    /**
     * Used for test coverage.
     */
    @Test
    public void test() {
          BottleList bottleList = 
            new RandomBottleListBuilder()
                .randomize(FULL_BOTTLES, EMPTY_BOTTLES, POURS);
        
        SearchResult data1 = new BFSMagicSortSolver().solve(bottleList);
        SearchResult data2 = new DFSMagicSortSolver().solve(bottleList);
        
        assertTrue(isValidPath(bottleList, data1.transitions()));
        assertTrue(isValidPath(bottleList, data2.transitions()));
    }
    
    private static boolean isValidPath(BottleList startState,
                                       List<MagicSortTransition> transitions) {
        BottleList goalState = MagicSortSolver.applyTransitions(startState, 
                                                                transitions);
        
        return goalState.isSolved();
    }
}
