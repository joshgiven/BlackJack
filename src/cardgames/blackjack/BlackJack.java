package cardgames.blackjack;

import java.util.*;
import java.util.concurrent.TimeUnit;

import menu.*;
import cardgames.core.*;
import cardgames.blackjack.Player.Play;
import cardgames.blackjack.Player.Status;

public class BlackJack {
	InputPrompter menu;
	Deck deck;
	Dealer dealer;
	List<Player> players;
	List<Player> users;
	List<Player> quitters;
	CardCounter lenny;
	boolean showCardCounts = false;
	
	final int STARTING_PURSE = 200;

	public BlackJack() {
		menu = new InputPrompter(null);
		players  = new ArrayList<>();
		users    = new ArrayList<>();
		quitters = new ArrayList<>();
		deck = null;
		lenny = new CardCounter();
	}

	public static void main(String[] args) {
		BlackJack game = new BlackJack();
		game.initialize();
		game.play();
	}

	private void initialize() {
		displaySplash();

		int numUsers = 0;
		do {
			numUsers = menu.getUserInt("How many users we be playing (1-5)? ");
		} while(numUsers < 1 && numUsers > 5);
		
		//showCardCounts = menu.getUserYN("Would you like to count cards? (y/n) ");
		
		// get player info
		for(int i=0; i < numUsers; i++) {
			String userName = menu.getUserString(String.format("Enter User%d's name: ", i+1));
			Player userPlayer = new UserPlayer(userName, STARTING_PURSE);
			users.add(userPlayer);
			players.add(userPlayer);
		}
		
		int numPlayers = 5;
		while(players.size() < numPlayers) {
			players.add(new DummyPlayer("Player" + (players.size()+1), STARTING_PURSE));
		}
		
		Collections.shuffle(players);
		players.add(dealer = new Dealer());
	}

	private void play() {
		boolean keepPlaying = true;
		do {
			deck = getNewDeckIfNeeded(deck);
			
			getWagers();
			
			// a wager of zero kicks a user from the game
			for(int i=0; i < users.size(); i++) {
				Player user = users.get(i);
				if(user.getCurrentWager() <= 0) {
					users.remove(i);
					quitters.add(user);
					i--; // look into Iterators
				}
			}
			
			// no users ends the fun
			if(users.size() == 0)
				break;
			
			dealCards();

			// cheater hands for testing
			// dealer.newHand();
			// dealer.getHand().addCard(new BJCard(Rank.ACE, Suit.SPADES));
			// dealer.getHand().addCard(new BJCard(Rank.TEN, Suit.SPADES));
			// user.newHand();
			// user.getHand().addCard(new BJCard(Rank.ACE, Suit.SPADES));
			// user.getHand().addCard(new BJCard(Rank.TEN, Suit.SPADES));
			
			//dealer.newHand();
			//dealer.getHand().addCard(new BJCard(Rank.THREE, Suit.SPADES));
			//dealer.getHand().addCard(new BJCard(Rank.TEN, Suit.SPADES));
			// user.newHand();
			// user.getHand().addCard(new BJCard(Rank.TEN, Suit.DIAMONDS));
			// user.getHand().addCard(new BJCard(Rank.TEN, Suit.SPADES));
			// deck.add(0,new BJCard(Rank.TEN, Suit.DIAMONDS));
			// deck.add(0,new BJCard(Rank.TEN, Suit.DIAMONDS));
			// deck.add(0,new BJCard(Rank.TEN, Suit.DIAMONDS));
			// deck.add(0,new BJCard(Rank.TEN, Suit.DIAMONDS));
			// TODO: add insurance option
			// if(dealer.isShowingAce()) {} // insurance

			boolean isGameOver = false;
			boolean isDealerTurn = false;

			if(dealer.hasBlackJack()) {
				dealer.setStatus(Status.BLACKJACK);
				dealer.showHoleCard();
			}
			
			while (!isGameOver) {
				displayTable(isGameOver);
				
				// does a dealer blackjack end game?
				//  (assignment says yes)
				if(dealer.hasBlackJack()) {
					for(Player p : players)
						p.setStatus(Status.STAND);
					
					isGameOver = true;
					
					print("    * Dealer has BLACKJACK! *");
				}

				for(int index=0; index < players.size(); index++) {
					Player p = players.get(index);
					
					if(p == dealer && !isDealerTurn)
						continue;

					if (p.getStatus() == Status.INPLAY) {

						if(p.hasTwentyOne()) {
							if(p.hasBlackJack()) {
								p.setStatus(Status.BLACKJACK);
								
								// Does player blackjack end game?
								//   (assignment says yes)
								{
									dealer.setStatus(Status.STAND);
									dealer.showHoleCard();
									print("    * " + p.getName() + " has BLACKJACK *");
								}
							}
							else {
								p.setStatus(Status.TWENTYONE);
							}
							
							continue;
						}

						Play play = p.getPlay(dealer);

						switch (play) {
						case HIT:
							playerHits(p);
							break;
						case SPLIT:
							playerSplitsHand(p);
							index--; // rollback index so player goes again
							break;
						case DOUBLE:
							playerDoubles(p);
							break;
						case STAND:
						default:
							playerStands(p);
							break;
						}
	
						displayPlay(p, play);
					}					
				}

				int numBusted = 0;
				int numInPlay = 0;
				boolean isDealerWinning = true;
				for(Player p : players) {
					if(p == dealer)
						continue;

					if(p.getStatus() == Status.INPLAY)
						numInPlay++;

					if(p.getStatus() == Status.BUST)
						numBusted++;
					
					if(dealer.getHand().value() < p.getHand().value())
						isDealerWinning = false;
				}

				// if all but dealer have busted, stop now
				if(numBusted == players.size()-1)
					isGameOver = true;

				// if all players done, let dealer play
				if(numInPlay == 0) {
					if(dealer.getStatus() != Status.INPLAY)
						isGameOver = true;
					else
						isDealerTurn = true;
				}
				
				if(isDealerTurn) {
					dealer.showHoleCard();
					
					// if nobody playing and dealer has highest hand, then stop
					if(isDealerWinning)
						isGameOver = true;
				}				
			}

			displaySummary();
			settleWagers();
			resetTable();

			// remove broke players from list
			for(int i=0; i < users.size(); i++) {
				Player user = users.get(i);
				if(user.getPurse() <= 0) {
					users.remove(i);
					quitters.add(user);
					i--; // look into Iterators
				}
			}

			
		} while (keepPlaying && users.size() > 0);

		displayExitMessage();
	}

