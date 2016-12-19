package cardgames.blackjack;

import java.util.*;
import java.util.concurrent.TimeUnit;

import cardgames.*;
import cardgames.blackjack.Player.Play;
import cardgames.blackjack.Player.Status;

import menu.*;

public class BlackJack {
	InputPrompter menu;
	Deck deck;
	List<Player> players;
	Dealer dealer;
	Player user;

	public BlackJack() {
		menu = new InputPrompter(null);
		players = new ArrayList<>();
		deck = null;
	}

	public static void main(String[] args) {
		BlackJack game = new BlackJack();
		game.initialize();
		game.play();
	}

	private void initialize() {
		displaySplash();

		// get player info
		String userName = "User";
		int numPlayers = 1;
		
		// init players
		players.add(user = new UserPlayer(userName));
		
		while(players.size() < numPlayers) {
			players.add(new DummyPlayer("Player" + (players.size()+1)));
		}
		
		players.add(dealer = new Dealer());
	}


	private void play() {
		boolean keepPlaying = true;
		do {
			deck = getNewDeckIfNeeded(deck);
			dealCards();

			// cheater hands for testing
			// dealer.newHand();
			// dealer.getHand().addCard(new BJCard(Rank.ACE, Suit.SPADES));
			// dealer.getHand().addCard(new BJCard(Rank.TEN, Suit.SPADES));
			// user.newHand();
			// user.getHand().addCard(new BJCard(Rank.ACE, Suit.SPADES));
			// user.getHand().addCard(new BJCard(Rank.TEN, Suit.SPADES));

			// TODO: add insurance option
			// if(dealer.isShowingAce()) {} // insurance

			boolean isGameOver = false;
			boolean isDealerTurn = false;


			while (!isGameOver) {
				if(dealer.hasBlackJack()) {
					dealer.setStatus(Status.BLACKJACK);
					dealer.showHoleCard();
					
					// does a dealer blackjack end game?
					//  (assignment says yes)
					{
						for(Player p : players)
							p.setStatus(Status.STAND);
						
						isGameOver = true;
					}
				}

				displayTable(isGameOver);

				for (Player p : players) {
					if(p == dealer && !isDealerTurn)
						continue;

					if (p.getStatus() == Status.INPLAY) {

						if(p.hasTwentyOne()) {
							if(p.hasBlackJack()) {
								p.setStatus(Status.BLACKJACK);
								
								// Does player blackjack end game?
								//   (assignment says yes)
								dealer.setStatus(Status.STAND);
							}
							else {
								p.setStatus(Status.TWENTYONE);
							}
							
							continue;
						}

						Play play = p.getPlay(dealer);
						displayPlay(p, play);

						switch (play) {
						case HIT:
							p.getHand().addCard(deck.draw());
							int newHandValue = p.getHand().value();
							if (newHandValue > 21)
								p.setStatus(Status.BUST);
							else if(newHandValue == 21)
								p.setStatus(Status.TWENTYONE);
							break;

						case STAND:
						default:
							p.setStatus(Status.STAND);
							break;
						}
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
					
					if(p.getHand().value() > dealer.getHand().value())
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
				
				if(isGameOver) {
					displaySummary();
				}
			}

			resetTable();

			keepPlaying = menu.getUserYN("\nPlay again? (Y/N) ");
		} while (keepPlaying);

	}

	private void resetTable() {
		for (Player p : players) {
			p.newHand();
			p.setStatus(Status.INPLAY);
		}
		dealer.hideHoleCard();
	}

	private void dealCards() {
		print("Dealing cards...");
		for (int i = 0; i < 2; i++) {
			for (Player p : players) {
				p.getHand().addCard(deck.draw());
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
		
		return deck;
	}

	void print(String text) {
		System.out.println(text);
		
		try { TimeUnit.MILLISECONDS.sleep(750); } 
		catch (Exception e) { /* eat e */ }
	}
	
	private void displayPlay(Player p, Play play) {
		print("    * " + p.getName() + " " + 
				play.toString().toLowerCase() + "s *" );
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
				sb.append("    " + p.getName() + " busted");
			}
			else if(dealer.getStatus() == Status.BUST ||
					p.getHand().value() > dealer.getHand().value()) {
				// player wins
				sb.append("    " + p.getName() + " wins!");
			}
			else if(p.getHand().value() < dealer.getHand().value()) {
				// dealer wins
				sb.append("    " + p.getName() + " loses");
			}
			else {
				// push
				sb.append("    " + p.getName() + " pushes");
			}
		}
		
		sb.append("\n\n" + "\u2664\u2661\u2662\u2667" + "\u2664\u2661\u2662\u2667" +
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
		
		String sep = "----------------------------------";
		
		sb.append("\n");
		sb.append(sep).append("\n");
		for (Player p : displayers) {
			
			sb.append(" " + p.getName() + "\t: ");
			sb.append(p.displayHand());
			if(p.getStatus() == Status.BUST)
				sb.append("(BUSTED)");
			else if(showValues)
				sb.append("(value: " + p.getHand().value() + ")");
			
			sb.append("\n");
		}
		sb.append(sep).append("\n");
		
		return sb.toString();
	}
	
	private void displaySplash() {
		StringBuilder sb = new StringBuilder();
		sb.append("****************************************************\n");
		sb.append("* ************************************************ *\n");
		sb.append("* *                                              * *\n");
		sb.append("* *      +                                -      * *\n");
		sb.append("* *     +++       blackjack              - -     * *\n");
		sb.append("* *    +++++                            -   -    * *\n");
		sb.append("* *   +++++++                          -     -   * *\n");
		sb.append("* *    ++ ++               woo-hoo      -   -    * *\n");
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

