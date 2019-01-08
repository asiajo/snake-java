package entitiesAndControl;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

import gui.Dialog;
import main.SharedVariables;

/**
 * The most important class of the game - controlling all the elements on the
 * game field
 * 
 * @author joanna Czernicka
 */
public class GameField extends JPanel implements Runnable {

	private static final long serialVersionUID = 1L; // default
	private int blockSize;
	private int gridSize;
	private int square;
	private int surrounding;

	private Thread thread;
	private static int speed;
	private static int speed_copy;

	private Icon lostIcon;

	private Food food;
	private Snake snake;
	private Random r;
	private int xCoorFood;
	private int yCoorFood;
	private boolean isEaten = true;

	// -------------------------------------------------------
	/**
	 * Class constructor
	 */
	public GameField() {

		setFocusable(true);
		setOpaque(false);

		blockSize = SharedVariables.BLOCK_SIZE;
		gridSize = SharedVariables.gridSize;
		square = 40 * blockSize;
		surrounding = gridSize * 2;
		snake = Snake.getInstance();

		r = new Random();

		start();
	}

	// --------------METHODS THREADS-------------------------------------

	/**
	 * running new Thread
	 */
	private void start() {
		getSnake().setRunning(true);
		thread = new Thread(this, "Game loop");
		getSnake().restart();
		thread.start();
	}

	@Override
	public void run() {
		while (getSnake().isRunning()) {
			getSnake().controlSnake();
			controlObjects();
			repaint();
		}
	}

	/**
	 * Method called after the death of the snake displaying a window with "what to
	 * do next" question
	 */
	public void gameEnd() {
		UIManager.put("OptionPane.background", Color.black);
		UIManager.put("Panel.background", Color.black);

		String[] options1 = { "Quit", "Play again", "Choose Level" };
		lostIcon = new ImageIcon(getClass().getResource("/resources/msc/lost.png"));
		int choice = JOptionPane.showOptionDialog(null, null, "Game over!", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE, lostIcon, options1, null);

		// interpret the user's choice
		if (choice == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
		if (choice == JOptionPane.NO_OPTION) {
			getSnake().snake.clear();
			getSnake().restart();
		}
		if (choice == JOptionPane.CANCEL_OPTION) {
			getSnake().snake.clear();
			getSnake().restart();
			Dialog method = new Dialog();
			method.chooseLevel();
		}
	}

	// ------------CUSTOM METHODS--------------------------------------

	/**
	 * Main method controlling snake and food position, snake's movements and game
	 * speed
	 */
	private void controlObjects() {

		if (!getSnake().isRunning()) {
			gameEnd();
		}

		int[] foodPosition = foodPosition();
		food = new Food(foodPosition[0], foodPosition[1]);

		// Food eaten
		if (getSnake().xCoor == xCoorFood && getSnake().yCoor == yCoorFood) {
			getSnake().newBody();
			isEaten = true;
			try {
				// Making sure that you see that he opens the mouth.
				Thread.sleep(60);
			} catch (InterruptedException e) {
				System.out.println(e + " Exception from GameField controlObjects() 1");
			}
		}

		// the longer the snake - the fastest it gets
		speed = speed_copy - snake.snakeSize / 2;

		// slowing down movement of the snake
		try {
			Thread.sleep(speed);
		} catch (InterruptedException e) {
			System.out.println(e + " Exception from GameField controlObjects() 2");
		}
	}

	/**
	 * Method responsible for creating a new position of the food.
	 * 
	 * @return an array of two integers with new food position.
	 */
	private int[] foodPosition() {

		if (isEaten == true) {
			xCoorFood = r.nextInt(square / gridSize - 1) * gridSize + surrounding;
			yCoorFood = r.nextInt(square / gridSize - 1) * gridSize + surrounding;
			// Not allowing a food creation inside a body of a snake.
			for (SnakeBodyPart sbp : getSnake().snake) {
				if (sbp.getxCoor() == xCoorFood && sbp.getyCoor() == yCoorFood) {
					foodPosition();
				}
			}
			isEaten = false;
		}
		int[] foodPosition = { xCoorFood, yCoorFood };
		return foodPosition;
	}

	// ----------------------PAINTING METHODS---------------------
	public void paintComponent(Graphics g) {

		super.paintComponent(g);

		// draw food
		food.draw(g);

		// draw snake
		for (int i = 0; i < getSnake().snake.size(); i++) {
			getSnake().snake.get(i).draw(g);
		}
	}

	// --------------------GETTERS and SETTERS--------------------
	public Snake getSnake() {
		return snake;
	}

	public void setSnake(Snake snake) {
		this.snake = snake;
	}

	public static void setSpeed(int speed) {
		GameField.speed = speed;
		speed_copy = speed;
		if (speed == SharedVariables.LEVEL_EASY) {
			SharedVariables.LEVEL_MULT = 2;
		} else if (speed == SharedVariables.LEVEL_MEDIUM) {
			SharedVariables.LEVEL_MULT = 3;
		} else if (speed == SharedVariables.LEVEL_HARD) {
			SharedVariables.LEVEL_MULT = 4;
		}
	}

	public static int getSpeed() {
		return speed;
	}

}
