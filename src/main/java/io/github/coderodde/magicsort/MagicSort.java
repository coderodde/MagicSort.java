package io.github.coderodde.magicsort;

/**
 * This class is responsible for demonstrating the Magic Sort solution 
 * algorithms.
 */
public final class MagicSort {

    private MagicSort() {
        
    }
    
    public static void main(String[] args) {
        BottleList bottleList = new RandomBottleListBuilder().randomize(50);
        
        System.out.println(bottleList);
    }
}
