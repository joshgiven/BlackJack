package cardgames;

import java.util.*;
import java.util.concurrent.TimeUnit;

import cardgames.Player.Play;
import cardgames.Player.Status;
import menu.*;

public class BlackJack {
	InputPrompter menu;
	Deck deck;
	List<Player> players;
	Dealer dealer;
	//Player user;

	public BlackJack() {
		menu = new InputPrompter(null);
		players = new ArrayList<>();
		deck = new Deck();
	}

	public static void main(String[] args) {
		BlackJack game = new BlackJack();
		game.initialize();
		game.play();
	}

	private void initialize() {
		displaySplash();

		// get player info
		//String userName = menu.getUserString("enter your name: ");
		String userName = "User";

		// init players
		players.add(new UserPlayer(userName));
		players.add(new DummyPlayer("Player2"));
		//players.add(new DummyPlayer("Player3"));
		//players.add(new DummyPlayer("Player4"));
		//players.add(new DummyPlayer("Player5"));
		players.add(dealer = new Dealer());
	}


	private void play() {
		boolean keepPlaying = true;
		double deckFactor = .75;
		
		do {
			// check deck
			if(deck.size()/(double)52 < (1 - deckFactor)) {
				System.out.println("new deck");	
				deck = new Deck();
			}
			
			// deal
			deck.shuffle();
			for (int i = 0; i < 2; i++) {
				for (Player p : players) {
					p.getHand().addCard(deck.draw());
				}
			}
			
//			dealer.newHand();
//			dealer.getHand().addCard(new BJCard(Rank.ACE, Suit.SPADES));
//			dealer.getHand().addCard(new BJCard(Rank.TEN, Suit.SPADES));
			
			// while no winners
			//   for each player (non-dealer)
			//     get player choice
			//     update player (hit, split, dd)
			//     check status
			//   
			//   dealer loop
			//
			//   update display
			
			if(dealer.isShowingAce()) {} // insurance
			
			if(dealer.hasTwentyOne()) {
				dealer.setStatus(Status.TWENTYONE);
				if(dealer.hasBlackJack()) {
					dealer.setStatus(Status.BLACKJACK);
				}
				dealer.showHoleCard();
				
				// does a dealer 21 end game?
				for(Player p : players)
					p.setStatus(Status.STAND);
			}
			
			boolean isGameOver = false;
			boolean isDealerTurn = false;
			while (!isGameOver) {
				display(isGameOver);
				
				for (Player p : players) {
					if(p == dealer && !isDealerTurn)
						continue;
					
					if(p.getHand().value() == 21) {
						if(p.hasBlackJack())
							p.setStatus(Status.BLACKJACK);
						else
							p.setStatus(Status.TWENTYONE);
					}
					
					if (p.getStatus() == Status.INPLAY) {
						Play play = p.getPlay(dealer);
						
						System.out.println("    " + p.getName() + " " + 
						                   play.toString().toLowerCase() + "s" );
						
						try { TimeUnit.MILLISECONDS.sleep(750); } 
						catch (Exception e) {}
						
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

				isDealerTurn = true;
				for(Player p : players) {
					if(p == dealer)
						continue;
					
					if(p.getStatus() == Status.INPLAY) {
						isDealerTurn = false;
						break;
					}
				}
				if(isDealerTurn)
					dealer.showHoleCard();
				
				isGameOver = true;
				for(Player p : players) {
					if(p.getStatus() == Status.INPLAY) {
						isGameOver = false;
						break;
					}
				}

				// check for case that all non-dealers bust
				
				if(isGameOver) {
					display(isGameOver);

					for(Player p : players) {
						if(p==dealer) {
							continue;
						}
						else if(p.getStatus() == Status.BUST) {
							// player busted
							System.out.println(p.getName() + " busted");
						}
						else if(dealer.getStatus() == Status.BUST ||
								p.getHand().value() > dealer.getHand().value()) {
							// player wins
							System.out.println(p.getName() + " wins!");
						}
						else if(p.getHand().value() < dealer.getHand().value()) {
							// dealer wins
							System.out.println(p.getName() + " loses");
						}
						else {
							// push
							System.out.println(p.getName() + " pushes");
						}
					}
					
				}
			}

			// reset
			for (Player p : players) {
				p.newHand();
				p.setStatus(Status.INPLAY);
			}
			dealer.hideHoleCard();
			
			keepPlaying = menu.getUserYN("\nplay again? (Y/N) ");
		} while (keepPlaying);

	}

	private void display(boolean showValues) {
		List<Player> displayers = new ArrayList<>(players);
		displayers.remove(dealer);
		displayers.add(0, dealer);
		
		String sep = "----------------------------------";
		
		System.out.println();
		System.out.println(sep);
		for (Player p : displayers) {
			
			//System.out.print(p.getName() + "'s cards : ");
			System.out.print(" " + p.getName() + "\t: ");
			System.out.print(p.displayHand());
			if(p.getStatus() == Status.BUST)
				System.out.print("(BUSTED)");
			else if(showValues)
				System.out.print("(value: " + p.getHand().value() + ")");
			
			System.out.println();
		}
		System.out.println(sep);
	}
	
	private void displaySplash() {
		StringBuilder sb = new StringBuilder();
		sb.append("******************************************************\n");
		sb.append("******************************************************\n");
		sb.append("****                                              ****\n");
		sb.append("****                                              ****\n");
		sb.append("****             blackjack                        ****\n");
		sb.append("****                                              ****\n");
		sb.append("****                                              ****\n");
		sb.append("****                    woo-hoo                   ****\n");
		sb.append("****                                              ****\n");
		sb.append("****                                              ****\n");
		sb.append("******************************************************\n");
		sb.append("******************************************************\n");
		
//		String s = sb.toString().replaceAll("\\*{4}", "\u2660\u2662\u2663\u2664");
//		System.out.println(s);

		System.out.println(sb);
	}
}

