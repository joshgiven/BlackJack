package cardgames.blackjack;

import cardgames.core.Card;

abstract public class SplitablePlayer extends Player {
	SplitablePlayer originalPlayer;
	private boolean hasSplit;
	
	public SplitablePlayer(String name, int purse) {
		super(name, purse);
		originalPlayer = this;
		hasSplit = false;
	}

	abstract protected SplitablePlayer newSplitablePlayer(String name, int purse);
	
	public SplitablePlayer getOriginalPlayer() {
		return originalPlayer;
	}
	
	protected void setOriginalPlayer(SplitablePlayer ogp) {
		originalPlayer = ogp;
	}
	
	public void newHand() {
		super.newHand();
		setHasSplit(false);
	}
	

	public Player splitPlayer() {
		String tmpName = getName() + "(2)";
		
		hasSplit = true;
		setName(getName() + "(1)");
		settleLoss(getCurrentWager());
		
		SplitablePlayer tmpPlayer = newSplitablePlayer(tmpName, getCurrentWager());
		tmpPlayer.setOriginalPlayer(this);
		tmpPlayer.setCurrentWager(getCurrentWager());

		Card card = getHand().remove(1);
		tmpPlayer.getHand().addCard(card);
		tmpPlayer.setHasSplit(true);

		return tmpPlayer;
	}

	public void unsplitPlayer(Player tmpPlayer) {
		if(tmpPlayer == null || 
		   tmpPlayer == this ||
		   ! (tmpPlayer instanceof UserPlayer))
			return;
		
		collectWinnings(tmpPlayer.getPurse());
		setName(getName().replaceAll("\\(1\\)$", ""));
		hasSplit = false;
	}

	public boolean hasSplit() {
		return hasSplit;
	}

	public void setHasSplit(boolean hasSplit) {
		this.hasSplit = hasSplit;
	}

}
