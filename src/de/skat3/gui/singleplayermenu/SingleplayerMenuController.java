package de.skat3.gui.singleplayermenu;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * Class to control the corresponding view file.
 * 
 * @author tistraub
 */
public class SingleplayerMenuController {

	public void startSinglePlayerGame() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setTitle("Information Dialog");
		alert.setContentText("SinglePlayer Game started!");

		alert.showAndWait();
	}
	
	public void startTrainingMode() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText(null);
		alert.setTitle("Information Dialog");
		alert.setContentText("TrainingMode started!");

		alert.showAndWait();
	}
	
}
