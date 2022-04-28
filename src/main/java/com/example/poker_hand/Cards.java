package com.example.poker_hand;

import java.util.Collections;
import java.util.Random;
import java.util.stream.Stream;

public class Cards {
    static PokerHand randomHand(Random random) {
        return new PokerHand(Stream.generate(() -> randomCard(random)).limit(5).toArray(Card[]::new));
    }

    static Card[] randomHandCards(Random random) {
        return Stream.generate(() -> randomCard(random)).limit(5).toArray(Card[]::new);
    }

    static Card randomCard(Random random) {
        return new Card(
                Card.Rank.values()[random.nextInt(Card.Rank.values().length)],
                Card.Suit.values()[random.nextInt(Card.Suit.values().length)]
        );
    }
}
