package cardgames.blackjack;

public class DummyPlayer extends Player {

	public DummyPlayer(String name) {
		super(name);
	}

	@Override
	public Play getPlay(Player dealer) {
		return Play.HIT;
	}

}
