package io.github.coderodde.magicsort;

import java.util.List;

/**
 * This class is responsible for demonstrating the Magic Sort solution 
 * algorithms.
 */
public final class MagicSort {

    private static final int POURS = 10;
    private static final int FULL_BOTTLES = 3;
    private static final int EMPTY_BOTTLES = 2;
    
    private MagicSort() {
        
    }
    
    public static void main(String[] args) {
        BottleList bottleList = 
            new RandomBottleListBuilder()
                .randomize(FULL_BOTTLES, EMPTY_BOTTLES, POURS);
        
        System.out.println("Starting configuration:");
        System.out.println(bottleList);
        
        long ta = System.currentTimeMillis();
        List<MagicSortTransition> path1 = 
            new BFSMagicSortSolver().solve(bottleList);
        long tb = System.currentTimeMillis();
        
        System.out.printf("BFS path: %s\n", path1);
        System.out.printf("BFS duration: %d ms.\n", tb - ta);
    }
}
