package oh_heaven.game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.Oh_Heaven.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SmartStrategy implements TrickStrategy{

    public static final Comparator<Card> rankComparator = new Comparator<Card>(){

        @Override
        public int compare(Card o1, Card o2) {
            return o2.getRankId() - o1.getRankId();  // This will work because age is positive integer
        }

    };

    public static boolean rankGreater(Card card1, Card card2) {
        return card1.getRankId() < card2.getRankId(); // Warning: Reverse rank order of cards (see comment on enum)
    }

    public Card selectCard(Suit lead, Suit trumps, Hand trick, Hand hand) {
        trick.sort(Hand.SortType.SUITPRIORITY, true);
        ArrayList<Card> table = trick.getCardList();
        ArrayList<Card> validTable = trick.getCardsWithSuit(lead);


        // ArrayList<Card> list = hand.getCardList();
        ArrayList<Card> valid = hand.getCardsWithSuit(lead);
        ArrayList<Card> trump = hand.getCardsWithSuit(trumps);

        /*
        ArrayList<Card> spade = getHand().getCardsWithSuit(Oh_Heaven.Suit.SPADES);
        ArrayList<Card> club = getHand().getCardsWithSuit(Oh_Heaven.Suit.CLUBS);
        ArrayList<Card> heart = getHand().getCardsWithSuit(Oh_Heaven.Suit.HEARTS);
        ArrayList<Card> diamond = getHand().getCardsWithSuit(Oh_Heaven.Suit.DIAMONDS);
        */

        if (valid.size() == 0) {
            if (trump.size() == 1) {
                return trump.get(0);
            }
            else if (trump.size() > 1 ) {

                Collections.sort(trump, rankComparator);

                return trump.get(0);
            }
            else {
                hand.sort(Hand.SortType.RANKPRIORITY, true);
                return hand.getLast();
            }
        }

        else if (valid.size() == 1){
            return valid.get(0);
        }

        else
        {
            Collections.sort(valid, rankComparator);
            for (int i=0; i<valid.size();i++){
                if (rankGreater(valid.get(i), validTable.get(0))) {
                    return valid.get(i);
                }
            }
            return valid.get(0);
        }
    }
}
