package cardgames.blackjack;

public class DummyPlayer extends SplitablePlayer {

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

	@Override
	protected SplitablePlayer newSplitablePlayer(String name, int purse) {
		return new DummyPlayer(name, purse);
	}
}
