package oh_heaven.game;

// A Smart NPC, extended from NPC
public class SmartNPC extends NPC {

    private static final String TYPE = "smart";

    // smart NPC uses smart strategy
    private static final TrickStrategy STRATEGY = new SmartStrategy();

    public SmartNPC(int index){
        super(TYPE, STRATEGY, index);
    }

}
