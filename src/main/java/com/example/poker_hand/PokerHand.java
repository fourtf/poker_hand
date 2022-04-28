package com.example.poker_hand;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class PokerHand implements Comparable<PokerHand> {
    enum Ranking {
        HighCard(0),
        Pair(1),
        DoublePair(2),
        ThreeOfAKind(3),
        Straight(4),
        Flush(5),
        FullHouse(6),
        FourOfAKind(7),
        StraightFlush(8);

        public final int value;

        Ranking(int ranking) {
            this.value = ranking;
        }
    };

    final Card[] cards;
    private Ranking ranking;
    // Cards compared pair-wise when the ranking is equal.
    private Card[] tiebreakers;

    PokerHand(Card[] cards) {
        if (cards.length != 5) {
            throw new IllegalArgumentException("hand must contain exactly 5 cards");
        }

        this.cards = cards;
        this.calculateRanking(Cards.sortedDescending(this.cards));
    }

    private void calculateRanking(Card[] cards) {
        var sorted = Cards.sortedDescending(cards);

        var allSameSuit = Arrays.stream(cards).map((card) -> card.rank).allMatch(cards[0]::equals);
        var consecutive = Cards.areConsecutive(cards);
        Map<Card.Rank, Long> groups = Arrays.stream(cards).collect(Collectors.groupingBy((card) -> card.rank, Collectors.counting()));

        // Check for all the types of hands in order:
        if (allSameSuit && consecutive) {
            this.ranking = Ranking.StraightFlush;
            this.tiebreakers = new Card[]{ sorted[0] };
        } else if (groups.values().contains(4)) {
            this.ranking = Ranking.FourOfAKind;
            this.tiebreakers = Cards.getTiebreakers(groups, cards);
        } else if (groups.values().contains(3) && groups.values().contains(2)) {
            this.ranking = Ranking.FullHouse;
        } else if (allSameSuit) {
            this.ranking = Ranking.Flush;
            this.tiebreakers = sorted;
        } else if (consecutive) {
            this.ranking = Ranking.Straight;
        } else if (groups.values().contains(3)) {
            this.ranking = Ranking.ThreeOfAKind;
            this.tiebreakers = Cards.getTiebreakers(groups, cards);
        } else if (groups.entrySet().stream().filter((entry) -> entry.getValue() == 2).count() == 2) {
            this.ranking = Ranking.DoublePair;
            this.tiebreakers = Cards.getTiebreakers(groups, cards);
        } else if (groups.values().contains(2)) {
            this.ranking = Ranking.Pair;
            this.tiebreakers = Cards.getTiebreakers(groups, cards);
        } else {
            this.ranking = Ranking.HighCard;
            this.tiebreakers = sorted;
        }
    }

    static PokerHand of(String... cardRepresentations) {
        return new PokerHand(Arrays.stream(cardRepresentations).map(Card::of).toArray(Card[]::new));
    }

    @Override
    public int compareTo(PokerHand o) {
        if (this.ranking != o.ranking) {
            return this.ranking.value - o.ranking.value;
        }

        assert (this.tiebreakers == null && o.tiebreakers == null) || this.tiebreakers.length == o.tiebreakers.length;

        if (this.tiebreakers == null) {
            return 0;
        }

        return Cards.compareRanks(this.cards, o.cards);
    }
}
