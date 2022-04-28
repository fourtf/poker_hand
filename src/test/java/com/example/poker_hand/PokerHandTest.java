package com.example.poker_hand;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class PokerHandTest {
    PokerHand badHand = PokerHand.of("2H", "3H", "4S", "5S", "6H");

    @Test
    void draw() {
        var random = new Random(1234);

        for (int i = 0; i < 3; i++) {
            var cards1 = Cards.randomHandCards(random);
            var cards2 = Arrays.asList(cards1);
            Collections.shuffle(cards2, random);

            assertEquals(new PokerHand(cards1).compareTo(new PokerHand(cards2.toArray(Card[]::new))), 0);
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
        var sixes = PokerHand.of("2H", "3H", "4H", "6H", "8H");
        assertTrue(sixes.compareTo(badHand) > 0);

        // double 7H
        var sevens = PokerHand.of("2H", "3H", "4H", "7H", "QH");
        assertTrue(sixes.compareTo(sevens) < 0);
    }
}
