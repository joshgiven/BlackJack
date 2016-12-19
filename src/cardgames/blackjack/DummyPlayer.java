package cardgames.blackjack;

public class DummyPlayer extends Player {

	public DummyPlayer(String name, int purse) {
		super(name, purse);
	}

	@Override
	public Play getPlay(Player dealer) {
		return Play.HIT;
	}

	@Override
	public int placeWager() {
		return 5;
	}

}
