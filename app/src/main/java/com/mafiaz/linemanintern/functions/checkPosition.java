package com.mafiaz.linemanintern.functions;

public class checkPosition {
    public static boolean getResult(int position) {
        return (position + 1) % 5 == 0;
    }
}
