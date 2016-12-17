package cardgames;

public enum Suit {
    HEARTS("\u2661"), 
    SPADES("\u2660"), 
    CLUBS("\u2663"),
    DIAMONDS("\u2662");
    
    private Suit(String unicode) {
    		this.unicode = unicode;
    }
    private String unicode;
	public String unicode() { return unicode; }
	public String toString() { return unicode; }
}
