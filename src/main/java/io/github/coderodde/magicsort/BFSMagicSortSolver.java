package io.github.coderodde.magicsort;

import io.github.coderodde.magicsort.BottleList.BottleListNeighbourhood;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * This class implements the breadth-first search for solving the Magic Sort
 * game.
 */
public final class BFSMagicSortSolver implements MagicSortSolver {

    @Override
    public List<MagicSortTransition> solve(BottleList startState) {
        Deque<BottleList> q = new ArrayDeque<>(List.of(startState));
        
        Map<BottleListNeighbourhood, 
            BottleListNeighbourhood> p = new HashMap<>();
        
        BottleListNeighbourhood pInit = 
            new BottleListNeighbourhood(startState, -1, -1, -1);
        
        p.put(pInit, null);
        
        while (!q.isEmpty()) {
            BottleList state = q.removeFirst();
            
            if (state.isSolved()) {
                return generateTransition(state, p);
            }
            
            for (BottleListNeighbourhood neighour : state.generateNeighbors()) {
                
            }
        }
        
        return List.of();
    }
    
    private static List<MagicSortTransition>
         generateTransitions(
             BottleListNeighbourhood goal, 
             Map<BottleListNeighbourhood, 
                 BottleListNeighbourhood> parents) {
        
        List<MagicSortTransition> result = new ArrayList<>();
        List<BottleListNeighbourhood> neighbourhoods = new ArrayList<>();
        
        BottleListNeighbourhood current = goal;
        
        while (current.pours > 0) {
            neighbourhoods.add(current);
            current = parents.get(current);
        }
        
        Collections.reverse(neighbourhoods);
        
        for (int i = 0; i < neighbourhoods.size() - 1; ++i) {
            BottleListNeighbourhood source = neighbourhoods.get(i);
            BottleListNeighbourhood target = neighbourhoods.get(i + 1);
            
            result.add(infer(source, target));
        }
        
        return result;
    }
         
    private static MagicSortTransition 
        infer(BottleListNeighbourhood sourceNeighbourhood,
              BottleListNeighbourhood targetNeighbourhood) {
        return new MagicSortTransition(sourceNeighbourhood.targetBottleList, sourceNeighbourhood.so
    }
}
