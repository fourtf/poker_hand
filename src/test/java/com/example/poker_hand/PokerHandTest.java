package com.example.poker_hand;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class PokerHandTest {
    @Test
    void sameHandTest() {
        var random = new Random(1234);
        var cards1 = Cards.randomHandCards(random);
        var cards2 = Arrays.asList(cards1);
        Collections.shuffle(cards2, random);

        assertEquals(new PokerHand(cards1).compareTo(new PokerHand(cards2.toArray(Card[]::new))), 0);
    }
}
