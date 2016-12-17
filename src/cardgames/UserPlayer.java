package cardgames;

import menu.*;

public class UserPlayer extends Player {
	
	static enum UserPlayMenuItem implements MenuItem {
		HIT  ("(H)it",   "H"),
		STAND("(S)tand", "S")
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

	InputPrompter menu;
	
	public UserPlayer(String name) {
		super(name);
		menu = new InputPrompter(UserPlayMenuItem.values());
		menu.setMenuItemSeperator(" ");
		menu.setMenuTopSeperator(   "\n----------------------------\n");
		menu.setMenuBottomSeperator("\n----------------------------\n");
		menu.setMenuPrompt(         "Enter play: ");
	}

	@Override
	public Play getPlay(Player dealer) {
		UserPlayMenuItem choice = (UserPlayMenuItem)menu.getUserMenuChoice();
		System.out.println();
		
		switch(choice) {
		case HIT:
			return Play.HIT;
		case STAND:
		default:
			return Play.STAND;
		}
	}

}
