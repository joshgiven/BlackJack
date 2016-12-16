package cardgames;

import java.util.ArrayList;
import java.util.List;

public class Deck {
    public static void main(String[] args) {
        List<Card> deck = new ArrayList<>(52);

        for (Suit s : Suit.values()) {
            for (Rank r : Rank.values()) {
                deck.add(new Card(r, s));
            }
        }

        for (Card card : deck) {
            System.out.println(card);
        }
    }

}