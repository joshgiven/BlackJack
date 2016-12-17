package cardgames;

import java.util.*;

public class Hand {

	private List<Card> cards;

	public Hand() {
		cards = new ArrayList<Card>();
	}
	
	public int value() {
		int sum = 0;
		for(Card card : cards) {
			sum += card.value();
		}
		return sum;
	}
	
	public void addCard(Card card) {
		if(card == null)
			return;

		cards.add(card);
	}
	
	public void removeCard(Card card) {
		cards.remove(card);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Card card : cards)
			sb.append(card).append(" ");
		
		return sb.toString();
	}

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
}
