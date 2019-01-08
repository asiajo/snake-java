package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

/**
 * Class created for keeping all the important variables in one place
 * 
 * @author joanna czernicka
 *
 */
public class SharedVariables {

	// java - get screen size using the Toolkit class
	// the aim: game automatically adjusts its size to the screen resolution
	static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	// ratio based on on background graphic
	private static final double WIDTH_HEIGHT_RATIO = 0.75;
	// game window height will be in proportion of SCALE_FACTOR to user displays
	// height
	private static final double SCALE_FACTOR = 0.9;
	// 48 is a factor setting block size based on background graphic
	public static final int BLOCK_SIZE = (int) (screenSize.getHeight() * SCALE_FACTOR * WIDTH_HEIGHT_RATIO / 48);
	// the size of game elements. Should be an int. The bigger it is the bigger are
	// the elements.
	public static double gridScale = 2;
	public static int gridSize = (int) (BLOCK_SIZE * gridScale);
	// the size of game field
	public static final int SQUARE = 40 * BLOCK_SIZE;

	// 48 and 64 based on background graphic
	public static final int GAME_WIDTH = BLOCK_SIZE * 48;
	public static final int GAME_HEIGHT = BLOCK_SIZE * 64;

	public static final Color TEXT_COLOR = new Color(219, 106, 15);// green (120, 220, 26);

	public static final int LEVEL_EASY = 150;
	public static final int LEVEL_MEDIUM = 100;
	public static final int LEVEL_HARD = 60;

	// used for multiplying points depending of the level.
	public static int LEVEL_MULT = 0;

	// values for the movement
	public static final int[] RIGHT = { 1, 0 };
	public static final int[] LEFT = { -1, 0 };
	public static final int[] UP = { 0, -1 };
	public static final int[] DOWN = { 0, 1 };
	public static final int[] PAUSE = { 0, 0 };
	public static int x = 1;
	public static int y = 0;

}
