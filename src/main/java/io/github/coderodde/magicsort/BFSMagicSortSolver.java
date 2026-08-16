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
    public List<MagicSortTransition> solve(BottleList startState) {
        Deque<BottleList> q = new ArrayDeque<>(List.of(startState));
        Map<BottleList, BottleList>      p = new HashMap<>();
        Map<BottleList, StateTransition> t = new HashMap<>();
       
        p.put(startState, null);
        t.put(startState, null);
        
        while (!q.isEmpty()) {
            BottleList state = q.removeFirst();
            
            if (state.isSolved()) {
                return generateTransitions(state, p, t);
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
        
        return List.of();
    }
}
