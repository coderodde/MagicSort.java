package io.github.coderodde.magicsort;

/**
 * This record implements the state transition from one bottle list to the 
 * neighbouring bottle list.
 */
public final record StateTransition(BottleList nextBottleList,
                                    Bottle sourceBottle,
                                    Bottle targetBottle,
                                    int sourceBottleIndex,
                                    int targetBottleIndex,
                                    int pours) {
    
}
