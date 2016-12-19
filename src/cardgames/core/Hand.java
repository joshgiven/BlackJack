package cardgames.core;

import java.util.*;

abstract public class Hand extends ArrayList<Card> {

	public Hand() {
		super();
	}
	
	abstract public int value();
	
	public void addCard(Card card) {
		if(card == null)
			return;

		add(card);
	}
	
	public void removeCard(Card card) {
		remove(card);
	}
	
	public int getNumCards() {
		return size();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(Card card : this)
			sb.append(card).append(" ");
		
		return sb.toString();
	}

	private static final long serialVersionUID = 1L;
}
