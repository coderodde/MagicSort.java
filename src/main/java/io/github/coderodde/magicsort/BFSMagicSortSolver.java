package io.github.coderodde.magicsort;

import static io.github.coderodde.magicsort.MagicSortSolver.generateTransitions;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class implements the breadth-first search for solving the Magic Sort
 * game.
 */
public final class BFSMagicSortSolver implements MagicSortSolver {

    @Override
    public SearchResult solve(BottleList startState) {
        Deque<BottleList> q = new ArrayDeque<>(List.of(startState));
        Map<BottleList, BottleList>      p = new HashMap<>();
        Map<BottleList, StateTransition> t = new HashMap<>();
       
        p.put(startState, null);
        t.put(startState, null);
        
        int expandedStates = 0;
        
        while (!q.isEmpty()) {
            BottleList state = q.removeFirst();
            
            ++expandedStates;
            
            if (state.isSolved()) {
                List<MagicSortTransition> path = 
                    generateTransitions(state, p, t);
                
                return new SearchResult(path, expandedStates, p.size());
            }
            
            for (StateTransition transition : state.generateNeighbors()) {
                BottleList nextState = transition.nextBottleList();
                
                if (!p.containsKey(nextState)) {
                    p.put(nextState, state);
                    q.addLast(nextState);
                    
                    StateTransition stateTransition = 
                        new StateTransition(
                            nextState,
                            transition.sourceBottle(),
                            transition.targetBottle(),
                            transition.sourceBottleIndex(),
                            transition.targetBottleIndex(),
                            transition.pours());
                    
                    t.put(nextState, stateTransition);
                }
            }
        }
        
        return new SearchResult(List.of(), -1, -1);
    }
}
