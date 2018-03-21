package de.skat3.gui.singleplayermenu;

import javafx.animation.ScaleTransition;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 * Class to control the corresponding view file.
 * 
 * @author tistraub
 */
public class SingleplayerMenuController {

	private ScaleTransition buttonSizeAnimation;

	/**
	 * .
	 */
	public void startSinglePlayerGame() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setTitle("Information Dialog");
		alert.setContentText("SinglePlayer Game started!");

		alert.showAndWait();
	}

	/**
	 * .
	 */
	public void startTrainingMode() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setTitle("Information Dialog");
		alert.setContentText("TrainingMode started!");

		alert.showAndWait();
	}

	/**
	 * Is starting the animation when the cursor is over a button.
	 * 
	 * @param e
	 *            Event to get the source from.
	 */
	public void handleMouseOverEvent(MouseEvent e) {
		Button source = (Button) e.getSource();
		buttonSizeAnimation = new ScaleTransition();
		buttonSizeAnimation.setDuration(Duration.millis(1));
		buttonSizeAnimation.setNode(source);
		buttonSizeAnimation.setToX(1.1);
		buttonSizeAnimation.setToY(1.1);
		buttonSizeAnimation.play();
	}

	/**
	 * Is starting the animation when the cursor is over a button.
	 * 
	 * @param e
	 *            Event to get the source from.
	 */
	public void handleMouseLeaveEvent(MouseEvent e) {
		Button source = (Button) e.getSource();
		buttonSizeAnimation = new ScaleTransition();
		buttonSizeAnimation.setDuration(Duration.millis(1));
		buttonSizeAnimation.setNode(source);
		buttonSizeAnimation.setToX(1.0);
		buttonSizeAnimation.setToY(1.0);
		buttonSizeAnimation.play();
	}
}
