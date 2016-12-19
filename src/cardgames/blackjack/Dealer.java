package cardgames.blackjack;

public class Dealer extends Player {
	
	private boolean hideHoleCard;
	
	public Dealer() {
		super("Dealer");
		hideHoleCard();
	}

	@Override
	public Play getPlay(Player dealer) {
		if(getHand().value() < 17)
			return Play.HIT;
		else
			return Play.STAND;
	}
	
	public String displayHand() {
		String s = getHand().toString();
		
		if(hideHoleCard) {
			String[] arr = s.split(" ");
			arr[1] = "?";
			s = String.join(" ", arr);
		}
		
		return s;
	}

	public void hideHoleCard() {
		hideHoleCard = true;
	}

	public void showHoleCard() {
		hideHoleCard = false;
	}

	public boolean isShowingAce() {
		// TODO Auto-generated method stub
		return getHand().toString().matches("^A");
	}

}
