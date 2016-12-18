package cardgames;

import java.util.*;

public class Hand {

	private List<Card> cards;

	public Hand() {
		cards = new ArrayList<Card>();
	}
	
	public int value() {
		int hv = hardValue();
		int sv = softValue();
		return (hv <= 21) ? hv : sv;
	}
	
	public int hardValue() {
		int sum = 0;
		for(Card card : cards) {
			sum += card.value();
		}
		return sum;
	}
	
	public int softValue() {
		int sum = 0;
		boolean hasAce = false;
		
		for(Card card : cards) {
			sum += card.value();
			
			if(card.getRank() == Rank.ACE)
				hasAce = true;
		}
		
		return (hasAce) ? sum-10 : sum;
	}
	
	public void addCard(Card card) {
		if(card == null)
			return;

		cards.add(card);
	}
	
	public void removeCard(Card card) {
		cards.remove(card);
	}
	
	public boolean contains(Card card) {
		return cards.contains(card);
	}

	public int getNumCards() {
		return cards.size();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Card card : cards)
			sb.append(card).append(" ");
		
		return sb.toString();
	}

	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;

//	public static void main(String[] args) {
//		Hand hand = new Hand();
//		hand.addCard(new BJCard(Rank.JACK, Suit.SPADES));
//		System.out.println(hand.contains(new BJCard(Rank.JACK, Suit.SPADES)));
//	}
}
