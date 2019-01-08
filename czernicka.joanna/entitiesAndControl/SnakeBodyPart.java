package entitiesAndControl;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

import main.SharedVariables;

/**
 * Class responsible for snake body creation
 * 
 * @author joanna czernicka
 *
 */
public class SnakeBodyPart {

	private int gridSize;

	private int xCoor;
	private int yCoor;

	private GameField gameField;

	private Image bodyImage;
	protected Image headDown;
	private ImageIcon headDownII;

	// -------------------------------------------------------
	/**
	 * Class constructor
	 * 
	 * @param xCoor     x position of snake's head
	 * @param yCoor     y position of snake's head
	 * @param tileSize  length of snake's body
	 * @param bodyImage the image of created body part
	 */
	public SnakeBodyPart(int xCoor, int yCoor, int tileSize, Image bodyImage) {

		this.xCoor = xCoor;
		this.yCoor = yCoor;
		gridSize = SharedVariables.gridSize;

		try {
			this.bodyImage = bodyImage.getScaledInstance((int) (gridSize * 2), (int) (gridSize * 2),
					Image.SCALE_SMOOTH);
		} catch (Exception e) {
			System.out.println(e + "Exception form SnakeBodyPart constructor");
		}
	}

	// ----------PAINTING METHODS-----------------------------------------

	public void draw(Graphics g) {
		// loadPictures();
		try {
			headDownII = new ImageIcon(bodyImage);
			headDownII.paintIcon(gameField, g, xCoor, yCoor);
		} catch (Exception e) {
			System.out.println(e + "Exception from SnakeBodyPart draw() method");
		}
	}

	// ------- getters and setters----------------
	public int getxCoor() {
		return xCoor;
	}

	public int getyCoor() {
		return yCoor;
	}

	public void setxCoor(int xCoor) {
		this.xCoor = xCoor;
	}

	public void setyCoor(int yCoor) {
		this.yCoor = yCoor;
	}

}
