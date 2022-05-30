package oh_heaven.game;

import ch.aplu.jcardgame.Hand;
import oh_heaven.game.Oh_Heaven.*;
import java.util.Map;

// A class Table which serves as a hub of information for all players in the game
public class Table {

    // information visible for all players in each round
    Hand trick;
    Suit lead;
    Suit trump;
    Map<Integer, Integer> allScores;
    Map<Integer, Integer> allTricks;
    Map<Integer, Integer> allBids;

    // initialize instance of a table with constructor
    public Table(Hand trick, Suit lead, Suit trump, Map<Integer, Integer> allScores,
                    Map<Integer, Integer> allTricks, Map<Integer, Integer> allBids){
        this.trick = trick;
        this.lead = lead;
        this.trump = trump;
        this.allScores = allScores;
        this.allTricks = allTricks;
        this.allBids = allBids;
    }

    // getters for Table class
    public Hand getTricks(){
        return this.trick;
    }
    public Suit getLead(){
        return this.lead;
    }
    public Suit getTrump(){
        return this.trump;
    }
    public Map<Integer, Integer> getAllScores(){
        return this.allScores;
    }
    public Map<Integer, Integer> getAllTricks(){
        return this.allTricks;
    }
    public Map<Integer, Integer> getAllBids(){
        return this.allBids;
    }

    // update lead and tricks of the table as it get changed/appended per round of a trick
    public void updateTable(Suit lead, Hand trick, Map<Integer, Integer> allTricks ){
        this.lead = lead;
        this.trick = trick;
        this.allTricks = allTricks;
    }
}
