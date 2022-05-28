package oh_heaven.game;

// A Legal NPC, extended from NPC
public class LegalNPC extends NPC {

    private static final String TYPE = "legal";

    // legal NPC uses legal strategy
    private static final TrickStrategy STRATEGY = new LegalStrategy();

    public LegalNPC(){
        super(TYPE, STRATEGY);
    }

}