	private void playerDoubles(Player p) {
		// is there enough money to cover add'l bet?
		if(p.getCurrentWager() < p.getPurse())
			p.setCurrentWager(2 * p.getCurrentWager());
		
		// player must hit ...
		playerHits(p);
		
		// ... and end turn
		if(p.getStatus() == Status.INPLAY)
			p.setStatus(Status.STAND);
	}

	private Card dealCard() {
		Card card = deck.draw();
		lenny.count(card);
		return card;
	}
	
	private void playerHits(Player p) {
		p.getHand().addCard(dealCard());
		int newHandValue = p.getHand().value();
		if (newHandValue > 21)
			p.setStatus(Status.BUST);
		else if(newHandValue == 21)
			p.setStatus(Status.TWENTYONE);
	}

	private void playerStands(Player p) {
		p.setStatus(Status.STAND);
	}

	private void playerSplitsHand(Player p) {
		if(p.getCurrentWager() > p.getPurse())
			playerStands(p);
		
		// create tmp Player for 2nd hand
		Player tmpPlayer = ((SplitablePlayer)p).splitPlayer();

		// insert tmp after user's hand
		int index = players.indexOf(p) + 1;
		players.add(index, tmpPlayer);
		
		displayTable(false);
	}

	private void settleWagers() {
		for(int i=0; i < players.size(); i++) {
			Player p = players.get(i);
			if(p.getCurrentWager() > 0) {
				if(p.getStatus() == Status.BUST) {
					p.settleLoss(p.getCurrentWager());
				}
				else if(dealer.getStatus() == Status.BUST ||
				        p.getHand().value() > dealer.getHand().value()) {
					p.collectWinnings(p.getCurrentWager());
				}
				else if(p.getHand().value() < dealer.getHand().value()) {
					p.settleLoss(p.getCurrentWager());
				}
			}
			
			// resolve the split players
			if(p instanceof SplitablePlayer) {
				SplitablePlayer sp = (SplitablePlayer) p;
				if(sp.getOriginalPlayer() != sp) {
					sp.getOriginalPlayer().unsplitPlayer(sp);
					players.remove(p);
					i--;
				}
			}
		}
	}

	private void getWagers() {
		for(Player p : players) {
			if(p == dealer) {
				p.setCurrentWager(-1);
				continue;
			}
			
			if(p.getPurse() <= 0) {
				p.setCurrentWager(0);
				continue;
			}
			
			int bet = p.placeWager();
			if(bet > p.getPurse()) {
				p.setCurrentWager(0);
				continue;
			}
			
			p.setCurrentWager(bet);
		}
	}

	private void resetTable() {
		for (Player p : players) {
			p.newHand();
			p.setStatus(Status.INPLAY);
			p.setCurrentWager(0);
		}
		dealer.hideHoleCard();
	}

	private void dealCards() {
		print("Dealing cards...");
		for (int i = 0; i < 2; i++) {
			for (Player p : players) {
				if(p.getCurrentWager() == 0) {
					p.setStatus(Status.STAND);
				}
				else {
					p.getHand().addCard(dealCard());
				}
			}
		}
	}
	
	private Deck getNewDeckIfNeeded(Deck deck) {
		double deckFactor = .75;
		int deckSize = 52;
		
		if(deck == null || deck.size()/(double)deckSize < (1-deckFactor)) {
			print("Replacing deck...");	
			deck = new Deck();
			deck.shuffle();
		}
		
		lenny.reset();
		return deck;
	}

	void print(String text) {
		System.out.println(text);
		
		try { TimeUnit.MILLISECONDS.sleep(750); } 
		catch (Exception e) { /* eat e */ }
	}
	
