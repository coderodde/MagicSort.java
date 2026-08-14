package io.github.coderodde.magicsort;

/**
 * This record implements a transition in the game of Magic Sort.
 */
public final record MagicSortTransition(Bottle source,
                                        Bottle target,
                                        int pours) {
    
    @Override
    public String toString() {
        return "(%s - %d -> %s)".formatted(source, pours, target);
    }
}
