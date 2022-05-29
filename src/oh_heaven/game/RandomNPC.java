package oh_heaven.game;

// A Random NPC, extended from NPC
public class RandomNPC extends NPC{
    private static final String TYPE = "random";

    // random NPC uses random strategy
    private static final TrickStrategy STRATEGY = new RandomStrategy();

    public RandomNPC(int index){
        super(TYPE, STRATEGY, index);
    }


}
