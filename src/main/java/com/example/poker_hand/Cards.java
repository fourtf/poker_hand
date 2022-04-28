package com.example.poker_hand;

import java.util.*;
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

        for (int i = 0; i < a.length; i++) {
            int comparison = a[i].rank.value - b[i].rank.value;
            if (comparison != 0) {
                return comparison;
            }
        }

        return 0;
    }

    static boolean areConsecutive(Card[] a) {
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i].rank.value != a[i+1].rank.value + 1) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets cards which are not in a pair, triple or quadruple. Sorts the result descending.
     */
    static Card[] notInGroups(Map<Card.Rank, Long> groups, Card[] cards) {
        var ranksWithOne = groups.entrySet().stream().filter((entry) -> entry.getValue() == 1).map(entry -> entry.getKey());
        var remaining = ranksWithOne.map((rank) -> Arrays.stream(cards).filter((card) -> card.rank == rank).findFirst().get()).toArray(Card[]::new);
        return sortedDescending(remaining);
    }

    static Card[] inGroups(Map<Card.Rank, Long> groups, Card[] cards) {
        var ranksWithoutOne = groups.entrySet().stream().filter((entry) -> entry.getValue() != 1).map(entry -> entry.getKey());
        var remaining = ranksWithoutOne.map((rank) -> Arrays.stream(cards).filter((card) -> card.rank == rank).findFirst().get()).toArray(Card[]::new);
        return sortedDescending(remaining);
    }

    /**
     * Tiebreakers are compared before non-tiebreakers.
     */
    static Card[] sortedTiebreakersAndNonTiebreakers(Map<Card.Rank, Long> groups, Card[] cards) {
        var tiebreakers = new ArrayList<Card>();
        tiebreakers.addAll(List.of(sortedDescending(Cards.inGroups(groups, cards))));
        tiebreakers.addAll(List.of(Cards.notInGroups(groups, cards)));
        return tiebreakers.toArray(Card[]::new);
    }

    static boolean hasNTuple(Map<Card.Rank, Long> groups, int n) {
        return getNTupleCount(groups, n) > 0;
    }

    static long getNTupleCount(Map<Card.Rank, Long> groups, int n) {
        return groups.values().stream().filter((box) -> box.longValue() == n).count();
    }
}