	private String cardCountsToString() {
		return lenny.toString();
	}
	
	private void displayPlay(Player p, Play play) {
		StringBuilder sb = new StringBuilder();
		
		sb.append("    * ").append(p.getName()).append(" ");
		sb.append(play.toString().toLowerCase()).append("s");
		
		if(play == Play.HIT || play == Play.DOUBLE) {
			sb.append(", ");
			sb.append(p.getHand().get(p.getHand().size()-1));
			sb.append(" was drawn");
		}
		sb.append(" *");
		
		print(sb.toString());
	}

	private void displaySummary() {
		StringBuilder sb = new StringBuilder();
		// big \u2664 	\u2661 	\u2662 	\u2667
		// small \u2660\u2665\u2666\u2663
		sb.append("\n" + "\u2664\u2661\u2662\u2667"+ "\u2664\u2661\u2662\u2667" + 
		      "   Game Summary   " + 
		      "\u2664\u2661\u2662\u2667" + "\u2664\u2661\u2662\u2667");
		
		sb.append(tableToString(true));

		for(Player p : players) {
			if(p==dealer) {
				continue;
			}
			else if(p.getStatus() == Status.BUST) {
				// player busted
				sb.append("    " + p.getName() + " busted" + 
				          ", loses $" + p.getCurrentWager());
			}
			else if(dealer.getStatus() == Status.BUST ||
					p.getHand().value() > dealer.getHand().value()) {
				// player wins
				sb.append("    " + p.getName() + " wins!" +
						" Collects $" + p.getCurrentWager());
			}
			else if(p.getHand().value() < dealer.getHand().value()) {
				// dealer wins
				sb.append("    " + p.getName() + " loses" + 
						", loses $" + p.getCurrentWager());
			}
			else {
				// push
				sb.append("    " + p.getName() + " pushes");
			}
			sb.append("\n");
		}
		
		sb.append("\n" + "\u2664\u2661\u2662\u2667" + "\u2664\u2661\u2662\u2667" +
				"\u2664\u2661\u2662\u2667" + "\u2664\u2661\u2662\u2667" +
				"\u2664\u2661\u2662\u2667" + "\u2664\u2661\u2662\u2667" +
				"\u2664\u2661\u2662");
		
		print(sb.toString());
	}

	private void displayTable(boolean showValues) {
		print(tableToString(showValues));
	}

	private String tableToString(boolean showValues) {
		StringBuilder sb = new StringBuilder();
		
		List<Player> displayers = new ArrayList<>(players);
		displayers.remove(dealer);
		displayers.add(0, dealer);
		
		String sep = "  " + "+---------------------------------";
		
		sb.append("\n");
		sb.append(sep).append("\n");
		for (Player p : displayers) {
			
			sb.append("  " + "| " + p.getName());
			
			if(p != dealer) 
				sb.append("($" + p.getCurrentWager() + ") ");
			else
				sb.append("      ");
			
			sb.append("\t: " + p.displayHand());
			
			if(p.getStatus() == Status.BUST)
				sb.append("(BUSTED)");
			else if(p.getStatus() == Status.BLACKJACK)
				sb.append("(BLACKJACK)");
			else if(showValues)
				sb.append("(value: " + p.getHand().value() + ")");
			
			sb.append("\n");
		}
		sb.append(sep).append("\n");
		
		if(showCardCounts)
			sb.append(cardCountsToString()).append("\n");
		
		return sb.toString();
	}
	
	private void displayExitMessage() {
		
		for(Player user : quitters) {
			print("\n" + user.getName() + " left with $" + user.getPurse());
			if(user.getPurse() == 0) {
				print("Gambling problem? Call 1-800-522-4700");
			}
			else if(user.getPurse() == STARTING_PURSE){
				print("You broke even. That's kind of like winning.");
			}
			else if(user.getPurse() > STARTING_PURSE){
				print("WOO-HOO! Big Winner!");
			}
			else {
				print("At least you still have your shirt.");
			}
			print("");
		}
	}

	private void displaySplash() {
		StringBuilder sb = new StringBuilder();
		sb.append("****************************************************\n");
		sb.append("* ************************************************ *\n");
		sb.append("* *                                              * *\n");
		sb.append("* *      +                                -      * *\n");
		sb.append("* *     +++       blackjack!             - -     * *\n");
		sb.append("* *    +++++                            -   -    * *\n");
		sb.append("* *   +++++++                          -     -   * *\n");
		sb.append("* *    ++ ++             (woo-hoo)      -   -    * *\n");
		sb.append("* *      +                               - -     * *\n");
		sb.append("* *     +++                               -      * *\n");
		sb.append("* *                                              * *\n");
		sb.append("* ************************************************ *\n");
		sb.append("****************************************************\n");
		

		//print(sb.toString());
		//print(sb.toString().replaceAll("\\*{4}", "\u2660\u2665\u2666\u2663"));
		print(sb.toString().replaceAll("\\-", "\u2666").replaceAll("\\+","\u2660"));
	}
}

