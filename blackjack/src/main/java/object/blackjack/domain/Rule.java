package object.blackjack.domain;

import java.util.List;

/**
 * Created by rius0918@gmail.com on 2024. 5. 26.
 * Blog : http://coasis.tistory.com
 * Github : https://github.com/Ssspil
 */
public class Rule {

    private int VICTORY_DEALER = 0;
    private int VICTORY_GAMER = 0;

    private final int BLACKJACK_SCORE = 21;

    public int getVICTORY_DEALER() {
        return VICTORY_DEALER;
    }

    public int getVICTORY_GAMER() {
        return VICTORY_GAMER;
    }

    public int getBLACKJACK_SCORE() {
        return BLACKJACK_SCORE;
    }

    // 점수 계산
    public int calculateScore(List<Card> cards){
        int sum = 0;
        int aceCount = 0;

        // 첫 번째 합계 계산 및 에이스 카운트
        for (Card card : cards) {
            int value = card.getDenomination().getValues()[0];
            if (value == 1) {  // 에이스 카드인 경우
                value = 11;    // 먼저 11로 계산
                aceCount++;
            }
            sum += value;
        }

        // 점수가 21을 초과하면, 에이스를 1로 변환
        while (sum > 21 && aceCount > 0) {
            sum -= 10;  // 에이스 하나를 11에서 1로 변경 (11 - 10 = 1)
            aceCount--;
        }

        return sum;
    }

    // 승리자 판별
    public String getWinner(Dealer dealer, Gamer gamer) {
        int dealerScore = calculateScore(dealer.getHaveCards());    // 딜러 점수
        int gamerScore = calculateScore(gamer.getHaveCards());      // 게이머 점수

        String winner = null;

        if (gamerScore == BLACKJACK_SCORE) {  // 게이머가 블랙잭일 경우
            winner = gamer.getName();
            VICTORY_GAMER++;
        } else if (gamerScore < BLACKJACK_SCORE && dealerScore < gamerScore) { // 블랙잭 점수보다 낮으면서 딜러보다 점수 높을 떄
            winner = gamer.getName();
            VICTORY_GAMER++;
        } else if (BLACKJACK_SCORE < dealerScore && gamerScore < BLACKJACK_SCORE) { // 딜러도 블랙잭 점수 넘었을 떄
            winner = gamer.getName();
            VICTORY_GAMER++;
        } else if (gamerScore < BLACKJACK_SCORE && (gamerScore == dealerScore)){  // 무승부
            winner = "무승부";
        } else {
            winner = dealer.getName();
            VICTORY_DEALER++;
        }

        return winner;
    }
}
