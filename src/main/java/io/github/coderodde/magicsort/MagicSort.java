package io.github.coderodde.magicsort;

import io.github.coderodde.magicsort.MagicSortSolver.SearchResult;
import java.util.List;

/**
 * This class is responsible for demonstrating the Magic Sort solution 
 * algorithms.
 */
public final class MagicSort {

    private static final int POURS = 30;
    private static final int FULL_BOTTLES = 4;
    private static final int EMPTY_BOTTLES = 2;
    
    private MagicSort() {
        
    }
    
    public static void main(String[] args) {
        BottleList bottleList = 
            new RandomBottleListBuilder()
                .randomize(FULL_BOTTLES, EMPTY_BOTTLES, POURS);
        
        System.out.println("Starting configuration:");
        System.out.println(bottleList);
        
        System.out.println();
        
        long ta = System.currentTimeMillis();
        SearchResult data1 = new BFSMagicSortSolver().solve(bottleList);
        long tb = System.currentTimeMillis();
        
        System.out.println("BFS path:");
        
        printPath(data1.transitions());
        System.out.println("BFS expanded states: " + data1.expandedStates());
        System.out.println("BFS visited states : " + data1.visitedStates());
        
        System.out.printf("BFS duration: %d ms.\n", tb - ta);
        
        ta = System.currentTimeMillis();
        SearchResult data2 = 
            new DFSMagicSortSolver().solve(bottleList);
        tb = System.currentTimeMillis();
        
        System.out.println();
        
        System.out.println("DFS path:");
        
        printPath(data2.transitions());
        
        System.out.println("DFS expanded states: " + data2.expandedStates());
        System.out.println("DFS visited states : " + data2.visitedStates());
        
        System.out.printf("DFS duration: %d ms.\n", tb - ta);
        
        System.out.println();
        
        System.out.println(
            "BFS path valid: " + isValidPath(bottleList, data1.transitions()));
        
        System.out.println(
            "DFS path valid: " + isValidPath(bottleList, data2.transitions()));
    }
    
    private static boolean isValidPath(BottleList startState,
                                       List<MagicSortTransition> transitions) {
        BottleList goalState = MagicSortSolver.applyTransitions(startState, 
                                                                transitions);
        
        return goalState.isSolved();
    }
    
    private static void printPath(List<MagicSortTransition> path) {
        int width = Integer.toString(path.size()).length();
        String format = "%" + width + "d: %s%n";
        
        for (int i = 1; i <= path.size(); ++i) {
            System.out.printf(format, i, path.get(i - 1));
        }
    }
}
