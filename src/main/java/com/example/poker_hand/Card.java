package com.example.poker_hand;

import java.util.Arrays;

public class Card {
    public enum Rank {
        _2('2', 0), _3('3', 1), _4('4', 2), _5('5', 3), _6('6', 4),
        _7('7', 5), _8('8', 6), _9('9', 7), _10('T', 8),
        Jack('J', 9), Queen('Q', 10), King('K', 11), Ace('A', 12);

        public final char representation;
        public final int value;

        Rank(char representation, int value) {
            this.representation = representation;
            this.value = value;
        }

        static Rank of(char representation) {
            return Arrays.stream(Rank.values())
                    .filter((suit) -> suit.representation == representation)
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException(String.format("%s is not a valid representation of Value")));
        }


        @Override
        public String toString() {
            return "" + this.representation;
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

        @Override
        public String toString() {
            return "" + this.representation;
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

    @Override
    public String toString() {
        return rank.toString() + suit.toString();
    }
}
