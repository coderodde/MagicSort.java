package io.github.coderodde.magicsort;

import java.util.List;

/**
 * This interface defines the API for the Magic Sort solvers.
 */
public sealed interface MagicSortSolver permits BFSMagicSortSolver {
    
    /**
     * Attempts to solve the {@code startState}.
     * 
     * @param startState the starting state/bottle list.
     * 
     * @return a path of state transitions. If the search fails, empty list is
     *         returned.
     */
    public List<MagicSortTransition> solve(BottleList startState);
}
