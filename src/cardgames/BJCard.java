package cardgames;

public class BJCard extends Card {
	
	public BJCard(Rank r, Suit s) {
		super(r, s);
	}

	@Override
	public int value() {
		switch(getRank()) {
		case ACE:
			return 11;
		case KING:
		case QUEEN:
		case JACK:
		case TEN:
			return 10;
		case NINE:
			return 9;
		case EIGHT:
			return 8;
		case SEVEN:
			return 7;
		case SIX:
			return 6;
		case FIVE:
			return 5;
		case FOUR:
			return 4;
		case THREE:
			return 3;
		case TWO:
		default:
			return 2;
		}
	}

}
