package com.example.poker_hand;

import java.util.*;
import java.util.stream.Collectors;
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
        Arrays.sort(clone, (a, b) -> b.rank.value - a.rank.value);
        return clone;
    }

    static int compareRanks(Card[] a, Card[] b) {
        assert a.length == b.length;

        Card[] lhs = sortedDescending(a);
        Card[] rhs = sortedDescending(b);

        for (int i = 0; i < a.length; i++) {
            int comparison = lhs[i].rank.value - rhs[i].rank.value;
            if (comparison != 0) {
                return comparison;
            }
        }

        return 0;
    }

    static boolean areConsecutive(Card[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] == a[i++]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets cards which are not in a pair, triple or quadruple. Sorts the result descending.
     */
    static Card[] getTiebreakers(Map<Card.Rank, Long> groups, Card[] cards) {
        var ranksWithOne = groups.entrySet().stream().filter((entry) -> entry.getValue() == 1).map(entry -> entry.getKey());
        var remaining = ranksWithOne.map((rank) -> Arrays.stream(cards).filter((card) -> card.rank == rank).findFirst().get()).toArray(Card[]::new);
        return sortedDescending(remaining);
    }
}
