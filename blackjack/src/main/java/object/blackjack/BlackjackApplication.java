package object.blackjack;

import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by rius0918@gmail.com on 2024. 5. 26.
 * Blog : http://coasis.tistory.com
 * Github : https://github.com/Ssspil
 */
@SpringBootApplication
public class BlackjackApplication {
	public static void main(String[] args) {
		Game game = new Game();
		game.play();
	}
}
