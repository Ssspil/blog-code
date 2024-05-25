package object.blackjack.domain;

import object.blackjack.exception.DeckEmptyException;

import java.util.Collections;
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

    public CardDeck() {
        cardDeck = new Stack<>();
        init();
        shuffle();
    }

    // 트럼트 카드 세팅
    private void init(){
        // 카드 덱에 52장의 카드 추가
        for (Card.Pattern pattern : Card.Pattern.values()) {
            for (Card.Denomination denomination : Card.Denomination.values()) {
                cardDeck.add(new Card(pattern, denomination));
            }
        }
    }

    // 카드 섞기
    private void shuffle(){
        Collections.shuffle(cardDeck);
    }

    // 카드 얻기
    public Card getCard(){
        if(cardDeck.isEmpty()){
            throw new DeckEmptyException("카드를 다 뽑았습니다.");
        } else {
            return cardDeck.pop();
        }
    }
}
