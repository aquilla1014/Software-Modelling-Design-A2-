package oh_heaven.game;

public class Smart extends NPC {

    private static final String TYPE = "smart";
    private static final TrickStrategy STRATEGY = new SmartStrategy();

    public Smart(){
        super(TYPE, STRATEGY);
    }

}
