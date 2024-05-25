package object.blackjack.domain;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractPlayer implements Player {
    protected List<Card> haveCards = new ArrayList<>();
    protected String name;
    protected int score = 0;

    public AbstractPlayer(String name) {
        this.name = name;
    }

    @Override
    public List<Card> getHaveCards() {
        return haveCards;
    }

    @Override
    public String getName() {
        return name;
    }

    // 카드 뽑기
    @Override
    public void draw(CardDeck cardDeck) {
        Card card = cardDeck.getCard();
        haveCards.add(card);
    }

}

