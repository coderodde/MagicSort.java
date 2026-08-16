package io.github.coderodde.magicsort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This interface defines the API for the Magic Sort solvers.
 */
public sealed interface MagicSortSolver permits BFSMagicSortSolver,
                                                DFSMagicSortSolver {
    
    /**
     * Attempts to solve the {@code startState}.
     * 
     * @param startState the starting state/bottle list.
     * 
     * @return a path of state transitions. If the search fails, empty list is
     *         returned.
     */
    public SearchResult solve(BottleList startState);
    
    static List<MagicSortTransition>
         generateTransitions(
             BottleList goal, 
             Map<BottleList, BottleList> parents,
             Map<BottleList, StateTransition> transitions) {
        
        List<MagicSortTransition> result = new ArrayList<>();
        BottleList current = goal;
        
        while (true) {
            StateTransition transition = transitions.get(current);
            
            if (transition == null) {
                break;
            }
            
            MagicSortTransition magicSortTransition = 
                new MagicSortTransition(
                    transition.sourceBottle(), 
                    transition.targetBottle(),
                    transition.sourceBottleIndex(),
                    transition.targetBottleIndex(),
                    transition.pours());
            
            result.add(magicSortTransition);
            current = parents.get(current);
        }
        
        return result.reversed();
    }
         
    public static BottleList 
        applyTransitions(BottleList startState,
                         List<MagicSortTransition> transitions) {
        BottleList bl = new BottleList(startState);
        
        for (MagicSortTransition t : transitions) {
            bl = bl.applyTransition(t);
        }
        
        return bl;
    }
        
    public final record SearchResult(List<MagicSortTransition> transitions,
                                     int expandedStates,
                                     int visitedStates) {
    }
}
