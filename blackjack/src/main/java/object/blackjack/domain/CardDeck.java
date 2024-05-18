package object.blackjack.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * Created by rius0918@gmail.com on 2024. 5. 26.
 * Blog : http://coasis.tistory.com
 * Github : https://github.com/Ssspil
 */
public class CardDeck {
    private Stack<Card> cardDeck;

    public Stack<Card> getCardDeck() {
        return cardDeck;
    }

    public CardDeck(Card card) {
        List<Card> cards = new ArrayList<>();
        // 카드 덱에 52장의 카드 추가
        for (Card.Pattern pattern : card.getPattern().values()) {
            for (Card.Denomination denomination : card.getDenomination().values()) {
                cards.add(new Card(pattern, denomination));
            }
        }
        cardDeck = new Stack<>();
        cardDeck.addAll(cards);
        shuffle();
    }

    private void shuffle(){
        Collections.shuffle(cardDeck);
    }

    public Card getCard(){
        return cardDeck.pop();
    }
}
