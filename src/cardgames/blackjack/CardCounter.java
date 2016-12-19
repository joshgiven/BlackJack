package cardgames.blackjack;

import java.util.*;

import cardgames.core.Card;
import cardgames.core.Rank;

public class CardCounter {

	Map<Rank, Integer> rankTable;
	
	public CardCounter() {
		reset();
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
		
		for(Rank rank : Rank.values()) {
			rankTable.put(rank, 0);
		}
	}

	public String toString() {
		StringBuilder sb1 = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		StringBuilder sb3 = new StringBuilder();
		
		sb1.append("  |");
		sb2.append("  |");
		sb3.append("  |");
		
		Set<Rank> sortedKeys = new TreeSet<>(rankTable.keySet());
		for(Rank rank : sortedKeys) {
			sb1.append(String.format("%3s", rank.toString())).append(" |");
			sb2.append(String.format("%4s", "----")).append("+");
			sb3.append(String.format("%3d", rankTable.get(rank))).append(" |");
		}

		return sb1.append("\n").append(sb2).append("\n").append(sb3).append("\n").append(sb2).toString();
	}
}
