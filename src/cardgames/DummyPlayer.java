package cardgames;

public class DummyPlayer extends Player {

	public DummyPlayer(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Play getPlay(Player dealer) {
		// TODO Auto-generated method stub
		return Play.HIT;
	}

}
