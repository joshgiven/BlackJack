package cardgames.blackjack;

import menu.*;

public class UserPlayer extends SplitablePlayer {
	
	final static UserPlayMenuItem[] SIMPLE_MENU = { 
			UserPlayMenuItem.HIT, 
			UserPlayMenuItem.STAND
			};
	
	final static UserPlayMenuItem[] SIMPLE_SPLIT_MENU = { 
			UserPlayMenuItem.HIT, 
			UserPlayMenuItem.STAND,
			UserPlayMenuItem.SPLIT 
	};
	
	final static UserPlayMenuItem[] FULL_MENU = { 
			UserPlayMenuItem.HIT, 
			UserPlayMenuItem.STAND, 
			UserPlayMenuItem.DOUBLE, 
			UserPlayMenuItem.SPLIT 
	};

	final static UserPlayMenuItem[] NO_SPLIT_MENU = { 
			UserPlayMenuItem.HIT, 
			UserPlayMenuItem.STAND, 
			UserPlayMenuItem.DOUBLE
	};
	
	InputPrompter menu;
	
	public UserPlayer(String name, int purse) {
		super(name, purse);
		// menu = new InputPrompter(UserPlayMenuItem.values());
		menu = new InputPrompter(SIMPLE_MENU);
		menu.setMenuItemSeperator("   ");
		menu.setMenuTopSeperator(     "----------------------------------\n   ");
		menu.setMenuBottomSeperator("\n----------------------------------\n");
		menu.setMenuPrompt(           " "+ name +"'s play: ");
	}


	@Override
	protected SplitablePlayer newSplitablePlayer(String name, int purse) {
		return new UserPlayer(name, purse);
	}

	@Override
	public int placeWager() {
		return menu.getUserInt(
				String.format("\nPlace your bet ($1-$%d) (or 0 to quit): ", getPurse()));
	}

	@Override
	public Play getPlay(Player dealer) {
		
		if(hasDoubles() && getPurse() > getCurrentWager())
		 	menu.setMenuItems(SIMPLE_SPLIT_MENU);
		 else
			menu.setMenuItems(SIMPLE_MENU);
		
		menu.setMenuPrompt(           " "+ getName() +"'s play: ");
		UserPlayMenuItem choice = (UserPlayMenuItem)menu.getUserMenuChoice();
		System.out.println();
		
		switch(choice) {
		case HIT:
			return Play.HIT;
		case DOUBLE:
			return Play.DOUBLE;
		case SPLIT:
			return Play.SPLIT;
		case STAND:
		default:
			return Play.STAND;
		}
	}
	
	static enum UserPlayMenuItem implements MenuItem {
		HIT    ("H\u0332it",    "H"),
		STAND  ("S\u0332tand",  "S"),
		DOUBLE ("D\u0332ouble", "D"),
		SPLIT  ("sP\u0332lit",  "P"),
		;

		private UserPlayMenuItem(String label, String keyOption) {
			this.label = label;
			this.keyOption = keyOption;
		}
		
		private String label;
		private String keyOption;
		public String label() {	return label; }
		public String keyOption() { return keyOption; }
		public MenuItem[] enumValues() { return values(); }
	}
}
