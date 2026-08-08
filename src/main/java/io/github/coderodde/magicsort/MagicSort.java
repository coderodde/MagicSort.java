package io.github.coderodde.magicsort;

import java.util.List;

/**
 * This class is responsible for demonstrating the Magic Sort solution 
 * algorithms.
 */
public final class MagicSort {

    private MagicSort() {
        
    }
    
    public static void main(String[] args) {
        List<Bottle> bottleList = new BottleFieldRandomizer().randomize(20);
        
        System.out.println("yeah");
    }
}
