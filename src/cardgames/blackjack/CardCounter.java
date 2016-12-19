package cardgames.blackjack;

import java.util.*;

import cardgames.core.Card;
import cardgames.core.Rank;

public class CardCounter {

	Map<Rank, Integer> rankTable;
	
	public CardCounter() {
		rankTable = new HashMap<>();
	}
	
	public void count(Card card) {
		if(rankTable.get(card.getRank()) == null) {
			rankTable.put(card.getRank(), 0);
		}
		
		rankTable.put(card.getRank(), rankTable.get(card.getRank())+1);
	}

	public void reset() {
		// TODO Auto-generated method stub
		rankTable = new HashMap<>();
	}

	public String toString() {
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		StringBuilder sb3 = new StringBuilder();
		
		Set<Rank> sortedKeys = new TreeSet<>(rankTable.keySet());
		for(Rank rank : sortedKeys) {
			sb1.append(String.format("%4s", rank.toString()));
			sb2.append(String.format("%4s", "---"));
			sb3.append(String.format("%4d", rankTable.get(rank)));
		}
		
		return sb1.append("\n").append(sb2).append("\n").append(sb3).toString();
	}
}
