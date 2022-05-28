package oh_heaven.game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;
import oh_heaven.game.Oh_Heaven.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

// A smart strategy used by smart NPC
public class SmartStrategy implements TrickStrategy {

    // a custom comparator method to compare card's rank
    // used to sort
    public static final Comparator<Card> rankComparator = new Comparator<Card>() {
        @Override
        public int compare(Card o1, Card o2) {
            return o2.getRankId() - o1.getRankId();
        }
    };

    // method to compare card's rank
    public static boolean rankGreater(Card card1, Card card2) {
        return card1.getRankId() < card2.getRankId();
    }

    // select a legal card, outputted by the smart strategy's logic
    public Card selectCard(Suit lead, Suit trumps, Hand trick, Hand hand) {
        trick.sort(Hand.SortType.SUITPRIORITY, true);

        ArrayList<Card> validTable = trick.getCardsWithSuit(lead);
        ArrayList<Card> valid = hand.getCardsWithSuit(lead);
        ArrayList<Card> trump = hand.getCardsWithSuit(trumps);

        if (lead == null) {
            // Smart NPC player leads the round, returns the highest ranked card it has
            hand.sort(Hand.SortType.RANKPRIORITY, true);
            return hand.getFirst();
        }

        else if (valid.size() == 0) {
            // no valid card available on hand, consider some options

            if (trump.size() == 1) {
                // returns the only trump card it has
                return trump.get(0);
            } else if (trump.size() > 1) {
                // returns the lowest ranked trump card it has
                Collections.sort(trump, rankComparator);
                return trump.get(0);
            } else {
                // no trumps, and no valids
                // returns lowest ranked card it has
                hand.sort(Hand.SortType.RANKPRIORITY, true);
                return hand.getLast();
            }
        }

        else if (valid.size() == 1) {
            // only a single valid card on hand, return it
            return valid.get(0);
        }

        else {
            // valid cards are on hand

            Collections.sort(valid, rankComparator);
            for (int i = 0; i < valid.size(); i++) {
                // finds the lowest ranked valid card which could win the current round
                // based on what cards are placed on the table
                if (rankGreater(valid.get(i), validTable.get(0))) {
                    return valid.get(i);
                }
            }
            return valid.get(0);
        }
    }

}


