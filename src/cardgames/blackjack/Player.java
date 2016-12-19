package cardgames.blackjack;

import cardgames.core.Hand;

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
	
	private int purse;
	private int currentWager;
	
	public Player(String name, int purse) {
		this.name = name;
		newHand();
		status = Status.INPLAY;
		
		this.purse = purse;
		this.currentWager = 0;
	}

	abstract public Play getPlay(Player dealer);

	abstract public int placeWager();

	public Status getStatus() {
		return status;
	}
	
	public void setStatus(Status status) {
		this.status = status;
	}
	
	public String getName() {
		return name;
	}

	protected void setName(String name) {
		this.name = name;
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
		if(hand.getNumCards() == 2 &&
		   hand.get(0).getRank() == hand.get(1).getRank()) {
				return true;
		}
		return false;
	}

	public boolean hasBlackJack() {
		if(hasTwentyOne() && hand.getNumCards() == 2)
			return true;
		else
			return false;
	}
	
	public void settleLoss(int debt) {
		purse -= debt;
	}

	public void collectWinnings(int cashMoney) {
		purse += cashMoney;
	}
	
	public int getCurrentWager() {
		return currentWager;
	}

	public void setCurrentWager(int currentWager) {
		this.currentWager = currentWager;
	}

	public int getPurse() {
		return purse;
	}

	protected void setPurse(int purse) {
		this.purse = purse;
	}

}
