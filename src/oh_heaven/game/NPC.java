package oh_heaven.game;


import ch.aplu.jcardgame.*;
import oh_heaven.game.Oh_Heaven.*;

// A NPC abstract player
// All NPC types extends from this abstract class
public abstract class NPC extends Player {

    // Each NPC has its own strategy, depending on their type
    private TrickStrategy strategy;

    public NPC(String type, TrickStrategy strategy) {
        super(type);
        this.strategy = strategy;
    }

    // gets the NPC's strategy, depending on type
    public TrickStrategy getStrategy() {
        return this.strategy;
    }

}
