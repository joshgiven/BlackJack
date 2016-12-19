package cardgames.blackjack;

import cardgames.core.Card;

abstract public class SplitablePlayer extends Player {
	SplitablePlayer originalPlayer;
	
	public SplitablePlayer(String name, int purse) {
		super(name, purse);
		originalPlayer = this;
	}

	abstract protected SplitablePlayer newSplitablePlayer(String name, int purse);
	
	public SplitablePlayer getOriginalPlayer() {
		return originalPlayer;
	}
	
	protected void setOriginalPlayer(SplitablePlayer ogp) {
		originalPlayer = ogp;
	}
	
	public Player splitPlayer() {
		String tmpName = getName() + "(2)";
		
		setName(getName() + "(1)");
		settleLoss(getCurrentWager());
		
		SplitablePlayer tmpPlayer = newSplitablePlayer(tmpName, getCurrentWager());
		tmpPlayer.setOriginalPlayer(this);
		tmpPlayer.setCurrentWager(getCurrentWager());

		Card card = getHand().remove(1);
		tmpPlayer.getHand().addCard(card);
		
		return tmpPlayer;
	}

	public void unsplitPlayer(Player tmpPlayer) {
		if(tmpPlayer == null || 
		   tmpPlayer == this ||
		   ! (tmpPlayer instanceof UserPlayer))
			return;
		
		collectWinnings(tmpPlayer.getPurse());
		setName(getName().replaceAll("\\(1\\)$", ""));
	}

}
