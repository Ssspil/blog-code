package object.blackjack.domain;

import object.blackjack.domain.Card;
import object.blackjack.domain.CardDeck;
import object.blackjack.exception.DeckEmptyException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class BlackjackApplicationTests {

	private CardDeck cardDeck;

	@BeforeEach
	public void setUp() {
		cardDeck = new CardDeck();
	}

	@Test
	@DisplayName("카드가 52장 생성 되는가?")
	public void testCardDeckInitialization() {
		Assertions.assertThat(cardDeck.getCardDeck().size()).isEqualTo(52);
	}

	@Test
	@DisplayName("카드를 52장 뽑은 후 또 카드를 뽑을라면 내가 만들어둔 DeckEmptyException이 발생되는가?")
	public void testGetCardFromEmptyDeck() {
		for (int i = 0; i < 52; i++) {
			cardDeck.getCard();
		}
		Assertions.assertThatThrownBy(() -> cardDeck.getCard())
				.isInstanceOf(DeckEmptyException.class)
				.hasMessage("카드를 다 뽑았습니다.");
	}

	@Test
	@DisplayName("서로 카드 숫자의 합이 21(블랙잭 점수) 일 때 승리자는 게이머인가?")
	public void testWinnerCheck1(){
		Rule rule = new Rule();
		Dealer dealer = new Dealer();
		Gamer gamer = new Gamer("Gamer");

		gamer.getHaveCards().addAll(createBlackjackHand());
		dealer.getHaveCards().addAll(createBlackjackHand());
		String winner = rule.getWinner(dealer, gamer);
		Assertions.assertThat(winner).isEqualTo(gamer.getName());

	}

	@Test
	@DisplayName("서로 카드 숫자의 합이 블랙잭 점수보다 낮으면서 같으면 무승부인가?")
	public void testWinnerCheck2(){
		Rule rule = new Rule();
		Dealer dealer = new Dealer();
		Gamer gamer = new Gamer("Gamer");

		gamer.getHaveCards().addAll(createNonBlackjackHand());
		dealer.getHaveCards().addAll(createNonBlackjackHand());
		String winner = rule.getWinner(dealer, gamer);
		Assertions.assertThat(winner).isEqualTo("무승부");

	}

	@Test
	@DisplayName("내가 카드숫자의 합이 11인데 A(에이스) 카드를 뽑게 되면 11이 아닌 1로 카드 숫자가 증가 되는가?")
	public void testCardScore(){
		Rule rule = new Rule();

		List<Card> cards = new ArrayList<>();
		cards.add(new Card(Card.Pattern.DIAMOND, Card.Denomination.THREE));
		cards.add(new Card(Card.Pattern.CLUB, Card.Denomination.EIGHT));

		int beforeScore = rule.calculateScore(cards);

		Assertions.assertThat(beforeScore).isEqualTo(11);

		cards.add(new Card(Card.Pattern.SPADE, Card.Denomination.ACE));

		// 에이스 카드를 추가한 후 점수 다시 계산
		int afterScore = rule.calculateScore(cards);

		Assertions.assertThat(afterScore).isEqualTo(12);

	}

	// 블랙잭 점수 카드
	private List<Card> createBlackjackHand() {
		List<Card> cards = new ArrayList<>();
		cards.add(new Card(Card.Pattern.SPADE, Card.Denomination.ACE));
		cards.add(new Card(Card.Pattern.HEART, Card.Denomination.TEN));
		return cards;
	}

	// 블랙잭이 아닌 점수 카드
	private List<Card> createNonBlackjackHand() {
		List<Card> cards = new ArrayList<>();
		cards.add(new Card(Card.Pattern.DIAMOND, Card.Denomination.THREE));
		cards.add(new Card(Card.Pattern.CLUB, Card.Denomination.EIGHT));
		return cards;
	}


}
