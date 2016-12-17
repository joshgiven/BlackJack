package menu;

public class InputPrompterDriver {
	public static void main(String[] args) {
		InputPrompter menu = new InputPrompter(MyMenuItem.values());
		setup(menu);
		menu.go();
	}
	
	private static void setup(InputPrompter menu) {
		menu.addMenuAction(MyMenuItem.HIT,    () -> menu.println("hit me!") );
		menu.addMenuAction(MyMenuItem.STAY,   () -> menu.println("whoa!") );
		menu.addMenuAction(MyMenuItem.QUIT,   () -> menu.stop() );
		menu.setMenuItemSeperator(" ");
	}

}

enum MyMenuItem implements MenuItem {
	HIT   ("(H)it",         "H"),
	STAY  ("(S)tay",        "S"),
	DOUBLE("(D)ouble Down", "D"),
	SPLIT ("s(P)lit",       "P"),
	QUIT  ("(Q)uit",        "Q"),
	;
	
	private String label, keyOption;
	
	private MyMenuItem(String label, String keyOption) {
		this.label = label;
		this.keyOption = keyOption;
	}
	
	public String label() { return label; }
	
	public String keyOption() { return keyOption; }

	public MenuItem[] enumValues() {
		return values();
	}

}

