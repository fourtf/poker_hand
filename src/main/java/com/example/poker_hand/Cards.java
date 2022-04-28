package com.example.poker_hand;

import java.util.Arrays;
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

    static Card[] sortedDescending(Card[] cards) {
        var clone = cards.clone();
        Arrays.sort(clone, (a, b) -> a.rank.value - b.rank.value);
        return clone;
    }

    static int compareHighCard(Card[] a, Card[] b) {
        Card[] lhs = sortedDescending(a);
        Card[] rhs = sortedDescending(b);

        for (int i = 0; i < 5; i++) {
            int comparison = lhs[i].rank.value - rhs[i].rank.value;
            if (comparison != 0) {
                return comparison;
            }
        }

        return 0;
    }
}
