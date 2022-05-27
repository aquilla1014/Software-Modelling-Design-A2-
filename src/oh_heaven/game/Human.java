package oh_heaven.game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.CardAdapter;
import ch.aplu.jcardgame.CardListener;

public class Human extends Player{
    private static final String TYPE = "human";

    public Human() {
        super(TYPE);
    }

    // Set up human player for interaction
    CardListener cardListener = new CardAdapter()  // Human Player plays card
    {
        public void leftDoubleClicked(Card card) {
            setSelected(card);
            getHand().setTouchEnabled(false);
        }
    };

    public void addEvent(){
        getHand().addCardListener(cardListener);
    }

}
