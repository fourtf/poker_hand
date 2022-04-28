package com.example.poker_hand;

import java.util.*;
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
    public Ranking ranking() { return this.ranking; }
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

        var allSameSuit = Arrays.stream(cards).allMatch((card) -> cards[0].suit.equals(card.suit));
        var consecutive = Cards.areConsecutive(cards);
        Map<Card.Rank, Long> groups = Arrays.stream(cards).collect(Collectors.groupingBy((card) -> card.rank, Collectors.counting()));

        // Check for all the types of hands in order:
        if (allSameSuit && consecutive) {
            this.ranking = Ranking.StraightFlush;
            this.tiebreakers = new Card[]{ sorted[0] };
            // :(
        } else if (Cards.hasNTuple(groups, 4)) {
            this.ranking = Ranking.FourOfAKind;
            this.tiebreakers = Cards.sortedTiebreakersAndNonTiebreakers(groups, cards);
        } else if (Cards.hasNTuple(groups, 3) && Cards.hasNTuple(groups, 2)) {
            this.ranking = Ranking.FullHouse;
            // TODO: small bug, triple is not checked before double if rank is smaller
            this.tiebreakers = Cards.sortedTiebreakersAndNonTiebreakers(groups, cards);
        } else if (allSameSuit) {
            this.ranking = Ranking.Flush;
            this.tiebreakers = sorted;
        } else if (consecutive) {
            this.ranking = Ranking.Straight;
            this.tiebreakers = new Card[]{ sorted[0] };
        } else if (Cards.hasNTuple(groups, 3)) {
            this.ranking = Ranking.ThreeOfAKind;
            this.tiebreakers = Cards.sortedTiebreakersAndNonTiebreakers(groups, cards);
        } else if (Cards.getNTupleCount(groups, 2) == 2) {
            this.ranking = Ranking.DoublePair;
            this.tiebreakers = Cards.sortedTiebreakersAndNonTiebreakers(groups, cards);
        } else if (Cards.hasNTuple(groups, 2)) {
            this.ranking = Ranking.Pair;
            this.tiebreakers = Cards.sortedTiebreakersAndNonTiebreakers(groups, cards);
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

        return Cards.compareRanks(this.tiebreakers, o.tiebreakers);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PokerHand pokerHand = (PokerHand) o;
        return this.compareTo(pokerHand) == 0;
    }

    @Override
    public int hashCode() {
        throw new Error("not implemented");
    }

    @Override
    public String toString() {
        return Arrays.stream(this.cards).map(Card::toString).sorted().collect(Collectors.joining(" "));
    }
}
