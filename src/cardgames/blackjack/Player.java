package cardgames.blackjack;

import cardgames.Hand;

public abstract class Player {
	static enum Status {
		INPLAY, BUST, STAND, TWENTYONE, BLACKJACK, DEALERREVEAL
	}
	
	static enum Play {
		HIT, STAND ,DOUBLE, SPLIT //, INSURANCE, SURRENDER
	}
	
	private String name;
	private Hand hand;
	private Status status;
	
	public Player(String name) {
		this.name = name;
		newHand();
		status = Status.INPLAY;
	}

	public abstract Play getPlay(Player dealer);
		
	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public String getName() {
		return name;
	}

	public String displayHand() {
		return hand.toString();
	}
	
	public void newHand() {
		hand = new BJHand();
	}
	
	public Hand getHand() {
		return hand;
	}

	public boolean hasTwentyOne() {
		return getHand().value() == 21;
	}
	
	public boolean hasDoubles() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean hasBlackJack() {
		if(hasTwentyOne() && hand.getNumCards() == 2)
			return true;
		else
			return false;
	}
}
