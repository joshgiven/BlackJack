package cardgames;

import java.util.*;

import cardgames.blackjack.BJCard;

public class Deck extends ArrayList<Card>{

	public Deck() {
		super(52);
		initialize();
	}
	
	private void initialize() {
		for (Suit s : Suit.values()) {
			for (Rank r : Rank.values()) {
				add(new BJCard(r, s));
			}
		}
	}
	
	public int cardsLeft() {
		return size();
	}
	
	public void shuffle() {
		Collections.shuffle(this);
	}
	
	public Card draw() {
		return remove(0);
	}
	
	public void cut(int index) {
		index %= size();
		Collections.rotate(this, index);
	}

	private static final long serialVersionUID = 1L;
}