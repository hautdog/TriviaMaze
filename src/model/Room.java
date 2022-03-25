package model;

public class Room {
	
	private boolean doorNorth;
	
	private boolean doorEast;
	
	private boolean doorWest;
	
	private boolean doorSouth;
	
	public Room() {
		doorNorth = true;
		doorEast = true;
		doorWest = true;
		doorSouth = true;
	}
	
	public Room(boolean theNorth, boolean theEast, boolean theWest, boolean theSouth) {
		doorNorth = theNorth;
		doorEast = theEast;
		doorWest = theWest;
		doorSouth = theSouth;
	}
	
	public boolean getMyNorth() {
		return doorNorth;
	}
	
	public boolean getMyEast() {
		return doorEast;
	}
	
	public boolean getMyWest() {
		return doorWest;
	}
	
	public boolean getMySouth() {
		return doorSouth;
	}
	
	public void setMyNorth(boolean theNorth) {
		doorNorth = theNorth;
	}
	
	public void setMyEast(boolean theEast) {
		doorEast = theEast;
	}
	
	public void setMyWest(boolean theWest) {
		doorWest = theWest;
	}
	
	public void setMySouth(boolean theSouth) {
		doorSouth = theSouth;
	}
	
	public String toString() {
		return "North is " + doorNorth + ", East is " + doorEast + ", West is " +
				doorWest + ", South is " + doorSouth + ".";
	}
}
