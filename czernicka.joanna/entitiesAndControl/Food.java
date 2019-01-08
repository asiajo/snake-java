package entitiesAndControl;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

import main.SharedVariables;

/**
 * Class responsible for food creation
 * 
 * @author joanna czernicka
 */
public class Food {

	private int gridSize;

	private int xCoor;
	private int yCoor;

	private GameField gameField;

	private Image food;
	private Image foodScaled;
	private ImageIcon foodIcon;

	// -------------------------------------------------------
	/**
	 * Class constructor
	 * 
	 * @param xCoor x position where the food should be placed
	 * @param yCoor y position where the food should be placed
	 */
	public Food(int xCoor, int yCoor) {
		this.xCoor = xCoor;
		this.yCoor = yCoor;

		gridSize = SharedVariables.gridSize;
	}

	// -----PAINTING METHODS----------------------------------------------

	public void draw(Graphics g) {
		food = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/food/mouse.png"));
		foodScaled = food.getScaledInstance((int) (gridSize * 1.3), (int) (gridSize * 1.3), 1);
		foodIcon = new ImageIcon(foodScaled);
		foodIcon.paintIcon(gameField, g, xCoor, yCoor);
	}

}
