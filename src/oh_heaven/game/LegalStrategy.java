package oh_heaven.game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.Oh_Heaven.*;

import java.util.ArrayList;

// A legal strategy used by legal NPC
public class LegalStrategy implements TrickStrategy{

    // selects a legal card
    public Card selectCard(Suit lead, Suit trumps, Hand trick, Hand hand) {
        ArrayList<Card> list = hand.getCardList();
        ArrayList<Card> valid = hand.getCardsWithSuit(lead);

        if (valid.size() != 0) {
            // ran out of all cards with the leading suit, return a random card
            int x = Oh_Heaven.random.nextInt(valid.size());
            return valid.get(x);
        }
        else {
            // returns a random legal card
            int x = Oh_Heaven.random.nextInt(list.size());
            return list.get(x);
        }
    }
}
