package de.skat3.gui.matchfield;

import de.skat3.gamelogic.Card;
import de.skat3.gamelogic.CardDeck;
import de.skat3.gamelogic.Player;
import de.skat3.gamelogic.Suit;
import de.skat3.gamelogic.Value;
import de.skat3.io.profile.Profile;
import de.skat3.main.LocalGameState;
import de.skat3.main.SkatMain;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * TestClass to start the app.
 * 
 * @author Aljoscha Domonell
 *
 */
public class TestClassStart extends Application {
  public static void main(String[] args) {
    launch();
  }

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
      l2.add(new GuiCard(new Card()));
      l3.add(new GuiCard(new Card()));
    }

    m.playerHand.addAll(l1);
    m.leftHand.addAll(l2);
    m.rightHand.addAll(l3);

    // m.showStartButton();
    m.showSkatSelection();
    // m.setCardsPlayable(true);
    // m.bidRequest(50);

    InGameController igc = new InGameController(m);
    Player p = new Player(new Profile("Name"));
    m.leftHand.setPlayer(p);

    Timeline t = new Timeline();
    t.getKeyFrames().add(new KeyFrame(Duration.seconds(3), e -> {
      igc.playCard(p, new Card(Suit.CLUBS, Value.ACE));
    }));
    t.play();

    primaryStage.setScene(m.getScene());
    // primaryStage.setMinWidth(1280);
    // primaryStage.setMinHeight(720);
    primaryStage.show();
  }
}
