package oh_heaven.game;

// Oh_Heaven.java

import ch.aplu.jcardgame.*;
import ch.aplu.jgamegrid.*;
import java.awt.Color;
import java.awt.Font;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class Oh_Heaven extends CardGame {

	private final String version = "1.0";
	Font bigFont = new Font("Serif", Font.BOLD, 36);
	final String trumpImage[] = {"bigspade.gif","bigheart.gif","bigdiamond.gif","bigclub.gif"};

	private final int handWidth = 400;
	private final int trickWidth = 40;
	private final Deck deck = new Deck(Suit.values(), Rank.values(), "cover");
	private final Location[] handLocations = {
			new Location(350, 625),
			new Location(75, 350),
			new Location(350, 75),
			new Location(625, 350)
	};
	private final Location[] scoreLocations = {
			new Location(575, 675),
			new Location(25, 575),
			new Location(575, 25),
			// new Location(650, 575)
			new Location(575, 575)
	};

	private final Location trickLocation = new Location(350, 350);
	private final Location textLocation = new Location(350, 450);
	private Location hideLocation = new Location(-500, - 500);
	private Location trumpsActorLocation = new Location(50, 50);

	private Card selected;
	static Random random;
	private int nbStartCards;
	private int nbPlayers;
	private int madeBidBonus;
	private int nbRounds;
	private boolean enforceRules;
	private List<Player> allPlayers;
	private Actor[] scoreActors;
	
  public enum Suit
  {
    SPADES, HEARTS, DIAMONDS, CLUBS
  }

  public enum Rank
  {
    // Reverse order of rank importance (see rankGreater() below)
	// Order of cards is tied to card images
	ACE, KING, QUEEN, JACK, TEN, NINE, EIGHT, SEVEN, SIX, FIVE, FOUR, THREE, TWO
  }

	// return random Enum value
  public static <T extends Enum<?>> T randomEnum(Class<T> clazz){
      int x = random.nextInt(clazz.getEnumConstants().length);
      return clazz.getEnumConstants()[x];
  }

  // return random Card from Hand
  public static Card randomCard(Hand hand){
      int x = random.nextInt(hand.getNumberOfCards());
      return hand.get(x);
  }

  private void dealingOut(int nbPlayers, int nbCardsPerPlayer) {
	  Hand pack = deck.toHand(false);
	  // pack.setView(Oh_Heaven.this, new RowLayout(hideLocation, 0));
	  for (int i = 0; i < nbCardsPerPlayer; i++) {
		  for (int j=0; j < nbPlayers; j++) {
			  if (pack.isEmpty()) return;
			  Card dealt = randomCard(pack);
		      // System.out.println("Cards = " + dealt);
		      dealt.removeFromHand(false);

		      allPlayers.get(j).getHand().insert(dealt, false);
		  }
	  }
  }
  
  public boolean rankGreater(Card card1, Card card2) {
	  return card1.getRankId() < card2.getRankId(); // Warning: Reverse rank order of cards (see comment on enum)
  }

  public void setStatus(String string) { setStatusText(string); }

private void initScoreActor(){
  	scoreActors = new Actor[nbPlayers];
}

private void initScore() {
	 for (int i = 0; i < nbPlayers; i++) {
		 // scores[i] = 0;
		 // String text = "[" + String.valueOf(scores[i]) + "]" + String.valueOf(tricks[i]) + "/" + String.valueOf(bids[i]);
		 String text = "[" + String.valueOf(allPlayers.get(i).getScore()) + "]" + String.valueOf(allPlayers.get(i).getTrick()) + "/" + String.valueOf(allPlayers.get(i).getBid());
		 scoreActors[i] = new TextActor(text, Color.WHITE, bgColor, bigFont);
		 addActor(scoreActors[i], scoreLocations[i]);
	 }
  }

private void updateScore(int player) {
	removeActor(scoreActors[player]);
	String text = "[" + String.valueOf(allPlayers.get(player).getScore()) + "]" + String.valueOf(allPlayers.get(player).getTrick()) + "/" + String.valueOf(allPlayers.get(player).getBid());
	scoreActors[player] = new TextActor(text, Color.WHITE, bgColor, bigFont);
	addActor(scoreActors[player], scoreLocations[player]);
}

private void updateScores() {

	 for (int i = 0; i < nbPlayers; i++) {
	 	/*
		 scores[i] += tricks[i];
		 if (tricks[i] == bids[i]) scores[i] += madeBidBonus;
	 	 */
		 allPlayers.get(i).addScoreTrick();
		 if (allPlayers.get(i).getTrick() == allPlayers.get(i).getBid()){
		 	allPlayers.get(i).setScore(madeBidBonus);
		 }
	 }
}

private void initTricks() {
	 for (int i = 0; i < nbPlayers; i++) {
		 allPlayers.get(i).initTrick();
	 }
}

private void initBids(Suit trumps, int nextPlayer) {
	int total = 0;
	for (int i = nextPlayer; i < nextPlayer + nbPlayers; i++) {
		 int iP = i % nbPlayers;

		 // bids[iP] = nbStartCards / 4 + random.nextInt(2);
		 allPlayers.get(iP).setBid(nbStartCards / 4 + random.nextInt(2));

		 // total += bids[iP];
		 total += allPlayers.get(iP).getBid();
	 }
	 if (total == nbStartCards) {  // Force last bid so not every bid possible
		 int iP = (nextPlayer + nbPlayers) % nbPlayers;

		 /*
		 if (bids[iP] == 0) {
			 bids[iP] = 1;
		 } else {
			 bids[iP] += random.nextBoolean() ? -1 : 1;
		 }
		  */

		 if (allPlayers.get(iP).getBid() == 0){
			 allPlayers.get(iP).setBid(1);
		 } else {
		 	int rndm = random.nextBoolean() ? -1 : 1;
			 allPlayers.get(iP).updateBid(rndm);
		 }
	 }
	// for (int i = 0; i < nbPlayers; i++) {
	// 	 bids[i] = nbStartCards / 4 + 1;
	//  }
 }



private void initRound() {

		for (int i = 0; i < nbPlayers; i++) {
			allPlayers.get(i).setHand(new Hand(deck));
		}

		dealingOut(nbPlayers, nbStartCards);

		 for (int i = 0; i < nbPlayers; i++) {
			   allPlayers.get(i).getHand().sort(Hand.SortType.SUITPRIORITY, true);

			   if (allPlayers.get(i).getType().equals("human")){
				   ((Human)allPlayers.get(i)).addEvent();
			   }
		 }

		 // graphics
	    RowLayout[] layouts = new RowLayout[nbPlayers];

	    for (int i = 0; i < nbPlayers; i++) {
	      layouts[i] = new RowLayout(handLocations[i], handWidth);
	      layouts[i].setRotationAngle(90 * i);
	      // layouts[i].setStepDelay(10);

	      allPlayers.get(i).getHand().setView(this, layouts[i]);
	      allPlayers.get(i).getHand().setTargetArea(new TargetArea(trickLocation));
	      allPlayers.get(i).getHand().draw();
	    }
//	    for (int i = 1; i < nbPlayers; i++) // This code can be used to visually hide the cards in a hand (make them face down)
//	      hands[i].setVerso(true);			// You do not need to use or change this code.
	    // End graphics
 }

	private void playRound() {
	// Select and display trump suit
		final Suit trumps = randomEnum(Suit.class);
		final Actor trumpsActor = new Actor("sprites/"+trumpImage[trumps.ordinal()]);
	    addActor(trumpsActor, trumpsActorLocation);
	// End trump suit

	Hand trick;
	int winner;
	Card winningCard;
	Suit lead;

	int nextPlayer = random.nextInt(nbPlayers); // randomly select player to lead for this round
	initBids(trumps, nextPlayer);
    // initScore();
    for (int i = 0; i < nbPlayers; i++)
		updateScore(i);
		for (int i = 0; i < nbStartCards; i++) {
			trick = new Hand(deck);
			selected = null;
			allPlayers.get(nextPlayer).resetSelect();
			// if (false) {
			int thinkingTime = 2000;

			if (allPlayers.get(nextPlayer).getType().equals("human")){
				allPlayers.get(nextPlayer).getHand().setTouchEnabled(true);
				setStatus("Player " + nextPlayer + " double-click on card to lead.");
				while (null == allPlayers.get(nextPlayer).getSelected()) delay(100);
				selected = allPlayers.get(nextPlayer).getSelected();
			}

			else {
				setStatusText("Player " + nextPlayer + " thinking...");
				delay(thinkingTime);
				selected = randomCard(allPlayers.get(nextPlayer).getHand());
			}

        	// Lead with selected card
	        trick.setView(this, new RowLayout(trickLocation, (trick.getNumberOfCards()+2)*trickWidth));
			trick.draw();
			selected.setVerso(false);
			// No restrictions on the card being lead
			lead = (Suit) selected.getSuit();
			selected.transfer(trick, true); // transfer to trick (includes graphic effect)
			winner = nextPlayer;
			winningCard = selected;
			// End Lead

			for (int j = 1; j < nbPlayers; j++) {
				if (++nextPlayer >= nbPlayers)
					nextPlayer = 0;  // From last back to first

				selected = null;
				allPlayers.get(nextPlayer).resetSelect();

				if (allPlayers.get(nextPlayer).getType().equals("human")) {
					allPlayers.get(nextPlayer).getHand().setTouchEnabled(true);
					setStatus("Player " + nextPlayer + " double-click on card to follow.");
					while (null == allPlayers.get(nextPlayer).getSelected()) delay(100);
					selected = allPlayers.get(nextPlayer).getSelected();
				}
				else {
					setStatusText("Player " + nextPlayer + " thinking...");
					delay(thinkingTime);
					selected = ((NPC)allPlayers.get(nextPlayer)).getStrategy().selectCard(lead, trumps, trick, ((NPC)allPlayers.get(nextPlayer)).getHand());
				}

				// Follow with selected card
				trick.setView(this, new RowLayout(trickLocation, (trick.getNumberOfCards()+2)*trickWidth));
				trick.draw();
				selected.setVerso(false);  // In case it is upside down

				// Check: Following card must follow suit if possible
				if (selected.getSuit() != lead && allPlayers.get(nextPlayer).getHand().getNumberOfCardsWithSuit(lead) > 0) {
					// Rule violation
					String violation = "Follow rule broken by player " + nextPlayer + " attempting to play " + selected;
					System.out.println(violation);
					if (enforceRules)
						try {
							throw(new BrokeRuleException(violation));
						}
						catch (BrokeRuleException e) {
							e.printStackTrace();
							System.out.println("A cheating player spoiled the game!");
							System.exit(0);
						}
				}
				// End Check

				selected.transfer(trick, true); // transfer to trick (includes graphic effect)

				System.out.println("winning: " + winningCard);
				System.out.println(" played: " + selected);
				// System.out.println("winning: suit = " + winningCard.getSuit() + ", rank = " + (13 - winningCard.getRankId()));
				// System.out.println(" played: suit = " +    selected.getSuit() + ", rank = " + (13 -    selected.getRankId()));

				if ( // beat current winner with higher card
						 (selected.getSuit() == winningCard.getSuit() && rankGreater(selected, winningCard)) ||
						  // trumped when non-trump was winning
						 (selected.getSuit() == trumps && winningCard.getSuit() != trumps)) {
						 System.out.println("NEW WINNER");
						 winner = nextPlayer;
						 winningCard = selected;
					 	}
				// End Follow
			}
			delay(600);
			trick.setView(this, new RowLayout(hideLocation, 0));
			trick.draw();
			nextPlayer = winner;
			setStatusText("Player " + nextPlayer + " wins trick.");

			allPlayers.get(nextPlayer).updateTrick();
			updateScore(nextPlayer);

		}
		removeActor(trumpsActor);
	}



  public Oh_Heaven(Properties properties)
  {
  	// headers
	super(700, 700, 30);
    setTitle("Oh_Heaven (V" + version + ") Constructed for UofM SWEN30006 with JGameGrid (www.aplu.ch)");
    setStatusText("Initializing...");

	random = PropertiesLoader.loadSeed(properties);
	nbPlayers = PropertiesLoader.loadTotalPlayers(properties);
	allPlayers = PropertiesLoader.loadNPC(properties, nbPlayers);
	nbStartCards = PropertiesLoader.loadStartCards(properties);
	nbRounds = PropertiesLoader.loadRounds(properties);
	madeBidBonus = PropertiesLoader.loadBidBonus(properties);
	enforceRules = PropertiesLoader.loadRules(properties);

	initScoreActor();
	initScore(); // set GUI of scores, tricks and bids of each player

    for (int i=0; i <nbRounds; i++) {
      initTricks(); // initialize tricks of each player to 0
      initRound(); // initialize a hand with 13 cards for each player
      playRound();
      updateScores();
    };

    for (int i=0; i <nbPlayers; i++) updateScore(i);
    int maxScore = 0;
	  for (int i = 0; i <nbPlayers; i++) if (allPlayers.get(i).getScore() > maxScore) maxScore = allPlayers.get(i).getScore();
    Set <Integer> winners = new HashSet<Integer>();
	  for (int i = 0; i <nbPlayers; i++) if (allPlayers.get(i).getScore() == maxScore) winners.add(i);
    String winText;
    if (winners.size() == 1) {
    	winText = "Game over. Winner is player: " +
    			winners.iterator().next();
    }
    else {
    	winText = "Game Over. Drawn winners are players: " +
    			String.join(", ", winners.stream().map(String::valueOf).collect(Collectors.toSet()));
    }
    addActor(new Actor("sprites/gameover.gif"), textLocation);
    setStatusText(winText);
    refresh();
  }

  public static void main(String[] args)
  {
	// System.out.println("Working Directory = " + System.getProperty("user.dir"));
	final Properties properties;
	if (args == null || args.length == 0) {
		properties = PropertiesLoader.loadPropertiesFile(null);
	} else {
		properties = PropertiesLoader.loadPropertiesFile(args[0]);
	}
    new Oh_Heaven(properties);
  }

}
