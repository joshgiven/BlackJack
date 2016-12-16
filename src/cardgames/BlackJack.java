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
		// TODO Auto-generated method stub
		// get player info
		// init players
	}

	private void go() {
		// TODO Auto-generated method stub
		
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
		
	}
	
	
}
