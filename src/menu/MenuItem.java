package menu;

public interface MenuItem {
	String label();
	String keyOption();
	MenuItem[] enumValues();
	
	default MenuItem keyOptionChoice(String key) {
		MenuItem mc = null;
		for(MenuItem item : enumValues()) {
			if(item.keyOption().equals(key)) {
				mc = item;
				break;
			}
		}
		return mc;
	}
}

