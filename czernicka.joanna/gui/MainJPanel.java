package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.Box;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import entitiesAndControl.GameField;
import main.SharedVariables;

/**
 * 
 * Main JPanel, where all other sub-panels are positioned Sets background
 * graphic
 * 
 * @author joanna czernicka
 *
 */
public class MainJPanel extends JPanel {

	private static final long serialVersionUID = 1L; // default

	private int blockSize;
	private int gameHeight;
	private int gameWidth;
	private int gridSize;
	private int square;
	private Image image;

	private PanelBottom panelBottom;
	private GameField gameField;

	private static boolean drawHelpGrid;

	// ------------------------------------------------------------------
	/**
	 * Class constructor adds two main panels : game field and lower panel to the
	 * Main JPanel
	 */
	public MainJPanel() {

		blockSize = SharedVariables.BLOCK_SIZE;
		gameHeight = SharedVariables.GAME_HEIGHT;
		gameWidth = SharedVariables.GAME_WIDTH;
		gridSize = SharedVariables.gridSize;
		square = 40 * blockSize;

		panelBottom = new PanelBottom();
		gameField = new GameField();

		create();
		keyBindings();

		setLayout(new BorderLayout());
		add(Box.createRigidArea(new Dimension(4 * blockSize - gridSize * 2, 0)), BorderLayout.WEST);
		add(Box.createRigidArea(new Dimension(0, 10 * blockSize - gridSize * 2)), BorderLayout.NORTH);
		add(gameField, BorderLayout.CENTER);
		add(panelBottom, BorderLayout.SOUTH);

	}

	private void keyBindings() {
		InputMap im = gameField.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, false), "Left");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, false), "Right");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, false), "Up");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, false), "Down");
		ActionMap ap = gameField.getActionMap();
		ap.put("Left", leftAction);
		ap.put("Right", rightAction);
		ap.put("Up", upAction);
		ap.put("Down", downAction);
	}

	// --------------CUSTOM METHODS------------------------------------------

	/**
	 * Creating the JPanel and setting its size
	 */
	public void create() {
		try {
			image = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/background_800.png"));
		} catch (Exception e) {
			System.out.println(e + " Exception from MainJPanel create()");
		}
	}

	public static void setDrawHelpGrid(boolean drawHelpGrid) {
		MainJPanel.drawHelpGrid = drawHelpGrid;
	}

	// --------------PAINTING METHODS----------------------------------------
	/**
	 * Graphics of the main panel
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, gameWidth, gameHeight, this);
		if (drawHelpGrid) {
			drawHelpGrid(g);
		}
	}

	/**
	 * draws helping grid
	 * 
	 * @param g
	 */
	private void drawHelpGrid(Graphics g) {
		g.setColor(Color.darkGray);
		g.drawRect(4 * blockSize, 10 * blockSize, square, square);

		// vertical lines
		for (int i = 1; i < square / gridSize; i++) {
			g.drawLine(i * gridSize + 4 * blockSize, 10 * blockSize, i * gridSize + 4 * blockSize,
					square + 10 * blockSize);
		}

		// horizontal lines
		for (int i = 1; i < square / gridSize; i++) {
			g.drawLine(square + 4 * blockSize, i * gridSize + 10 * blockSize, 4 * blockSize,
					i * gridSize + 10 * blockSize);
		}
	}

	// ----------- OVERRIDE--------------------------------
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(gameWidth, gameHeight);
	}

	// ------IMPLEMENTING ACTIONS FOR KEYS-------------------

	Action leftAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (!(SharedVariables.x == 1 && SharedVariables.y == 0)) {
				SharedVariables.x = SharedVariables.LEFT[0];
				SharedVariables.y = SharedVariables.LEFT[1];
				gameField.getSnake().getMoves().add(SharedVariables.LEFT);
			}
		}
	};
	Action rightAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (!(SharedVariables.x == -1 && SharedVariables.y == 0)) {
				SharedVariables.x = SharedVariables.RIGHT[0];
				SharedVariables.y = SharedVariables.RIGHT[1];
				gameField.getSnake().getMoves().add(SharedVariables.RIGHT);
			}
		}
	};
	Action upAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (!(SharedVariables.x == 0 && SharedVariables.y == 1)) {
				SharedVariables.x = SharedVariables.UP[0];
				SharedVariables.y = SharedVariables.UP[1];
				gameField.getSnake().getMoves().add(SharedVariables.UP);
			}
		}
	};
	Action downAction = new AbstractAction() {
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if (!(SharedVariables.x == 0 && SharedVariables.y == -1)) {
				SharedVariables.x = SharedVariables.DOWN[0];
				SharedVariables.y = SharedVariables.DOWN[1];
				gameField.getSnake().getMoves().add(SharedVariables.DOWN);
			}
		}
	};
}
