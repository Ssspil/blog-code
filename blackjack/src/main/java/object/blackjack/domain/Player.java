package object.blackjack.domain;

public interface Player {
    void draw(CardDeck cardDeck);
    void cardOpen(Rule rule);
}
