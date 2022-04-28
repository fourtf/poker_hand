package com.example.poker_hand;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class PokerHandTest {
    PokerHand tmp = PokerHand.of("2H", "3H", "4H", "5H", "7H");
    PokerHand badHand = PokerHand.of("2H", "3H", "4S", "5S", "7H");

    @Test
    void draw() {
        var random = new Random(1234);

        for (int i = 0; i < 3; i++) {
            var cards1 = Cards.randomHandCards(random);
            var cards2 = Arrays.asList(cards1);
            Collections.shuffle(cards2, random);

            assertEquals(0, new PokerHand(cards1).compareTo(new PokerHand(cards2.toArray(Card[]::new))));
        }
    }

    @Test
    void highCard() {
        var hand1 = PokerHand.of("5H", "7H", "9S", "KC", "QD");
        var hand2 = PokerHand.of("2H", "3H", "4S", "5S", "8H");

        assertTrue(hand1.compareTo(hand2) > 0);
    }

    @Test
    void pair() {
        // double 6H
        var sixes = PokerHand.of("2H", "3H", "4S", "6H", "6H");
        assertEquals(PokerHand.Ranking.Pair, sixes.ranking());
        assertTrue(sixes.compareTo(badHand) > 0);

        // double 7H
        var sevens = PokerHand.of("2H", "3H", "4S", "7H", "7H");
        assertEquals(PokerHand.Ranking.Pair, sevens.ranking());
        assertTrue(sixes.compareTo(sevens) < 0);
    }

    @Test
    void sortMany() {
        var random = new Random(1234);

        var handRepresentations = new String[][]{
                // high card
                new String[]{ "2H", "3S", "4D", "5C", "7C" },
                new String[]{ "2H", "3S", "4D", "6C", "7C" },
                new String[]{ "4H", "5S", "6D", "TC", "KC" },
                new String[]{ "3H", "4S", "JD", "QC", "KC" },
                // pair
                new String[]{ "5H", "6C", "6C", "TC", "QS" },
                new String[]{ "5H", "6C", "6C", "TC", "KS" },
                new String[]{ "5H", "7C", "7C", "TC", "JS" },
                // double pair
                new String[]{ "5H", "5C", "6C", "6C", "QS" },
                new String[]{ "5H", "5C", "6C", "6C", "KS" },
                new String[]{ "6H", "6C", "7C", "7C", "JS" },
                // three of a kind
                new String[]{ "5H", "6C", "6C", "6C", "QS" },
                new String[]{ "5H", "6C", "6C", "6C", "KS" },
                new String[]{ "6H", "7C", "7C", "7C", "JS" },
                // straight
                new String[]{ "2H", "3S", "4D", "5C", "6C" },
                new String[]{ "3H", "4S", "5D", "6C", "7C" },
                new String[]{ "TH", "JS", "QD", "KC", "AC" },
                // flush
                new String[]{ "2S", "3S", "4S", "5S", "TS" },
                new String[]{ "3H", "4H", "5H", "6H", "TH" },
                new String[]{ "2C", "3C", "4C", "JC", "QC" },
                // full house
                new String[]{ "5H", "5C", "5C", "6C", "6S" },
                new String[]{ "5H", "5C", "6C", "6C", "6S" },
                new String[]{ "6H", "6C", "7C", "7C", "7S" },
                // four of a kind
                new String[]{ "5H", "5C", "5C", "5C", "QS" },
                new String[]{ "5H", "5C", "5C", "5C", "KS" },
                new String[]{ "2S", "6H", "6C", "6C", "6C" },
                // straight flush
                new String[]{ "2S", "3S", "4S", "5S", "6S" },
                new String[]{ "3H", "4H", "5H", "6H", "7H" }
        };

        var handsSorted = Arrays.stream(handRepresentations).map(PokerHand::of).toArray();

        // Shuffle hands and cards in hands.
        var handsShuffled = Arrays.stream(handRepresentations).map((repr) -> {
                    var shuffled = Arrays.asList(repr);
                    Collections.shuffle(shuffled, random);
                    return PokerHand.of(shuffled.toArray(String[]::new));
                })
                .collect(Collectors.toList());
        Collections.shuffle(handsShuffled, random);
        Collections.sort(handsShuffled);

        // Check all
        assertArrayEquals(handsSorted, handsShuffled.toArray());
    }
}
