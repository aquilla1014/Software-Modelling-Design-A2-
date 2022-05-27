package oh_heaven.game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.Oh_Heaven.*;

import java.util.ArrayList;

public class Legal extends NPC {

    private static final String TYPE = "legal";
    private static final TrickStrategy STRATEGY = new LegalStrategy();

    public Legal(){
        super(TYPE, STRATEGY);
    }

}
