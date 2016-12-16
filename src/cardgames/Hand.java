package cardgames;

import java.util.*;

public class Hand {
	private static final long serialVersionUID = 1L;

	private List<Card> cards;

	public Hand() {
		cards = new ArrayList<Card>();
	}
	
	public void addCard(Card card) {
		if(card == null)
			return;
		
		int index = Collections.binarySearch(cards, card);
		index = (index < 0) ? Math.abs(index+1) : index;
		cards.add(index, card);
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
}
