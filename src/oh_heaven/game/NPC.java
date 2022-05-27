package oh_heaven.game;


import ch.aplu.jcardgame.*;
import oh_heaven.game.Oh_Heaven.*;


public abstract class NPC extends Player {
    private TrickStrategy strategy;

    public NPC(String type, TrickStrategy strategy) {
        super(type);
        this.strategy = strategy;
    }

    public TrickStrategy getStrategy() {
        return this.strategy;
    }

}
