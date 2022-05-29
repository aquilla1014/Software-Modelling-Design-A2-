package oh_heaven.game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.CardAdapter;
import ch.aplu.jcardgame.CardListener;

// Human Player
public class Human extends Player{
    private static final String TYPE = "human";

    public Human(int index) {
        super(TYPE, index);
    }

    // Set up human player for interaction, moved from Oh_Heaven class
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
