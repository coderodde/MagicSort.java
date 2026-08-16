package io.github.coderodde.magicsort;

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
        
        long ta = System.currentTimeMillis();
        List<MagicSortTransition> path1 = 
            new BFSMagicSortSolver().solve(bottleList);
        long tb = System.currentTimeMillis();
        
        System.out.println("BFS path:");
        
        String fmt = 
            String.format("%%%d: %%s\n", ("" + path1.size()).length());
        
        for (int i = 1; i <= path1.size(); ++i) {
            System.out.printf(fmt, i, path1.get(i - 1));
        }
        
        System.out.printf("BFS duration: %d ms.\n", tb - ta);
    }
}
