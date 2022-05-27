package oh_heaven.game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.Oh_Heaven.*;

public interface TrickStrategy {
    Card selectCard(Suit lead, Suit trumps, Hand trick, Hand hand);
}
