package de.skat3.network.server;

import de.skat3.gamelogic.GameController;
import de.skat3.main.SkatMain;
import de.skat3.network.datatypes.AnswerType;
import de.skat3.network.datatypes.Message;
import de.skat3.network.datatypes.MessageAnswer;
import de.skat3.network.datatypes.SubType;
/**
 * ServerNetwork > thisClass > Logic
 * @author Jonas
 *
 */
public class GameLogicHandler {
	
	GameController gc;

	public GameLogicHandler(GameController gc) {
		// TODO Auto-generated constructor stub
		this.gc = gc;
	}

	public void handleAnswer(Message m) {
		// TODO Auto-generated method stub

		AnswerType at = (AnswerType) m.getSubType();
		switch (at) {
		case BID_ANSWER:
			bidHandler(m);
			break;
		case CONTRACT_ANSWER:
			contractHandler(m);
			break;
		case HAND_ANSWER:
			handHandler(m);
			break;
		case PLAY_ANSWER:
			playHandler(m);
			break;
		case THROW_ANSWER:
			skatHandler(m);
			break;
		case GAME_ANSWER:
			gameHandler(m);
			break;
		case MATCH_ANSWER:
			break;
		case ROUND_ANSWER:
			break;
		default:
			throw new AssertionError();
		}
		
	

	}

	private void bidHandler(Message m) {
		// TODO Auto-generated method stub
	MessageAnswer ma = (MessageAnswer) m;
	boolean accept = (boolean) ma.payload;
	gc.notifyLogicofBid(accept);
		
		
	}

	private void contractHandler(Message m) {
		// TODO Auto-generated method stub
		MessageAnswer ma = (MessageAnswer) m;
		Object pay = ma.payload;
		gc.notifyLogicofContract(null, null); //FIXME
	}

	private void handHandler(Message m) {
		// TODO Auto-generated method stub
		MessageAnswer ma = (MessageAnswer) m;
	}

	private void playHandler(Message m) {
		// TODO Auto-generated method stub
		MessageAnswer ma = (MessageAnswer) m;
	}

	private void skatHandler(Message m) {
		// TODO Auto-generated method stub
		MessageAnswer ma = (MessageAnswer) m;
	}

	private void gameHandler(Message m) {
		// TODO Auto-generated method stub
		MessageAnswer ma = (MessageAnswer) m;
	}

}
