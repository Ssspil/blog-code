package object.blackjack.domain;

/**
 * Created by rius0918@gmail.com on 2024. 5. 26.
 * Blog : http://coasis.tistory.com
 * Github : https://github.com/Ssspil
 */
public class Card {

    private Pattern pattern;
    private Denomination denomination;

    public Card(Pattern pattern, Denomination denomination) {
        this.pattern = pattern;
        this.denomination = denomination;
    }
    enum Denomination {  // 카드 숫자
        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6),
        SEVEN(7), EIGHT(8), NINE(9), TEN(10),
        JACK(10), QUEEN(10), KING(10), ACE(1, 11);

        public final int[] values;

        Denomination(int ... value) {
            this.values = value;
        }

        public int[] getValues() {
            return values;
        }
    }

    enum Pattern {   // 카드 패턴
        DIAMOND, HEART, CLUB, SPADE
    }

    public Pattern getPattern() {
        return pattern;
    }

    public Denomination getDenomination() {
        return denomination;
    }

}
