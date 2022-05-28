package oh_heaven.game;

import ch.aplu.jcardgame.Card;
import ch.aplu.jcardgame.Hand;

// An abstract class of all players that can play the game
public abstract class Player {
    // type of player
    private String type;

    // each player's personal in-game data
    private Hand hand;
    private Card selected;
    private int score;
    private int trick;
    private int bid;

    public Player(String type){
        this.type = type;
        initPlayer();
    }

    // initialize player's score, trick and bid
    public void initPlayer() {
        initScore();
        initTrick();
        initBid();
    }

    // methods to initialize
    public void initScore(){
        this.score= 0;
    }
    public void initTrick(){
        this.trick = 0;
    }
    public void initBid() {this.bid = 0;}

    // getters for players
    public String getType(){
        return this.type;
    }
    public int getScore() {return this.score;}
    public int getTrick() {return this.trick;}
    public int getBid() {return this.bid;}
    public Hand getHand() {return this.hand;}
    public Card getSelected() {return this.selected;}

    // setters for players
    public void setHand(Hand deck) {this.hand = deck;}
    public void setSelected(Card selected) {this.selected = selected;}
    public void setBid(int bid) {this.bid = bid;}

    // increments trick by 1 if round is won
    public void updateTrick() {this.trick+=1;}

    // increment player's bid
    public void addBid(int bid) {this.bid += bid;}

    // increment player's score
    public void addScore(int score) {this.score += score;}

    // add player's score with tricks won
    public void addScoreTrick() {this.score += this.trick;}

    // resets selected card of player back to null in new trick round
    public void resetSelect(){this.selected = null;}

}
