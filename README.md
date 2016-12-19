# BlackJack : A Functional Command Line Blackjack Game

![Jack](https://upload.wikimedia.org/wikipedia/commons/5/5e/Poker-sm-214-Js.png)

'To turn in a project, you must push it to GitHub. You must include a README.md that describes how to run your program. Include an overview of the class structure you chose to use.'

### OVERVIEW

### HOW TO run
'java cardgames.blackjac.BlackJack'

### CLASS STRUCTURE
- **cardgames.core** package:
  - *Rank* : enumeration of card ranks
  - *Suit* : enumeration of card suits
  - *Card* : abstraction of a playing card with Rank and Suit
  - *Deck* IS-A List of Cards : A collection of playing cards
    - initialized with all 52 playing cards
  - *Hand* IS-A List of Cards : A small collection of cards

- **cardgames.core** package:
  - *BlackJack* : Game logic and driver
  - *BJCard* IS-A Card : Blackjack-specific card
  - *BJHand* IS-A Hand : Blackjack-specific hand
  - *Player* : abstraction of a Blackjack player
  - *Dealer* IS-A Player : Blackjack player with dealer-specific behavior
  - *UserPlayer* IS-A Player : player that gets user input to make game decisions
  - *DummyPlayer* IS-A Player : player that always HITs
