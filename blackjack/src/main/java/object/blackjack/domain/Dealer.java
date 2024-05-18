package object.blackjack.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rius0918@gmail.com on 2024. 5. 26.
 * Blog : http://coasis.tistory.com
 * Github : https://github.com/Ssspil
 */
public class Dealer implements Player{

    private List<Card> haveCards = new ArrayList<>();
    private String name = "딜러";

    public String getName() {
        return name;
    }

    public List<Card> getHaveCards() {
        return haveCards;
    }

    @Override
    public void draw(CardDeck cardDeck) {
        Card card = cardDeck.getCard();
        haveCards.add(card);
    }

    @Override
    public void cardOpen() {

    }
}
