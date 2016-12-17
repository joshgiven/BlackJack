package cardgames;

import java.util.*;

public class BlackJack {
	List<Player> players;
	Deck deck;

	public BlackJack() {
		deck = new Deck();
		players = new ArrayList<>();
	}
	
	public static void main(String[] args) {
		BlackJack game = new BlackJack();
		game.initialize();
		game.go();
	}

	private void initialize() {
		// get player info
		// init players
		players.add(new UserPlayer("Player 1"));
		players.add(new Dealer());
	}

	private void go() {
		
		// while keep playing
		//   check deck
		//   deal
		//   while no winners
		//     display
		//     for each player
		//       get player choice
		//       update player (hit, split, dd)
		//       check status
		//       update display
		
		deck.shuffle();
		for(int i=0; i<2; i++) {
			for(Player p : players) {
				p.getHand().addCard(deck.draw());
			}
		}
		
		display();
	}

	private void display() {
		for(Player p : players) {
			System.out.println(p.getName() + ":");
			System.out.println(p.getHand());
			System.out.println("value: " + p.getHand().value());
			System.out.println();
		}
	}
	
	
}
