package de.skat3.gui.matchfield;

import de.skat3.gamelogic.CardDeck;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * TestClass to start the app.
 * 
 * @author Aljoscha Domonell
 *
 */
public class TestClassStart extends Application {
  /*
   * (non-Javadoc)
   * 
   * @see javafx.application.Application#start(javafx.stage.Stage)
   */
  @Override
  public void start(Stage primaryStage) throws Exception {
    Matchfield m = new Matchfield();
    m.setCardsPlayable(true);

    CardDeck d = new CardDeck();

    List<GuiCard> l1 = new ArrayList<GuiCard>();
    List<GuiCard> l2 = new ArrayList<GuiCard>();
    List<GuiCard> l3 = new ArrayList<GuiCard>();

    int n = 10;
    int j = 0;
    for (int i = 0; i < n; i++) {
      l1.add(new GuiCard(d.getCards()[j++]));
      l2.add(new GuiCard(d.getCards()[j++]));
      l3.add(new GuiCard(d.getCards()[j++]));
    }

    m.playerHand.addAll(l1);
    m.leftHand.addAll(l2);
    m.rightHand.addAll(l3);

    m.showSkatSelection();

    primaryStage.setScene(m.getScene());
    // primaryStage.setMinWidth(1280);
    // primaryStage.setMinHeight(720);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch();
  }
}
