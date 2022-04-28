package com.example.poker_hand;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class CardsTest {
    Card[] ofStr(String cards) {
        return Arrays.stream(cards.split(" ")).map(Card::of).toArray(Card[]::new);
    }

    @Test
    void sortDescending() {
        var str = "AS KS QS JS TS 9S 8S 7S 6S 5S 4S 3S 2S";

        var expected = ofStr(str);

        var actualList = List.of(ofStr(str));
        Collections.shuffle(actualList);
        var actual = Cards.sortedDescending(actualList.toArray(Card[]::new));

        assertArrayEquals(expected, actual);
    }

    @Test
    void compareRanks() {
        var str1 = "AS KS 6S";
        var str2 = "AS KS QS";
        var str3 = "AS KS 6S";

        assertTrue(Cards.compareRanks(ofStr(str1), ofStr(str2)) < 0);
        assertTrue(Cards.compareRanks(ofStr(str1), ofStr(str3)) == 0);
    }

    @Test
    void areConsecutive() {
        var str1 = "6S 5S 4S";
        var str2 = "AS 5S TS";

        assertTrue(Cards.areConsecutive(ofStr(str1)));
        assertFalse(Cards.areConsecutive(ofStr(str2)));
    }

    // TODO: getTiebreaker
}
