package cardgames;

public class Dealer extends Player {

	public Dealer() {
		super("Dealer");
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
		
		if(getStatus() != Status.DEALERREVEAL) {
			String[] arr = s.split(" ");
			arr[1] = "?";
			s = String.join(" ", arr);
		}
		
		return s;
	}

}
