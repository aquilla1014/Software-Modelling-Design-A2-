package oh_heaven.game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.Oh_Heaven.*;

import java.util.Map;

// A common interface at which all strategies used by NPC is based on
public interface TrickStrategy {

    // abstract method that all strategies implement
    Card selectCard(Table table,  Hand hand, int nextPlayer);
}
