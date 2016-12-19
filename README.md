# BlackJack : A Functional Command Line Blackjack Game

![Jack](https://upload.wikimedia.org/wikipedia/commons/5/5e/Poker-sm-214-Js.png)![Ace](https://upload.wikimedia.org/wikipedia/commons/b/be/Poker-sm-231-Ad.png)
### OVERVIEW
Plays a game of Blackjack to SD homework specs
  - added wagers
  - added split hands
  - added double-down wagers
  - enabled multi-user
  - added prototype card counter

### HOW TO RUN
From the command line:
`java cardgames.blackjack.BlackJack`

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
    - HAS-A Deck
    - HAS-A List of Players (one instance of Dealer, one of UserPlayer)
  - *BJCard* IS-A Card : Blackjack-specific behavior'd Card
  - *BJHand* IS-A Hand : Blackjack-specific behavior'd Hand
  - *Player* : abstraction of a Blackjack player with default bevahior
    - HAS-A Hand
  - *Dealer* IS-A Player : Blackjack Player with dealer-specific behavior
  - *SplitablePlayer* IS-A Player : Abstract class for player that may split hands
  - *UserPlayer* IS-A SplitablePlayer : Player that gets user input to make game decisions
  - *DummyPlayer* IS-A SplitablePlayer : Player that always HITs

- **menu** package:
  - *InputPrompter* : User input and menu-ing class
  - *Actionable* : Functional Interface for use with InputPrompter
  - *MenuItem* : Interface for use with InputPrompter menu-ing methods
  - *InputPrompterDriver* : Test driver for InputPrompter
