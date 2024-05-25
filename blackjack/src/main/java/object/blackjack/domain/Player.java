package object.blackjack.domain;

import java.util.List;

/**
 * Created by rius0918@gmail.com on 2024. 5. 26.
 * Blog : http://coasis.tistory.com
 * Github : https://github.com/Ssspil
 */
public interface Player {
    void draw(CardDeck cardDeck);
    List<Card> getHaveCards();
    String getName();
}

