package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.SharedVariables;

/**
 * Panel displaying points gained during the game
 */
public class PanelBottom extends JPanel {

	private static final long serialVersionUID = 1L; // default
	private static JLabel lenghtNum;
	private static JLabel pointsNum;
	private Color textColor;
	private int blockSize;

	/**
	 * Class constructor
	 */
	public PanelBottom() {

		textColor = SharedVariables.TEXT_COLOR;
		blockSize = SharedVariables.BLOCK_SIZE;

		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		JPanel lenPanel = new JPanel();
		lenPanel.setLayout(new FlowLayout());
		lenPanel.setOpaque(false);

		JLabel lenghtText = new JLabel("Snake lenght: ");
		makeText(lenghtText);
		lenPanel.add(lenghtText);

		lenghtNum = new JLabel();
		lenghtNum.setText("004");
		makeText(lenghtNum);
		lenPanel.add(lenghtNum);

		JPanel pointsPanel = new JPanel();
		pointsPanel.setLayout(new FlowLayout());
		pointsPanel.setOpaque(false);

		JLabel pointsText = new JLabel("Score: ");
		makeText(pointsText);
		pointsPanel.add(pointsText);

		pointsNum = new JLabel();
		pointsNum.setText("000");
		makeText(pointsNum);
		pointsPanel.add(pointsNum);

		add(lenPanel);
		add(pointsPanel);
		add(Box.createRigidArea(new Dimension(0, 4 * blockSize)));

		setOpaque(false);

	}

	private void makeText(JLabel jLabel) {
		jLabel.setForeground(textColor);
		jLabel.setFont(new Font("arial", Font.PLAIN, (int) (blockSize * 2)));
	}

	public static void displayLenght(int snakeSize) {
		String sizeFormatted = String.format("%03d", snakeSize);
		lenghtNum.setText(sizeFormatted);
	}

	public static void displayPoints(int points) {
		String pointsFormatted = String.format("%03d", points);
		pointsNum.setText(pointsFormatted);

	}
}
