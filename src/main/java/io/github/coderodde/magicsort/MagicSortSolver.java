package io.github.coderodde.magicsort;

import io.github.coderodde.magicsort.BottleList.BottleListNeighbourhood;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    
    public default List<MagicSortTransition>
        tracebackPath(
            BottleListNeighbourhood goal, 
            Map<BottleListNeighbourhood, 
                BottleListNeighbourhood> parents) {
        
        List<MagicSortTransition> result = new ArrayList<>();
        
        BottleListNeighbourhood current = goal;
        BottleListNeighbourhood previous = parents.get(goal);
        
        while (previous != null && previous.pours > 0) {
            MagicSortTransition transition = generateTransition(previous,
                                                                current);
            
            result.addLast(transition);
            current = previous;
            previous = parents.get(previous);
        }
        
        return result.reversed();
    }
         
    public default MagicSortTransition 
        generateTransition(BottleListNeighbourhood previous,
                           BottleListNeighbourhood current) {
            
        if (previous.targetBottleList.size() != 
             current.targetBottleList.size()) {
            
            throw new IllegalArgumentException(
                "The two input bottle lists are of different size.");
        }
        
        int cnt = 0;
        
        for (int i = 0; i < current.targetBottleList.size(); ++i) {
            if (!previous.targetBottleList.get(i)
                         .equals(current.targetBottleList.get(i))) {
                ++cnt;
            }
            
            if (cnt == 3) {
                break;
            }
        }
        
        if (cnt != 2) {
            throw new IllegalArgumentException(
                "Two differing bottles expected. Incorrect count: " + cnt);
        }
        
        return new MagicSortTransition(
            previous.targetBottleList.get(previous.sourceBottleIndex), 
            previous.targetBottleList.get(previous.targetBottleIndex), 
            previous.pours);
    }
}
