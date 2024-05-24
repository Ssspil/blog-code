package object.blackjack;

import object.blackjack.domain.*;

import java.util.Scanner;


/**
 * Created by rius0918@gmail.com on 2024. 5. 26.
 * Blog : http://coasis.tistory.com
 * Github : https://github.com/Ssspil
 */
public class Game {
    private Rule rule;
    private Dealer dealer;
    private Gamer gamer;
    private CardDeck cardDeck;

    public Game(Rule rule, Dealer dealer, Gamer gamer, CardDeck cardDeck) {
        this.rule = rule;
        this.dealer = dealer;
        this.gamer = gamer;
        this.cardDeck = cardDeck;
    }

    // 게임 시작
    public void start() {
        Scanner scanner = new Scanner(System.in);
        String input = "";

        while (!input.equalsIgnoreCase("exit")) {
            play();
            System.out.print("게임을 계속하시겠습니까? (계속하려면 아무 키나 누르세요, 종료하려면 'exit' 입력): ");
            input = scanner.nextLine();
            reset();
        }
        System.out.println("게임을 종료합니다.");
    }

    // 카드 리셋
    private void reset() {
        // 게임 재설정을 위해 카드 덱과 플레이어의 카드 목록 초기화
        this.cardDeck = new CardDeck();
        dealer.getHaveCards().clear();
        gamer.getHaveCards().clear();
    }

    // 게임 플레이
    public void play() {
        System.out.println("================ Blackjack Game START ================");

        // 초기 카드 분배
        initialDeal();

        // 딜러 턴
        dealerTurn();

        // 게이머 턴
        gamerTurn();

        // 승자 결정과 카드 점수 출력
        gameFinish(dealer, gamer);

    }

    // 초기 카드 분배
    private void initialDeal() {
        // 딜러와 게이머에게 각각 두 장의 카드를 분배
        dealer.draw(cardDeck);
        dealer.draw(cardDeck);
        gamer.draw(cardDeck);
        gamer.draw(cardDeck);
    }

    // 딜러 턴
    private void dealerTurn() {
        // 딜러는 점수가 17 이상이 될 때까지 카드를 받음
        while (rule.calculateScore(dealer.getHaveCards()) < 17) {
            dealer.draw(cardDeck);
        }
        System.out.println("딜러의 카드 한장은 : " + dealer.getHaveCards().get(0));
    }

    // 플레이어 턴
    private void gamerTurn() {
        Scanner scanner = new Scanner(System.in);
        boolean continueDrawing = true;

        while (continueDrawing && rule.calculateScore(gamer.getHaveCards()) <= rule.getBLACKJACK_SCORE()) {
            System.out.println("현재 나의 숫자는 : " + rule.calculateScore(gamer.getHaveCards()) +" 이며 " + gamer.getHaveCards().size() + "장 있습니다.");
            System.out.print("카드를 더 받으시겠습니까? (y/n): ");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("y")) {
                gamer.draw(cardDeck);
            } else {
                continueDrawing = false;
            }
        }
    }

    // 게임 승자 결정과 console 출력
    private void gameFinish(Dealer dealer, Gamer gamer){
        String winner = rule.getWinner(dealer, gamer);
        String msg = dealer.getName() + "의 카드 점수는 " + rule.calculateScore(dealer.getHaveCards()) + "이고 " + gamer.getName() + "의 카드 점수는 " + rule.calculateScore(gamer.getHaveCards())+" 입니다.";

        System.out.println(msg);
        System.out.println("따라서 승리자는 " + winner);
        System.out.println("현재 나의 전적 : " + rule.getVICTORY_GAMER() + "승 , " + rule.getVICTORY_DEALER() + "패");
    }
}
