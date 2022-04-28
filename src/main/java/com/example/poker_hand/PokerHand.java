package com.example.poker_hand;

import java.util.Arrays;

public class PokerHand implements Comparable<PokerHand> {
    final Card[] cards;

    PokerHand(Card[] cards) {
        if (cards.length != 5) {
            throw new IllegalArgumentException("hand must contain exactly 5 cards");
        }

        this.cards = cards;
    }

    static PokerHand of(String[] cardRepresentations) {
        return new PokerHand(Arrays.stream(cardRepresentations).map(Card::of).toArray(Card[]::new));
    }

    @Override
    public int compareTo(PokerHand o) {
        return 0;
    }
}
