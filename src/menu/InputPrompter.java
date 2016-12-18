package menu;

import java.util.*;
import java.util.regex.Pattern;

public class InputPrompter {

	Scanner input = new Scanner(System.in);
	
	Actionable stateDisplayAction;
	Map<MenuItem, List<Actionable>> menuActions;
	private MenuItem[] menuItems;
	private boolean keepGoing = true;
	
	private String menuItemSeperator   = "\n";
	private String menuTopSeperator    = "\n";
	private String menuBottomSeperator = "\n";
	private String menuPrompt          = "> ";
	private String menuErrorPrompt     = "Bad value. Try again. ";
	
	
	public InputPrompter(MenuItem[] menuItems) {
		menuActions = new LinkedHashMap<>();
		this.menuItems = menuItems;
	}
	
	public void println(String outputString) {
		System.out.println(outputString);
	}
	
	public void addStateDisplayAction(Actionable stateDisplayAction) {
		this.stateDisplayAction = stateDisplayAction;
	}
	
	public void addMenuAction(MenuItem menuItem, Actionable menuAction) {
		if(!menuActions.containsKey(menuItem)) {
			menuActions.put(menuItem, new ArrayList<>());
		}
		
		menuActions.get(menuItem).add(menuAction);
	}

	public void stop() {
		keepGoing = false;
	}
	
	public void go() {
		keepGoing = true;
		
		do {
			displayState();
			displayMenu();
			MenuItem actionName = getAction();
			
			List<Actionable> toDoList = menuActions.get(actionName);
			for(Actionable toDo : toDoList) {
				toDo.doit();	
			}

		} while(keepGoing);
	}
	
	public MenuItem getUserMenuChoice() {
		displayMenu();
		return getAction();
	}
	
	
	private void displayState() {
		if(stateDisplayAction != null)
			stateDisplayAction.doit();
	}

	private MenuItem getAction() {
		String choiceString = null;
		MenuItem menuAction = null;
		MenuItem derp = menuItems[0]; // ugh!
		
		MenuItem[] validActions = menuActions.keySet().toArray(new MenuItem[0]);
		if(validActions == null || validActions.length == 0)
			validActions = menuItems;
		
		List<MenuItem> validActionsList = Arrays.asList(validActions);; 
		
		do {
			System.out.print(menuPrompt);
			choiceString = input.nextLine().toUpperCase();
			menuAction = derp.keyOptionChoice(choiceString);
			if(validActionsList.contains(menuAction))
				break;
			System.out.println(menuErrorPrompt);
		} while(true);
		
		return menuAction;
	}

	private void displayMenu() {
		MenuItem[] choices = menuActions.keySet().toArray(new MenuItem[0]);
		if(choices == null || choices.length == 0)
			choices = menuItems;

		System.out.print(menuTopSeperator);
		for (MenuItem choice : choices) {
			System.out.print(choice.label() + menuItemSeperator);
		}
		System.out.print(menuBottomSeperator);
	}

	public boolean getUserYN(String prompt) {
		String resp = null;
		do {
			resp = getUserString(prompt).toUpperCase();
			if(resp.equals("Y"))
				return true;
			else if(resp.equals("N"))
				return false;
		} while(true);
	}
	
	public String getUserString(String prompt){
		Pattern p = input.delimiter();
		input.useDelimiter("\n");
		
		String name = null;
		while(name == null || name.equals("")) {
			System.out.print(prompt);
			//name = input.next();
			name = input.nextLine();
		}
		
		input.useDelimiter(p);
		return name;
	}
	
	public int getUserInt(String prompt) {
		do {
			System.out.print(prompt);
			if(input.hasNextInt()) {
				return input.nextInt();
			}
			else {
				input.next(); // eat bad data
			}
		} while(true);
	}
	
	public double getUserDouble(String prompt) {
		do {
			System.out.print(prompt);
			if(input.hasNextDouble()) {
				return input.nextDouble();
			}
			else {
				input.next(); // eat bad data
			}
		} while(true);
	}

	public MenuItem[] getMenuItems() {
		return menuItems;
	}

	public void setMenuItems(MenuItem[] menuItems) {
		this.menuItems = menuItems;
	}

	public String getMenuItemSeperator() {
		return menuItemSeperator;
	}

	public void setMenuItemSeperator(String menuItemSeperator) {
		this.menuItemSeperator = menuItemSeperator;
	}

	public String getMenuTopSeperator() {
		return menuTopSeperator;
	}

	public void setMenuTopSeperator(String menuTopSeperator) {
		this.menuTopSeperator = menuTopSeperator;
	}

	public String getMenuBottomSeperator() {
		return menuBottomSeperator;
	}

	public void setMenuBottomSeperator(String menuBottomSeperator) {
		this.menuBottomSeperator = menuBottomSeperator;
	}

	public String getMenuPrompt() {
		return menuPrompt;
	}

	public void setMenuPrompt(String menuPrompt) {
		this.menuPrompt = menuPrompt;
	}

	public String getMenuErrorPrompt() {
		return menuErrorPrompt;
	}

	public void setMenuErrorPrompt(String menuErrorPrompt) {
		this.menuErrorPrompt = menuErrorPrompt;
	}
}
