package oh_heaven.game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

public abstract class NPC {
    private Hand hands[];
    private int scores[];
    private int tricks[];
    private int bids[];
    private Card selected;

    public NPC(){

    }

    public abstract void selectCard();
    public abstract void playTrick();

}
