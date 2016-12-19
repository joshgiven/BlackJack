package cardgames.blackjack;

import cardgames.Card;
import cardgames.Hand;
import cardgames.Rank;

public class BJHand extends Hand {

	public int value() {
		int hv = hardValue();
		int sv = softValue();
		return (hv <= 21) ? hv : sv;
	}
	
	public int hardValue() {
		int sum = 0;
		for(Card card : this) {
			sum += card.value();
		}
		return sum;
	}
	
	public int softValue() {
		int sum = 0;
		boolean hasAce = false;
		
		for(Card card : this) {
			sum += card.value();
			
			if(card.getRank() == Rank.ACE)
				hasAce = true;
		}
		
		return (hasAce) ? sum-10 : sum;
	}
	
	private static final long serialVersionUID = 1L;
}
