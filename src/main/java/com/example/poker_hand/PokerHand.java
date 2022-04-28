package com.example.poker_hand;

public class PokerHand implements Comparable<PokerHand> {
    final Card[] cards;

    PokerHand(Card[] cards) {
        this.cards = cards;
    }

    @Override
    public int compareTo(PokerHand o) {
        return 0;
    }
}
