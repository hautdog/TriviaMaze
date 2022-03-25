package model;

import java.io.*;
/**
 * GameState Class which controls the state of the game throughout the playthrough a player has.
 * @author Alec Dowty
 * @author Aaron Gitell
 * @author Joel Hemphill
 *
 */
public class GameState implements Serializable {
	
	/**
	 * Serial ID.
	 */
	private static final long serialVersionUID = 7912621071699318862L;
	
	/**
	 * The Maze Width
	 */
	private int myMazeWidth;
	
	/**
	 * The Maze Height.
	 */
	private int myMazeHeight;
	
	/**
	 * The Rooms containing doors which are available or not.
	 */
	private Room[][] myRooms;
	
	/**
	 * The Player's current row in myRooms.
	 */
	public int myRow;
	
	/*
	 * The Player's current column in myRooms.
	 */
	public int myCol; // current player y-coord
	
	/*
	 * The Questions being used, referencing the QuestionState Class.
	 */
	private QuestionState myQuestions; // some way to identify what question set is used
			// and some way to identify what questions have already been asked
	
	/*
	 * The enum which states the direction a player is going, if going one at all.
	 */
	public enum Direction {NORTH, SOUTH, WEST, EAST, NONE};

	/*
	 * The current direction, referencing the direction enum above.
	 */
	private Direction myDirection;
	
	/**
	 * Constructor for GameState, builds a new game based on the height and width.
	 * Makes sure to make a game board bigger, but only shows a specific XxX.
	 * @param theMazeWidth Width of the maze.
	 * @param theMazeHeight Height of the maze.
	 */
	public GameState(int theMazeWidth, int theMazeHeight) {
		myMazeWidth = theMazeWidth;
		myMazeHeight = theMazeHeight;
		myRow = 1;
		myCol = 1;
		myQuestions = new QuestionState();
		myDirection = Direction.NONE;
		myRooms = new Room[myMazeHeight + 2][myMazeWidth + 2];
		for (int i = 1; i <= myMazeHeight; i++) {
			for (int j = 1; j <= myMazeWidth; j++) {
				if (i == 1) {
					if (j == 1) {
						myRooms[i][j] = new Room(false, true, false, true);
					} else if (j == myMazeWidth) {
						myRooms[i][j] = new Room(false, false, true, true);
					} else {
						myRooms[i][j] = new Room(false, true, true, true);
					}
				} else if (i == myMazeHeight) {
					if (j == 1) {
						myRooms[i][j] = new Room(true, true, false, false);
					} else if (j == myMazeWidth) {
						myRooms[i][j] = new Room(true, false, true, false);
					} else {
						myRooms[i][j] = new Room(true, true, true, false);
					}
				} else if (j == 1 && i != 1 && i != myMazeHeight) {
					myRooms[i][j] = new Room(true, true, false, true);
				} else if (j == myMazeWidth && i != 1 && i != myMazeHeight) {
					myRooms[i][j] = new Room(true, false, true, true);
				} else {
					myRooms[i][j] = new Room();
				}
			}
		}
	}
	
	/**
	 * Getter for the Question State.
	 * @return Questions.
	 */
	public QuestionState getQuestionState() {
		return myQuestions;
	}
	
	/**
	 * Setter for the Question State.
	 * @param theQuestions Question state to set to.
	 */
	public void setQuestionState(QuestionState theQuestions) {
		myQuestions = theQuestions;
	}
	
	/**
	 * Getter for the direction.
	 * @return The Direction.
	 */
	public Direction getDirection() {
		return myDirection;
	}
	
	/**
	 * Setter for the direction.
	 * @param theDirection Direction to set it to.
	 */
	public void setDirection(Direction theDirection) {
		myDirection = theDirection;
	}
	
	/**
	 * Getter for maze width.
	 * @return Maze width.
	 */
	public int getMazeWidth() {
		return myMazeWidth;
	}
	
	/**
	 * Getter for maze height.
	 * @return Maze Height.
	 */
	public int getMazeHeight() {
		return myMazeHeight;
	}
	
	/**
	 * Getter for the X Coordinate.
	 * @return the X Coordinate.
	 */
	public int getMyRow() {
		return myRow;
	}
	
	/**
	 * Getter for the Y Coordinate.
	 * @return the Y Coordinate.
	 */
	public int getMyCol() {
		return myCol;
	}
	
	/**
	 * Setter for the X Coordinate.
	 * @param the X Coordinate to set to.
	 */
	public void setMyRow(int theRow) {
		myRow = theRow;
	}
	
	/**
	 * Setter for the Y Coordinate.
	 * @param the Y Coordinate to set to.
	 */
	public void setMyCol(int theCol) {
		myCol = theCol;
	}
	
	/**
	 * Returns myPaths.
	 * 
	 * @return boolean[][] of paths.
	 */
	public Room[][] getMyRooms() {
		return myRooms;
	}
	
	/**
	 * Sets given boolean array to myPaths.
	 * 
	 * @param thePaths array of paths.
	 */
	public void setMyRoomss(Room[][] theRooms) {
		myRooms = theRooms;
	}
	
	public boolean checkBoxedIn(Room theRoom) {
		return !theRoom.getMyEast() && !theRoom.getMyNorth() && !theRoom.getMySouth() && !theRoom.getMyWest();
	}
	/**
	 * Method which checks for victory by checking if the current location of the player
	 * is the same as the maze width and height (the winning tile).
	 * @return Boolean true or false for if they have won.
	 */
	public boolean checkVictory() {
		boolean victory = false;
		
		if (myRow == myMazeWidth - 1 && myCol == myMazeHeight - 1) {
			victory = true;
		}
		return victory;
	}
	
	/**
	 * Method for checking the defeat, checking if there is a path available to the winning tile or not.
	 * @return Boolean true or false for it they can win.
	 */
	public boolean checkDefeat() {
		boolean defeat = false;
		
		if (checkBoxedIn(myRooms[myRow][myCol])) {
			defeat = true;
		}
		return defeat;
	
	}
	
	public static void main(String[] args) {
		GameState g = new GameState(5, 5);
		Room[][] r = g.getMyRooms();
		r[1][5].setMyWest(false);
		r[1][5].setMySouth(false);
		r[1][5].setMyWest(true);
		System.out.print(g.checkBoxedIn(r[1][5]));
	}

}
