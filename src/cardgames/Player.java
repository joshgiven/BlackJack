package cardgames;

public abstract class Player {
	static enum Status {
		INPLAY, BUST, STAND, TWENTYONE, BLACKJACK, DEALERREVEAL
	}
	
	static enum Play {
		HIT, STAND //, DOUBLE_HIT, DOUBLE_STAND, SPLIT, SURRENDER, INSURANCE
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
		hand = new Hand();
	}
	
	public Hand getHand() {
		return hand;
	}
}
