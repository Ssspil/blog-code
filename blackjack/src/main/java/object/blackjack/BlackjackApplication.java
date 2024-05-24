package object.blackjack;

import object.blackjack.domain.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by rius0918@gmail.com on 2024. 5. 26.
 * Blog : http://coasis.tistory.com
 * Github : https://github.com/Ssspil
 */
@SpringBootApplication
public class BlackjackApplication {
	public static void main(String[] args) {

		// 객체 주입
		Game game = new Game(new Rule(), new Dealer(), new Gamer("송파의 타짜"), new CardDeck());
		// 게임 시작
		game.start();
	}
}
