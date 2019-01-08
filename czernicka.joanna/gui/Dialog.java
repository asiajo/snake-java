package gui;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import entitiesAndControl.GameField;
import main.SharedVariables;

public class Dialog {

	/**
	 * Displays a dialogbox with choice of level to play
	 */
	public void chooseLevel() {
		UIManager.put("OptionPane.background", Color.black);
		UIManager.put("Panel.background", Color.black);

		String[] options1 = { "Easy", "Medium", "Hard" };
		ImageIcon welcomeIcon = new ImageIcon(getClass().getResource("/resources/msc/welcome.png"));
		int choice = JOptionPane.showOptionDialog(null, null, "Hello!", JOptionPane.YES_NO_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE, welcomeIcon, options1, null);

		// interpret the user's choice
		if (choice == JOptionPane.YES_OPTION) {
			GameField.setSpeed(SharedVariables.LEVEL_EASY);
			MainJPanel.setDrawHelpGrid(true);
		}
		if (choice == JOptionPane.NO_OPTION) {
			GameField.setSpeed(SharedVariables.LEVEL_MEDIUM);
			MainJPanel.setDrawHelpGrid(false);
		}
		if (choice == JOptionPane.CANCEL_OPTION) {
			GameField.setSpeed(SharedVariables.LEVEL_HARD);
			MainJPanel.setDrawHelpGrid(false);
		}
	}

}
