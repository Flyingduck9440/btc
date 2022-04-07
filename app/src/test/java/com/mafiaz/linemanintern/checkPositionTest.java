package com.mafiaz.linemanintern;

import static org.junit.Assert.*;

import com.mafiaz.linemanintern.functions.checkPosition;

import org.junit.Test;

public class checkPositionTest {

    @Test
    public void everyFivePosition() {
        int[] index = {5, 10, 15, 20, 25};

        for (int i : index) {
            assertTrue(checkPosition.getResult(i-1));
        }
    }

    @Test
    public void zeroPosition() {
        int index = 0;
        assertFalse(checkPosition.getResult(index));
    }

    @Test
    public void negativePosition() {
        int[] index = {-5, -10, -15, -20, -25};

        for(int i : index){
            assertFalse(checkPosition.getResult(i+1));
        }
    }
}