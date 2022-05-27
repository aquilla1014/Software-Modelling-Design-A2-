package oh_heaven.game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

public abstract class Player {

    private String type;

    private Hand hand;
    private Card selected;

    private int score;
    private int trick;
    private int bid;

    public Player(String type){
        this.type = type;
        initPlayer();
    }

    public void initPlayer() {
        initScore();
        initTrick();
        initBid();
    }
    private void initScore(){
        this.score= 0;
    }
    public void initTrick(){
        this.trick = 0;
    }
    private void initBid() {this.bid = 0;}

    public String getType(){
        return this.type;
    }
    public int getScore() {return this.score;}
    public int getTrick() {return this.trick;}
    public int getBid() {return this.bid;}
    public Hand getHand() {return this.hand;}
    public Card getSelected() {return this.selected;}

    public void setHand(Hand deck) {this.hand = deck;}
    public void setSelected(Card selected) {this.selected = selected;}
    public void updateTrick() {this.trick+=1;}
    public void updateBid(int bid) {this.bid += bid;}
    public void addScoreTrick() {this.score += this.trick;}
    public void setScore(int score) {this.score += score;}
    public void setBid(int bid) {this.bid = bid;}

    public void resetSelect(){this.selected = null;}

}
