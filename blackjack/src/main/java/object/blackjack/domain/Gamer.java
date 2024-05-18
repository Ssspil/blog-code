package object.blackjack.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rius0918@gmail.com on 2024. 5. 26.
 * Blog : http://coasis.tistory.com
 * Github : https://github.com/Ssspil
 */
public class Gamer implements Player {

    private List<Card> haveCards = new ArrayList<>();
    private String name;
    private int score = 0;

    public Gamer(String name) {
        this.name = name;
    }

    public List<Card> getHaveCards() {
        return haveCards;
    }

    public String getName() {
        return name;
    }

    // 카드 뽑기
    @Override
    public void draw(CardDeck cardDeck) {
        Card card = cardDeck.getCard();
        haveCards.add(card);
    }

    // 카드 오픈
    @Override
    public void cardOpen(Rule rule) {
        score = rule.calculateScore(haveCards);
    }
}
