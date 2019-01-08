package entitiesAndControl;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.LinkedList;

import gui.PanelBottom;
import main.SharedVariables;

/**
 * The snake singleton.
 * 
 * @author joanna Czernicka
 */
public class Snake {

	private int gridSize;
	private int square;
	private int surrounding;

	private Image headDown;
	private Image headDownDead;
	private Image headDownEat;
	private Image body;

	private SnakeBodyPart sbp;
	ArrayList<SnakeBodyPart> snake;
	int xCoor;
	int yCoor;
	int snakeSize;
	int points;

	private LinkedList<int[]> moves;

	private boolean running = true;

	private volatile static Snake instance;

	// ------------ PRIVATE CONSTRUCTOR------------------------
	// ------------implementing Singleton----------------------

	private Snake() {
		snake = new ArrayList<>();
		gridSize = SharedVariables.gridSize;
		square = SharedVariables.SQUARE;
		surrounding = gridSize * 2;
		loadPictures();
		setMoves(new LinkedList<>());
	}

	public static Snake getInstance() {
		if (instance == null) {
			synchronized (Snake.class) {
				if (instance == null) {
					instance = new Snake();
				}
			}
		}
		return instance;
	}

	// --------------METHODS-------------------------------------

	/**
	 * setting snake's start position
	 */
	void restart() {

		if (snake.size() == 0) {
			snakeSize = 4;
			points = 0;
			PanelBottom.displayPoints(0);
			PanelBottom.displayLenght(snakeSize);
			xCoor = 7 * gridSize;
			yCoor = 4 * gridSize;
			sbp = new SnakeBodyPart(xCoor, yCoor, gridSize, headDown);
			snake.add(sbp);
			for (int i = 1; i < snakeSize; i++) {
				sbp = new SnakeBodyPart(xCoor - i * gridSize, yCoor, gridSize, body);
				snake.add(sbp);
			}
			SharedVariables.x = 1;
			SharedVariables.y = 0;
			running = true;
		}
	}

	/**
	 * Implementing game stop
	 */
	void stop() {
		setRunning(false);
	}

	/**
	 * loading snake and other graphics
	 */
	public void loadPictures() {
		try {
			headDown = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/heads/head_down.png"));
			headDownDead = Toolkit.getDefaultToolkit()
					.getImage(getClass().getResource("/resources/heads/head_down_dead.png"));
			headDownEat = Toolkit.getDefaultToolkit()
					.getImage(getClass().getResource("/resources/heads/head_eat_down.png"));
			body = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/heads/body.png"));
		} catch (Exception e) {
			System.out.println(e + " Exception from GameField - loadPictures()");
		}
	}

	/**
	 * Increasing the size of the snake after the food was eatten.
	 */
	void newBody() {
		sbp = new SnakeBodyPart(xCoor, yCoor, gridSize, headDownEat);
		snake.set(snake.size() - 1, sbp);
		snakeSize++;
		points += SharedVariables.LEVEL_MULT;
		// This is a static method. It should be probably made as an Observer,
		// but it would include more complexity for relatively small thing
		PanelBottom.displayLenght(snakeSize);
		PanelBottom.displayPoints(points);
	}

	/**
	 * Main method controlling snake and food position, snake's movements and game
	 * speed
	 */
	void controlSnake() {
		int x;
		int y;
		// using a queue to to make sure that the snake does not make an in-place
		// U-turn.
		if (getMoves().size() > 0) {
			int[] xy = getMoves().pop();
			x = xy[0];
			y = xy[1];
		} else {
			x = SharedVariables.x;
			y = SharedVariables.y;
		}

		// collision with own body
		for (int i = 1; i < snake.size() - 1; i++) {
			if (xCoor == snake.get(i).getxCoor() && yCoor == snake.get(i).getyCoor()) {
				stop();
			}
		}

		// replacing head with body part, as head moved one field further
		sbp = new SnakeBodyPart(xCoor, yCoor, gridSize, body);
		snake.set(snake.size() - 1, sbp);

		if (isRunning()) {
			// move
			xCoor = xCoor + gridSize * x;
			yCoor = yCoor + gridSize * y;

			// movement definition and crash to the rocks
			// a bit complicated because of rocks position

			// if the snake leaves the field on the right side
			if ((xCoor > square + surrounding - gridSize)
					&& ((yCoor < 0.25 * square + surrounding) || (yCoor >= 0.75 * square + surrounding))) {
				xCoor = surrounding;
			} else if ((xCoor > square + surrounding - gridSize) && (yCoor >= 0.25 * square + surrounding)
					&& (yCoor < 0.75 * square + surrounding)) {
				stop();
			}

			// if the snake leaves the field on the upper side
			if ((yCoor < surrounding) && (xCoor >= 0.4 * square + surrounding)) {
				yCoor = square + surrounding - gridSize;
			} else if ((yCoor < surrounding) && (xCoor < 0.4 * square + surrounding)) {
				stop();
			}

			// if the snake leaves the field on the lower side
			if ((yCoor > square + surrounding - gridSize) && (xCoor >= 0.4 * square + surrounding)) {
				yCoor = surrounding;
			} else if ((yCoor > square + surrounding - gridSize) && (xCoor < 0.4 * square + surrounding)) {
				stop();
			}

			// if the snake leaves the field on the left side
			if ((xCoor < surrounding)
					&& ((yCoor < 0.25 * square + surrounding) || (yCoor >= 0.75 * square + surrounding))) {
				xCoor = square + surrounding - gridSize;
			} else if ((xCoor < surrounding) && (yCoor >= 0.25 * square + surrounding)
					&& (yCoor < 0.75 * square + surrounding)) {
				stop();
			}
		}

		// creating new head on one field further
		if (isRunning()) {
			sbp = new SnakeBodyPart(xCoor, yCoor, gridSize, headDown);
		} else {
			sbp = new SnakeBodyPart(xCoor, yCoor, gridSize, headDownDead);
		}
		snake.add(sbp);

		// removing last element of the snake as it moved already further
		if (snake.size() > snakeSize) {
			snake.remove(0);

		}
	}

	public LinkedList<int[]> getMoves() {
		return moves;
	}

	public void setMoves(LinkedList<int[]> moves) {
		this.moves = moves;
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public void setPoints(int points) {
		this.points = points;
	}

}
