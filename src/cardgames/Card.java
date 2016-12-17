package cardgames;

public abstract class Card implements Comparable<Card> {
	private Rank rank;
	private Suit suit;

	public Card(Rank r, Suit s) {
		rank = r;
		suit = s;
	}

	public Rank getRank() {
		return rank;
	}

	public Suit getSuit() {
		return suit;
	}

	public abstract int value();

	@Override
	public int compareTo(Card other) {
		int retVal = this.rank.compareTo(other.rank);
		return (retVal == 0) ? this.suit.compareTo(other.suit) : retVal;
	}

	@Override
	public String toString() {
		return suit.toString() + rank.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((rank == null) ? 0 : rank.hashCode());
		result = prime * result + ((suit == null) ? 0 : suit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Card other = (Card) obj;
		if (rank != other.rank)
			return false;
		if (suit != other.suit)
			return false;
		return true;
	}
}
