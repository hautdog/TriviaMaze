package controller;

import java.util.function.Function;

import gui.Map;
import gui.OptionBar;
import gui.QuestionBox;
import model.GameState;
import model.SQLDatabase;

/**
 * Class Game which controls the major parts of the game.
 * @author Alec Dowty
 * @author Aaron Gitell
 * @author Joel HempHill
 *
 */
public class Game {
	
	/*
	 * GameState variable.
	 */
	private GameState gamestate;
	
	/*
	 * Map variable.
	 */
	private Map map;
	/*
	 * Question Controller variable.
	 */
	private QuestionController myQuestioncontroller;
	/*
	 * Option bar variable.
	 */
	private OptionBar optionbar;
	
	/*
	 * Exit routine variable.
	 */
	public Function<Void,Void> myExitRoutine;
	
	/**
	 * Constructor for class Game.
	 */
	public Game() {
		gamestate = new GameState(5, 5);
		map = new Map(this);
	}
	
	/**
	 * Start function, begins the game.
	 * @param filename file created when the game starts/loads.
	 * @param theExitRoutine How the game exits.
	 */
	public void start(String filename, Function<Void,Void> theExitRoutine) {

		gamestate = new GameState(5,5);
		
		// create map
		map = new Map(this);
		
		myExitRoutine = theExitRoutine;
		
		// create database
		SQLDatabase DB = new SQLDatabase();
		DB.setUp();
		
		// create questionbox
		myQuestioncontroller = new QuestionController(DB.getMyQuestionList(), this);
		
		// create optionbar
		optionbar = new OptionBar(this);
		
		if (gamestate.getDirection() == GameState.Direction.NONE) {
			handleMovementSelection();
		} else {
			handleQuestionSelection();
		}
		
	}
	/**
	 * Method handleMovementSelection which handles the movement selected by the player, calling into the map.
	 */
	public void handleMovementSelection() {
		map.getPlayerMovement();
	}
	
	/**
	 * Method handleMovementResolution which handles what happens based on if the question is right or not.
	 */
	public void handleMovementResolution() {
		handleQuestionSelection();
	}

	/**
	 * Method handleQuestionSelection which deals with what the current or next question is going to be.
	 */
	public void handleQuestionSelection() {
		gamestate.setQuestionState(myQuestioncontroller.askNextQuestion(gamestate.getQuestionState(), 
								  (Function<Game,Void>)(Game game)->{game.handleQuestionResolution(); return null;}, this));
		
	}
	
	/**
	 * Getter for the QuestionBox.
	 * @return the Question box.
	 */
	public QuestionBox getQuestionBox() {
		return myQuestioncontroller.myQuestionBox;
	}
	
	/**
	 * Getter for the current Game State.
	 * @return the Game State.
	 */
	public GameState getGameState() {
		return gamestate;
	}
	
	/**
	 * Getter for the current Map.
	 * @return The current Map.
	 */
	public Map getMap() {
		return map;
	}
	
	/**
	 * Getter for the OptionBar.
	 * @return The OptionBar.
	 */
	public OptionBar getOptionBar() {
		return optionbar;
	}
	
	public void handleQuestionResolution() {
		if (gamestate.getQuestionState().isAnsweredCorrectly()) {
			
			if (gamestate.getDirection() == GameState.Direction.EAST) {
				gamestate.setMyCol(gamestate.getMyCol() + 1);
			} else if (gamestate.getDirection() == GameState.Direction.SOUTH) {
				gamestate.setMyRow(gamestate.getMyRow() + 1);
			} else if (gamestate.getDirection() == GameState.Direction.WEST) {
				gamestate.setMyCol(gamestate.getMyCol() - 1);
			} else if (gamestate.getDirection() == GameState.Direction.NORTH) {
				gamestate.setMyRow(gamestate.getMyRow() - 1);
			}
			
		} else {
			if (gamestate.getDirection() == GameState.Direction.EAST) {
				gamestate.getMyRooms()[gamestate.getMyRow()][gamestate.getMyCol()].setMyEast(false);
			} else if (gamestate.getDirection() == GameState.Direction.SOUTH) {
				gamestate.getMyRooms()[gamestate.getMyRow()][gamestate.getMyCol()].setMySouth(false);
			} else if (gamestate.getDirection() == GameState.Direction.WEST) {
				gamestate.getMyRooms()[gamestate.getMyRow()][gamestate.getMyCol()].setMyWest(false);
			} else if (gamestate.getDirection() == GameState.Direction.NORTH) {
				gamestate.getMyRooms()[gamestate.getMyRow()][gamestate.getMyCol()].setMyNorth(false);
			}
		}
		
		map.updateVisuals();
		map.getPlayerMovement();//handleMovementSelection();
	}	
}