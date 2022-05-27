package oh_heaven.game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.Oh_Heaven.*;

import java.util.ArrayList;

public class RandomNPC extends NPC{
    private static final String TYPE = "random";
    private static final TrickStrategy STRATEGY = new RandomStrategy();

    public RandomNPC(){
        super(TYPE, STRATEGY);
    }


}
