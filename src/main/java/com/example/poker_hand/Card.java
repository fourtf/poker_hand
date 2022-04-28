package com.example.poker_hand;

import java.util.Arrays;

public class Card {
    public enum Rank {
        _2('2'), _3('3'), _4('4'), _5('5'), _6('6'),
        _7('7'), _8('8'), _9('9'), _10('T'),
        Jack('J'), Queen('Q'), King('K'), Ace('A');

        public final char representation;

        Rank(char representation) {
            this.representation = representation;
        }

        static Rank of(char representation) {
            return Arrays.stream(Rank.values())
                    .filter((suit) -> suit.representation == representation)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(String.format("%s is not a valid representation of Value")));
        }
    }

    public enum Suit {
        Clubs('C'), Diamonds('D'), Hearts('H'), Spades('S');

        public final char representation;

        Suit(char representation) {
            this.representation = representation;
        }

        static Suit of(char representation) {
            return Arrays.stream(Suit.values())
                    .filter((suit) -> suit.representation == representation)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(String.format("%s is not a valid representation of Suit")));
        }
    }

    public final Rank rank;
    public final Suit suit;

    Card(Rank rank, Suit suit) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * A Card with suit/rank based on two chars in a string.
     * @param representation Two letters, one representation of Suit (C, D, H, S) and one representation of Value (2, 3, ..., T, J, Q, K, A)
     */
    static Card of(String representation) {
        if (representation.length() != 2) {
            throw new IllegalArgumentException("representation must be of length 2");
        }

        return new Card(Rank.of(representation.charAt(0)), Suit.of(representation.charAt(1)));
    }
}
