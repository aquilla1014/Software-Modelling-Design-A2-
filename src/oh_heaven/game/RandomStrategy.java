package oh_heaven.game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.Oh_Heaven.*;

import java.util.ArrayList;
import java.util.Map;

// A random strategy used by random NPC
public class RandomStrategy implements TrickStrategy{

    // selects a random card
    public Card selectCard(Table table, Hand hand, int nextPlayer) {
        ArrayList<Card> list = hand.getCardList();
        int x = Oh_Heaven.random.nextInt(list.size());
        return list.get(x);
    }

}
